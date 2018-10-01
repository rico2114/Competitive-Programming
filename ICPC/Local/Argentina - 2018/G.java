import java.io.*;
import java.util.*;

public class G {
	
	public static int [] x = new int[305];
	public static int [] y = new int[305];
	public static int N;
	
	public static double EPS = 0.0000000001;
	
	public static boolean areSame(double l, double r) {
		return Math.abs(r-l) < EPS;
	}
	
	public static final double dstSinRaiz(int a, int b) {
	    return (x[a] - x[b]) * (x[a] - x[b]) + (y[a] - y[b]) * (y[a] - y[b]);
	}
	
	public static int solve() {
		int ans = 0;
		for (int t1 = 0; t1 < N; ++ t1) {
			for (int t2 = t1 + 1; t2 < N; ++ t2) {
				for (int t3 = t2 + 1; t3 < N; ++ t3) {
					double a = (double) dstSinRaiz(t1, t3);
					double b = (double) dstSinRaiz(t2, t1);
					double c = (double) dstSinRaiz(t2, t3);
					double h = (double) Math.max(Math.max(a, b), c);
					if (h == a) {
	                    ans += areSame(a, b + c) ? 1 : 0;
					} else if(h == b) {
	                    ans += areSame(b, a + c) ? 1 : 0;
					} else {
	                    ans += areSame(c, a + b) ? 1 : 0;
					}
				}
			}
		}
		return ans;
	}
	public static void main(final String [] args) throws Exception {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
		
		String line = reader.readLine();
		String [] parts= null;
		while (line != null) {
		    N = Integer.parseInt(line);
		    for (int i = 0; i < N; ++ i) {
		    	parts = reader.readLine().split(" ");
		    	x[i] = Integer.parseInt(parts[0]);
		    	y[i] = Integer.parseInt(parts[1]);
		    }
		    
		    writer.write(solve() + "\n");
			line = reader.readLine();
		}
		
		reader.close();
		writer.close();
	}
}