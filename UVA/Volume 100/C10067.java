import java.io.*;
import java.util.*;
import java.math.*;

public class C10067 {
	
	private static final int MAX_VERTEX = 10_000;
	private static final int OFFSET = 5;
	private static LinkedList<Integer>[] GRAPH = new LinkedList[MAX_VERTEX + OFFSET];
	private static boolean [] VISITED = new boolean[MAX_VERTEX + OFFSET];
	private static int MODULE = 10;


	public static int getByPos(final int value, final int pos) {
		int a = (value / 1000);
		int b = (value / 100) - (a * 10);
		int c = (value / 10) - (a * 100) - (b * 10);
		int d = (value) - (a * 1000) - (b * 100) - (c * 10);

		if (pos == 1) {
			return a;
		} else if (pos == 2) {
			return b;
		} else if (pos == 3) {
			return c;
		} else {
			return d;
		}
	}

	public static int convertToDecimal(int i, int s, int t, int f) {
		return i * 1000 + s * 100 + t * 10 + f;
	}

	public static int search(final int s, final int t) {
		final LinkedList<Pair> queue = new LinkedList<>();
		queue.add(new Pair(s, 0));
		// our initial state is always available because we are not 'passing over it'
		VISITED[s] = false;
		while (!queue.isEmpty()) {
			final Pair pair = queue.pop();
			if (VISITED[pair.origin]) {
				continue;
			}

			if (pair.origin == t) {
				return pair.distance;
			}

			for (int to : GRAPH[pair.origin]) {
				queue.add(new Pair(to, pair.distance + 1));
			}
			VISITED[pair.origin] = true;
		}
		return -1;
	}

	public static void main(final String [] args) throws Exception {
		// Used a scanner because i was getting runtime error.
		final Scanner reader = new Scanner(System.in);
		final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
		int T = reader.nextInt();
		String [] parts = null;
		for (int i = 0; i < 10_000; ++ i) {
			int firstE = getByPos(i, 1);
			int l1 = (firstE - 1) % MODULE;
			int r1 = (firstE + 1) % MODULE;

			int secondE = getByPos(i, 2);
			int l2 = (secondE - 1) % MODULE;
			int r2 = (secondE + 1) % MODULE;

			int thirdE = getByPos(i, 3);
			int l3 = (thirdE - 1) % MODULE;
			int r3 = (thirdE + 1) % MODULE;

			int fourthE = getByPos(i, 4);
			int l4 = (fourthE - 1) % MODULE;
			int r4 = (fourthE + 1) % MODULE;

			if (l1 < 0) {
				l1 = 9;
			}
			if (l2 < 0) {
				l2 = 9;
			}
			if (l3 < 0) {
				l3 = 9;
			}
			if (l4 < 0) {
				l4 = 9;
			}

			int state1 = convertToDecimal(l1, secondE, thirdE, fourthE);
			int state2 = convertToDecimal(r1, secondE, thirdE, fourthE);
			int state3 = convertToDecimal(firstE, l2, thirdE, fourthE);
			int state4 = convertToDecimal(firstE, r2, thirdE, fourthE);
			int state5 = convertToDecimal(firstE, secondE, l3, fourthE);
			int state6 = convertToDecimal(firstE, secondE, r3, fourthE);
			int state7 = convertToDecimal(firstE, secondE, thirdE, l4);
			int state8 = convertToDecimal(firstE, secondE, thirdE, r4);

			// Notice how this is a bidirectional graph (in the end)
			GRAPH[i] = new LinkedList<Integer>();
			GRAPH[i].add(state1);
			GRAPH[i].add(state2);
			GRAPH[i].add(state3);
			GRAPH[i].add(state4);
			GRAPH[i].add(state5);
			GRAPH[i].add(state6);
			GRAPH[i].add(state7);
			GRAPH[i].add(state8);
		}

		while (T > 0) {
			// Grab initial state and target state
			int initialState = convertToDecimal(reader.nextInt(), reader.nextInt(),
								reader.nextInt(), reader.nextInt());
			int goalState = convertToDecimal(reader.nextInt(), reader.nextInt(),
								reader.nextInt(), reader.nextInt());

			// Handle forbidden states
			int N = reader.nextInt();
			for (int i = 0; i < N; ++ i) {
				int forbidden = convertToDecimal(reader.nextInt(), reader.nextInt(),
								reader.nextInt(), reader.nextInt());
				// Cant pass over forbidden stuff
				VISITED[forbidden] = true;
			}

			// search
			int answer = search(initialState, goalState);
			writer.write(answer + "\n");

			-- T;

			// Empty forbidden list
			if (T > 0) {
				for (int i = 0; i < MAX_VERTEX; ++ i) {
					VISITED[i] = false;
				}
				reader.nextLine();
			}
		}
		reader.close();
		writer.close();
	}

	public static class Pair {
		public int origin;
		public int distance;

		public Pair(final int origin, final int distance) {
			this.origin = origin;
			this.distance = distance;
		}
	}
}