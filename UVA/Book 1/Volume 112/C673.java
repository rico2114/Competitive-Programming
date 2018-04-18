import java.util.*;
import java.io.*;

public class C673 {

	public static void main(final String [] args) throws Exception {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		final PrintWriter writer  = new PrintWriter(new OutputStreamWriter(System.out));
		int T = Integer.parseInt(reader.readLine());
		while (T > 0) {
			Stack<Character> stack = new Stack<Character>();
			char[] chain = reader.readLine().toCharArray();
			String answer = "Yes";
			for (int i = 0; i < chain.length; i ++) {
				// Si abre la cadena la metemos a la pila
				if (chain[i] == '(' || chain[i] == '[') {
					stack.push(chain[i]);
				} else {
					// Si cierra revisamos que el stack no este vacio (Si lo esta es porque la formula esta mal formada)
					if (stack.size() == 0) {
						answer = "No";
						break;
					}
					char peek = stack.peek();
					stack.pop();
					// Si el char que cierra es un parentesis X reviso que sea igual a su parentesis de cierre de X
					// Si no son iguales entonces la formula no es es bien formada
					if (chain[i] == ')' && peek != '(') {
						answer = "No";
						break;
					}
					if (chain[i] == ']' && peek != '[') {
						answer = "No";
						break;
					}
				}
			}
			// la formula esta bien formada si la respuesta es Yes y el stack esta vacio
			answer = (answer.equals("Yes") && stack.isEmpty()) ? "Yes" : "No";
			writer.write(answer + "\n");
			-- T;
		}
		writer.close();
		reader.close();
	}
}