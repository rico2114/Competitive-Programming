import java.io.*;
import java.util.*;

/**
 * So sorry for what you will see
 * This solution includes explainations and is suposed to be easy to understand
 * Perhaps not the most organized nor clean code, but works :-)
 * Notice: I inverted D and U because my grid is backwards
 */

public class a {
	public static final int MAX_N = 1000 + 1;
	public static final int MAX_M = 1000 + 1;

	public static boolean [][][] VISITED = new boolean[2][MAX_M][MAX_N];
	// block out of bounds too!!! don think i need
	public static boolean [][] BLOCKED = new boolean[MAX_M + 10][MAX_N+ 10];
	// Parada (1x1)
	public static final int STANDING = 0;
	// Acostada (2*1)
	public static final int LAYING = 1;

	public static int X;
	public static int Y;

	public static Pair bfs(final int sx, final int sy, final int ex, final int ey) {
		final LinkedList<Pair> stack = new LinkedList<>();
		stack.add(new Pair(sx, sy, STANDING, "", 0));
		while (stack.size() != 0) {
			Pair p = stack.removeLast();
			if (p.s == STANDING) {
				if (p.x == ex && p.y == ey) {
					return p;
				}
				// attempt to move to the left
				int newState = LAYING;
				int nxf = p.x - 1;
				int nyf = p.y;
				int nxs = nxf - 1;
				int nys = nyf;
				if (!(nxf < 0 || nxs < 0 || nxf >= X || nxs >= X)) {
					if (!(VISITED[newState][nxf][nyf] || VISITED[newState][nxs][nys])) {
						if (!(BLOCKED[nxf][nyf] || BLOCKED[nxs][nys])) {
							stack.add(new Pair(nxf, nyf, newState, 0, p.path + "L", p.d + 1));
						}
					}
				}
				// attempt to move to the right
				nxf = p.x + 1;
				nyf = p.y;
				nxs = nxf + 1;
				nys = nyf;
				if (!(nxf < 0 || nxs < 0 || nxf >= X || nxs >= X)) {
					if (!(VISITED[newState][nxf][nyf] || VISITED[newState][nxs][nys])) {
						if (!(BLOCKED[nxf][nyf] || BLOCKED[nxs][nys])) {
							stack.add(new Pair(nxf, nyf, newState, 2, p.path + "R", p.d + 1));
						}
					}
				}
				// attempt to move to the north 
				nxf = p.x;
				nyf = p.y + 1;
				nxs = nxf;
				nys = nyf + 1;
				if (!(nyf < 0 || nys < 0 || nyf >= Y || nys >= Y)) {
					if (!(VISITED[newState][nxf][nyf] || VISITED[newState][nxs][nys])) {
						if (!(BLOCKED[nxf][nyf] || BLOCKED[nxs][nys])) {
							//System.out.println("La movi a arriba y cae acostada");
							stack.add(new Pair(nxf, nyf, newState, 1, p.path + "D", p.d + 1));
						}
					}
				}
				// attempt to move to the south
				nxf = p.x;
				nyf = p.y - 1;
				nxs = nxf;
				nys = nyf - 1;
				if (!(nyf < 0 || nys < 0 || nyf >= Y || nys >= Y)) {
					if (!(VISITED[newState][nxf][nyf] || VISITED[newState][nxs][nys])) {
						if (!(BLOCKED[nxf][nyf] || BLOCKED[nxs][nys])) {
							//System.out.println("La movi a abajo y cae acostada");
							stack.add(new Pair(nxf, nyf, newState, 3, p.path + "U", p.d + 1));
						}
					}
				}
			} else {
				// if fell to the left
				if (p.fellTo == 0) {
					// i can either stand up by moving to the left or going back to the right

					// make it stand up moving to the left
					// attempt to move left
					int nx = p.x - 2;
					int ny = p.y;
					int newState = STANDING;
					if (!(nx < 0|| nx >= X )) {
						if (!(VISITED[newState][nx][ny])) {
							if (!(BLOCKED[nx][ny])) {
								stack.add(new Pair(nx, ny, newState, p.path + "L", p.d + 1));
							}
						}
					}
					// make it stand up moving to the right
					// attempt to move right
					nx = p.x + 1;
					ny = p.y;
					newState = STANDING;
					if (!(nx < 0|| nx >= X )) {
						if (!(VISITED[newState][nx][ny])) {
							if (!(BLOCKED[nx][ny])) {
								stack.add(new Pair(nx, ny, newState, p.path + "R", p.d + 1));
							}
						}
					}

					// or roll it to the north
					nx = p.x;
					ny = p.y + 1;
					int nxs = p.x - 1;
					int nys = ny;
					if (!(ny < 0 || nys < 0 || ny >= Y || nys >= Y)) {
						if (!(nx < 0 || nxs < 0 || nx >= X || nxs >= X)) {
							if (!(VISITED[p.s][nx][ny] || VISITED[p.s][nxs][nys])) {
								if (!(BLOCKED[nx][ny] || BLOCKED[nxs][nys])) {
									// we dont change the state neither the facing to
									stack.add(new Pair(nx, ny, p.s, p.fellTo, p.path + "D", p.d + 1));
								}
							}
						}
					}

					// or roll it to the south
					nx = p.x;
					ny = p.y - 1;
					nxs = p.x - 1;
					nys = ny;
					if (!(ny < 0 || nys < 0 || ny >= Y || nys >= Y)) {
						if (!(nx < 0 || nxs < 0 || nx >= X || nxs >= X)) {
							if (!(VISITED[p.s][nx][ny] || VISITED[p.s][nxs][nys])) {
								if (!(BLOCKED[nx][ny] || BLOCKED[nxs][nys])) {
									// we dont change the state neither the facing to
									stack.add(new Pair(nx, ny, p.s, p.fellTo, p.path + "U", p.d + 1));
								}
							}
						}
					}
				// if fell to the right
				} else if (p.fellTo == 2) {
					// i can either stand up by moving to the left or going back to the right
					// make it stand up moving to the right
					// attempt to move right
					int nx = p.x + 2;
					int ny = p.y;
					int newState = STANDING;
					if (!(nx < 0|| nx >= X )) {
						if (!(VISITED[newState][nx][ny])) {
							if (!(BLOCKED[nx][ny])) {
								stack.add(new Pair(nx, ny, newState, p.path + "R", p.d + 1));
							}
						}
					}

					// make it stand up moving to the left
					// attempt to move left
					nx = p.x - 1;
					ny = p.y;
					newState = STANDING;
					if (!(nx < 0|| nx >= X )) {
						if (!(VISITED[newState][nx][ny])) {
							if (!(BLOCKED[nx][ny])) {
								stack.add(new Pair(nx, ny, newState, p.path + "L", p.d + 1));
							}
						}
					}

					// or roll it to the north
					nx = p.x;
					ny = p.y + 1;
					int nxs = p.x + 1;
					int nys = ny;
					if (!(ny < 0 || nys < 0 || ny >= Y || nys >= Y)) {
						if (!(nx < 0 || nxs < 0 || nx >= X || nxs >= X)) {
							if (!(VISITED[p.s][nx][ny] || VISITED[p.s][nxs][nys])) {
								if (!(BLOCKED[nx][ny] || BLOCKED[nxs][nys])) {
									// we dont change the state neither the facing to
									stack.add(new Pair(nx, ny, p.s, p.fellTo, p.path + "D", p.d + 1));
								}
							}
						}
					}

					// or roll it to the south
					nx = p.x;
					ny = p.y - 1;
					nxs = p.x + 1;
					nys = ny;
					if (!(ny < 0 || nys < 0 || ny >= Y || nys >= Y)) {
						if (!(nx < 0 || nxs < 0 || nx >= X || nxs >= X)) {
							if (!(VISITED[p.s][nx][ny] || VISITED[p.s][nxs][nys])) {
								if (!(BLOCKED[nx][ny] || BLOCKED[nxs][nys])) {
									// we dont change the state neither the facing to
									stack.add(new Pair(nx, ny, p.s, p.fellTo, p.path + "U", p.d + 1));
								}
							}
						}
					}

				// if fell to the north
				} else if (p.fellTo == 1) {
					// i can either stand up by moving to the left or going back to the right
					// make it stand up moving to the right
					// attempt to move to the north
					int nx = p.x;
					int ny = p.y + 2;
					int newState = STANDING;
					if (!(ny < 0|| ny >= Y )) {
						if (!(VISITED[newState][nx][ny])) {
							if (!(BLOCKED[nx][ny])) {
								stack.add(new Pair(nx, ny, newState, p.path + "D", p.d + 1));
							}
						}
					}

					// make it stand up moving to the south
					// attempt to move south
					nx = p.x;
					ny = p.y - 1;
					newState = STANDING;
					if (!(ny < 0|| ny >= Y )) {
						if (!(VISITED[newState][nx][ny])) {
							if (!(BLOCKED[nx][ny])) {
								stack.add(new Pair(nx, ny, newState, p.path + "U", p.d + 1));
							}
						}
					}

					// or roll it to the right
					nx = p.x + 1;
					ny = p.y;
					int nxs = nx;
					int nys = ny + 1;
					if (!(ny < 0 || nys < 0 || ny >= Y || nys >= Y)) {
						if (!(nx < 0 || nxs < 0 || nx >= X || nxs >= X)) {
							if (!(VISITED[p.s][nx][ny] || VISITED[p.s][nxs][nys])) {
								if (!(BLOCKED[nx][ny] || BLOCKED[nxs][nys])) {
									// we dont change the state neither the facing to
									stack.add(new Pair(nx, ny, p.s, p.fellTo, p.path + "R", p.d + 1));
								}
							}
						}
					}

					// or roll it to the left
					nx = p.x - 1;
					ny = p.y;
					nxs = nx;
					nys = ny + 1;
					if (!(ny < 0 || nys < 0 || ny >= Y || nys >= Y)) {
						if (!(nx < 0 || nxs < 0 || nx >= X || nxs >= X)) {
							if (!(VISITED[p.s][nx][ny] || VISITED[p.s][nxs][nys])) {
								if (!(BLOCKED[nx][ny] || BLOCKED[nxs][nys])) {
									// we dont change the state neither the facing to
									stack.add(new Pair(nx, ny, p.s, p.fellTo, p.path + "L", p.d + 1));
								}
							}
						}
					}
				// if fell to the south
				} else if (p.fellTo == 3) {
					// i can either stand up by moving to the north or going back to the south
					// make it stand up moving to the south
					// attempt to move to the south
					int nx = p.x;
					int ny = p.y - 2;
					int newState = STANDING;
					if (!(ny < 0|| ny >= Y )) {
						if (!(VISITED[newState][nx][ny])) {
							if (!(BLOCKED[nx][ny])) {
								stack.add(new Pair(nx, ny, newState, p.path + "U", p.d + 1));
							}
						}
					}

					// make it stand up moving to the north
					// attempt to move north
					nx = p.x;
					ny = p.y + 1;
					newState = STANDING;
					if (!(ny < 0|| ny >= Y )) {
						if (!(VISITED[newState][nx][ny])) {
							if (!(BLOCKED[nx][ny])) {
								stack.add(new Pair(nx, ny, newState, p.path + "D", p.d + 1));
							}
						}
					}

					// or roll it to the right
					nx = p.x + 1;
					ny = p.y;
					int nxs = nx;
					int nys = ny - 1;
					if (!(ny < 0 || nys < 0 || ny >= Y || nys >= Y)) {
						if (!(nx < 0 || nxs < 0 || nx >= X || nxs >= X)) {
							if (!(VISITED[p.s][nx][ny] || VISITED[p.s][nxs][nys])) {
								if (!(BLOCKED[nx][ny] || BLOCKED[nxs][nys])) {
									// we dont change the state neither the facing to
									stack.add(new Pair(nx, ny, p.s, p.fellTo, p.path + "R", p.d + 1));
								}
							}
						}
					}

					// or roll it to the left
					nx = p.x - 1;
					ny = p.y;
					nxs = nx;
					nys = ny - 1;
					if (!(ny < 0 || nys < 0 || ny >= Y || nys >= Y)) {
						if (!(nx < 0 || nxs < 0 || nx >= X || nxs >= X)) {
							if (!(VISITED[p.s][nx][ny] || VISITED[p.s][nxs][nys])) {
								if (!(BLOCKED[nx][ny] || BLOCKED[nxs][nys])) {
									// we dont change the state neither the facing to
									stack.add(new Pair(nx, ny, p.s, p.fellTo, p.path + "L", p.d + 1));
								}
							}
						}
					}
				}
			}
			VISITED[p.s][p.x][p.y] = true;
		}

		return null;
	}

