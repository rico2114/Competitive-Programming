import java.util.*;
import java.io.*;
import java.util.*;

public class C11503 {

	// Conjuntos disyuntos 
	public static void main(final String [] args) throws Exception {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		final PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out));
		int T = Integer.parseInt(reader.readLine());
		while (T > 0) {
			int F = Integer.parseInt(reader.readLine());
			// En el peor de los casos habran F * 2 friends diferenets
			DisjoinSets disjointSets = new DisjoinSets(F * 2);
			HashMap<String, Integer> stringToIndex = new HashMap<String, Integer>();
			int index = 0;
			while (F > 0) {
				String line = reader.readLine();
				String [] split = line.split(" ");
				String nameA = split[0];
				String nameB = split[1];
				int indexA, indexB = -1;
				if (stringToIndex.containsKey(nameA)) {
					indexA = stringToIndex.get(nameA);
				} else {
					indexA = index ++;
					stringToIndex.put(nameA, indexA);
				}
				if (stringToIndex.containsKey(nameB)) {
					indexB = stringToIndex.get(nameB);
				} else {
					indexB = index ++;
					stringToIndex.put(nameB, indexB);
				}
				disjointSets.union(indexA, indexB);
				writer.write(disjointSets.count(indexA) + "\n");
				-- F;
			}
			-- T;
		}
		reader.close();
		writer.close();
	}

	static class DisjoinSets {

        int[] parent, rank;
        int [] count;

        public DisjoinSets(int N) {
            this.parent = new int[N];
            this.rank = new int[N];
            this.count = new int[N];
            // Inicializo los padres de cada uno siendo padre de su respectivo
            for (int i = 0; i < N; i ++) {
            	this.parent[i] = i;
            	this.rank[i] = 0;
            	// Estoy yo mismo en mi lista de amigos :,(
            	this.count[i] = 1;
            }
        }

        void union(int x, int y) {
            int px = find(x);
            int py = find(y);
            // Cuando debo incrementar la cantidad? Cuando no tengamos amigos en comun
            // Si mis gruposd de amigos no son los mismos
            if (px != py) {
            	// el nuevo grupo de amigos va a ser mis amigos mas sus amigos
            	int t = this.count[px] + this.count[py];
            	this.count[px] = t;
            	this.count[py] = t;
            }
            if (rank[px] > rank[py]) {
                parent[py] = px;
            } else {
                parent[px] = py;
            }
            if (rank[px] == rank[py]) {
                rank[py]++;
            }
        }

        int find(int x) {
            if (x != parent[x]) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        int count(int x) {
        	return count[find(x)];
        }
    }
}