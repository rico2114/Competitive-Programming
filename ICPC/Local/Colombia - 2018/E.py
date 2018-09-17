from sys import stdin
from sys import stdout

def main():
	line = stdin.readline()
	while (len(line) != 0):
		R,C = [int(x) for x in line.split()]
		A = [0 for i in range((R * C))]
		offset = 0
		rowIdx = 0
		for i in range(R):
			line = stdin.readline().split()
			for e in line:
				x = int(e)
				if x == R * C:
					rowIdx = (i + 1)
					A[offset] = float('inf')
				else:
					A[offset] = x

				offset += 1

		count = 0
		for i in range(len(A)):
			# Empty square
			if A[i] == float('inf'):
				continue

			for j in range(i + 1, len(A)):
				# Notice nothing is greater than inf so it wont do anything harmful
				if A[i] > A[j]:
					count += 1		

		tot = count + rowIdx
		if tot % 2 == 0:
			stdout.write("Y\n")
		else:
			stdout.write("N\n")

		line = stdin.readline()
		
	stdout.close()
	stdin.close()
main()

