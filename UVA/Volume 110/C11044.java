import java.io.*;
import java.util.*;

public class C11044 {
	
	public static void main(final String [] args) throws Exception {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

		int T = Integer.parseInt(reader.readLine());
		String [] parts = null;
		while (T > 0) {
			parts = reader.readLine().split(" ");
			int rows = Integer.parseInt(parts[0]);
			int cols = Integer.parseInt(parts[1]);
			int widthAns = (int) Math.ceil((cols - 2) / 3D);
			int rowAns = (int)  Math.ceil((rows - 2) / 3D);
			writer.write(widthAns * rowAns + "\n");
			-- T;
		}

		reader.close();
		writer.close();
	}
}