import java.io.*;
import java.util.*;
import java.math.*;

public class C100 {

	public static HashMap<Integer, Integer> MEMO = new HashMap<Integer, Integer>();

	public static int calculate(int n) {
		int originalN = n;
		int ans = 1;
		int lookup = 0;
		if (MEMO.containsKey(n)) {
			return MEMO.get(n);
		}

		while (true) {
			if (n == 1) {
				break;
			}
			if (n % 2 != 0) {
				lookup = 3 * n + 1;
			} else {
				lookup = n >> 1;
			}
			if (MEMO.containsKey(lookup)) {
				MEMO.put(originalN, MEMO.get(lookup) + ans);
				return MEMO.get(originalN);
			} else {
				++ ans;
			}
			n = lookup;
		}
		MEMO.put(originalN, ans);
		return MEMO.get(originalN);
	}

	public static void main(final String [] args) throws Exception {
		MEMO.put(0, 0);
		MEMO.put(1, 1);
		MEMO.put(2, 2);
		final Scanner reader = new Scanner(System.in);
		final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
		while (reader.hasNext()) {
			int i = reader.nextInt();
			int j = reader.nextInt();
			int max = -Integer.MAX_VALUE;
			for (int k = Math.min(i, j); k <= Math.max(i, j); ++ k) {
				max = Math.max(max, calculate(k));
			}
			writer.write(i + " " + j + " " + max + "\n");
		}
		writer.close();
	}
}