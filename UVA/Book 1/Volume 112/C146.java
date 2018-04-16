import java.util.*;
import java.io.*;

public class C146 {
	
	/**
	 * Ojo: C++ puede hacer mas sencillo esta operacion gracias a la biblioteca de "algorithm" -> next_permutation genera
	 * La siguiente permutacion de enteros, cadenas, cosas comparables
	 */
	public static void main(final String [] args) throws Exception {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String line = reader.readLine();
		while (!line.equals("#")) {
			String next = nextPermutation(line);
			if (next == null) {
				System.out.println("No Successor");
			} else {
				System.out.println(next);
			}
			line = reader.readLine();
		}
	}

	/**
	 * Este algoritmo tambien puede ser aplicado a enteros
	 **/
	public static String nextPermutation(final String in) {
		char [] array = in.toCharArray();
		int lastIndexI = -1;
		// Encuentro el mayor indice i tal que A[i] < A[i + 1] (Si no existe este es el ultimo orden de la permutacion)
		for (int i = 0; i < in.length() - 1; ++ i) {
			if (array[i] < array[i + 1]) {
				lastIndexI = i;
			}
		}
		if (lastIndexI == -1) {
			return null;
		}
		// Encuentro el mayor indice j tal que A[i] < A[j] (Si no hay otro diferente de i + 1 entonces j = i + 1)
		int lastIndexJ = lastIndexI + 1;
		for (int j = lastIndexI + 2; j < in.length(); ++ j) {
			if (array[lastIndexI] < array[j]) {
				lastIndexJ = j;
			}
		}
		// intercambio -> A[j], A[i] = A[i], A[j]
		char temp = array[lastIndexI];
		array[lastIndexI] = array[lastIndexJ];
		array[lastIndexJ] = temp;
		int i = lastIndexI + 1;
		int last = in.length() - 1;
		// Reverso el orden de los elementos desde [i + 1 .. N)
		while (i < last) {
			temp = array[last];
			array[last] = array[i];
			array[i] = temp;

			++ i;
			-- last;
		}
		return String.valueOf(array);
	}
}