import java.util.*;
import java.io.*;
import java.util.*;

public class C459 {

	public static void dfs(final char src, final HashMap<Character, ArrayList<Character>> adyancencyList, final HashMap<Character, Boolean> visited) {
		final Stack<Character> stack = new Stack();
		stack.add(src);
		visited.put(src, true);
		while (!stack.isEmpty()) {
			char s = stack.peek(); stack.pop();
			for (char c : adyancencyList.get(s)) {
				if (visited.containsKey(c)) {
					continue;
				}

				visited.put(c, true);
				stack.add(c);
			}
		}
	}

	public static int solve(PrintWriter writer, final HashMap<Character, ArrayList<Character>> adyancencyList) {
		final HashMap<Character, Boolean> visited = new HashMap<>();
		int maximalSets = 0;
		for (Map.Entry<Character, ArrayList<Character>> entry : adyancencyList.entrySet()) {
			if (visited.containsKey(entry.getKey())) {
				continue;
			}
			dfs(entry.getKey(), adyancencyList, visited);
			++ maximalSets;
		}
		return maximalSets;
	}

	public static void main(final String [] args) throws Exception {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		final PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out));
		int T = Integer.parseInt(reader.readLine());
		reader.readLine();
		while (T > 0) {
			final HashMap<Character, ArrayList<Character>> adyancencyList = new HashMap<>();
			// Debo meter en la lista de adyacencia desde A hasta el largest char
			char largest = reader.readLine().charAt(0);
			for (char x = 'A'; x <= largest; x ++) {
				adyancencyList.put(x, new ArrayList<Character>());
			}
			String line = "";
			while ((line = reader.readLine()) != null) {
				if (line.length() == 0) {
					break;
				}
				char src = line.charAt(0);
				char dst = line.charAt(1);
				if (adyancencyList.containsKey(src)) {
					adyancencyList.get(src).add(dst);
				} else {
					ArrayList<Character> list = new ArrayList<Character>();
					list.add(dst);
					adyancencyList.put(src, list);
				}
				if (adyancencyList.containsKey(dst)) {
					adyancencyList.get(dst).add(src);
				} else {
					ArrayList<Character> list = new ArrayList<Character>();
					list.add(src);
					adyancencyList.put(dst, list);
				}
			}
			int ans = solve(writer, adyancencyList);
			writer.write(ans + "\n");
			-- T;
			if (T > 0) {
				writer.write("\n");
			}
		}
		reader.close();
		writer.close();
	}
}