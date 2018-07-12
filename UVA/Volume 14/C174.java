import java.util.*;
import java.io.*;

public class C174 {

	private static final double [] D = new double [] {
		100, 50, 20, 10, 5, 2, 1, 0.50, 0.20, 0.10, 0.05
	};

	// Solve the tabulation to hold 300.KW we need to be able to store that
	private static final int MAX_N = 30_000;
	private static final int EXTRA = 2;

	public static final long [][] DP = new long [MAX_N + EXTRA][D.length + EXTRA];

	/**
	 * This is the recursive backtrack solution
	 * This solution uses the minimal parameters I could think of
	 * It's clear to see how this recursion can be converted into the tabular method
	 */
	public static int solve(int n, int i) {
		if (n == 0) {
			// I managed to exactly give back the amount I needed to give back
			return 1;
		} else if (n < 0) {
			// Cash exceeded! This configuration is not valid
			return 0;
		}

		if (i < 0) {
			return 0;
		}

		// If I can't pay with the coin amount (coin is too big)
		if (D[i] > n) {
			// Then decrease the coin
			return solve(n, i - 1);
		} else {
			// Otherwise if I can pay it then i will try to pay it with that coin and also i will try to pay it with 
			// One less denomination (To grab all the configurations)
			return solve(n - D[i], i) + solve(n, i - 1);
		}
	}

	public static void tabulate() {
		int I = D.length;
		int N = MAX_N + EXTRA;

		for (int n = 0; n < N; ++ n) {
			for (int i = 0; i < I; ++ i) {
				DP[n][i] = 0;
			}
		}

		for (int d = 0; d < I; ++ d) {
			DP[0][d] = 1;
		}
		long ignoreCoinCase = 0;
		long takeCoinCase = 0;
		for (int n = 1; n < N; ++ n) {
			for (int i = 0; i < I; ++ i) {
				if (i - 1 >= 0) {
					ignoreCoinCase = DP[n][i - 1];
				} else {
					ignoreCoinCase = 0;
				}
				if (n - D[i] >= 0) {
					takeCoinCase = DP[n - D[i]][i];
				} else {
					takeCoinCase = 0;
				}
				if (D[i] > n) {
					DP[n][i] = ignoreCoinCase;
				} else {
					DP[n][i] = takeCoinCase + ignoreCoinCase;
				}
			}
		}
	}

	public static void main(final String [] args) throws Exception {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		final PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out));

		tabulate();
		String line = "";
		while ((line = reader.readLine()) != null) {
			final int N = Integer.parseInt(line);
			final long ways = DP[N][D.length - 1];
			writer.write(ways + "\n");
		}
		reader.close();
		writer.close();
	}
}