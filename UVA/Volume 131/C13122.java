import java.io.*;
import java.util.*;

/**
 * This solution had to be moved to C++ for it to pass!
 */
public class C13122 {
	
	public static final int MAX_N = 256 + 1;
	public static final int OFFSET = 5;
	public static int N = 0;

	// Used from [1..N]
	public static final Point [] POINTS = new Point[MAX_N + OFFSET];
	public static final double [][][] DP = new double[MAX_N + OFFSET][MAX_N + OFFSET][MAX_N + OFFSET];

	/**
	 * Solucion fuerza bruta
	 * Attempt N 1 to solve the probem
	 */
	/*public static double solve(final int i, final int remaining) {
		if (remaining < 0) {
			return Integer.MAX_VALUE;
		}	
		if (i == N) {
			if (remaining != 0) {
				return Integer.MAX_VALUE;
			}

			return TOTAL_LENGTH;
		}

		// Si quito i
		prev[i] = i - 1;
		// Calculo el previo no eliminado
		int u = i - 1;
		while (u != prev[u]) {
			u -= 1;
		}
		// Agregando el nuevo segmento AB
		double agregandoNuevoAB = Math.hypot((POINTS[u].x - POINTS[i + 1].x), (POINTS[u].y - POINTS[i + 1].y));
		// Quitando los dos viejos
		double quitandoleViejoAB = Math.hypot((POINTS[u].x - POINTS[i].x), (POINTS[u].y - POINTS[i].y));
		double quitandoleViejoBC = Math.hypot((POINTS[i].x - POINTS[i + 1].x), (POINTS[i].y - POINTS[i + 1].y));

		// Calculo la nueva diferencia
		double ansQuitando = solve(i + 1, remaining - 1) + (agregandoNuevoAB - quitandoleViejoAB - quitandoleViejoBC);

		// Si no quito i
		prev[i] = i;
		double ansNoQuitando = solve(i + 1, remaining);

		return Math.min(ansQuitando, ansNoQuitando);
	}*/

	/**
	 * Recursive form so everyone can understand the approach
	 * DP Knapsack overloaded
	 **/
	public static double solve(final int point, final int lastChosen, final int remaining) {
		if (remaining < 0) {
			return Integer.MAX_VALUE;
		}	
		if (point == N) {
			if (remaining != 0) {
				return Integer.MAX_VALUE;
			}
			return Math.hypot((POINTS[point].x - POINTS[lastChosen].x), (POINTS[point].y - POINTS[lastChosen].y));
		}
		if (DP[point][lastChosen][remaining] != -1) {
			return DP[point][lastChosen][remaining];
		}
		double left = solve(point + 1, point, remaining) +  Math.hypot((POINTS[point].x - POINTS[lastChosen].x), (POINTS[point].y - POINTS[lastChosen].y));
		double right = solve(point + 1, lastChosen, remaining - 1);
		double ans = Math.min(left, right);
		DP[point][lastChosen][remaining] = ans;
		return ans;
	}

	/**
	 * Tabulated version, this was a pain in the ass
	 **/
	public static double tabulate(final int K) {
		for (int lc = N - 1; lc >= 1; -- lc) {
			DP[N][lc][0] = Math.hypot((POINTS[N].x - POINTS[lc].x), (POINTS[N].y - POINTS[lc].y));
		}
		for (int p = N - 1; p >= 1; -- p) {
			for (int lc = N - 1; lc >= 1; -- lc) {
				for (int r = 0; r <= K; ++ r) {
					//DP[N - 1][N-1][1] = DP[N][N - 1][1]
					double left = DP[p + 1][p][r] + Math.hypot((POINTS[p].x - POINTS[lc].x), (POINTS[p].y - POINTS[lc].y));
					double right = Integer.MAX_VALUE;
					if (r - 1 >= 0) {
					//DP[N - 1][N-1][1] = DP[N][N - 1][0]
						right = DP[p + 1][lc][r - 1];
					}
					DP[p][lc][r] = Math.min(left, right);
				}
			}
		}
		return DP[2][1][K];
	}

	public static void main(final String [] args) throws Exception {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		final PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out));
		String line = reader.readLine();
		String [] parts = null;
		while (line != null) {
			parts = line.split(" ");
			N = Integer.parseInt(parts[0]);
			int K = Integer.parseInt(parts[1]);
			for (int i = 0; i < N; ++ i) {
				parts = reader.readLine().split(" ");
				int x = Integer.parseInt(parts[0]);
				int y = Integer.parseInt(parts[1]);
				POINTS[i + 1] = new Point(x, y);
			}
			for (int i = 0; i <= N + 5; ++ i) {
				for (int j = 0; j <= N + 5; ++ j) {
					for (int k = 0; k <= K + 5; ++ k) {
						DP[i][j][k] = Integer.MAX_VALUE;
					}
				}
			}

			double ans = tabulate(K);
			writer.write(String.format(Locale.US, "%.3f", ans) + "\n");
			line = reader.readLine();
		}
		reader.close();
		writer.close();
	}
}

class Point {
	public int x;
	public int y;

	public Point(final int x, final int y) {
		this.x = x;
		this.y = y;
	}
}