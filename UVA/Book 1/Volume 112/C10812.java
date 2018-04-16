import java.util.*;

public class C10812 {

	public static void main(final String [] args) {
		final Scanner scanner = new Scanner(System.in);
		int T = scanner.nextInt();
		scanner.nextLine();
		int x, y = 0;
		String [] line = null;
		while (T > 0) {
			-- T;
			line = scanner.nextLine().split(" ");
			
			int in_0 = Integer.valueOf(line[0]);
			int in_1 = Integer.valueOf(line[1]);
			// Resolver las ecuaciones (DEJO planteado el abs() para cubrir el caso en el que X >= Y y X < Y)
			y = Math.abs(in_0 - in_1) >> 1;
			x = in_0 - y;

			if ((x + y) == in_0 && (Math.abs(x - y) == in_1)) {
				System.out.println(Math.max(x, y) + " " + Math.min(x, y));
			} else {
				System.out.println("impossible");
			}
		}
	}
}