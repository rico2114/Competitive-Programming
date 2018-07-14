#include<stdio.h>

int main() {
	int DP[999 + 3][999 + 3] = {};
	int C[52] = {0};
	int L, N, temp;

	while(scanf("%d", &L) == 1 && L) {
        scanf("%d", &N);
        for(int a = 0; a < N; a++) {
            scanf("%d", &C[a]);
        }

        for (int i = 0; i <= L; ++ i) {
 			for (int j = 0; j <= L; ++ j) {
 				DP[i][j] = 21747000;
 			}
 		}
 		//DP = new int [MAX_STICK_LENGTH + OFFSET][MAX_STICK_LENGTH + OFFSET];
 		// Tabulation (I can either generate all UP TO MAX_STICK_LENGTH or up to L)
 		// But notice how the cuts can change so it is worthless to calculate all up to MAX_STICK_LENGTH
 		for (int l = L; l >= 0; -- l) {
 			for (int r = 0; r <= L; ++ r) {
 				int couldCut = 0;
 				for (int i = 0; i < N; ++ i) {
 					// Break because our C[i] is in increasing order
 					if (C[i] > r) {
 						break;
 					}
 					if (C[i] > l && C[i] < r) {
 						couldCut = 1;
 						temp = (DP[l][C[i]] + DP[C[i]][r] + (r - l));
 						if (temp < DP[l][r]) {
 							DP[l][r] = temp;
 						}
 					}
 				}
 				// Base case
 				if (couldCut == 0) {
 					DP[l][r] = 0;
 				}
 			}
 		}
        printf("The minimum cutting is %d.\n", DP[0][L]);
    }

}