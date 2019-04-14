import java.io.*;
import java.util.*;

public class C1510 {

	public static void main(final String [] args) throws Exception {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
		
		int t = Integer.parseInt(reader.readLine());
		while (t > 0) {
			int n = Integer.parseInt(reader.readLine());
			int ans = n * (n - 1) * (n - 2) / 6;
			int [] matrix = new int[n];
			
			int x = 0;
			String [] entrada = null;
			for (int e = 0; e < n - 1; ++ e) {
				entrada = reader.readLine().split(" ");
				int i = e + 1;
				int off = 0;
				while (i < n) {
					if (entrada[off].equals("1")) {
						matrix[e] += 1;
						matrix[i] += 1;
					}
					++ i;
					++ off;
				}
				
			}
			for (int e : matrix) {
				x += (n - e - 1) * e;
			}
			ans -= x >> 1;
			writer.write(ans + "\n");
			-- t;
		}
		writer.close();
		reader.close();
	}
}
