import java.io.*;
import java.util.*;

public class C11547 {
	
	public static void main(final String [] args) throws Exception {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

		int T = Integer.parseInt(reader.readLine());
		while (T > 0) {
			int N = Integer.parseInt(reader.readLine());
			N = N * 315 + 36962;

			// Segunda cifra de derecha a izq
			int tens = Math.abs((N / 10) % 10);
			writer.write(tens + "\n");
			-- T;
		}

		reader.close();
		writer.close();
	}
}