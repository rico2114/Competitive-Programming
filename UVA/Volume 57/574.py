from sys import stdin
from sys import stdout as writer
from sys import setrecursionlimit

ELEMS = [0 for i in range(20)]
ACUMS = [0 for i in range(20)]
ANS = list()
KEYS = {}
N = 0

def solve(i, temp, acum):
    global ELEMS, ACUMS, ANS, N
    if acum < 0:
        return
    elif acum == 0:
        if temp in KEYS:
            return

        if len(temp) > 0:
            ANS.append(temp[0:len(temp) - 1])
            KEYS[temp] = True
        return

    if i >= N:
        return

    if ACUMS[i] < acum:
        return

    solve(i + 1, (temp + str(ELEMS[i]) + "+"), acum - ELEMS[i])
    solve(i + 1, temp, acum)
    
def main():
    global ELEMS, ACUMS, ANS, N
    setrecursionlimit(50000)
    line = stdin.readline()
    
    while len(line) != 0:
        elems = [int(x) for x in line.split()]
        t = elems[0]
        N = elems[1]
        
        if N == 0:
            break
        
        for i in range(0, N):
            ELEMS[i] = elems[i + 2]

        for i in range(N - 1, -1, -1):
            if i + 1 == N:
                ACUMS[i] = ELEMS[i]
            else:
                ACUMS[i] = ELEMS[i] + ACUMS[i + 1]

        writer.write("Sums of " + str(t) + ":\n")
        solve(0, "", t)

        if len(ANS) > 0:
            for s in ANS:
                writer.write(s + "\n")
        else:
            writer.write("NONE\n")

        ANS = list()
        KEYS = {}

        line = stdin.readline()
        
    stdin.close()
    writer.close()
    
main()
