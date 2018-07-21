import java.util.*;
import java.io.*;

public class C481 {
	
	private static int N;
	private static int [] V;

	private static final int OFFSET = 5;
	private static int [] DP;
	private static HashMap<Integer, Integer> PATH = new HashMap<Integer, Integer>();

	public static int solve(final int i, final boolean [] taken) {
		if (DP[i] != -1) {
			return DP[i];
		}
		// Max is always 1 (the element[i] itself is a subsequence)
		int max = 1;
		int forwardTo = -1;
		boolean x = false;
		for (int j = i + 1; j < N; ++ j) {
			int temp = 0;
			if (taken[j]) {
				continue;
			}

			if (V[j] > V[i]) {
				taken[j] = true;
				temp = 1 + solve(j, taken);
				taken[j] = false;
			}

			if (temp >= max) {
				max = temp;
				forwardTo = j;
			}
		}

		DP[i] = max;
		if (forwardTo != -1) {
			int key = Objects.hash(i, taken);
			PATH.put(key, forwardTo);
		}
		return DP[i];
	}

	public static void main(final String [] args) throws Exception {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		final PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out));
		String line = "";
		final LinkedList<Integer> input = new LinkedList<>();
		while ((line = reader.readLine()) != null) {
			input.add(Integer.parseInt(line));
		}
		N = input.size();
		V = new int[N];
		DP = new int[N];
		for (int i = 0; i < N; ++ i) {
			DP[i] = -1;
			V[i] = input.getFirst();
			input.remove();
		}

		int max = -Integer.MAX_VALUE;
		boolean [] taken = new boolean[N];
		int indexSTR = 0;
		for (int i = 0; i < N; ++ i) {
			taken[i] = true;
			int ans = solve(i, taken);
			if (ans > max) {
				// Used to backtrack the solution
				indexSTR = i;
				max = ans;
			}
			taken[i] = false;
		}
		writer.write(max + "\n");
		writer.write("-\n");
		int times = max - 1;
		int key = 0;
		writer.write(V[indexSTR] + "\n");
		while (times > 0) {
			taken[indexSTR] = true;
			key = Objects.hash(indexSTR, taken);
			indexSTR = PATH.get(key);
			writer.write(V[indexSTR] + "\n");
			-- times;
		} 

		reader.close();
		writer.close();
	}

}