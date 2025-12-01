import java.io.*;
import java.util.*;

public class GenerateTests {

    // ---- DEFINICIJA TETRIS LIKOV 1,2,3 (IZ PDF) ----
    // Vsak lik predstavljen kot seznam celic (dx, dy)
    // Pri padanju gledamo maksimalno dy za vsak x-stolpec.
    static Map<Integer, int[][]> shapes = new HashMap<>();

    static {
        // Lik 1
        shapes.put(1, new int[][]{
                {0,0},{1,0}   // obliko dobiš iz slike v PDF
        });

        // Lik 2
        shapes.put(2, new int[][]{
                {0,0},{0,1},{1,0}
        });

        // Lik 3
        shapes.put(3, new int[][]{
                {0,0},{0,1},{0,2}
        });
    }

    public static void main(String[] args) throws Exception {

        int numberOfTests = 50;     // koliko testnih primerov želiš
        Random rnd = new Random();

        for (int t = 1; t <= numberOfTests; t++) {

            int n = 5;  // število padcev
            List<int[]> inputList = new ArrayList<>();

            // Generiraj .in podatke
            for (int i = 0; i < n; i++) {
                int l = 1 + rnd.nextInt(3);   // {1,2,3}
                int x = rnd.nextInt(11) - 5;  // od -5 do 5
                inputList.add(new int[]{l, x});
            }

            // Zapiši testXX.in
            String inName = String.format("test%02d.in", t);
            try (PrintWriter pw = new PrintWriter(inName)) {
                pw.println(n);
                for (int[] a : inputList) pw.println(a[0] + " " + a[1]);
            }

            // Simulacija padanja in izračun višine stolpcev
            TreeMap<Integer, Integer> height = new TreeMap<>();

            for (int[] fall : inputList) {
                int l = fall[0];
                int x = fall[1];
                int[][] shape = shapes.get(l);

                // Izračunaj kolizijo: za vsak dx ugotovi višino
                int dropHeight = 0;
                for (int[] cell : shape) {
                    int cx = x + cell[0];
                    int h = height.getOrDefault(cx, 0);
                    dropHeight = Math.max(dropHeight, h - cell[1]);
                }

                // Postavi lik na višino dropHeight
                for (int[] cell : shape) {
                    int cx = x + cell[0];
                    int cy = dropHeight + cell[1] + 1;
                    height.put(cx, Math.max(height.getOrDefault(cx, 0), cy));
                }
            }

            // Zapiši testXX.out
            String outName = String.format("test%02d.out", t);
            try (PrintWriter pw = new PrintWriter(outName)) {
                for (int x : height.keySet()) {
                    pw.println(x + ": " + height.get(x));
                }
            }

            System.out.println("Generated " + inName + " and " + outName);
        }
    }
}

