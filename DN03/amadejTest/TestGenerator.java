import java.io.*;
import java.util.*;

public class TestGenerator {
    
    // Calculate tiles needed based on the problem description
    // The algorithm: place largest possible tiles first, then recursively fill remainders
    public static long calculateTiles(long h, long w, int k) {
        long maxTileSize = (long)Math.pow(2, k);
        return coverRectangle(h, w, maxTileSize);
    }
    
    private static long coverRectangle(long h, long w, long maxTileSize) {
        // Base case: no area to cover
        if (h == 0 || w == 0) {
            return 0;
        }
        
        // Find the largest tile size that fits in both dimensions
        long tileSize = maxTileSize;
        while (tileSize > h || tileSize > w) {
            tileSize /= 2;
        }
        
        // If no tile fits (shouldn't happen with 1x1 tiles available)
        if (tileSize == 0) {
            return h * w; // Use 1x1 tiles for everything
        }
        
        // Calculate how many tiles of this size fit perfectly
        long tilesInHeight = h / tileSize;
        long tilesInWidth = w / tileSize;
        long tilesUsed = tilesInHeight * tilesInWidth;
        
        // Calculate remaining areas
        long remainderHeight = h % tileSize;
        long remainderWidth = w % tileSize;
        long coveredHeight = tilesInHeight * tileSize;
        long coveredWidth = tilesInWidth * tileSize;
        
        // Recursively cover the three remaining strips:
        // 1. Right strip (full height minus remainder, remainder width)
        long rightStrip = coverRectangle(coveredHeight, remainderWidth, maxTileSize);
        
        // 2. Bottom strip (remainder height, full width minus remainder)
        long bottomStrip = coverRectangle(remainderHeight, coveredWidth, maxTileSize);
        
        // 3. Bottom-right corner (remainder height, remainder width)
        long corner = coverRectangle(remainderHeight, remainderWidth, maxTileSize);
        
        return tilesUsed + rightStrip + bottomStrip + corner;
    }
    
    // Manual verification for the public test cases
    public static void verifyPublicTests() {
        System.out.println("=== VERIFYING PUBLIC TEST CASES ===");
        
        // Test 1: 16x24 with k=3 (max tile 8x8) -> should be 6
        long result1 = calculateTiles(16, 24, 3);
        System.out.println("Test 1: 16x24 k=3 -> " + result1 + " (expected 6) " + (result1 == 6 ? "✓" : "✗"));
        
        // Test 2: 16x29 with k=3 -> should be 26
        long result2 = calculateTiles(16, 29, 3);
        System.out.println("Test 2: 16x29 k=3 -> " + result2 + " (expected 26) " + (result2 == 26 ? "✓" : "✗"));
        
        // Test 3: 22x29 with k=3 -> should be 53
        long result3 = calculateTiles(22, 29, 3);
        System.out.println("Test 3: 22x29 k=3 -> " + result3 + " (expected 53) " + (result3 == 53 ? "✓" : "✗"));
        
        System.out.println();
    }
    
