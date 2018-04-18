import java.util.*;
import java.io.*;

public class C11462 {
	
	public static void main(final String [] args) throws Exception {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		final PrintWriter writer  = new PrintWriter(new OutputStreamWriter(System.out));
		int N = Integer.parseInt(reader.readLine());
		while (N != 0) {
			String [] line = reader.readLine().split(" ");
			int array [] = new int [N];
			for (int i = 0; i < N; i++) {
				array[i] = Integer.parseInt(line[i]);
			}
			Arrays.sort(array);
			for (int i = 0; i < array.length; i ++) {
				writer.write(array[i] + ((i == array.length - 1) ? "" : " "));
			}
			writer.write("\n");
			N = Integer.parseInt(reader.readLine());
		}
		writer.close();
		reader.close();
	}
}