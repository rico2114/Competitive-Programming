from sys import stdin
from sys import stdout
from math import ceil

def main():
    line = stdin.readline()
    while(len(line) != 0):
        n, w, d, wscoins = [int(x) for x in line.split()]

        # n * (n + 1) / 2
        summ = w * ((n - 1) * (n) / 2)

        # Esto deberia ser cierto si esta la basket en el N 
        # Pero curiosamente en el input no es asi, esta malo..
        if summ == wscoins:
            stdout.write(str(n) + "\n")
        else:
            lo = 1
            hi = n
            found = False
            while lo + 1 != hi:
                mid = (lo + hi) >> 1

                tmp = summ - (mid * w) + mid * (w - d)

                if tmp < wscoins:
                    hi = mid
                elif tmp > wscoins:
                    lo = mid
                else:
                    stdout.write(str(mid) + "\n")
                    found = True
                    break
            # Ojo en Busqueda binaria siempre probar con lo al final
            # Porque lo al final no se computa
            if not found:
                # Pruebe con el mismo lo, ya que que si lo + 1 == hi falta probar el mismo lo
                tmp = summ - (lo * w) + lo * (w - d)
                if tmp == wscoins:
                    stdout.write(str(lo) + "\n")
                else:
                    # Si no, es n el resultado final
                    stdout.write(str(n) + "\n")

        line = stdin.readline()

    stdout.close()
    stdin.close()

main()