    public static void main(String[] args) {
        try {
            // First verify our algorithm is correct with public tests
            verifyPublicTests();
            
            // Create test files directory if it doesn't exist
            File testsDir = new File("tests");
            if (!testsDir.exists()) {
                testsDir.mkdirs();
                System.out.println("Created 'tests' directory");
            }
            
            // Test case counter
            int testNum = 1;
            
            System.out.println("=== GENERATING TEST FILES ===");
            
            // Public test cases from problem statement (VERIFIED)
            generateTest(testNum++, 16, 24, 3, 6);
            generateTest(testNum++, 16, 29, 3, 26);
            generateTest(testNum++, 22, 29, 3, 53);
            
            // Test cases 4-15: h and w divisible by 2^k (perfect fit)
            generateTest(testNum++, 16, 16, 3);
            generateTest(testNum++, 32, 32, 3);
            generateTest(testNum++, 8, 16, 3);
            generateTest(testNum++, 64, 64, 4);
            generateTest(testNum++, 16, 32, 2);
            generateTest(testNum++, 8, 8, 3);
            generateTest(testNum++, 24, 16, 3);
            generateTest(testNum++, 32, 48, 3);
            generateTest(testNum++, 40, 24, 3);
            generateTest(testNum++, 16, 40, 3);
            generateTest(testNum++, 48, 48, 3);
            generateTest(testNum++, 8, 24, 3);
            generateTest(testNum++, 56, 32, 3);
            generateTest(testNum++, 32, 56, 3);
            generateTest(testNum++, 64, 32, 3);
            
            // Test cases 16-35: h divisible by 2^k, w not necessarily
            generateTest(testNum++, 16, 25, 3);
            generateTest(testNum++, 32, 50, 3);
            generateTest(testNum++, 8, 13, 3);
            generateTest(testNum++, 16, 19, 3);
            generateTest(testNum++, 24, 35, 3);
            generateTest(testNum++, 32, 41, 3);
            generateTest(testNum++, 16, 27, 3);
            generateTest(testNum++, 40, 55, 3);
            generateTest(testNum++, 48, 63, 3);
            generateTest(testNum++, 8, 9, 3);
            generateTest(testNum++, 56, 71, 3);
            generateTest(testNum++, 64, 100, 4);
            generateTest(testNum++, 32, 45, 3);
            generateTest(testNum++, 24, 31, 3);
            generateTest(testNum++, 16, 33, 3);
            generateTest(testNum++, 48, 77, 3);
            generateTest(testNum++, 40, 59, 3);
            generateTest(testNum++, 32, 67, 3);
            generateTest(testNum++, 8, 21, 3);
            generateTest(testNum++, 56, 89, 3);
            
            // Test cases 36-50: no restrictions
            generateTest(testNum++, 17, 23, 3);
            generateTest(testNum++, 19, 27, 3);
            generateTest(testNum++, 13, 19, 3);
            generateTest(testNum++, 21, 31, 3);
            generateTest(testNum++, 25, 37, 3);
            generateTest(testNum++, 11, 13, 3);
            generateTest(testNum++, 27, 43, 3);
            generateTest(testNum++, 33, 47, 3);
            generateTest(testNum++, 15, 17, 3);
            generateTest(testNum++, 29, 41, 3);
            generateTest(testNum++, 23, 29, 3);
            generateTest(testNum++, 31, 39, 3);
            generateTest(testNum++, 37, 53, 3);
            generateTest(testNum++, 41, 59, 3);
            generateTest(testNum++, 43, 61, 3);
            
            // Test cases 51-100: additional varied tests
            // More perfect fits with different k values
            generateTest(testNum++, 128, 128, 5);
            generateTest(testNum++, 256, 256, 6);
            generateTest(testNum++, 4, 4, 2);
            generateTest(testNum++, 2, 2, 1);
            generateTest(testNum++, 32, 64, 4);
            
            // Large values (testing constraints up to 10^9)
            generateTest(testNum++, 1000000, 1000000, 10);
            generateTest(testNum++, 999999999, 999999999, 20);
            generateTest(testNum++, 524288, 524288, 19); // 2^19
            generateTest(testNum++, 1048576, 2097152, 20); // 2^20 x 2^21
            generateTest(testNum++, 100000, 200000, 10);
            
            // h divisible by 2^k, w random
            generateTest(testNum++, 64, 127, 4);
            generateTest(testNum++, 128, 255, 5);
            generateTest(testNum++, 256, 511, 6);
            generateTest(testNum++, 32, 99, 3);
            generateTest(testNum++, 64, 199, 4);
            generateTest(testNum++, 128, 399, 5);
            generateTest(testNum++, 16, 11, 3);
            generateTest(testNum++, 32, 17, 3);
            generateTest(testNum++, 64, 33, 4);
            generateTest(testNum++, 8, 7, 3);
            
            // No restrictions - various combinations
            generateTest(testNum++, 51, 73, 3);
            generateTest(testNum++, 63, 91, 4);
            generateTest(testNum++, 77, 103, 5);
            generateTest(testNum++, 99, 127, 4);
            generateTest(testNum++, 111, 149, 5);
            generateTest(testNum++, 123, 167, 6);
            generateTest(testNum++, 5, 7, 2);
            generateTest(testNum++, 9, 11, 3);
            generateTest(testNum++, 3, 5, 1);
            generateTest(testNum++, 7, 9, 2);
            generateTest(testNum++, 45, 67, 3);
            generateTest(testNum++, 55, 79, 4);
            generateTest(testNum++, 65, 83, 5);
            generateTest(testNum++, 71, 97, 4);
            generateTest(testNum++, 81, 101, 5);
            generateTest(testNum++, 89, 113, 6);
            
            // Edge cases
            generateTest(testNum++, 1, 1, 0); // Single 1x1 tile
            generateTest(testNum++, 1, 1000000, 10); // Very thin
            generateTest(testNum++, 1000000, 1, 10); // Very tall
            generateTest(testNum++, 1, 999999999, 20); // Extreme thin
            generateTest(testNum++, 999999999, 1, 20); // Extreme tall
            generateTest(testNum++, 3, 3, 1); // Small odd square
            generateTest(testNum++, 5, 5, 2); // Small odd square
            generateTest(testNum++, 7, 7, 2); // Small odd square
            generateTest(testNum++, 15, 15, 3); // Medium odd square
            generateTest(testNum++, 31, 31, 4); // Medium odd square
            generateTest(testNum++, 63, 63, 5); // Medium odd square
            generateTest(testNum++, 127, 127, 6); // Large odd square
            generateTest(testNum++, 255, 255, 7); // Large odd square
            generateTest(testNum++, 1023, 1023, 9); // Very large odd square
            
            System.out.println("\n=== SUMMARY ===");
            System.out.println("Generated " + (testNum - 1) + " test files successfully!");
            System.out.println("Test files are in the 'tests' directory.");
            System.out.println("\nTo test your solution, run:");
            System.out.println("  tj.exe DN03_63250104.java tests .");
            
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void generateTest(int num, long h, long w, int k) throws IOException {
        generateTest(num, h, w, k, -1);
    }
    
    private static void generateTest(int num, long h, long w, int k, long expectedAnswer) throws IOException {
        String testIn = String.format("tests/test%02d.in", num);
        String testOut = String.format("tests/test%02d.out", num);
        
        // Calculate answer
        long answer = (expectedAnswer == -1) ? calculateTiles(h, w, k) : expectedAnswer;
        
        // Verify expected answer if provided
        if (expectedAnswer != -1) {
            long calculated = calculateTiles(h, w, k);
            if (calculated != expectedAnswer) {
                System.err.println("WARNING: Test " + num + " calculated=" + calculated + " but expected=" + expectedAnswer);
            }
        }
        
        // Write input file: h w k (single line)
        PrintWriter inWriter = new PrintWriter(new FileWriter(testIn));
        inWriter.println(h + " " + w + " " + k);
        inWriter.close();
        
        // Write output file: just the number (single line)
        PrintWriter outWriter = new PrintWriter(new FileWriter(testOut));
        outWriter.println(answer);
        outWriter.close();
        
        System.out.printf("Test %02d: %dx%d k=%d -> %d tiles%n", num, h, w, k, answer);
    }
}
