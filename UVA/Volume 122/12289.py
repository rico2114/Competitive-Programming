from sys import stdin
from sys import stdout

def main():
	T = int(stdin.readline())
	while T > 0:
		word = stdin.readline().strip()
		if len(word) == 5:
			stdout.write("3\n")
		else:
			if ('o' in word and 'n' in word) or ('o' in word and 'e' in word) or ('n' in word and 'e' in word):
				stdout.write("1\n")
			else:
				stdout.write("2\n")

		T -= 1

	stdout.close()

main()