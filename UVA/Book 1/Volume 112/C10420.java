import java.util.*;

public class C10420 {

	/**
	 * TreeMap Preserves ordering of the key
	 * So I Avoid the re ordering of it
	 **/
	private static final TreeMap<String, Integer> ORDERING = new TreeMap<>();

	public static void main(final String [] args) {
		final Scanner scanner = new Scanner(System.in);
		int T = scanner.nextInt();
		scanner.nextLine();
		String [] line = new String[]{""};
		String country = "";
		int cuantity = 0;
		while (T > 0) {
			line = scanner.nextLine().split(" ");
			country = line[0];
			cuantity = 0;
			if (ORDERING.containsKey(country)) {
				cuantity = ORDERING.get(country);
			}
			ORDERING.put(country, cuantity + 1);
			-- T;
		}
		for (Map.Entry<String, Integer> entry : ORDERING.entrySet()) {
			System.out.println(entry.getKey() + " " + entry.getValue());
		}
	}
}