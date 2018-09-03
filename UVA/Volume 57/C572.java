import java.io.*;
import java.util.*;

class C572 {
	
	private static final int [] DR = new int [] {
		-1, -1, -1, 1, 1, 1, 0, 0
	};

	private static final int [] DC = new int [] {
		 0,  1, -1, 0, 1, -1, 1, -1
	};

	private static final int MAX_ROWS = 100;
	private static final int MAX_COLUMN = MAX_ROWS;

	private static final boolean [][] VISITED = new boolean [MAX_ROWS + 5][MAX_COLUMN + 5];
	private static final int [][] MATRIX = new int [MAX_ROWS + 5][MAX_COLUMN + 5];
	// a[0] -> [0, 1, 2, ..]
	// a[1] -> [0, 1, 2, ..]
	// ...

	public static void dfs(final int r, final int c) {
		final Stack<Pair> stack = new Stack<Pair>();
		stack.add(new Pair(r, c));
		while (stack.size() != 0) {
			Pair p = stack.pop();

			if (VISITED[p.r][p.c]) {
				continue;
			}

			for (int i = 0; i < DR.length; ++ i) {
				int nr = p.r + DR[i];
				int nc = p.c + DC[i];
				if (nr < 0 || nr > MAX_ROWS || nc < 0 || nc > MAX_ROWS) {
					continue;
				}

				if (VISITED[nr][nc]) {
					continue;
				}

				// Only search dfs oil
				if (MATRIX[nr][nc] == 1) {
					stack.add(new Pair(nr, nc));
				}
			}

			VISITED[p.r][p.c] = true;
		}
	}

	public static void main(final String [] args) throws Exception {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		final PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out));
		String [] parts;
		char [] array;
		while (true) {
			parts = reader.readLine().split(" ");
			int M = Integer.parseInt(parts[0]);
			if (M == 0) {
				break;
			}
			// [0, ..N)
			// [0, ..M)
			int N = Integer.parseInt(parts[1]);
			for (int row = 0; row < M; ++ row) {
				array = reader.readLine().toCharArray();
				for (int col = 0; col < N; ++ col) {
					VISITED[row][col] = false;
					if (array[col] == '*') {
						MATRIX[row][col] = 0; // Abscence of oil
					} else {
						MATRIX[row][col] = 1;
					}
				}
			}

			int answer = 0;
			for (int row = 0; row < M; ++ row) {
				for (int col = 0; col < N; ++ col) {
					if (VISITED[row][col]) {
						continue;
					}

					if (MATRIX[row][col] == 1) {
						dfs(row, col);
						answer += 1;
					}
				}
			}

			writer.write(answer + "\n");
		}
		reader.close();
		writer.close();
	}
}

class Pair {
	public int r;
	public int c;

	public Pair(final int r, final int c) {
		this.r = r;
		this.c = c;
	}
}