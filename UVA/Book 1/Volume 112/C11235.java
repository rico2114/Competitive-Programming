import java.util.*;
import java.io.*;

public class C11235 {

	public static final int [] SEQUENCE = new int [100_000];
	public static final int [] FRECUENCY = new int [100_000];

	public static class SegmentTree {

		public static final int [] values = new int[100_000];

		public static SegmentTree build(final int size, final int left, final int right) {
			
		}

	}

	public static void generateFrecuencyArray(final int n) {
		int left = 0; 
		int count = 0;
		for (int i = 0; i < n; ++ i) {
			++ count;

			if (SEQUENCE[i] != SEQUENCE[i + 1]) {
				for (int j = left; j <= i; ++ j) {
					FRECUENCY[j] = count;
				}
				left = i + 1;
				count = 0;
			}
		}
	}
	
	public static void main() {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		final BufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

		do {
			String line = reader.readLine();
			if (line.equals("0")) {
				break;
			}

			String [] split = line.split(" ");
			int n = Integer.parseInt(split[0]);
			int q = Integer.parseInt(split[1]);

			String [] sequenceString = reader.readLine().split(" ");

			for (int i = 0; i < n; ++ i) {
				SEQUENCE[i] = Integer.parseInt(sequenceString[i]);
			}

			generateFrecuencyArray(n);

		} while (true);
	}
}