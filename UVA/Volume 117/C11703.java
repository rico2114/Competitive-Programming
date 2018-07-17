import java.io.*;
import java.math.*;
import java.util.*;

public class C11703 {
	
	private static final int N = 1_000_000 + 1;
	private static int [] DP = new int [N];

	public static void tabulate() {
		DP[0] = 1;
		int sqrt = 0;
		int ln = 0;
		int sin = 0;
		for (int i = 1; i < N; ++ i) {
			if (i != 1) {
				sqrt = (int) Math.floor(i - Math.sqrt(i));
				ln = (int) Math.floor(Math.log(i));
				sin = (int) Math.floor(i * Math.pow(Math.sin(i), 2));
			}
			DP[i] = DP[sqrt] + DP[ln] + DP[sin];
			DP[i] = DP[i] % 1_000_000;
		}
	}

	public static void main(final String [] args) throws Exception {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		final PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out));
		int input = 0;
		tabulate();
		while (true) {
			input = Integer.parseInt(reader.readLine());
			if (input == -1) {
				break;
			}

			writer.write(DP[input] + "\n");
		}
		writer.close();
		reader.close();
	} 
}