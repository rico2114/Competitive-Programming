from sys import stdin
from sys import stdout

def main():
	line = stdin.readline()
	case = 1
	while (len(line) != 0):
		P = int(line)
		fullList = list()
		for i in range(P):
			line = stdin.readline().split(';')
			name = line[0]
			score = list()
			pScore = 0
			for k in range(1, len(line)):
				shots = line[k].split(' ')
				for w in range(len(shots)):
					val = int(shots[w])
					if val == 1:
						if w == len(shots) - 1:
							pScore += 2
						else:
							pScore += 1
			fullList.append((name, name.upper(), pScore))
		# - player score so we do it in descending order since if
		# a > b => -a < -b and python by default sorts by default ascending order
		
		fullList = sorted(fullList, key = lambda x: (-x[2], x[1]))
		stdout.write("Case " + str(case) + ":\n")
		for i in range(P):
			stdout.write(fullList[i][0] + " " + str(fullList[i][2]) + "\n")

		case += 1
		line = stdin.readline()

main()