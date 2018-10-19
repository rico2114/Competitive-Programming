import java.io.*;
import java.util.*;

class E {

    public static int mergeSort(int arr[], int array_size) { 
        int temp[] = new int[array_size]; 
        return _mergeSort(arr, temp, 0, array_size - 1); 
    } 
  
    public static int _mergeSort(int arr[], int temp[], int left, int right) { 
        int mid, inv_count = 0; 
        if (right > left) { 
            mid = (right + left) / 2; 
  
            inv_count = _mergeSort(arr, temp, left, mid); 
            inv_count += _mergeSort(arr, temp, mid + 1, right); 
            inv_count += merge(arr, temp, left, mid + 1, right); 
        } 
        return inv_count; 
    } 

    public static int merge(int arr[], int temp[], int left, int mid, int right) { 
        int i, j, k; 
        int inv_count = 0; 
  
        i = left;
        j = mid;
        k = left;
        while ((i <= mid - 1) && (j <= right)) { 
            if (arr[i] <= arr[j]) { 
                temp[k++] = arr[i++]; 
            } 
            else { 
                temp[k++] = arr[j++]; 
                inv_count = inv_count + (mid - i); 
            } 
        } 
  
        while (i <= mid - 1) 
            temp[k++] = arr[i++]; 
        while (j <= right) 
            temp[k++] = arr[j++]; 
        for (i = left; i <= right; i++) 
            arr[i] = temp[i]; 
  
        return inv_count; 
    }

    public static int taxicabDistance(int p1, int p2, int q1, int q2) {
      return Math.abs(p1 - q1) + Math.abs(p2 - q2);
    } 

    public static void main(final String [] args) throws Exception {
      final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
      final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
      String line = reader.readLine();
      String [] parts = null;
      while (line != null) {
        parts = line.split(" ");
        int R = Integer.parseInt(parts[0]);
        int C = Integer.parseInt(parts[1]);
        int A [] = new int [(R * C) - 1 + 1];
        int offset = 0;
        int rowIdx = 0;
        int colIdx = 0;
        for (int i = 0; i < R; ++ i) {
          parts = reader.readLine().split(" ");
          int cIdx = 0;
          for (String c : parts) {
            int val = Integer.parseInt(c);
            if (val == R * C) {
              rowIdx = i + 1;
              colIdx = cIdx + 1;
            }
            A[offset] = val;
            offset += 1;
            ++ cIdx;
          }
        }

        int parity = mergeSort(A, A.length);
        int txDst = taxicabDistance(rowIdx, colIdx, R, C);
        int total = parity + taxicabDistance(rowIdx, colIdx, R, C);
        if (total % 2 == 0) {
         writer.write("Y\n");
        } else {
         writer.write("N\n");
        }
        line = reader.readLine();
       }
       reader.close();
       writer.close();
      }
}