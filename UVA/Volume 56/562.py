from sys import stdin

memo = {}

def solution(i, acc, n, d):
	global memo
	key = hash((i, acc))

	if i == n:
		memo[key] = abs(acc)
		return memo[key]

	if key in memo:
		return memo[key]

	memo[key] = min(solution(i + 1, acc - d[i], n, d), solution(i + 1, acc + d[i], n, d))
	return memo[key]

# Correct but ne
def solution2(i, acc, n, d):
	if i == n:
		return acc

	return min(abs(solution2(i + 1, acc - d[i], n, d)), abs(solution2(i + 1, acc + d[i], n, d) ))

# This wont work without an accumulate because we can't cast abs by the whole result
def analisis(i, n, d):
	if i == n:
		return 0

	left = abs(analisis2(i + 1, n, d) - d[i])
	right = abs(analisis2(i + 1, n, d) + d[i])
	return min(left, right)

def analisis2(i, n, d):
	if i == n:
		return 0

	left = analisis2(i + 1, n, d) - d[i]
	right = analisis2(i + 1, n, d) + d[i]
	return min(left, right)

# Wrong tabulation
def wrongTabulation(n, d):
	global DP
	for i in range(n - 1, -1, -1):
		DP[i] = min(DP[i + 1] - d[i], DP[i + 1] + d[i])

	return abs(DP[0])

def main():
	global memo
	T = int(stdin.readline())
	for i in range(T):
		N = int(stdin.readline())
		D = [int(x) for x in stdin.readline().split()]

		#print(analisis(0, N, D))
		#print(solution2(0, 0, N, D))
		print(solution(0, 0, N, D))
		#print(wrongTabulation(N, D))

		memo = {}

main()