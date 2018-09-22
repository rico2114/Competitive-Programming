import java.io.*;
import java.util.*;

/**
 * Beware, ugly code beyond
 **/
public class C13118 {
	
	public static int R;
	public static int C;

	// Go upstairs, downstairs, right, left
	public static final int [] L_D_R = new int [] {
		-1, 1, 0, 0
	};	

	public static final int [] L_D_C = new int [] {
		0, 0, 1, -1
	};

	public static final int [] R_D_R = new int [] {
		-1, 1, 0, 0
	};	

	public static final int [] R_D_C = new int [] {
		0, 0, -1, 1
	};

	public static boolean [][][][] VISITED = new boolean[40 + 5][40 + 5][40 + 5][40 + 5];
	public static boolean [][] BLOCKED = new boolean[40 + 5][40 + 5];

	/**
	 * Basically the strategy I used was
	 * 1) Analize the given test case, then, notice that each penguin can move at any given time so you need to generate all possible movements
	 * 2) Notice that VISITED[i][j] doesnt work because you can have a state A with left penguin, then switch to penguin right and stay
	 * in the same position for penguin left but change penguin right, so notice that visited depends on LEFT + RIGHT penguin
	 * 3) BFS for minimum distance but enqueue both, left and right penguin movements
	 **/
	public static final int bfs(int loveR, int loveC, int rightR, int rightC, int leftR, int leftC) {
		final LinkedList<Storage> queue = new LinkedList<>();
		queue.add(new Storage(leftR, leftC, rightR, rightC, 0));
		while (queue.size() != 0) {
			Storage p = queue.poll();
			if (VISITED[p.lr][p.lc][p.rr][p.rc]) {
				continue;
			}	

			if (p.lr == p.rr && p.lr == loveR && p.lc == p.rc && p.lc == loveC) {
				return p.d;
			}

			// moving left penguin
			for (int i = 0; i < L_D_R.length; ++ i) {
				int mnr = p.lr + L_D_R[i];
				int mnc = p.lc + L_D_C[i];
				// reactive right penguin
				int rnr = p.rr + R_D_R[i];
				int rnc = p.rc + R_D_C[i];
				if (!BLOCKED[mnr][mnc]) {
					if (BLOCKED[rnr][rnc]) {
						rnr = p.rr;
						rnc = p.rc;
					}
				} else {
					continue;
				}
				// out of bounds
				if (mnr < 1 || mnr > R || mnc < 1 || mnc > C || 
					rnr < 1 || rnr > R || rnc < 1 || rnc > C) {
					continue;
				}
				// Useless to go back
				if (VISITED[mnr][mnc][rnr][rnc]) {
					continue;
				}			
				queue.add(new Storage(mnr, mnc, rnr, rnc, p.d + 1));
			}

			// moving right penguin
			for (int i = 0; i < L_D_R.length; ++ i) {
				int mnr = p.rr + L_D_R[i];
				int mnc = p.rc + L_D_C[i];
				// reactive left penguin
				int rnr = p.lr + R_D_R[i];
				int rnc = p.lc + R_D_C[i];
				if (!BLOCKED[mnr][mnc]) {
					if (BLOCKED[rnr][rnc]) {
						rnr = p.lr;
						rnc = p.lc;
					}
				} else {
					continue;
				}
				// out of bounds
				if (mnr < 1 || mnr > R || mnc < 1 || mnc > C || 
					rnr < 1 || rnr > R || rnc < 1 || rnc > C) {
					continue;
				}
				// Useless to go back
				// Notice how it is necessarily to switch arguments in VISITED because now the moving penguin is the right one
				if (VISITED[rnr][rnc][mnr][mnc]) {
					continue;
				}			
				queue.add(new Storage(rnr, rnc, mnr, mnc, p.d + 1));
			}
			VISITED[p.lr][p.lc][p.rr][p.rc] = true;
		}
		return -1;
	}

	public static void main(final String [] args) throws Exception {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		final PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out));
		String [] parts = null;
		String line;
		char [] arr;
		while (true) {
			line = reader.readLine();
			if (line == null) {
				break;
			}
			parts = line.split(" ");
			R = Integer.parseInt(parts[0]);
			C = Integer.parseInt(parts[1]);
			parts = reader.readLine().split(" ");
			int loveR = Integer.parseInt(parts[0]);
			int loveC = Integer.parseInt(parts[1]);
			int rightR = Integer.parseInt(parts[2]);
			int rightC = Integer.parseInt(parts[3]);
			int leftR = Integer.parseInt(parts[4]);
			int leftC = Integer.parseInt(parts[5]);

			for (int row = 1; row <= R; ++ row) {
				arr = reader.readLine().toCharArray();
				for (int col = 1; col <= C; ++ col) {
					char c = arr[col - 1];
					if (c == '#') {
						BLOCKED[row][col] = true;
					} else {
						BLOCKED[row][col] = false;
					}
				}
			}

			// Bloqueo por arriba y por abajo
			for (int col = 0; col <= C + 1; ++ col) {
				BLOCKED[0][col] = true;
				BLOCKED[R + 1][col] = true;
			}
			// bloqueo extremo izq y derecho
			for (int row = 0; row <= R + 1; ++ row) {
				BLOCKED[row][0] = true;
				BLOCKED[row][C + 1] = true;
			}

			for (int row = 1; row <= R; ++ row) {
				for (int col = 1; col <= C; ++ col) {
					for (int row_ = 1; row_ <= R; ++ row_) {
						for (int col_ = 1; col_ <= C; ++ col_) {
							VISITED[row][col][row_][col_] = false;
						}
					}
				}
			}

			int d = bfs(loveR, loveC, rightR, rightC, leftR, leftC);
			if (d == -1) {
				writer.write("NO LOVE\n");
			} else {
				writer.write(d + "\n");
			}

		}
		writer.close();
	}
}

class Storage {
	public int lr;
	public int lc;

	public int rr;
	public int rc;
	public int d;

	public Storage(final int lr, final int lc, final int rr, final int rc, final int d) {
		this.lr = lr;
		this.lc = lc;
		this.rr = rr;
		this.rc = rc;
		this.d = d;
	}
}