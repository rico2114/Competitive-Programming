import java.util.*;
import java.io.*;
import java.util.*;

public class C793 {

	// Conjuntos disyuntos 
	public static void main(final String [] args) throws Exception {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		final PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out));
		int T = Integer.parseInt(reader.readLine());
		reader.readLine();
		while (T > 0) {
			// pcs + 1 porque la entrada utiliza numeros desde (0..n+1]
			int pcs = Integer.parseInt(reader.readLine()) + 1;
			DisjoinSets disjointSets = new DisjoinSets(pcs);
			String line = "";
			int unsuc = 0;
			int suc = 0;
			while ((line = reader.readLine()) != null) {
				if (line.length() == 0) {
					break;
				}
				String split [] = line.split(" ");
				char opt = split[0].charAt(0);
				int pc1 = Integer.parseInt(split[1]);
				int pc2 = Integer.parseInt(split[2]);

				if (opt == 'c') {
					disjointSets.union(pc1, pc2);
				} else {
					if (disjointSets.find(pc1) == disjointSets.find(pc2)) {
						suc ++;
					} else {
						unsuc ++;
					}
				}
			}
			writer.write(suc + "," + unsuc + "\n");

			-- T;
			if (T > 0) {
				writer.write("\n");
			}
		}
		reader.close();
		writer.close();
	}

	static class DisjoinSets {

        int[] parent, rank;

        public DisjoinSets(int N) {
            this.parent = new int[N];
            this.rank = new int[N];
            // Inicializo los padres de cada uno siendo padre de su respectivo
            for (int i = 0; i < N; i ++) {
            	this.parent[i] = i;
            	this.rank[i] = 0;
            }
        }

        void union(int x, int y) {
            int px = find(x);
            int py = find(y);
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
    }
}