import java.io.*;
import java.util.*;

public class GenerateTestsLike0 {

    // Lik 0 -> vodoravna palica širine 4
    static final int[][] SHAPE0 = {
            {0,0}, {1,0}, {2,0}, {3,0}
    };

    public static void main(String[] args) throws Exception {

        int numberOfTests = 20;         // <-- tukaj spremeni koliko testov želiš
        int n = 5;                      // število padcev v enem testu
        Random rnd = new Random();

        for (int t = 1; t <= numberOfTests; t++) {

            List<Integer> xs = new ArrayList<>();

            // Generate random x positions
            for (int i = 0; i < n; i++) {
                int x = rnd.nextInt(21) - 10;  // x ∈ [-10, 10]
                xs.add(x);
            }

            // --------------------------
            // Write testXX.in
            // --------------------------
            String inFile = String.format("test%02d.in", t);
            try (PrintWriter pw = new PrintWriter(inFile)) {
                pw.println(n);
                for (int x : xs) {
                    pw.println("0 " + x);     // l always 0
                }
            }

            // --------------------------
            // Simulate falling blocks
            // --------------------------
            TreeMap<Integer, Integer> height = new TreeMap<>();

            for (int x : xs) {

                // Find the height where block stops
                int dropHeight = 0;
                for (int[] cell : SHAPE0) {
                    int cx = x + cell[0];
                    int currentH = height.getOrDefault(cx, 0);
                    dropHeight = Math.max(dropHeight, currentH);
                }

                // Place the block
                for (int[] cell : SHAPE0) {
                    int cx = x + cell[0];
                    int finalH = dropHeight + 1;
                    height.put(cx, finalH);
                }
            }

            // --------------------------
            // Write testXX.out
            // --------------------------
            String outFile = String.format("test%02d.out", t);
            try (PrintWriter pw = new PrintWriter(outFile)) {
                for (int x : height.keySet()) {
                    pw.println(x + ": " + height.get(x));
                }
            }

            System.out.println("Generated " + inFile + " and " + outFile);
        }
    }
}

