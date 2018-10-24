import java.io.*;
import java.util.*;

public class C13302 {
	
	public static int L, N;

	public static long gcd(long a, long b) {
	    while(b > 0) {
	        long c = a % b;
	        a = b;
	        b = c;
	    }
	    return a;
	}

	public static String asFraction(long a, long b) {
	    long gcm = gcd(a, b);
	    return (a / gcm) + "/" + (b / gcm);
	}

	public static void main(final String [] args) throws Exception {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
		String line = reader.readLine();
		String [] parts = null;
		while (line != null) {
			parts = line.split(" ");
			L = Integer.parseInt(parts[0]);
			N = Integer.parseInt(parts[1]);
			int D = Integer.parseInt(parts[2]);

			if (L == N && N == D && L == 0) {
				break;
			}
			/** Note: Use doubles for better precision **/
			double min = Float.MAX_VALUE;
			long bestTop = 0;
			long bestBot = 0;
			double bestCalc = Float.MAX_VALUE;
			for (int i = N; i >= 1; -- i) {
				double rate = (double) L / i;
				if (rate > D) {
					if (Math.abs(rate - D) < Math.abs(bestCalc - D)) {
						bestTop = L;
						bestBot = i;
					}
					break;
				} else if (rate == D) {
					bestTop = L;
					bestBot = i;
					break;
				}

				long high = (long) Math.ceil(D / rate) + 1;
				long mid = 0;
				long low = 0;
				double calc = 0;
				double bestCalcLocal = 0;
				long bestMid = 0;
				while (low + 1 != high) {
					mid = (low + high) >> 1;
					calc = rate * mid;
					if (calc > D) {
						high = mid;
						bestMid = mid;
						bestCalcLocal = calc;
					} else if (calc < D) {
						low = mid;
					} else {
						bestMid = mid;
						bestCalcLocal = calc;
						break;
					}
				}
				if (min > bestCalcLocal) {
					bestTop = bestMid * L;
					bestBot = i;
					min = bestCalcLocal;
					bestCalc = Math.min(bestCalc, min);
				}
			}
			writer.write(asFraction(bestTop, bestBot) + "\n");
			line = reader.readLine();
		}
		writer.close();
		reader.close();
	}	

}