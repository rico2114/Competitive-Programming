import java.io.*;
import java.util.*;

public class C10664 {
	
	private static int I;

	private static final int MAX_N = 20;
	private static final int MAX_KILOGRAMS = (200 + 200) * 2; // Add some extra space for failures (out of boundary)
	private static final int OFFSET = 5;
	private static final int RELATIVE_ZERO = 400; // Increment relative zero to avoid failures
	private static final boolean [][] DP = new boolean[MAX_N + OFFSET][MAX_KILOGRAMS + OFFSET];
	private static int MAX_B = 0;
	private static int [] B;

	/**
	 * This solution works as expected but will require me to host twice as big the matrix to have a relative zero
	 * (because arrays cannot go from less than 0)
	 * I preffer this solution over adding a new parameter because the time complexity will increase from O(n²) (current)
	 * To O(n^3)
	 **/
	/**
	 * This is the recursive backtrack solution
	 * This solution uses the minimal parameters I could think of
	 * It's clear to see how this recursion can be converted into the tabular method
	 */
	public static boolean solve(final int i, final int bootsWeightDifference) {
		if (i == I) {
			return bootsWeightDifference == 0;
		}
		return solve(i + 1, bootsWeightDifference + B[i]) || solve(i + 1, bootsWeightDifference - B[i]);
	}

	public static void tabulate() {
		for (int i = 0; i <= I; ++ i) {
			for (int b = (RELATIVE_ZERO - MAX_B); b <= (RELATIVE_ZERO + MAX_B); ++ b) {
				DP[i][b] = false;
			}
		}
		DP[I][RELATIVE_ZERO] = true;
		for (int i = I - 1; i >= 0; -- i) {
			for (int b = (RELATIVE_ZERO - MAX_B); b <= (RELATIVE_ZERO + MAX_B); ++ b) {
				DP[i][b] = DP[i + 1][b + B[i]] || DP[i + 1][b - B[i]];
			}
		}
	}

	public static void main(final String [] args) throws Exception {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		final PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out));
		int T = Integer.parseInt(reader.readLine());
		String [] parts;
		while (T > 0) {
			parts = reader.readLine().split(" ");
			I = parts.length;
			B = new int[I];
			MAX_B = 0;
			for (int i = 0; i < I; ++ i) {
				B[i] = Integer.parseInt(parts[i]);
				MAX_B += B[i];
			}
			tabulate();
			String ans = "NO";
			if (DP[0][RELATIVE_ZERO]) {
				ans = "YES";
			}
			writer.write(ans + "\n");
			-- T;
		}
		writer.close();
		reader.close();
	}
}