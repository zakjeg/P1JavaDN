import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Generator 99 testnih primerov za nalogo "Na šejkovem dvoru".
 *
 * Vsak test je v datotekah:
 *   testXX.in  – vhod (n, k, nato n vrstic oznak)
 *   testXX.out – pričakovani izhod (število parov, long)
 *
 * Robni in zanimivi primeri, ki jih pokrijemo:
 *  1) n = 1, k = 2, vrednost 1  -> 1 par
 *  2) n = 1, k = 2000, vrednost 1 -> 0 parov (nemogoča vsota)
 *  3) vse vrednosti enake in k = 2*v -> n^2 parov (tudi > Integer.MAX_VALUE)
 *  4) brez parov (k premajhen / prevelik glede na min/max)
 *  5) unikatne vrednosti + liho k
 *  6) veliko duplikatov v majhnem razponu vrednosti
 *  7) mešani naključni primeri različnih velikosti
 *
 * Omejitve iz besedila:
 *   1 <= n <= 1_000_000,  2 <= k <= 2000,  oznake v [1, 1000]
 * Število parov lahko preseže Integer.MAX_VALUE, zato računamo z long.
 */
public class DN04_TestGenerator {

    private static final Random RNG = new Random(42); // deterministično

    public static void main(String[] args) throws Exception {
        // Ustvarimo 99 testov
        int t = 0;

        // --- 1) Minimalni robni primeri ---
        t++; writeCase(t, fixedArray(new int[]{1}), 2);            // 1 par
        t++; writeCase(t, fixedArray(new int[]{1}), 2000);         // 0 parov
        t++; writeCase(t, fixedArray(new int[]{1000}), 2);         // 0 parov (k premajhen)
        t++; writeCase(t, fixedArray(new int[]{1}), 1999);         // 0 parov (k prevelik)

        // --- 2) Majhni primeri, enostavno preverljivi ---
        t++; writeCase(t, fixedArray(new int[]{6,7,2,10,6}), 12);  // primer iz besedila (6)
        t++; writeCase(t, fixedArray(new int[]{1,2,3,4,5}), 7);    // nekaj parov
        t++; writeCase(t, fixedArray(new int[]{1,2,3,4,5}), 3);    // več parov (v=1<->2, itd.)
        t++; writeCase(t, fixedArray(new int[]{999,1,1000,2,3}), 1001); // pari ob robu

        // --- 3) Unikatne vrednosti + liho k (1–20 v skritih testih) ---
        for (int i = 0; i < 4; i++) {
            int n = 20 + i * 5;
            int[] a = uniqueValues(n, 1, 1000);
            int k = 2 * (100 + i) + 1; // liho
            t++; writeCase(t, a, k);
        }

        // --- 4) Vsi enaki (veliko parov) ---
        t++; writeCase(t, allSame(10, 500), 1000);           // 10^2 = 100
        t++; writeCase(t, allSame(1234, 333), 666);          // 1_522_756 parov
        // primer z rezultatom > Integer.MAX_VALUE (≈ 2.5e9)
        t++; writeCase(t, allSame(50_000, 1000), 2000);

        // --- 5) Brez parov (k izven možnih vsot) ---
        t++; writeCase(t, ramp(100, 1), 1);                  // min vsota = 2
        t++; writeCase(t, ramp(100, 901), 1905);             // max vsota = 1902, torej 0

        // --- 6) Dosti duplikatov v ozkem intervalu (statistika!) ---
        for (int i = 0; i < 6; i++) {
            int n = 5_000 + i * 1_000;
            int[] a = randomValues(n, 450, 550);             // ozko okno -> ogromno zadetkov
            int k = 1000;                                    // v sredini intervala
            t++; writeCase(t, a, k);
        }

        // --- 7) Naključni primeri različnih velikosti in k ---
        for (int i = 0; i < 30; i++) {
            int n = 100 + i * 50;                            // od 100 do ~1_600
            int[] a = randomValues(n, 1, 1000);
            int k = 2 + RNG.nextInt(1999);                   // 2..2000
            t++; writeCase(t, a, k);
        }

        // --- 8) Primeri z velikim n, a še znosnimi datotekami ---
        int[] bigSizes = { 10_000, 20_000, 30_000, 40_000 };
        for (int n : bigSizes) {
            int[] a = randomValues(n, 1, 1000);
            int k = 2 + RNG.nextInt(1999);
            t++; writeCase(t, a, k);
        }

        // --- 9) Konstrukcije, ki ciljajo na različne k in robne vrednosti ---
        t++; writeCase(t, alternating(5000, 1, 999), 1000);  // vsaka z 2500 ponovitev
        t++; writeCase(t, alternating(5001, 1, 1000), 1001); // neenako št. pojavitev
        t++; writeCase(t, checkerboard(6000, 400, 600), 1000);
        t++; writeCase(t, skewedBuckets(8000, new int[]{1,1,1,1,1, 1000,1000, 500,500,500,500}, 1, 1000), 1001);

        // --- 10) Zapolnimo do 99 z dodatnimi raznolikimi randomi ---
        while (t < 99) {
            int n = 200 + RNG.nextInt(4801);                 // 200..5000
            int low = 1 + RNG.nextInt(1000);
            int high = low + RNG.nextInt(1001 - low);
            int[] a = randomValues(n, low, high);
            int k = 2 + RNG.nextInt(1999);
            t++; writeCase(t, a, k);
        }

        System.out.println("Ustvarjenih " + t + " testnih primerov (test01..test99).");
    }

