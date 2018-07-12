def solve(n, k, ac, realn):
	if k == 0 and ac != realn:
		return 0
	if k == 0 and ac == realn:
		return 1
	if n <= 0:
		return 0
	return solve(n, k - 1, ac + n, realn) + solve(n - 1, k, ac, realn)

N = 63;
K = 59;
print(solve(N, K, 0, N))