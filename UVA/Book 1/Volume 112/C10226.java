import java.util.*;
import java.io.*;

public class C10226 {

	public static void main(final String [] args) throws Exception {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		final PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out));
		int T = Integer.parseInt(reader.readLine()) + 1;
		int initialT = T;
		while (T > 0) {
			String line = "";
			int trees = 0;
			TreeMap<String, Integer> mapping = new TreeMap<String, Integer>();
			while ((line = reader.readLine()) != null) {
				if (line.length() == 0) {
					break;
				} 
				int count = 0;
				if (mapping.containsKey(line)) {
					count = mapping.get(line);
				}
				mapping.put(line, count + 1);
				++ trees;
			}
			for (Map.Entry<String, Integer> entry : mapping.entrySet()) {
				writer.write(String.format("%s %.4f\n", entry.getKey(), (entry.getValue() * 100.0) / trees).replace(",", "."));
			}
			-- T;
			if (T > 0 && T != initialT - 1) {
				writer.write("\n");
			}
		}
		reader.close();
		writer.close();
	}
}