	public static void main(final String [] args) throws Exception {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
		String line = reader.readLine();
		String [] parts = null;
		char [] arr = null;
		while (line != null) {
			parts = line.split(" ");
			X = Integer.parseInt(parts[1]);
			Y = Integer.parseInt(parts[0]);

			for (int i = 0; i < 2; ++ i) {
				for (int x = 0; x < X; ++ x) {
					for (int y = 0; y < Y; ++ y) {
						VISITED[i][x][y] = false;
						BLOCKED[x][y] = false;
					}
				}
			}
			int sx = -1, sy = -1, ex = -1, ey = -1;

			for (int y = 0; y < Y; ++ y) {
				arr = reader.readLine().toCharArray();
				for (int x = 0; x < X; ++ x) {
					if (arr[x] == '#') {
						BLOCKED[x][y] = true;
					} else if (arr[x] == 'C') {
						sx = x;
						sy = y;
						BLOCKED[x][y] = false;
					} else if (arr[x] == 'E') {
						ex = x;
						ey = y;
						BLOCKED[x][y] = false;
					} else {
						BLOCKED[x][y] = false;
					}
				}
			}

			Pair p = bfs(sx, sy, ex, ey);
			if (p == null) {
				writer.write("-1\n");
			} else {
				writer.write(p.d + "\n");
				writer.write(p.path + "\n");
			}

			line = reader.readLine();
		}
		writer.close();
		reader.close();
	}
}

class Pair {
	public int x;
	public int y;
	public int s;
	public String path = "";
	public int d;
	// 0 left
	// 1 north
	// 2 right
	// 3 south
	public int fellTo;

	public Pair(final int x, final int y, final int s, final int fellTo, String path, int dst){
		this.x = x;
		this.y = y;
		this.s = s;
		this.fellTo = fellTo;
		this.path = path;
		this.d = dst;
	}

	public Pair(final int x, final int y, final int s, String path, int dst) {
		this(x, y, s, -1, path, dst);
	}

}