import java.util.*;
import java.io.*;

public class C11495 {

	public static int COUNT = 0;
      
    /* An auxiliary recursive method that sorts the input array and
      returns the number of inversions in the array. */
	public static void mergeSort(int arr[], int temp[], int left, int right) {
		if (right > left) {
			int mid = (right + left) / 2;
			mergeSort(arr, temp, left, mid);
			mergeSort(arr, temp, mid+1, right);
			merge(arr, temp, left, mid+1, right);
		}
	}
      
    public static void merge(int arr[], int temp[], int left, int mid, int right) {      
		int i = left; /* i is index for left subarray*/
		int j = mid;  /* j is index for right subarray*/
		int k = left; /* k is index for resultant merged subarray*/
		while ((i <= mid - 1) && (j <= right)) {
      		// El elemento de la izquierda es mayor que el de la derecha (Nada raro)
			if (arr[i] <= arr[j]) {
				temp[k++] = arr[i++];
			} else {
          		// El elemento de la derecha si es mayor que el de la izquierda se cuenta las inversiones (mid - i)
				temp[k++] = arr[j++];
				COUNT += (mid - i);
			}
		}
      
		while (i <= mid - 1) {
			temp[k++] = arr[i++];
		}
      
		while (j <= right) {
			temp[k++] = arr[j++];
		}
    
		for (i=left; i <= right; i++) {
			arr[i] = temp[i];
		}
	}

	public static void main(final String [] args) throws Exception {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		final PrintWriter writer  = new PrintWriter(new OutputStreamWriter(System.out));
		String line = reader.readLine();
		while (!line.equals("0")) {
			String [] lineSplit = line.split(" ");
			int N = Integer.parseInt(lineSplit[0]);
			int array [] = new int [N];
			int temp [] = new int[N];
			for (int i = 0; i < N; i++) {
				array[i] = Integer.parseInt(lineSplit[i + 1]);
			}
			mergeSort(array, temp, 0, N - 1);
			if (COUNT % 2 == 0) {
				writer.write("Carlos\n");
			} else {
				writer.write("Marcelo\n");
			}
			COUNT = 0;
			line = reader.readLine();
		}
		writer.close();
		reader.close();
	}
}