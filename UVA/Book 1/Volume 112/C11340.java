import java.util.*;
import java.io.*;

public class C11340 {


	public static void main(final String [] args) throws Exception {
		// SCANNER UTILIZA PATTERN MATCHING Y REGEXES (LENTO)
		// BUFFERED READER NO
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder builder = new StringBuilder("");
		// parseInt no crea objeto Integer
		// valueOf retorna objeto Integer
		int T = Integer.parseInt(reader.readLine());
		
		String [] line;
		char [] charArray;

		while (T > 0) {
			// Hashmap tiempo de lookup AMORTIZADO O(C) pero en realidad NO ES TOTALMENTE CONSTANTE
			// LIMPIAR EL HASHMAP toma O(n) PERO RE INICIALIZARLO (CREARLO DE NUEVO) tiene tiempo AMORTIZADO O(C)
			HashMap<Character, Integer> TABLE = new HashMap<Character, Integer>();

			int K = Integer.parseInt(reader.readLine());
			String l;
			for (int i = 0; i < K; ++ i) {
				l = reader.readLine();
				TABLE.put(l.charAt(0), Integer.parseInt(l.substring(2).replaceAll(" ", "")));
			}
			int M = Integer.parseInt(reader.readLine());
			int result = 0;
			for (int i = 0; i < M; ++ i) {
				charArray = reader.readLine().toCharArray();
				for (char c : charArray) {
					if (TABLE.containsKey(c)) {
						result += TABLE.get(c);
					}
					
				}
			}
			builder.append(String.format("%d.%02d$\n", result / 100, result % 100));
			-- T;
		}
		System.out.print(builder.toString());
	}
}	