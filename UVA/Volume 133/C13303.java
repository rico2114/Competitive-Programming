import java.io.*;
import java.util.*;
import java.math.*;

public class C13303 {
	
	/**
	  * Notice the ASCII caracter of '[' comes first than all lower alphabetic letters
	  **/
	public static final String START = "[a";
	public static final String END = "[b";

	public static void main(final String [] args) throws Exception {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
		String line = reader.readLine();
		String [] parts;
		while (line != null) {
			int N = Integer.parseInt(line);
			HashMap<String, Integer> wordToIdx = new HashMap<>();
			HashMap<String, Integer> wordToFrecuency = new HashMap<>();
			HashMap<String, Integer> wordFrecuencyExtrictlyNext = new HashMap<>();

			HashMap<String, ArrayList<String>> wordToNextWords = new HashMap<>();
			int idx = 0;
			wordToFrecuency.put(END, N);
			for (int i = 0; i < N; ++ i) {
				parts = reader.readLine().split(" ");
				for (int j = -1; j < parts.length; ++ j) {
					String word = "";
					// Initial word
					if (j == -1) {
						word = START;
					} else {
						word = parts[j];
					}

					String next = "";
					if (j == parts.length - 1) {
						next = END;
					} else {
						next = parts[j + 1];
					}
					String stricNext = word + "->" + next; 
					// Calculate the word frecuency
					if (wordFrecuencyExtrictlyNext.containsKey(stricNext)) {
						wordFrecuencyExtrictlyNext.put(stricNext, wordFrecuencyExtrictlyNext.get(stricNext) + 1);
					} else {
						wordFrecuencyExtrictlyNext.put(stricNext, 1);
					}
					// Store the word idx, accumulate frecuencies and generate the other hashes
					if (!wordToIdx.containsKey(word)) {
						wordToIdx.put(word, idx);
						wordToFrecuency.put(word, 1);
						wordToNextWords.put(word, new ArrayList<>());
						wordToNextWords.get(word).add(next);
						
 						++ idx;
					} else {
						// Accumulate frecuency and next
						wordToFrecuency.put(word, wordToFrecuency.get(word) + 1);
						wordToNextWords.get(word).add(next);
					}
				}
			}
			boolean [] VISITED = new boolean[idx];
			String word = START;
			StringBuilder builder = new StringBuilder();
			boolean correct = false;
			// Generate the output text
			while (true) {
				if (word == END) {
					correct = true;
					break;
				}
				final HashMap<String, Boolean> taken = new HashMap<>();
				int repeated = -1;
				int max = -Integer.MAX_VALUE;
				int cidx = 0;
				int overallMax = -Integer.MAX_VALUE;
				int overallRepeated = -1;
				int overallIdx = -1;
				HashMap<Integer, LinkedList<String>> backupDNext = new HashMap<>();
				/** Next words to word w  **/
				for (int i = 0; i < wordToNextWords.get(word).size(); ++ i) {
					String s = wordToNextWords.get(word).get(i);
					if (taken.containsKey(s)) {
						continue;
					}
					int directlyNext = wordFrecuencyExtrictlyNext.get(word + "->" + s);
					if (directlyNext > max) {
						max = directlyNext;
					}
					if (!backupDNext.containsKey(directlyNext)) {
						backupDNext.put(directlyNext, new LinkedList<>());
						backupDNext.get(directlyNext).add(s);
					} else {
						backupDNext.get(directlyNext).add(s);
					}

					taken.put(s, true);
				}
				// Check if the extrictly next words with max repetitions is only 1 elemento if so, we finished with word w
				if (backupDNext.get(max).size() == 1) {
					String ans = backupDNext.get(max).get(0);
					if (ans == END) {
						correct = true;
						break;
					}
					int realIndex = wordToIdx.get(ans);
					if (VISITED[realIndex]) {
						break;
					}
					builder.append(ans).append(" ");
					word = ans;
					VISITED[realIndex] = true;
				} else {
					// Now based on those words that repeat, calculate the ones with higher overall frecuency and store them in a treeset to guarante 
					// alphabetical ordering 
					HashMap<Integer, TreeSet<String>> backupOverall = new HashMap<>();
					for (String s : backupDNext.get(max)) {
						int overall = wordToFrecuency.get(s);
						if (overall > overallMax) {
							overallMax = overall;
						}
						if (!backupOverall.containsKey(overall)) {
							backupOverall.put(overall, new TreeSet<String>());
							backupOverall.get(overall).add(s);
						} else {
							backupOverall.get(overall).add(s);
						}
					}
					String ans = backupOverall.get(overallMax).first();
					if (ans == END) {
						correct = true;
						break;
					}
					int realIndex = wordToIdx.get(ans);
					if (VISITED[realIndex]) {
						break;
					}
					builder.append(ans + " ");
					word = ans;
					VISITED[realIndex] = true;
				
				}
			}
			builder.setLength(builder.length() - 1);
			if (correct) {
				writer.write(builder.toString() + "\n");
			} else {
				writer.write("INFINITE\n");
			}
			line = reader.readLine();
		}
		writer.close();
	}
}