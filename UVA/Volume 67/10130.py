def mayor(x, personIndex, taken, size, W, P):
	if personIndex == len(size):
		return x

	ans = 0;
	for i in range(len(taken)):
		if size[personIndex] >= W[i] and taken[i] == False:
			taken[i] = True
			size[personIndex] -= W[i]

			ans = max(ans, mayor(x + P[i], personIndex + 1, taken, size, W, P), mayor(x + P[i], personIndex, taken, size, W, P))

			size[personIndex] += W[i]
			taken[i] = False

	return ans

print(mayor(0, 0, [False, False, False, False, False, False], [23, 20, 20, 26], [26, 22, 4, 18, 13, 9], [64, 85, 52, 99, 39, 54]))