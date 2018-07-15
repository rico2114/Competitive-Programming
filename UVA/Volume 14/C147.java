import java.util.*;
import java.io.*;

public class C147 {

	// The only tricky part in this problem is how to represent floating point part?
	// Solution: Transform all to cents currency
	// 100 cents = 1 dollar
	private static final int [] D = new int [] {
		10_000, 5_000, 2_000, 1_000, 500, 200, 100, 50, 20, 10, 5
	};

	// 1 usd = 100 cents
	private static final int CONVERSION_RATE = 100;

	// We changed our currency to be in general CENTS
	// 100 cents = 1 dollar
	// Transform all to cents
	private static final int MAX_N = 300 * CONVERSION_RATE; // 300 usd to cents = (300 * CONVERSION_RATE) = 30_000
	private static final int EXTRA = 25;

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
			final int N = (int) (Float.parseFloat(line) * CONVERSION_RATE + 0.5); // Fixing rounding error for some numbers
			// Remember floats are dangerous!!! rounding errors
			
			if (N == 0) {
				break;
			}
			// %Ns; % s (a string); the N means justify to the right with a width of N
			writer.write(String.format("%6s%17s\n", line, DP[N][D.length - 1]));
		}

		writer.close();
		reader.close();
	}
}