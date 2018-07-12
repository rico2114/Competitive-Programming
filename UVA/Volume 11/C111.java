import java.util.*;
import java.io.*;

public class C111 {

	private static final int MAX_N = 20 + 5;
	private static final int [][] DP = new int [MAX_N][MAX_N];

	private static int N;
	/** Let L be the correct ordering and R be the student ordering **/
	private static int [] L, R;

	/**
	 * This is the recursive backtrack solution
	 * This solution uses the minimal parameters I could think of
	 * It's clear to see how this recursion can be converted into the tabular method
	 */
	public static int solve(int i, int j) {
		// We finished exploiting this recursion
		if (i >= N || j >= N) {
			return 0;
		}
		// If letters match 
		if (L[i] == R[j]) {
			// Then we will increase the longest subsequence and keep looking for more subsequences
			return 1 + solve(i + 1, j + 1);
		} else {
			// If they don't match i increase both indexes options
			return Math.max(solve(i + 1, j), solve(i, j + 1));
		}
	}

	public static int tabulate() {
		// Resetting the tabulation + filling the base cases
		for (int i = 0; i < MAX_N; ++ i) {
			for (int j = 0; j < MAX_N; ++ j) {
				DP[i][j] = 0;
			}
		}
		// Tabulation process
		for (int i = N - 1; i >= 0; -- i) {
			for (int j = N - 1; j >= 0; -- j) {
				if (L[i] == R[j]) {
					DP[i][j] = 1 + DP[i + 1][j + 1];
				} else {
					DP[i][j] = Math.max(DP[i + 1][j], DP[i][j + 1]);
				}
			}
		}
		return DP[0][0];
	}

	public static void main(final String [] args) throws Exception {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		final PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out));
		String line = "";
		String [] parts = null;
		line = reader.readLine();
		int readVal = 0;
		while (line != null) {
			N = Integer.parseInt(line);
			L = new int[N];
			R = new int[N];
			int j = 0;
			while ((line = reader.readLine()) != null && line.contains(" ")) {
				/** No se guarda como A[i] = readVal porque la entrada no es un ordernamiento **/
				/** Al ser un rankeo el proceso es inverso A[readVal] = i **/
				parts = line.split(" ");
				for (int i = 0; i < N; ++ i) {
					readVal = Integer.parseInt(parts[i]);
					if (j == 0) {
						L[readVal - 1] = i;
					} else {
						R[readVal - 1] = i;
					}
				}			

				if (j != 0) {
					// Notice we need to tabulate each time because the answer varies when R changes (each time after L is parsed)
					writer.write(tabulate() + "\n");
				}
				++ j;
			}
		}
		reader.close();
		writer.close();
	}
}