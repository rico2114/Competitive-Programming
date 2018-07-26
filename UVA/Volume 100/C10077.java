import java.io.*;
import java.util.*;

public class C10077 {

private static String PATH = "";
private static long M = 0;
private static long N = 0;

/**
 * This is an iterative solution of the binary search
 * The aproach used is some sort of binary search having 3 pivots, left fraction, middle fraction and right fraction
 **/
public static void solve() {
      // lm, ln
 long lNum = 0, lDen = 1;
      // rm, rn
 long rNum = 1, rDen = 0;
 while (true) {
  long mNum = lNum + rNum;
  long mDen = lDen + rDen;

  if (mNum == M && mDen == N) {
    break;
  }
  //double a = (double) M / (double) N;
  //double b = (double) mNum / (double) mDen;
  if (mDen * M < mNum * N) {
 // if (a < b) { Equivalente con lo de arriba y es mas certero porque no tocamos decimales!! super importante
  // evitar divisiones entre decimales
   PATH += "L";
   rNum = mNum;
   rDen = mDen;
  } else {
   PATH += "R";
   lNum = mNum;
   lDen = mDen;
  }
 }
}

public static void main(final String [] args) throws Exception {
 final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
 final PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out));

 String [] parts = null;
 while (true) {
  parts = reader.readLine().trim().split(" ");
  M = Long.parseLong(parts[0]);
  N = Long.parseLong(parts[1]);

  if (M == 1 && N == 1) {
   break;
  }

  solve();
  writer.write(PATH + "\n");
  PATH = "";
 }

 reader.close();
 writer.close();
}
}