    /* =================== Pomožne konstrukcije sekvenc =================== */

    private static int[] fixedArray(int[] arr) {
        return Arrays.copyOf(arr, arr.length);
    }

    private static int[] allSame(int n, int v) {
        int[] a = new int[n];
        Arrays.fill(a, v);
        return a;
    }

    private static int[] ramp(int n, int start) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = clamp(start + i, 1, 1000);
        return a;
    }

    private static int[] uniqueValues(int n, int lo, int hi) {
        n = Math.min(n, hi - lo + 1);
        List<Integer> vals = new ArrayList<>();
        for (int v = lo; v <= hi; v++) vals.add(v);
        Collections.shuffle(vals, RNG);
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = vals.get(i);
        return a;
    }

    private static int[] randomValues(int n, int lo, int hi) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = lo + RNG.nextInt(hi - lo + 1);
        return a;
    }

    private static int[] alternating(int n, int v1, int v2) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = (i % 2 == 0) ? v1 : v2;
        return a;
    }

    private static int[] checkerboard(int n, int a1, int a2) {
        // 2 vrednosti v blokih dolžine 2 (AA BB AA BB …)
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = ((i/2) % 2 == 0) ? a1 : a2;
        return a;
    }

    private static int[] skewedBuckets(int n, int[] bucketValues, int lo, int hi) {
        // napolnimo z vrednostmi iz bucketValues (močno neuravnotežena porazdelitev)
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            int v = bucketValues[RNG.nextInt(bucketValues.length)];
            a[i] = clamp(v, lo, hi);
        }
        return a;
    }

    private static int clamp(int x, int lo, int hi) {
        return Math.max(lo, Math.min(hi, x));
    }

    /* =================== Računanje števila parov & zapis datotek =================== */

    private static void writeCase(int idx, int[] a, int k) throws IOException {
        String base = String.format("test%02d", idx);
        File in = new File(base + ".in");
        File out = new File(base + ".out");

        long pairs = countPairs(a, k);

        try (PrintWriter pw = new PrintWriter(
                new OutputStreamWriter(new FileOutputStream(in), StandardCharsets.UTF_8))) {
            pw.println(a.length + " " + k);
            for (int v : a) pw.println(v);
        }

        try (PrintWriter pw = new PrintWriter(
                new OutputStreamWriter(new FileOutputStream(out), StandardCharsets.UTF_8))) {
            pw.println(pairs);
        }
    }

    /**
     * Število urejenih parov (i, j), kjer moški dobi a[i], ženska a[j], in a[i] + a[j] = k.
     * Ker je zaporedje pri moških in ženskah enako, gre za kombinacijo s ponovitvami.
     * Računamo z long, saj lahko rezultat preseže Integer.MAX_VALUE.
     */
    private static long countPairs(int[] a, int k) {
        int[] freq = new int[1001]; // vrednosti 1..1000
        for (int v : a) {
            if (v >= 1 && v <= 1000) freq[v]++;
            else throw new IllegalArgumentException("Vrednost izven [1,1000]: " + v);
        }
        long pairs = 0L;
        // Urejeni pari: vsoto seštejemo po vseh v, dopolnilo je (k - v)
        for (int v = 1; v <= 1000; v++) {
            int u = k - v;
            if (u >= 1 && u <= 1000) {
                pairs += (long) freq[v] * (long) freq[u];
            }
        }
        return pairs;
    }
}

