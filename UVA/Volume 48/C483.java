import java.io.*;
import java.util.*;
import java.math.*;

public class C483 {
	
	public static void main(final String [] args) throws Exception {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
		String line = reader.readLine();
		char [] array = null;
		final LinkedList<Character> stack = new LinkedList<Character>();
		while (line != null) {
			array = line.toCharArray();
			for (int i = 0; i < line.length(); ++ i) {
				if (array[i] == ' ') {
					while (!stack.isEmpty()) {
						writer.write(stack.pollLast() + "");
					}
					writer.write(" ");
				} else {
					stack.add(array[i]);
				}
			}
			// In case the line ended and i still have stuff in the stack
			while (!stack.isEmpty()) {
				writer.write(stack.pollLast() + "");
			}
			writer.write("\n");
			line = reader.readLine();
		}
		writer.close();
	}	

}