import java.io.*;
import java.util.*;

public class C216 {
	
	private static final int MAX_COMPUTERS = 9;
	private static final int OFFSET = 5;
	private static final int MAX_BITSET = (int) Math.pow(2, MAX_COMPUTERS);
	private static final double [][] DP = new double [MAX_COMPUTERS][MAX_BITSET];
	private static final int [][] POINTERS = new int [MAX_COMPUTERS][MAX_BITSET];

	private static int [] X;
	private static int [] Y;

	private static int P;

	private static final double distance(final int source, final int target) {
		return Math.hypot(X[source] - X[target], Y[source] - Y[target]);
	}

	public static void clean() {
		for (int i = 0; i < MAX_COMPUTERS; ++ i) {
			for (int j = 0; j < MAX_BITSET; ++ j) {
				DP[i][j] = -1;
				POINTERS[i][j] = -1;
			}
		}
	}

	public static double solve(final int i, final int path) {
		if (DP[i][path] != -1) {
			return DP[i][path];
		}
		double minimum = Double.MAX_VALUE;
		int pointsTo = 0;
		boolean couldPick = false;
		for (int j = 0; j < P; ++ j) {
			// already taken vertex
			if ((((path >> j) & 1) == 1)) {
				continue;
			}

			// Now solve for the vertex j (recursively do the best as we can)
			double answer = solve(j, path | (1 << j)) + distance(i, j) + 16;
			// Notice how caching that solve part will produce the wrong output since it needs to careful be processed
			// ONLY cache MAIN SOLVE CALLS e.g for a given (i, j) not (i + 1, j) nor (i, j + 1) nor (i + k, j + w)
			//DP[j][path | (1 << j)] = answer;
			if (answer < minimum) {
				minimum = answer;
				pointsTo = j;
			}
			couldPick = true;
		} 
		// No more options to pick return 0 to keep the flow of the recursion
		// Base case
		if (!couldPick) {
			return 0;
		}
		// Note that our dp table is never overriden because we start from different places generating always
		// a new path from the start i
		DP[i][path] = minimum;
		POINTERS[i][path] = pointsTo;
		return minimum;
	}

	public static void main(final String [] args) throws Exception {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		final PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out));
		String [] parts = null;
		int network = 0;
		while (true) {
			clean();
			P = Integer.parseInt(reader.readLine());
			if (P == 0) {
				break;
			}
			writer.write("**********************************************************\n");
			writer.write("Network #" + (++ network) + "\n");
			X = new int [P];
			Y = new int [P];
			for (int i = 0; i < P; ++ i) {
				parts = reader.readLine().split(" ");
				X[i] = Integer.parseInt(parts[0]);
				Y[i] = Integer.parseInt(parts[1]);
			}
			double ans = Double.MAX_VALUE;
			int backtrackBitset = 0;
			int startFrom = 0;
			// Now test each vertex to see what is the best option
			for (int i = 0; i < P; ++ i) {
				int bitset = 1 << i;
				// We wont pick the starting point again!
				// thats why 1 << i
				// Also marked so recursion works correctly otherwise we wil eventually pick it up further down
				double sol = solve(i, bitset);
				if (sol < ans) {
					ans = sol;
					backtrackBitset = bitset;
					startFrom = i;
				}
			}
			// backtrack to find the path
			int times = 1;
			while (times != P) {
				String distanceStr = String.format("%.2f", (distance(startFrom, POINTERS[startFrom][backtrackBitset]) + 16D)).replaceAll(",", ".");
				String formatted = "Cable requirement to connect (" + X[startFrom] + "," + Y[startFrom] + ") to (" + X[POINTERS[startFrom][backtrackBitset]] + "," + Y[POINTERS[startFrom][backtrackBitset]] + ") is " + distanceStr + " feet.\n";
				writer.write(formatted);
				startFrom = POINTERS[startFrom][backtrackBitset];
				backtrackBitset = backtrackBitset | (1 << startFrom);
				++ times;
			}		
			writer.write(String.format("Number of feet of cable required is %.2f.\n", ans).replaceAll(",", "."));			
		}
		writer.close();
		reader.close();
	}
}