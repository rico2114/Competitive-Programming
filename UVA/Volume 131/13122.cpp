#include <bits/stdc++.h>
using namespace std;

const double INF = 1e+30;
double DP[260 + 5][260 + 5][260 + 5] = {};
// This is the conversion of the java solution to the C++ solution for it to pass on UVa
void tabulate(int * x, int *y, int N, int K) {
	for (int lc = N - 1; lc >= 1; -- lc) {
		DP[N][lc][0] = hypot((x[N] - x[lc]), (y[N] - y[lc]));
	}
	for (int p = N - 1; p >= 1; -- p) {
		for (int lc = N - 1; lc >= 1; -- lc) {
			for (int r = 0; r <= K; ++ r) {
				double left = DP[p + 1][p][r] + hypot((x[p] - x[lc]), (y[p] - y[lc]));
				double right = INF;
				if (r - 1 >= 0) {
					right = DP[p + 1][lc][r - 1];
				}
				DP[p][lc][r] = min(left, right);
			}
		}
	}
}

int main() {
	int n, m;
	while (scanf("%d %d", &n, &m) == 2) {
		int x[260 + 5], y[260 + 5];
		for (int i = 0; i < n; i++)
			scanf("%d %d", &x[i + 1], &y[i + 1]);
		/** Tabulation configuration **/
		for (int i = 0; i <= n; ++ i) {
			for (int j = 0; j <= n; ++ j) {
				for (int k = 0; k <= n; ++ k) {
					DP[i][j][k] = INF;
				}
			}
		}
		tabulate(x, y, n, m);
		double ret = DP[2][1][m];
		printf("%.3lf\n", ret);
	}
	return 0;
}
