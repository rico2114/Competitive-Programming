import java.util.*;
import java.io.*;

public class C10943 {
	
	private static final int MAX_N = 100 + 1;
	private static final int MAX_K = 100 + 1;
	private static final int OFFSET = 5;
	private static final int [][] DP = new int [MAX_N + OFFSET][MAX_K + OFFSET];

	private static int N;
	private static int K;
	private static final int MODULO = 1_000_000;
	/**
	 * This is the ORIGINAL recursive backtrack solution
	 * It's clear to see how this recursion can be converted into the tabular method
	 * But notice how tabulating this would require extra memory because we have 3 parameters
	 * Which will call to 3 Dimensions (It is also harder to tabulate!)
	 * BEWARE: This solution is not solution % MOD, solution just implies an addition to the answer '% MOD'
	 */
	public static int solve(final int n, final int k, final int i) {
		if (n == 0 && k == 0) {
			return 1;
		}
		if (n < 0 || k < 0 || i < 0) {
			return 0;
		}

		if (n >= i) {
			// Notice how n - i will be the next available i to start decreasing from
			return (solve(n - i, k - 1, n - i) + solve(n, k, i - 1));
		} else {
			return solve(n, k, i - 1);
		}
	}

	/**
	 * This is the IMPROVED recursive backtrack solution
	 * This solution uses the minimal parameters I could think of
	 * It's clear to see how this recursion can be converted into the tabular method
	 * Note how we now have 2 parameters, the time complexity is the same as the above (Cubic) but
	 * the tabulation now requires less memory + it's easier to tabulate
	 * BEWARE: This solution is not solution % MOD, solution just implies an addition to the answer '% MOD'
	 */
	public static int solve(final int n, final int k) {
		if (n == 0|| k == 0) {
			return 1;
		}
		if (n < 0 || k < 0) {
			return 0;
		}

		int answer = 0;
		for (int i = 0; i <= n; ++ i) {
			answer += solve(n - i, k - 1);
		}

		return answer;
	}

	public static void tabulate() {
		for (int n = 0; n < MAX_N; ++ n) {
			for (int k = 0; k < MAX_K; ++ k) {
				DP[n][k] = 0;
			}
		}
		// Base case
		DP[0][0] = 1;
		for (int n = 0; n < MAX_N; ++ n) {
			for (int k = 0; k < MAX_K; ++ k) {
				for (int i = 0; i <= n; ++ i) {
					int newN = n - i;
					int newK = k - 1;
					if (newN >= 0 && newK >= 0) {
						DP[n][k] += DP[newN][newK];
						// The solution was asked to be modulo
						DP[n][k] %= MODULO;
					}
				}
			}
		}
	}

	public static void main(final String [] args) throws Exception {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		final PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out));
		String [] parts = null;
		tabulate();
		while (true) {
			parts = reader.readLine().split(" ");
			N = Integer.parseInt(parts[0]);
			K = Integer.parseInt(parts[1]);
			if (N == 0 && K == 0) {
				break;
			}

			writer.write(DP[N][K] + "\n");
		}
		writer.close();
		reader.close();
	}
} 