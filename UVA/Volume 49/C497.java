import java.io.*;
import java.util.*;

public class C497 {

	private static int N;
	private static int [] V;

	private static int [] DP = new int[8192];
	private static int [] PATH = new int [8192];

	public static int solve(final int i) {
		if (DP[i] != -1) {
			return DP[i];
		}

		int max = 1;
		int goTo = i;
		for (int j = i + 1; j < N; ++ j) {
			// I need to shoot things that are at higher altitude
			if (V[i] < V[j]) {
				int ans = solve(j) + 1;
				if (ans > max) {
					max = ans;
					goTo = j;
				}
			}
		}
		// Store the maximum
		DP[i] = max;
		// This will help me traceback the route
		PATH[i] = goTo;
		return DP[i];
	}

	public static void main(final String [] args) throws Exception {
		final PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out));
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(reader.readLine());
		reader.readLine();
		String line = "";
		final LinkedList<Integer> list = new LinkedList<>();
		while (T > 0) {
			line = reader.readLine();
			while (line != null && line.length() > 0) {
				list.add(Integer.parseInt(line));
				line = reader.readLine();
			}
			N = list.size();
			V = new int[N];
			for (int i = 0; i < N; ++ i) {
				V[i] = list.poll();
				DP[i] = -1;
				PATH[i] = -1;
			}

 			int max = solve(0);
 			int temp = 0;
 			int ansIdx = 0;
			for (int i = 1; i < N; ++ i) {
				temp = solve(i);
				if (temp > max) {
					max = temp;
					ansIdx = i;
				}
			}
			writer.write("Max hits: " + max + "\n");
			while (PATH[ansIdx] != ansIdx) {
				writer.write(V[ansIdx] + "\n");
				ansIdx = PATH[ansIdx];
			}
			writer.write(V[PATH[ansIdx]] + "\n");
			-- T;
			if (T > 0) {
				writer.write("\n");
			}
		}
		writer.close();
		reader.close();
	}
}