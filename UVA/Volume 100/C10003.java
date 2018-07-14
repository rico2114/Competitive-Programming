import java.io.*;
import java.util.*;

// Beware: This is solution is correct BUT doesn't passes on time
// Solution is on line: 42
public class C10003 {

	private static int N;
	private static int L;
	private static int [] C;

	private static final int MAX_STICK_LENGTH = 999 + 1;
	private static final int OFFSET = 2;

	private static int [][] DP = new int [MAX_STICK_LENGTH + OFFSET][MAX_STICK_LENGTH + OFFSET];

	/**
	 * This is the recursive backtrack solution
	 * It's clear to see how this recursion can be converted into the tabular method
	 */
	public static int solve(final int l, final int r) {
		int answer = Integer.MAX_VALUE;
		boolean couldCut = false;
		for (int i = 0; i < N; ++ i) {
			// Check if whether or not this interval can be cut
			if (C[i] > l && C[i] < r) {
				// If it could be cut then cut it
				couldCut = true;
				// So whenever we cut it there will now be two pieces from [l .. C[i]] and from [C[i] .. r]
				// Process those two and add the cost which is (r - l)
				answer = Math.min(answer, (solve(l, C[i]) + solve(C[i], r) + (r - l)));
			}
		}
		// If we couldn't cut anymore (because our interval [l .. r] doesn't contain any of the cuts required)
		// Then we can't do anything else
		if (!couldCut) {
			return 0;
		}
		return answer;
	}

	// This works as intended but the value of L can be as large as 1'000.000 making this O(L² * N) solution not
	// Feasible
	// Think in ways to avoid iterating over L, maybe some way smaller number?
	public static void tabulate() {
		// Resets the matrix and establish base cases
		// Notice how we only need to clear up until L because we are not using any other element in the matrix
 		for (int i = 0; i <= L; ++ i) {
 			for (int j = 0; j <= L; ++ j) {
 				DP[i][j] = Integer.MAX_VALUE;
 			}
 		}
 		//DP = new int [MAX_STICK_LENGTH + OFFSET][MAX_STICK_LENGTH + OFFSET];
 		// Tabulation (I can either generate all UP TO MAX_STICK_LENGTH or up to L)
 		// But notice how the cuts can change so it is worthless to calculate all up to MAX_STICK_LENGTH
 		for (int l = L; l >= 0; -- l) {
 			for (int r = 0; r <= L; ++ r) {
 				boolean couldCut = false;
 				for (int i = 0; i < N; ++ i) {
 					// Break because our C[i] is in increasing order
 					if (C[i] > r) {
 						break;
 					}
 					if (C[i] > l && C[i] < r) {
 						couldCut = true;
 						DP[l][r] = Math.min(DP[l][r], (DP[l][C[i]] + DP[C[i]][r] + (r - l)));
 					}
 				}
 				// Base case
 				if (!couldCut) {
 					DP[l][r] = 0;
 				}
 			}
 		}
	}

	public static void main(final String [] args) throws Exception {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		final PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out));
		String [] parts = null;
		while (true) {
			L = Integer.parseInt(reader.readLine());
			if (L == 0) {
				break;
			}
			N = Integer.parseInt(reader.readLine());
			C = new int[N];
			parts = reader.readLine().split(" ");
			for (int i = 0; i < N; ++ i) {
				C[i] = Integer.parseInt(parts[i]);
			}
			tabulate();
			writer.write("The minimum cutting is " + DP[0][L] + ".\n");
		}
		writer.close();
		reader.close();
	}
}