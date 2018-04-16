import java.util.*;

public class C594 {

	public static void main(final String [] args) {
		final Scanner scanner = new Scanner(System.in);
		int value = -1;
		while (scanner.hasNext()) {
			value = scanner.nextInt();
			// Byte menos significativo
			int value_0 = (value & 0xFF);
			int value_1 = (value >> 8) & 0xFF;
			int value_2 = (value >> 16) & 0xFF;
			// Byte mas significativo
			int value_3 = (value >> 24) & 0xFF;
			// Cambio orden
			int ans = (((value_0 << 24)) | (value_1 << 16) | (value_2 << 8) | (value_3));
			System.out.println(value + " converts to " + ans);
		}
	}
}