import java.io.*;
import java.util.*;

public class C11450 {

	private static final int MAX_MONEY = 200 + 1;
	private static final int MAX_GARMENTS = 20 + 1;
	private static final int MAX_MODELS = MAX_GARMENTS;

	private static int M, C, K;
	public static int [] S;

	public static final int OFFSET = 5;
	public static final int [][] DP = new int [MAX_MONEY + OFFSET][MAX_GARMENTS + OFFSET];
	public static final int [][] P = new int [MAX_GARMENTS + OFFSET][MAX_MODELS + OFFSET];
	private static final int UNSUCCESSFUL_CASE = 2_000_000_000;

	/**
	 * This is the recursive backtrack solution
	 * This solution uses the minimal parameters I could think of
	 * It's clear to see how this recursion can be converted into the tabular method
	 */
	public static int solve(final int m, final int i) {
		// We completed all garments we stop the recursion
		if (i == C) {
			return 0;
		}
		// Attempt to buy all
		boolean couldBuy = false;
		int answer = -Integer.MAX_VALUE;
		for (int j = 0; j < S[i]; ++ j) {
			if (m >= P[i][j]) {
				answer = Math.max(answer, solve(m - P[i][j], i + 1) + P[i][j]);
				couldBuy = true;
			}
		}
		// If we couldn't buy any item for the gargament I then it means we ran out of money!
		// Then accumulate the answer in a negative fashion
		if (!couldBuy) {
			// If the state is invalid then the answer will be -Int.max_value + K where K belongs to the Naturals
			// Making the answer still negative but smaller! 
			return -Integer.MAX_VALUE;
		}

		return answer;
	}

	public static void tabulate() {
		for (int i = 0; i <= M; ++ i) {
			for (int j = 0; j <= C; ++ j) {
				if (j == C) {
					DP[i][j] = 0;
				} else {
					DP[i][j] = -Integer.MAX_VALUE;
				}
			}
		}

		for (int i = 0; i <= M; ++ i) {
			for (int j = C - 1; j >= 0; -- j) {
				for (int k = 0; k < S[j]; ++ k) {
					if (i >= P[j][k]) {
						DP[i][j] = Math.max(DP[i][j], DP[i - P[j][k]][j + 1] + P[j][k]);
					}
				}
			}
		}
	}

	public static void main(final String [] args) throws Exception {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		final PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out));
		int T = Integer.parseInt(reader.readLine());
		while (T > 0) {
			String [] parts = reader.readLine().split(" ");
			M = Integer.parseInt(parts[0]);
			C = Integer.parseInt(parts[1]);
			S = new int[C];
			for (int i = 0; i < C; ++ i) {
				parts = reader.readLine().split(" ");
				K = Integer.parseInt(parts[0]);
				S[i] = K;
				for (int j = 1; j <= K; ++ j) {
					P[i][j - 1] = Integer.parseInt(parts[j]);
				}
			}
			tabulate();

			int ans = DP[M][0];
			if (ans <= 0) {
				writer.write("no solution\n");
			} else {
				writer.write(ans + "\n");
			}

			-- T;
		}
		writer.close();
		reader.close();
	}
}