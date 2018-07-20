import java.util.*;
import java.io.*;

public class C562 {
	
	private static final int OFFSET = 5;
	private static final int MAX_I = 100 + OFFSET;
	private static final int MAX_W = 25_000 + OFFSET; //(100 * 500 / 2)
	private static final int [][] DP = new int[MAX_I][MAX_W];

	private static int [] V;
	private static int N;
	private static int W;

	/**
	 * Esta solucion es incorrecta (Fue mi primer approach)
	 */
	public static final int solve(final int i, final int left, final int right) {
		if (i == N) {
			return Math.max(left, right) - Math.min(left, right);
		}
		return Math.min( solve(i + 1, left + V[i], right), solve(i + 1, left, right + V[i]) );
	}

	/**
	 * EXPLICACION: La idea general de solucion es la siguiente:
	 * Note que en el enunciado hay dos sujetos, sujeto A y sujeto B por simplicidad.
	 * Tambien hay una bolsa con unos FAJOS de monedas que en su total implican la cantidad total de dinero
	 * Su tarea es minimizar la diferencia entre su division del dinero para asi cada sujeto, A y B, queden con 
	 * La mayor cantidad de dinero posible (ambos)
	 *
	 * SOLUCION: Asuma que la cantidad de dinero total en la bolsa es M, cada fajo es denotado por V[i] donde i denota el indice
	 * Note que en el mejor de los casos cada sujeto quedaria con M / 2 en su pantalon (Ej: M = 4) -> Sujeto A = 2; Sujeto B = 2
	 * Pero pueden haber casos en los que esto no sea cierto (como M = 5)
	 * Ahora sabiendo que en el mejor de los casos la cantidad maxima de dinero obtenida por cualquiera de los sujetos es M/2
	 * Podemos hacer Knapsack sobre los fajos de monedas (Porque cada sujeto puede coger su fajo de monedas V[i] o darselo al otro)
	 * Al hacer Knapsack lo que buscamos es buscar la cantidad maxima que cualquier sujeto (no importa cual) obtenga cogiendo esos
	 * Fajos
	 * Ahora, se puede preguntar por que quiero encontrar que es lo maximo que cada sujeto puede encontrar? pues bueno,
	 * Resulta que si encuentro cuanto es lo maximo que puede coger cada sujeto pues si lo multiplico por 2 (porque son 2 sujetos)
	 * Tengo lo maximo que AMBOS pueden coger, y si a eso le quito el valor total de los fajos (M) obtengo lo minimo que resultaria
	 * En la division del dinero ans = (M - 2 * (knapsack(N, M / 2)))
	 * 
	 * EJEMPLO = Fajos V = {2, 3, 5}, Total = M = 2 + 3 + 5 = 10
	 * Hago knapsack con los parametros (Weight = 5) y este resultado me dara que el maximo que puede coger
	 * Con los fajos {2, 3, 5} es = 5 por lo tanto lo maximo que ambos puedan coger es 10 (5 * 2)
	 * Por lo tanto a la division del dinero sobra (M - 10) => 10 - 10 => 0
	 */
	public static final int knapsack(final int i, final int w) {
		if (i == N) {
			return 0;
		}

		if (V[i] <= w) {
			return Math.max(knapsack(i + 1, w), knapsack(i + 1, w - V[i]) + V[i]);
		} else {
			return knapsack(i + 1, w);
		}
	}

	public static final void tabulate(final int W) {
		// Resetting + base cases
		for (int i = 0; i <= N; ++ i) {
			for (int w = 0; w <= W; ++ w) {
				DP[i][w] = 0;
			}
		}

		for (int i = N - 1; i >= 0; -- i) {
			for (int w = 0; w <= W; ++ w) {
				if (V[i] <= w) {
					DP[i][w] = Math.max(DP[i + 1][w], DP[i + 1][w - V[i]] + V[i]);
				} else {
					DP[i][w] = DP[i + 1][w];
				}
			}
		}
	}


	public static void main(final String [] args) throws Exception {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		final PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out));
		int T = Integer.parseInt(reader.readLine());
		String [] parts = null;
		while (T > 0) {
			N = Integer.parseInt(reader.readLine());
			V = new int [N];
			parts = reader.readLine().split(" ");
			W = 0;
			for (int i = 0; i < N; ++ i) {
				V[i] = Integer.parseInt(parts[i]);
				W += V[i];
			}
			tabulate(W >> 1);
			// int knapsack = knapsack(0, W >> 1);
			int knapsack = DP[0][W >> 1];
			writer.write((W - (knapsack << 1)) + "\n");
			-- T;
		}
		reader.close();
		writer.close();
	}
}