import java.io.*;
import java.util.*;

class C10305 {

	private static final int MAX_N = 100;
	private static final int [] IN_DEG = new int [MAX_N + 5];
	private static final LinkedList<Integer>[] G = new LinkedList[MAX_N + 5];
	private static int N;
	private static StringBuilder builder = new StringBuilder();

	public static String toposort() {
		final LinkedList<Integer> active = new LinkedList<>();
		final LinkedList<Integer> answer = new LinkedList<>();
		for (int i = 1; i <= N; ++ i) {
			if (IN_DEG[i] == 0) {
				active.add(i);
			}
		}
		while (active.size() != 0) {
			int v = active.pop();
			answer.add(v);
			IN_DEG[v] -= 1;
			for (int w : G[v]) {
				IN_DEG[w] -= 1;
				if (IN_DEG[w] == 0) {
					active.add(w);
				}
			}
		}
		// Builders over string concatenation because strings are immutable so the complexity is higher
		builder.setLength(0);
		while (answer.size() != 0) {
			int v = answer.pop();
			if (answer.size() == 0) {
				builder.append((v + "\n"));
			} else {
				builder.append((v + " "));
			}
		}
		return builder.toString();
	}

	public static void main(final String [] args) throws Exception {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
		String [] parts;
		while (true) {
			parts = reader.readLine().split(" ");
			N = Integer.parseInt(parts[0]);
			int M = Integer.parseInt(parts[1]);
			if (M == N && N == 0) {
				break;
			}

			for (int i = 0; i <= N; ++ i) {
				G[i] = new LinkedList<>();
				IN_DEG[i] = 0;
			}

			for (int i = 0; i < M; ++ i) {
				parts = reader.readLine().split(" ");
				int s = Integer.parseInt(parts[0]);
				int t = Integer.parseInt(parts[1]);
				G[s].add(t);
				IN_DEG[t] += 1;
			}

			writer.write(toposort());
		}
		reader.close();
		writer.close();
	}
}