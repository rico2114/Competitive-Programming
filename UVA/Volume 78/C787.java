import java.io.*;
import java.util.*;
import java.math.*;

public class C787 {

	private static int N;
	private static int [] V;

	private static final HashMap<Integer, BigInteger> MEMO = new HashMap<Integer, BigInteger>();

	public static BigInteger solve(final int i, final BigInteger t) {
		int key = Objects.hash(i, t);
		if (MEMO.containsKey(key)) {
			return MEMO.get(key);
		}

		if (i == N) {
			return t;
		}

		BigInteger stop = solve(N, t); // Stop whenever I want of acumulating (that can be an option) (not pick anything else)
		BigInteger carry = solve(i + 1, t.multiply(BigInteger.valueOf(V[i]))); // Keep carring the cumulative product (keep picking up)
		BigInteger reset = solve(i + 1, BigInteger.valueOf(V[i])); // Or just reset and pick the new V as origin (start a new subsequence)
		BigInteger answer = stop.max(carry.max(reset));
		MEMO.put(key, answer);
		return answer;
	}

	public static void main(final String [] args) throws Exception {
		final Scanner reader = new Scanner(System.in);
		final PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out));
		int next = 0;
		final LinkedList<Integer> accumulated = new LinkedList<>();
		while (reader.hasNext()) {
			while ((next = reader.nextInt()) != -999999) {
				accumulated.add(next);
			}
			N = accumulated.size();
			V = new int[N];
			for (int i = 0; i < N; ++ i) {
				V[i] = accumulated.poll();
			}
			writer.write(solve(1, BigInteger.valueOf(V[0])) + "\n");
			MEMO.clear();
		}
		reader.close();
		writer.close();
	}
}