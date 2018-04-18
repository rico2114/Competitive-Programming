import java.util.*;
import java.io.*;

public class C299 {
	
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
		int T = Integer.parseInt(reader.readLine());
		while (T > 0) {
			int length = Integer.parseInt(reader.readLine());
			int [] array = new int[length];
			int [] temp = new int[length];
			String [] elements = reader.readLine().split(" ");
			for (int i = 0; i < length; i ++) {
				array[i] = Integer.parseInt(elements[i]);
			}
			mergeSort(array, temp, 0, length - 1);
			System.out.println("Optimal train swapping takes " + COUNT + " swaps.");
			COUNT = 0;
			-- T;
		}
		reader.close();
	}
}