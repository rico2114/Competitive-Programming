import java.io.*;
import java.util.*;

// Brief proof explaination: https://drive.google.com/open?id=1waEPwMAgX15nlOGnKF1G2BpKZbjsjeI4
// Author: Juan Sebastian Quiceno, Juan.2114@hotmail.com

public class C11369 {
	
	public static void main(final String [] args) throws Exception {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
		int T = Integer.parseInt(reader.readLine());
		String [] parts = null;
		while (T > 0) {
			int n = Integer.parseInt(reader.readLine());
			parts = reader.readLine().split(" ");
			int [] cost = new int[n];
			for (int i = 0; i < n; ++ i) {
				cost[i] = Integer.parseInt(parts[i]);
			}
			// Increasing order
			Arrays.sort(cost);
			// Calculate max in some sort of window scrolling way
			int max = 0;
			int left = n - 1;
			int carry = 1;
			while (left >= 0) {
				if (carry == 3) {
					max += cost[left];
					carry = 1;
				} else {
					++ carry;
				}
				-- left;
			}
			writer.write(max + "\n");
			-- T;
		}
		reader.close();
		writer.close();
	}
}