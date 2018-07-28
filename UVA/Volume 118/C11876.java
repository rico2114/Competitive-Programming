import java.io.*;
import java.util.*;

public class C11876 {

    private static final int MAX_SEQ = 64 _725 + 1;
    private static final int MAX_DIV = 999 _978 + 1;

    public static int[] SEQUENCE = new int[MAX_SEQ];
    public static final HashMap < Integer, Integer > NOD_PRECALC = new HashMap < Integer, Integer > ();

    private static int A;
    private static int B;

    /**
     * Note that precalculating ever nod value doesn't worth the time
     * Because we are only using up to MAX_SEQ n_i so it is doing over than 920k extra calculations for
     * nothing, better to memoize further solutions to the nod function and calculating the divisors on demand
     **/
    public static int solveNod(final int i) {
        if (NOD_PRECALC.containsKey(i)) {
            return NOD_PRECALC.get(i);
        }

        int solution = 0;
        for (int j = 1; j <= Math.sqrt(i); ++j) {
            if (i % j == 0) {
                if (i / j == j) {
                    // Divisores {j}
                    solution += 1;
                } else {
                    // Divisores {i, n/i}
                    solution += 2;
                }
            }
        }
        NOD_PRECALC.put(i, solution);
        return solution;
    }

    public static void precalculate() {
        NOD_PRECALC.put(0, 0);
        NOD_PRECALC.put(1, 1);
        SEQUENCE[0] = 1;
        SEQUENCE[1] = 2;
        for (int i = 2; i < MAX_SEQ; ++i) {
            SEQUENCE[i] = SEQUENCE[i - 1] + solveNod(SEQUENCE[i - 1]);
        }
    }

    public static int search(int value) {
        if (value < SEQUENCE[0]) {
            return SEQUENCE[0];
        }
        if (value > SEQUENCE[SEQUENCE.length - 1]) {
            return SEQUENCE[SEQUENCE.length - 1];
        }

        int lo = 0;
        int hi = SEQUENCE.length - 1;

        while (lo <= hi) {
            int mid = (hi + lo) / 2;

            if (value < SEQUENCE[mid]) {
                hi = mid - 1;
            } else if (value > SEQUENCE[mid]) {
                lo = mid + 1;
            } else {
                return mid;
            }
        }
        return (SEQUENCE[lo] - value) < (value - SEQUENCE[hi]) ? lo : hi;
    }


    public static void main(final String[] args) throws Exception {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        final PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out));
        precalculate();
        int T = Integer.parseInt(reader.readLine());
        String[] parts = null;
        int tCase = 1;
        while (T > 0) {
            parts = reader.readLine().split(" ");
            A = Integer.parseInt(parts[0]);
            B = Integer.parseInt(parts[1]);
            int l = search(A);
            int r = search(B);
            // Make sure we grab everything correctly
            if (SEQUENCE[l] < A) {
                ++l;
            }
            if (SEQUENCE[r] > B) {
                --r;
            }
            int ans = (r - l) + 1;
            writer.write("Case " + tCase + ": " + ans + "\n");
            ++tCase;
            --T;
        }
        reader.close();
        writer.close();
    }
}