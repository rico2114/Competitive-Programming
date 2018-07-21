import java.io.*;
import java.util.*;
import java.math.*;

public class C10261 {

	private static int F;
	private static int N;
	private static int [] V;
	private static String [] PATH;
	private static int [] MAX;
	private static int [] SUM;

	// Not feasible! too huge
	//private static int [][][] DP = new int[200 + OFFSET][10000 + OFFSET][10000 + OFFSET];

	private static int OFFSET = 5;
	private static int [][] DP = new int[200 + OFFSET][10000 + OFFSET];

	/**
	 * Has collisions!
	 **/
	/*public static int hash(final int i, final int wl, final int wr) {
		int hash = 1;
		hash = hash * 17 + i;
        hash = hash * 31 + wl;
        hash = hash * 13 + wr;
        return hash;
	}*/

	// Notice that USING a DP with these 3 states results in the correct answer
	// BUT  DP ARRAY SOLUTION NOT FEASIABLE BECAUSE OF THE SIZE OF THE ARRAY [I][L][L]
	// But using the key by Objects.hash provides the wrong answer 
	// That MEANS that a collision happened in the hash function
	// Solution: to avoid that collision compute our own hash function (WRONG ANSWER TOO)
	// Solution? DECREASE the parameters cuantity to 2
	// How? Note that we can calculate wr by the following
	// Note that at state i we have a volume on the left lane (call it wl)
	// now we know that the max_vol_at_i = SUM(0.. i) of V[i]
	// if at max_vol_at_i we decrease wl (max_vol_at_i - wl) we get the wr
	// because in every recursion state we either add it to the left lane or right lane
	// with that in mind we calculate the wr because if someting is not at wl it is at wr
	public static int solve2(final int i, final int wl, final int wr) {
		//int key = hash(i, wl, wr);
		if (wl < 0 || wr < 0) {
			return -Integer.MAX_VALUE;
		}

		if (i == N) {
			return 0;
		}

		/*if (MAP.containsKey(key)) {
			return MAP.get(key);
		}*/

		/*if (DP[i][wl][wr] != -1) {
			return DP[i][wl][wr];
		}*/

		int a = solve2(i + 1, wl - V[i], wr) + 1;
		int b = solve2(i + 1, wl, wr - V[i]) + 1;
		int c = solve2(N, wl, wr);// This will always return 0 but stopping is always an option

		int ans = Math.max(Math.max(a, b), c);

		/**
		 * Notice how doing this:
		 * MAX[i] = ans;
		 * PATH[i] = (a >= b) ? "port" : "starboard";
		 * without the if statement will produce the incorrect result
		 * This is because when recursing and opening too many branches PATH[0] will always be the correct answer
		 * althought the other branches (i = 1, i = 2, ... , i = N) will keep overriding PATH[i] not having in count
		 * the MAXIMUM which is what we want to backtrack correctly!!
		 * Thats why we need to always update the path IFF AND ONLY IFF ans > MAX[i]
		 */
		if (ans > MAX[i]) {
			MAX[i] = ans;
		 	PATH[i] = (a >= b) ? "port" : "starboard";
		}
		//MAP.put(key, ans);
		//DP[i][wl][wr] = ans;
		return ans;
	}

	/**
	 * Acumulated sum of values from 0 to i inclusive
	 * Note that we can cache this to avoid iterating over and over
	 * for an overall faster solution
	 * Solution: Cache it on SUM array
	 */
	/*public static int maxAt(final int i) {
		int acum = 0;
		for (int j = 0; j <= i; ++ j) {
			acum += V[i];
		}
		return acum;
	}*/

	/**
	 * This solution reduces the amount of parameters so the memory is accepted and time overall is done in O(n^2)
	 * Solution acepted
	 */
	public static int solve3(final int i, final int wTaken) {
		if (wTaken > F || SUM[i] - wTaken > F) {
			return -Integer.MAX_VALUE;
		}

		if (i == N) {
			return 0;
		}

		if (DP[i][wTaken] != -1) {
			return DP[i][wTaken];
		}

		int a = solve3(i + 1, wTaken + V[i]) + 1;
		int b = solve3(i + 1, wTaken) + 1;
		int ans = Math.max(Math.max(a, b), 0);

		if (ans > MAX[i]) {
			MAX[i] = ans;
		 	PATH[i] = (a >= b) ? "port" : "starboard";
		}
		DP[i][wTaken] = ans;
		return DP[i][wTaken];
	}

	// Need to finish this tabulation!
	public static void tabulate() {
		// Restoring tabulation + base cases
		for (int i = 0; i <= N; ++ i) {
			for (int j = 0; j <= F; ++ j) {
				DP[i][j] = 0;
			}
		}
		for (int i = N - 1; i >= 0; -- i) {
			for (int j = F - 1; j >= 0; -- j) {
				int wTaken = j + V[i];
				int a = 0;
				if (wTaken > F || SUM[i] - wTaken > F) {
					a = -Integer.MAX_VALUE;
				} else {
					a = DP[i + 1][wTaken] + 1;
				}
				int b = DP[i + 1][j] + 1;

				DP[i][j] = Math.max(Math.max(a, b), 0);
				if (DP[i][j] >= MAX[i]) {
					MAX[i] = DP[i][j];
					PATH[i] = (a >= b) ? "port" : "starboard";
				}
			}
		}
	}

	public static void main(final String [] args) throws Exception {
		final PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out));
		final Scanner reader = new Scanner(System.in);
		int T = reader.nextInt();
		while (T > 0) {
			reader.nextLine(); // blank space between consecutive test cases

			// Ferry length
			F = reader.nextInt() * 100;
			final LinkedList<Integer> list = new LinkedList<>();
			while (true) {
				int next = reader.nextInt();
				if (next == 0) {
					break;
				}
				list.add(next);
			}
			N = list.size();
			V = new int[N];
			PATH = new String[N];
			MAX = new int[N];
			SUM = new int[N + 1];
			SUM[0] = 0;
			for (int i = 0; i < N; ++ i) {
				V[i] = list.poll();
				MAX[i] = -1;
				if (i != 0) {
					SUM[i] = SUM[i - 1] + V[i - 1]; 
				}
			}
			SUM[N] = SUM[N - 1] + V[N - 1];

			// Reset the top down tabulation
			for (int i = 0; i <= N; ++ i) {
				for (int j = 0; j <= F; ++ j) {
					DP[i][j] = -1;
				}
			}

			// Calculate the answer
			int ans = 0;
			if (V[0] <= F) {
				ans = solve3(0, 0);
			}

			writer.write(ans + "\n");

			int index = 0;
			while (true) {
				// Backtrack the solution
				if (ans == 0 || index >= ans) {
					break;
				}
				writer.write(PATH[index ++] + "\n");
			}

			-- T;
			if (T > 0) {
				writer.write("\n");
			}
		}
		writer.close();
		reader.close();
	}
}