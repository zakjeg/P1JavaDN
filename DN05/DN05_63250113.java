import java.io.*;
import java.util.*;

public class DN05_63250113 { 

    private static final int[][][] SHAPES = {
        { {0,0}, {1,0}, {2,0}, {3,0} }, //0
        { {0,0}, {0,1}, {0,2}, {0,3} }, //1
        { {0,0}, {1,0}, {0,1}, {1,1} }, //2
        { {0,0},{1,0},{2,0},{1,1}}, // 3
        { {0,1},{1,0},{1,1},{1,2}}, // 4
        { {0,1},{1,1},{2,1},{1,0} }, // 5
        { {0,0},{0,1},{0,2},{1,1} }, // 6
        { {0,0},{0,1},{0,2},{1,0} }, // 7
        { {0,0},{1,0},{2,0},{2,1} }, // 8
        { {0,2}, {1,0},{1,1},{1,2} }, // 9
        { {0,0},{0,1},{1,1},{2,1}}, // 10
        { {0,0},{1,0},{1,1},{1,2} }, // 11
        { {0,1},{1,1},{2,1},{2,0} }, // 12
        { {0,0},{0,1},{0,2},{1,2} }, // 13
        { {0,0},{0,1},{1,0},{2,0} }, // 14
        { {0,1},{1,1},{1,0},{2,0} }, // 15
        { {0,0},{0,1},{1,1},{1,2}}, // 16
        { {0,0},{1,0},{1,1},{2,1} }, // 17
        { {0,1},{0,2},{1,0},{1,1} }  // 18
    };

    private static final int[] pieceWidth = new int[19];
    private static final int[][] pieceMinDy = new int[19][];
    private static final int[][] pieceMaxDy = new int[19][];

    static {
        for (int i = 0; i < SHAPES.length; i++) {
            int[][] blocks = SHAPES[i];
            int maxDx = 0;
            for (int[] b : blocks) {
                if (b[0] > maxDx) maxDx = b[0];
            }
            int w = maxDx + 1;
            pieceWidth[i] = w;

            int[] minDy = new int[w];
            int[] maxDy = new int[w];
            Arrays.fill(minDy, Integer.MAX_VALUE);
            Arrays.fill(maxDy, Integer.MIN_VALUE);

            for (int[] b : blocks) {
                int dx = b[0];
                int dy = b[1];
                if (dy < minDy[dx]) minDy[dx] = dy;
                if (dy > maxDy[dx]) maxDy[dx] = dy;
            }

            for (int c = 0; c < w; c++) {
                if (minDy[c] == Integer.MAX_VALUE) {
                    minDy[c] = 0;
                    maxDy[c] = -1;
                }
            }

            pieceMinDy[i] = minDy;
            pieceMaxDy[i] = maxDy;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = br.readLine();
        if (line == null || line.trim().isEmpty()) {
            return;
        }
        int n = Integer.parseInt(line.trim());

        Map<Integer, Integer> height = new HashMap<>();

        for (int i = 0; i < n; i++) {
            line = br.readLine();
            if (line == null) break;
            line = line.trim();
            if (line.isEmpty()) { i--; continue; }

            StringTokenizer st = new StringTokenizer(line);
            int l = Integer.parseInt(st.nextToken()); 
            int x = Integer.parseInt(st.nextToken());

            dropPiece(height, l, x);
        }

        List<Integer> xs = new ArrayList<>();
        for (Map.Entry<Integer,Integer> e : height.entrySet()) {
            if (e.getValue() > 0) {
                xs.add(e.getKey());
            }
        }
        Collections.sort(xs);

        StringBuilder out = new StringBuilder();
        for (int x : xs) {
            out.append(x).append(": ").append(height.get(x)).append('\n');
        }
        System.out.print(out.toString());
    }

    private static void dropPiece(Map<Integer, Integer> height, int pieceIndex, int xStart) {
        int w = pieceWidth[pieceIndex];
        int[] minDy = pieceMinDy[pieceIndex];
        int[] maxDy = pieceMaxDy[pieceIndex];

        int y0 = Integer.MIN_VALUE;
        for (int c = 0; c < w; c++) {
            int x = xStart + c;
            int hCol = height.getOrDefault(x, 0);
            int candidate = hCol - minDy[c];
            if (candidate > y0) {
                y0 = candidate;
            }
        }

        for (int c = 0; c < w; c++) {
            int x = xStart + c;
            int hCol = height.getOrDefault(x, 0);
            int newH = hCol;
            if (maxDy[c] >= 0) { 
                int topHere = y0 + maxDy[c] + 1;
                if (topHere > newH) newH = topHere;
            }
            height.put(x, newH);
        }
    }
}

