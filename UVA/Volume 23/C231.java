import java.io.*;
import java.util.*;

public class C231 {
	
	private static int [] V;
	private static int N;
	//private static int [][] DP = new int [1_000][32_767];
	// Since the problem doesn't give any limit on the length im assuming 32k will suffice
	// Remember powers of two allocate easier (2^14)
	private static int [] DP = new int [16383];

	/**
	 * This solution works as intended but the size of the input is so big that it can cause an stackoverflow
	 **/
	public static int solve(final int i, final int top) {
		if (i == N) {
			return 0;
		}

		if (V[i] >= top) {
			return solve(i + 1, top);
		}

		int ans = Math.max(solve(i + 1, V[i]) + 1, solve(i + 1, top));
		return ans;
	}

	/** 
	 * This DP solution requires so much memory that will throw a no more memory exception
	 **/
	/*public static void tabulate() {
		int top = V[0];
		int offset = 5;
		int newN = N + offset;
		int newJ = V[0] + offset;
		// Base cases
		for (int i = 0; i < newN; ++ i) {
			for (int j = 0; j < newJ; ++ j) {
				DP[i][j] = 0;
			}
		}

		for (int i = N - 1; i >= 0; -- i) {
			for (int j = 0; j <= top; j ++) {
				if (V[i] >= j) {
					DP[i][j] = DP[i + 1][j];
				} else {
					DP[i][j] = Math.max(DP[i + 1][V[i]], DP[i + 1][j]);
				}
				
			}
		}
	}*/

	/** 
	 * Suposse solve(i) is defined as the longest decreasing subsequence from i to N
	 * Then clearly the solution is solving for each i and comparing with the max that produces each i
	 **/
	public static int solve(final int i) {
		if (DP[i] != -1) {
			return DP[i];
		}

		int max = 1;
		for (int j = i + 1; j < N; ++ j) {
			// Notice is not strictly decreasing because we can still fire things at height H iff we are at height H
			if (V[j] <= V[i]) {
				// Solve for j (longest decreasing subsequence from j to n)
				// + 1 because the element V[i] itself is a subsequence
				max = Math.max(max, solve(j) + 1);
			}
		}
		DP[i] = max;
		return DP[i];
	}

	public static void main(final String [] args) throws Exception {
		final PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out));
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		final LinkedList<Integer> data = new LinkedList<>();
		int next = -1;
		int test = 0;
		boolean writeNewLine = false;
		while (true) {
			next = Integer.parseInt(reader.readLine().replaceAll(" ", ""));
			// end of main case
			if (next == -1) {
				break;
			}
			if (test > 0) {
				writer.write("\n");
			}
			do {
				data.add(next);
				next = Integer.parseInt(reader.readLine().replaceAll(" ", ""));
			} while (next != -1); // end of test case

			N = data.size();
			V = new int[N];
			for (int i = 0; i < N; ++ i) {
				V[i] = data.poll();
				DP[i] = -1;
			}

			/** Attempt to see which of the starting sequences produces the better result **/
			int max = solve(0);
			for (int i = 1; i < N; ++ i) {
				max = Math.max(max, solve(i));
			}

			/** Print the output **/
			writer.write("Test #" + (++ test) + ":\n");
			writer.write("  maximum possible interceptions: " + (max) + "\n");
		}
		writer.close();
		reader.close();
	}
}