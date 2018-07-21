import java.io.*;
import java.util.*;
import java.math.*;

public class C11456 {

	private static int N;
	private static int [] V;

	// Note that this solution has a bug where for example case 3 1 2
	// () - (3) => () - (3) (1) => (2) - (3) (1) [Note that that is possible because we dont have a copy of whose is our max at endpoints]
	public static int solve(final int i, final int max, final int min, final int prevMax, final int prevMin) {
		if (i == N) {
			return 0;
		}

		if (max < min) {
			return -Integer.MAX_VALUE;
		}

		if (max < prevMax) {
			return -Integer.MAX_VALUE;
		}

		if (min > prevMin) {
			return -Integer.MAX_VALUE;
		}

		int a = solve(i + 1, max, min, prevMax, prevMin);
		int b = solve(i + 1, V[i], min, max, prevMin) + 1;
		int c = solve(i + 1, max, V[i], prevMax, min) + 1;
		return Math.max(Math.max(a, b), c);
	}

	public static int solve2(final int i, final int lmax, final int lmin, final int rmax, final int rmin) {
		if (lmax < lmin || rmax < rmin || rmin > lmin || lmax < rmax || lmin < rmax) {
			return -Integer.MAX_VALUE;
		}
		if (i == N) {
			return 0;
		}
		// Aseguranse de que si voy a ponerlo al lado derecho entonces sea menor que rmax y menor que rmin porque si no no puedo ponerlo
		if (lmax != Integer.MAX_VALUE && rmax != -Integer.MAX_VALUE && (V[i] > rmax && V[i] < lmax)) {
				return -Integer.MAX_VALUE;
		}
		if (lmin != Integer.MAX_VALUE && rmin != -Integer.MAX_VALUE && (V[i] < lmin && V[i] > rmin)) {
				return -Integer.MAX_VALUE;
		}

		

		

		int a = solve2(i + 1, lmax, lmin, rmax, rmin);
		int b = 0;
		if (V[i] > lmax) {
			b = solve2(i + 1, V[i], lmin == Integer.MAX_VALUE ? V[i] : lmin, rmax, rmin) + 1;
		}		
		int c = 0;
		if (V[i] < rmin) {
			c = solve2(i + 1, lmax, lmin, rmax == -Integer.MAX_VALUE ? V[i] : rmax, V[i]) + 1;
		}
		return Math.max(Math.max(a, b), c);
	}

	public static void main(final String [] args) throws Exception {
		final PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out));
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(reader.readLine());
		while (T > 0) {
			N = Integer.parseInt(reader.readLine());
			V = new int[N];
			for (int i = 0; i < N; ++ i) {
				V[i] = Integer.parseInt(reader.readLine());
			}
			int max = solve2(0, Integer.MAX_VALUE, Integer.MAX_VALUE,  -Integer.MAX_VALUE, -Integer.MAX_VALUE);
			//int max = solve2(3, Integer.MAX_VALUE, Integer.MAX_VALUE, 3, 0);

		/*	for (int i = 1; i < N; ++ i) {
				max =  Math.max(max, solve2(i, Integer.MAX_VALUE, Integer.MAX_VALUE, -Integer.MAX_VALUE, -Integer.MAX_VALUE));
			}*/

			/*if (T <= 2) {
				writer.write("T: " + T + "\n");
				for (int i = 0; i < N; ++ i) {
					writer.write("V[" + i + "]= " + V[i] + ", ");
				}
				writer.write("\n");
			}*/

			writer.write(max + "\n");

			-- T;

		}
		reader.close();
		writer.close();
	}
}