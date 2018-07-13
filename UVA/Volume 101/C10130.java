import java.util.*;
import java.io.*;

public class C10130 {

	/** Notice how max objects in the pdf is 1 <= O <= 1_000 so thats why our max objects need to be 1001 so our iteration
	 * Goes to 1K atleast which is the required
	 * Same happens with weight
	 **/
	private static final int MAX_OBJECTS = 1_001;
	private static final int MAX_WEIGHT = 31;
	private static final int OFFSET = 2;

	private static final int [][] DP = new int [MAX_OBJECTS + OFFSET][MAX_WEIGHT + OFFSET];

	private static int N;
	private static int G;
	private static int [] W;
	private static int [] P;

	/**
	 * This is the recursive backtrack solution
	 * This solution uses the minimal parameters I could think of
	 * It's clear to see how this recursion can be converted into the tabular method
	 */
	public static int solve(final int wp, final int o) {
		if (o >= N) {
			return 0;
		}

		if (W[o] > wp) {
			return solve(wp, o + 1);
		} else {
			return Math.max( solve(wp - W[o], o + 1) + P[o], solve(wp, o + 1) );
		}
	}

	/**
	 * Tabular method 
	 **/
	public static void tabulate() {
		// Fill base cases + reset tabulation
		for (int i = 0; i < MAX_OBJECTS + OFFSET; ++ i) {
			for (int j = 0; j < MAX_WEIGHT + OFFSET; ++ j) {
				DP[i][j] = 0;
			}
		}

		// Tabulate
		for (int i = (N - 1); i >= 0; -- i) { // Object (o)
			for (int j = 1; j < MAX_WEIGHT; ++ j) { // Weight (wp)
				if (W[i] > j) {
					DP[i][j] = DP[i + 1][j];
				} else {
					DP[i][j] = Math.max(DP[i + 1][j - W[i]] + P[i], DP[i + 1][j]);
				}
			}
		}
	}

	public static void main(final String [] args) throws Exception {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		final PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out));
		int T = Integer.parseInt(reader.readLine());
		String [] parts;
		while (T > 0) {
			N = Integer.parseInt(reader.readLine());
			W = new int[N];
			P = new int[N];

			for (int i = 0; i < N; ++ i) {
				parts = reader.readLine().split(" ");
				P[i] = Integer.parseInt(parts[0]);
				W[i] = Integer.parseInt(parts[1]);
			}
			G = Integer.parseInt(reader.readLine());
			int ans = 0;
			int mw = 0;
			tabulate();
			for (int i = 0; i < G; ++ i) {
				mw = Integer.parseInt(reader.readLine());
				ans += DP[0][mw];
			}
			writer.write(ans + "\n");
			-- T;
		}
		reader.close();
		writer.close();
	}
}