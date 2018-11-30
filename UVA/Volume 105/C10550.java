import java.io.*;
import java.util.*;

public class C10550 {
	
	public static void main(final String [] args) throws Exception {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
		String line = null;
		String [] parts = null;
		int grads = 360 / 40;
		while (((line = reader.readLine())) != null) {
			if (line.equals("0 0 0 0")) {
				break;
			}
			parts = line.split(" ");
			int A = Integer.parseInt(parts[0]);
			int B = Integer.parseInt(parts[1]);
			int C = Integer.parseInt(parts[2]);
			int D = Integer.parseInt(parts[3]);

			int ans = 720;
			ans += A >= B ? ((A - B) * grads) : (((40 + A) - B) * grads);

			ans += 360;
			ans += B > C ? (((40 + C) - B) * grads) : ((C - B) * grads);

			ans += C >= D ? ((C - D) * grads) : (((40 + C) - D) * grads);
			writer.write(ans + "\n");
		}
		reader.close();
		writer.close();
	}
}