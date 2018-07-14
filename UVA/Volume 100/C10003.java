import java.io.*;
import java.util.*;

public class C10003 {

	private static int N;
	private static int L;

	private static final int MAX_STICK_LENGTH = 999 + 1;
	private static final int MAX_CUTS = 50 + 1;

	private static final int OFFSET = 2;

	private static int [] C = new int [MAX_CUTS];
	private static int [][] DP = new int [MAX_STICK_LENGTH + OFFSET][MAX_STICK_LENGTH + OFFSET];
	private static int [][] DP_2 = new int [MAX_CUTS + OFFSET][MAX_CUTS + OFFSET];

	/**
	 * This was my ATTEMPT 1, note that in this solution my parameters (l and r) have a diferent domain
	 * This domain is the domain of 'the length of the stick' and since the length of the stick can go up to
	 * 1000 meters it is pretty clear that a solution 1000*1000 = 1'000.000 O(n² * N) will not run in the time
	 * Required.
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

	/** 
	 * This works as intended but the value of L (using the Domain of the meters) 
	 * can be as large as 1'000.000 making this O(L² * N) solution not Feasible
	 */
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

	/**
	 * This solution is my ATTEMPT number 2
	 * Note that this solution CHANGED OF DOMAIN TO INDEX WISE RATHER THAN METERS WISE
	 * We are no longer using the length of the stick directly but instead
	 * We are working over the length of the Cuts |C| which reduces the iteration to 50
	 * Rather than 999
	 **/
	public static int attempt2(final int l, final int r) {
		int answer = Integer.MAX_VALUE;
		boolean couldCut = false;
		for (int i = 0; i < N; ++ i) {
			// Check if whether or not this interval can be cut
			if (C[i] > C[l] && C[i] < C[r]) {
				// If it could be cut then cut it
				couldCut = true;
				// So whenever we cut it there will now be two pieces from [l .. C[i]] and from [C[i] .. r]
				// Process those two and add the cost which is (r - l)
				answer = Math.min(answer, (solve(l, i) + solve(i, r) + (C[r] - C[l])));
			}
		}
		// If we couldn't cut anymore (because our interval [l .. r] doesn't contain any of the cuts required)
		// Then we can't do anything else
		if (!couldCut) {
			return 0;
		}
		return answer;
	}

	public static void tabulate2() {
		// Resets the matrix and establish base cases
		// Notice how we only need to clear up until L because we are not using any other element in the matrix
 		for (int i = 0; i < N; ++ i) {
 			for (int j = 0; j < N; ++ j) {
 				DP_2[i][j] = Integer.MAX_VALUE;
 			}
 		}
 		//DP = new int [MAX_STICK_LENGTH + OFFSET][MAX_STICK_LENGTH + OFFSET];
 		// Tabulation (I can either generate all UP TO MAX_STICK_LENGTH or up to L)
 		// But notice how the cuts can change so it is worthless to calculate all up to MAX_STICK_LENGTH
 		for (int l = N - 1; l >= 0; -- l) {
 			for (int r = 0; r < N; ++ r) {
 				boolean couldCut = false;
 				// Notice how our boundary changed (We don't need to process either 0 or L)
 				for (int i = 1; i < N - 1; ++ i) {
 					// Avoid testing a new cut that wont be available
 					if (C[i] >= C[r]) {
 						break;
 					}
 					if (C[i] > C[l] && C[i] < C[r]) {
 						couldCut = true;
 						DP_2[l][r] = Math.min(DP_2[l][r], (DP_2[l][i] + DP_2[i][r] + (C[r] - C[l])));
 					}
 				}
 				// Base case
 				if (!couldCut) {
 					DP_2[l][r] = 0;
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
			// + 2 because we now need to add 0 and L as boundaries to the cuts
			N = Integer.parseInt(reader.readLine()) + 2;
			parts = reader.readLine().split(" ");
			for (int i = 0; i < parts.length; ++ i) {
				C[i + 1] = Integer.parseInt(parts[i]);
			}
			C[0] = 0;
			C[N - 1] = L;
			tabulate2();
			writer.write("The minimum cutting is " + DP_2[0][N - 1] + ".\n");
		}
		writer.close();
		reader.close();
	}
}