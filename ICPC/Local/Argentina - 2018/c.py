from sys import stdin
from sys import stdout

w = [0, 4, 6, 2, 4, 8, 3, 2, 6]
o = [1, 2, 2, 4, 8, 3, 2, 6]

def ans(x):
	if x >= 0 and x <= 8:
		return w[x]
	elif x >= 12 and x <= 16:
		return o[x%9]
	elif x >= 17:
		return o[(x-1)%8]

def main():
	line = stdin.readline()
	while (len(line) != 0):
		queries = [int(x) for x in line.split()]
		for v in queries:
			stdout.write(str(ans(v)))

		stdout.write("\n")
		line = stdin.readline()

	stdout.close()
	stdin.close()

main()