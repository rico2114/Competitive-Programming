# Esta solucion cuenta con el problema del orden de la denominacion (se cuentan repetidos)
def solve(x, D):
	ans = 0
	if x == 0:
		return 1
	elif x < 0:
		return 0

	for i in range(len(D)):
		ans += solve(x - D[i], D)

	return ans

# Este soluciona el problema de los repetidos al agregar un orden en la denominacion
def solve2(x, D, d_i):
	ans = 0
	if x == 0:
		return 1
	if x < 0 or d_i < 0:
		return 0

	return solve2(x - D[d_i], D, d_i) + solve2(x, D, d_i - 1);

# Misma solucion de solve2 pero con bottom up approach
def solve2BottomUp(x, D):
	DP = [[0 for i in range(len(D))] for i in range(x)]
	for i in range(len(D)):
		DP[0][i] = 1

	for i in range(1, x):
		for j in range(len(D)):
			# A and B pointers in the DP array
			A, B = (i - D[j], j), (i, j - 1)
			A_val, B_val = -1, -1
			# Handling base case (x < 0 or di < 0) -> 0
			if A[0] < 0:
				A_val = 0
			if B[1] < 0:
				B_val = 0
			# If is not base case use the DP Table
			if A_val == -1:
				A_val = DP[A[0]][A[1]]
			if B_val == -1:
				B_val = DP[B[0]][B[1]]
			# Use the dp table based on the previously calculated values
			DP[i][j] = A_val + B_val

	return DP[x - 1][len(D) - 1]

x = 11
print(solve2BottomUp(x + 1, [1, 5, 10, 25, 50]))