import java.io.*;
import java.util.*;

public class C10684 {
	
	private static int N;
	private static int [] P;

	// Kadanes algorithm
	// Maximum contiguous sum
	public static int solve() {
		int max = 0, sum = 0;
		for (int i = 0; i < N; ++ i) {
			// I will accumulate to the carried sum the previous result
			sum += P[i];
			// Now we will update sum to be either 0 (if our previous value went beyond 0) or sum if our value is greater
			// Notice accumulation of negative numbers wont ever max out to anything
			sum = Math.max(0, sum);
			// Either my max value is the sum or the previous max I had
			max = Math.max(sum, max);
		}
		return max;
	}

	public static void main(final String [] args) throws Exception {
		final Scanner reader = new Scanner(System.in);
		final PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out));
		while (true) {
			N = reader.nextInt();
			if (N == 0) {
				break;
			}
			P = new int[N];
			for (int i = 0; i < N; ++ i) {
				P[i] = reader.nextInt();
			}
			int ans = solve();
			if (ans <= 0) {
				writer.write("Losing streak.\n");
			} else {
				writer.write("The maximum winning streak is " + ans + ".\n");
			}
		}
		writer.close();
		reader.close();
	}
}