import java.io.*;
import java.util.*;

public class GenerateTests012 {

    // definicije likov 0,1,2 kot sezname (dx, dy)
    static Map<Integer, int[][]> shapes = new HashMap<>();

    static {
        // lik 0: ####
        shapes.put(0, new int[][]{
                {0,0},{1,0},{2,0},{3,0}
        });

        // lik 1: kvadrat 2x2
        shapes.put(1, new int[][]{
                {0,0},{1,0},{0,1},{1,1}
        });

        // lik 2: navpična palica (višina 3)
        shapes.put(2, new int[][]{
                {0,0},{0,1},{0,2}
        });
    }

    public static void main(String[] args) throws Exception {

        int testsPerShape = 10;   // 10 testov za vsak lik
        int n = 7;                // število padcev v enem testu
        Random rnd = new Random();

        int testCounter = 1;

        // Za vsak lik posebej naredimo testsPerShape testov
        for (int l = 0; l <= 2; l++) {

            for (int t = 0; t < testsPerShape; t++) {

                List<int[]> inputData = new ArrayList<>();

                // -------------------------------------
                // GENERIRAJ VHODNE PODATKE (samo lik l)
                // -------------------------------------
                for (int i = 0; i < n; i++) {
                    int x = rnd.nextInt(21) - 10;  // x ∈ [-10,10]
                    inputData.add(new int[]{l, x});
                }

                // -------------------------------------
                // ZAPIŠI testXX.in
                // -------------------------------------
                String inName = String.format("test%02d.in", testCounter);
                try (PrintWriter pw = new PrintWriter(inName)) {
                    pw.println(n);
                    for (int[] a : inputData) {
                        pw.println(a[0] + " " + a[1]);
                    }
                }

                // -------------------------------------
                // SIMULACIJA PADANJA
                // -------------------------------------
                TreeMap<Integer, Integer> height = new TreeMap<>();
                int[][] shape = shapes.get(l);

                for (int[] a : inputData) {
                    int x = a[1];

                    // izračun "drop height"
                    int dropHeight = 0;
                    for (int[] cell : shape) {
                        int cx = x + cell[0];
                        int h = height.getOrDefault(cx, 0);
                        dropHeight = Math.max(dropHeight, h - cell[1]);
                    }

                    // postavitev lika
                    for (int[] cell : shape) {
                        int cx = x + cell[0];
                        int cy = dropHeight + cell[1] + 1;
                        height.put(cx, Math.max(height.getOrDefault(cx, 0), cy));
                    }
                }

                // -------------------------------------
                // ZAPIŠI testXX.out
                // -------------------------------------
                String outName = String.format("test%02d.out", testCounter);
                try (PrintWriter pw = new PrintWriter(outName)) {
                    for (int x : height.keySet()) {
                        pw.println(x + ": " + height.get(x));
                    }
                }

                System.out.printf("Generated %s and %s (only lik %d)%n",
                        inName, outName, l);

                testCounter++;
            }
        }
    }
}

