import java.io.*;
import java.util.*;

public class C272 {
	
	public static void main(final String [] args) throws Exception {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
		String line = reader.readLine();
		StringBuilder output = new StringBuilder();
		boolean useCurved = true;
		while (line != null) {
			for (char c : line.toCharArray()) {
				if (c == '\"') {
					if (useCurved) {
						output.append("``");
					} else {
						output.append("''");
					}
					useCurved = !useCurved;
				} else {
					output.append(c);
				}
			}
			line = reader.readLine();
			output.append("\n");
		}
		writer.write(output.toString());
		reader.close();
		writer.close();
	}
}