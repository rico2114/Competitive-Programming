from sys import stdin
from sys import stdout

def main():
	T = int(stdin.readline())
	case = 1
	while T > 0:
		arr = [int(x) for x in stdin.readline().split()]
		arr = sorted(arr)

		stdout.write("Case " + str(case) + ": " + str(arr[1]) + "\n")
		case += 1
		T -= 1

	stdout.close()

main()