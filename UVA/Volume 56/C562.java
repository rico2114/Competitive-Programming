import java.util.*;
import java.io.*;

public class C562 {
	
	private static int [] d;
	private static int n;
	private static HashMap<Pair, Integer> memo = new HashMap<Pair, Integer>();

	public static final int solution(int i, int acc) {
		Pair key = new Pair(i, acc);
		if (memo.containsKey(key)) {
			System.out.println("Recurrence CACHED: solution(" + i + ", " + acc + ")");
			return memo.get(key);
		} else {
			System.out.println("Recurrence CALLED: solution(" + i + ", " + acc + ")");
		}

		if (i == n) {
			return Math.abs(acc);
		}

		int ans = Math.min(solution(i + 1, acc - d[i]), solution(i + 1, acc + d[i]));
		memo.put(key, ans);
		return ans;
	}

	public static void main(final String [] args) throws Exception {
		final Scanner reader = new Scanner(System.in);
		final PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out));
		int T = reader.nextInt();
		while (T > 0) {
			n = reader.nextInt();
			d = new int [n];
			for (int i = 0; i < n; ++ i) {
				d[i] = reader.nextInt();
			}
			System.out.println(solution(0, 0));
			memo.clear();
			-- T;
		}
		reader.close();
		writer.close();
	}

	static class Pair {
		private int left;
		private int right;

		public Pair(final int left, final int right) {
			this.left = left;
			this.right = right;
		}

		public int getLeft() {
			return left;
		}

		public int getRight() {
			return right;
		}

		@Override
	    public boolean equals(Object o) {
	        if (o == this) return true;
	        if (!(o instanceof Pair)) {
	            return false;
	        }

	        Pair other = (Pair) o;

	        return getLeft() == other.getLeft() &&
	                getRight() == other.getRight();
	    }

	    //Idea from effective Java : Item 9
	    @Override
	    public int hashCode() {
	        return Objects.hash(left, right);
	    }
	}
}