from sys import stdin

def main():
	T = int(stdin.readline())
	while (T > 0):
		stdin.readline()
		order = [int(x) for x in stdin.readline().split()]
		values = [x for x in stdin.readline().split()]

		for i in range(len(order)):
			values[i] = ((order[i], values[i]))

		values.sort(key = lambda x : x[0])
		for order, val in values:
			print(val)

		T -= 1
		if T > 0:
			print("")

main()