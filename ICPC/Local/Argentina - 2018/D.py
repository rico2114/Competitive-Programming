from sys import stdin
from sys import stdout

def main():
	line = stdin.readline()
	while len(line) != 0:
		N, M = [int(x) for x in line.split()]

		maximizeMPos = 1 * (M - 1) + (N - M) * 0.5
		maximizeOurPos = maximizeMPos + 0.5
		stdout.write("{0:0.1f}\n".format(maximizeOurPos))

		line = stdin.readline()

main()