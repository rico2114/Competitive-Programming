import java.io.*;
import java.util.*;

class C280 {
	private static final int MAX_VERTEX = 100 + 5;
	private static final LinkedList<Integer>[] G = new LinkedList[MAX_VERTEX];
	private static final boolean VISITED[] = new boolean[MAX_VERTEX];

	public static int dfs(final int initialV) {
		final Stack<Integer> stack = new Stack<>();
		stack.add(initialV);
		int discovered = 0;
		int epoch = 0; // epoch is used to avoid counting the first vertex as visited and discovered
		while (stack.size() != 0) {
			int v = stack.pop();
			if (VISITED[v]) {
				continue;
			}

			for (int w : G[v]) {
				if (VISITED[w]) {
					continue;
				}
				stack.add(w);
			}

			if (epoch != 0) {
				VISITED[v] = true;
				discovered += 1;
			}
			epoch += 1;
		}
		return discovered;
	}

	public static void main(final String [] args) throws Exception {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		final PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out));
		String [] parts;
		while (true) {
			int N = Integer.parseInt(reader.readLine());
			if (N == 0) {
				break;
			}
			for (int i = 1; i <= N; ++ i) {
				G[i] = new LinkedList<>();
			}
			String line = reader.readLine();
			while (!line.equals("0")) {
				parts = line.split(" ");
				int i = Integer.parseInt(parts[0]);
				// -1 because last element is always 0
				for (int j = 1; j < parts.length - 1; ++ j) {
					int to = Integer.parseInt(parts[j]);
					G[i].add(to);
				}
				line = reader.readLine();
			}
			LinkedList<Integer> queries = new LinkedList<>();
			parts = reader.readLine().split(" ");
			for (int i = 1; i < parts.length; ++ i) {
				queries.add(Integer.parseInt(parts[i]));
			}

			while (queries.size() != 0) {
				int start = queries.pop();
				int ans = N - dfs(start);
				int temp = ans;
				if (ans >= 1) {
					writer.write(ans + " ");
					for (int i = 1; i <= N; ++ i) {
						if (!VISITED[i]) {
							writer.write(i + (temp == 1 ? "\n" : " "));
							temp -= 1;
						}
						VISITED[i] = false;
					}
				} else {
					writer.write("0\n");
					for (int i = 1; i <= N; ++ i) {
						VISITED[i] = false;
					}
				}
			}
		}
		writer.close();
		reader.close();
	}
}