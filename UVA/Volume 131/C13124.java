import java.io.*;
import java.util.*;

public class C13124 {
	
	public static int R, C;
	public static final int [] PRIMES = new int[] {
		2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113
	};
	public static char [][] MATRIX  = new char[101+5][101+5];
	public static final HashMap<Character, Integer> CHAR_TO_VALUE = new HashMap<>();
	public static final char END = '{';	
	private static final StringBuilder builder = new StringBuilder("");

	public static String formMatrix() {
		builder.setLength(0);
		// left to right
		for (int r = 0; r < R; ++ r) {
			for (int c = 0; c < C; ++ c) {
				builder.append(MATRIX[r][c]);
			}
			builder.append(END);
		}
		// top to down
		for (int c = 0; c < C; ++ c) {
			for (int r = 0; r < R; ++ r) {
				builder.append(MATRIX[r][c]);
			}
			builder.append(END);
		}
		// diagonal left to right 
		for (int ii = 0; ii < R; ii++) {
			for (int i = ii, j = 0; i < R && j < C; i++, j++) {
				builder.append(MATRIX[i][j]);
			}
			builder.append(END);
		}
		for (int jj = 1; jj < C; jj++) {
			for (int i = 0, j = jj; i < R && j < C; i++, j++) {
				builder.append(MATRIX[i][j]);
			}
			builder.append(END);
		}
		// diagonal right to left
		for (int ii = 0; ii < R; ii++) {
			for (int i = ii, j = C - 1; i < R && j >= 0; i++, j--) {
				builder.append(MATRIX[i][j]);
			}
			builder.append(END);
		}
		for (int jj = C - 2; jj >= 0; jj--) {
			for (int i = 0, j = jj; i < R && j >= 0; i++, j--) {
				builder.append(MATRIX[i][j]);
			}
			builder.append(END);
		}
		return builder.toString();
	}

	public static boolean sameFrecuency(final int [] wordFrecuency, final int [] matrixFrecuency) {
		for (int i = 0; i < 27; ++ i) {
			if (wordFrecuency[i] != matrixFrecuency[i]) {
				return false;
			}
		}
		return true;
	}

	public static void main(final String [] args) throws Exception {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
		String [] parts;
		String line = reader.readLine();
		int idx = 0;
		while (line != null) {	
			parts = line.split(" ");
			R = Integer.parseInt(parts[0]);
			C = Integer.parseInt(parts[1]);
			int W = Integer.parseInt(parts[2]);
			for (int row = 0; row < R; ++ row) {
				MATRIX[row] = reader.readLine().toCharArray();
			}

			String matrix = formMatrix();
			int ans = 0;
			for (int m = 0; m < W; ++ m) {
				String word = reader.readLine();
				// Pointless to look if the word is larger than the matrix
				if (word.length() >= matrix.length()) {
					continue;
				}
				// calculate the hash of matrix from [l..r]
				// 26 + delimiter (1)
				int [] wordFrecuency = new int[27];
				int [] matrixFrecuency = new int[27];
				for (int i = 0; i < word.length(); ++ i) {
					matrixFrecuency[(int) matrix.charAt(i) - 'a'] += 1;
					wordFrecuency[(int) word.charAt(i) - 'a'] += 1;
				}

				// interval [l, ..., r] both inclusive
				int l = 0;
				int r = word.length() - 1;
				// Loop until we have found the hash or until we finish
				while (r <= matrix.length() - 1) {
					if (!sameFrecuency(wordFrecuency, matrixFrecuency)) {
						if (r == matrix.length() - 1) {
							break;
						}

						matrixFrecuency[(int) matrix.charAt(l) - 'a'] -= 1;
						l += 1;
						r += 1;
						matrixFrecuency[(int) matrix.charAt(r) - 'a'] += 1;
					} else {
						ans += 1;
						break;
					}
				}
			}

			writer.write(ans + "\n");
			line = reader.readLine();
		}
		reader.close();
		writer.close();
	}
}