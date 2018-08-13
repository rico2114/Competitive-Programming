import java.io.*;
import java.util.*;

public class C352 {
	
	public static final boolean VISITED[][] = new boolean[25 + 1][25 + 1];
	public static final int BOARD[][] = new int[25 + 1][25 + 1];
	public static final int [] DELTA_X = new int [] {
		-1, -1, -1, 0, 0, 1, 1, 1
	};
	public static final int [] DELTA_Y = new int [] {
		-1, 0, 1, -1, 1, -1, 0, 1
	};

	public static int N;

	public static void floodFill(int x, int y){
		final LinkedList<Pair> queue = new LinkedList<>();
		queue.push(new Pair(x, y));
		while (!queue.isEmpty()) {
			Pair pair = queue.pop();
			if (VISITED[pair.x][pair.y]) {
				continue;
			}

			VISITED[pair.x][pair.y] = true;
			if (BOARD[pair.x][pair.y] == 1) {
				for (int i = 0; i < DELTA_X.length; ++ i) {
					int nx = pair.x + DELTA_X[i];
					int ny = pair.y + DELTA_Y[i];
					if (nx < 0 || ny < 0 || nx >= 26 || ny >= 26) {
						continue;
					}

					if (BOARD[nx][ny] == 1) {
						queue.push(new Pair(nx, ny));
					}
				}
			}
		}
	}

	public static void main(final String [] args) throws Exception {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		final PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out));

		String line = null;
		char [] parts = null;
		int T = 0;
		while ((line = reader.readLine()) != null) {
			N = Integer.parseInt(line);
			int answer = 0;
			for (int i = 0; i < N; ++ i) {
				parts = reader.readLine().toCharArray();
				for (int j = 0; j < N; ++ j) {
					VISITED[i][j] = false;
					if (parts[j] == '1') {
						BOARD[i][j] = 1;
					} else {
						BOARD[i][j] = 0;
					}
				}
			}

			for (int i = 0; i < N; ++ i) {
				for (int j = 0; j < N; ++ j) {
					if (BOARD[i][j] == 1 && !VISITED[i][j]) {
						floodFill(i, j);
						++ answer;
					}
				}
			}
			writer.write("Image number " + (++ T) + " contains " + answer + " war eagles.\n");
		}
		reader.close();
		writer.close();
	}	

	public static class Pair {
		public int x, y;

		public Pair(final int x, final int y) {
			this.x = x;
			this.y = y;
		}
	}
}