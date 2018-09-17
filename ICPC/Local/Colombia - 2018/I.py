import sys

from sys import stdin
from sys import stdout
from collections import deque

MAX_N = 50000 + 5
G = [list() for x in range(MAX_N)]
PREORDER = [-1 for x in range(MAX_N)]
IS_IN_S_STACK = [False for i in range(MAX_N)]
S, P, C = list(), list(), 0
TOTAL_COMPONENTS = 0

def gabow(v):
	global G, S, P, C, TOTAL_COMPONENTS, PREORDER
	PREORDER[v] = C
	C += 1
	S.append(v); P.append(v)
	IS_IN_S_STACK[v] = True

	for w in G[v]:
		if PREORDER[w] == -1:
			gabow(w)
		else:
			if IS_IN_S_STACK[w]:
				while True:
					top = P[-1]
					if PREORDER[top] <= PREORDER[w]:
						break

					P.pop()

	if v == P[-1]:
		while True:
			top = S.pop()
			IS_IN_S_STACK[top] = False
			if top == v:
				break
		P.pop();
		TOTAL_COMPONENTS += 1

def main():
	global G, S, P, C, TOTAL_COMPONENTS, PREORDER
	sys.setrecursionlimit(50000)

	line = stdin.readline()
	while (len(line) != 0):
		N, M = [int(x) for x in line.split()]

		TOTAL_COMPONENTS = 0
		for i in range(1, N + 1):
			G[i] = list()
			PREORDER[i] = -1;

		for i in range(M):
			line = stdin.readline().split()
			if line[0] == "1":
				u = int(line[1])
				v = int(line[2])
				G[u].append(v)
			else:
				k = int(line[0])
				for i in range(1, k):
					u = int(line[i])
					v = int(line[i + 1])
					G[u].append(v)
					G[v].append(u)

		for i in range(1, N + 1):
			if PREORDER[i] == -1:
				gabow(i)

		# If there are more than 1 components then clearly the graph is not strongly connected
		if TOTAL_COMPONENTS != 1:
			stdout.write("NO\n")
		else:
			stdout.write("YES\n")

		line = stdin.readline()

	stdout.close()

main()