import java.io.*;
import java.util.*;

public class I {

	public static void main(final String [] args) throws Exception {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
		
		String line = reader.readLine();
		while (line != null) {
			int TOT = Integer.parseInt(line);
			line = reader.readLine();
			int P = 0, I = 0, N = 0;
			for (char c : line.toCharArray()) {
				if (c == 'P') {
					++ P;
				} else if (c == 'I') {
					++ I;
				} else {
					++ N;
				}
			}
			if (P > N + I) {
			    writer.write("SI\n");
			} else if (N >= P + I) {
			    writer.write("NO\n");
			} else {
			    writer.write("INDECISOS\n");
			}
			line = reader.readLine();
		}
		
		reader.close();
		writer.close();
	}
}