#include <iostream>
#include <map>
#include <vector>
#include <queue>

int bfs(std::map< int, std::vector<int> > & adyacencyList, int src, int TTL) {
	std::map< int, int> distance;
	std::map< int, bool> visited;
	std::queue<int> queue;
	for ( std::map< int, std::vector<int> >::iterator it = adyacencyList.begin(); it != adyacencyList.end(); it++ ) {
	    distance[it -> first] = -1;
	}
	queue.push(src);
	distance[src] = 0;
	// Importante! El source debe estar marcado como visitado porque si no puedo ir a uno adyacente y volver a el actualizando su distancia
	// incorrectamente
	visited[src] = true;
	while (!queue.empty()) {
		int s = queue.front(); queue.pop();

		for (int i = 0; i < adyacencyList[s].size(); ++ i) {
			int t = adyacencyList[s][i];
			// Si al nodo al que voy a ir ya esta visitado lo salto
			if (visited[t]) {
				continue;
			}
			// Visito sus adyacentes para evitar que otro adyacente o la recursion de uno de ellos llegue a el antes de ser procesado
			visited[t] = true;
			distance[t] = distance[s] + 1;
			queue.push(t);
		}
	}
	int unreachable = 0;
	for ( std::map< int, std::vector<int> >::iterator it = adyacencyList.begin(); it != adyacencyList.end(); it++ ) {
	    if (distance[it -> first] == -1 || distance[it -> first] > TTL) {
	    	unreachable ++;
	    }
	}
	// Debo tener en cuenta que puede que hayan nodos sueltos!! si los hay tambien son incontables
	return unreachable;
}

int main() {
	// Inicializar valores!!! como puede tener basura puede que incumpla alguna invariante desde el inicio y cague todo
	int nodes  = 0, ttl = 0, cases = 0, count = 0;
	std::ios::sync_with_stdio(false);
	while(std::cin >> nodes) {
		// Hacer las comparaciones fuera de los ciclos
		// Unexpected things happen with cin
		if (nodes == 0) {
			break;
		}
		std::map< int, std::vector<int> > adyacencyList;
		// Escaneo la lista de adyacencia
		while (count ++ < nodes) {
			int src, dest;
			std::cin >> src >> dest;
			adyacencyList[src].push_back(dest);
			adyacencyList[dest].push_back(src);
		} 
		// Escaneo las queries
		int query, ttl;
		while (std::cin >> query >> ttl) {
			// Hacer las comparaciones fuera de los ciclos
			// Unexpected things happen with cin
			if (query == 0 && ttl == 0) {
				break;
			}
			int ans = bfs(adyacencyList, query, ttl);
			std::cout << "Case " << (++ cases) << ": " << ans << " nodes not reachable from node " << query << " with TTL = " << ttl << ".\n";
		} 
		count = 0;
	}
}