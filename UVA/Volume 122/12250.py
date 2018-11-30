from sys import stdin
from sys import stdout

def main():
	text = stdin.readline().strip()
	case = 1
	while text != "#":
		language = "UNKNOWN"
		if text == "HELLO":
			language = "ENGLISH"
		elif text == "HOLA":
			language = "SPANISH"
		elif text == "HALLO":
			language = "GERMAN"
		elif text == "BONJOUR":
			language = "FRENCH"
		elif text == "CIAO":
			language = "ITALIAN"
		elif text == "ZDRAVSTVUJTE":
			language = "RUSSIAN"

		stdout.write("Case " + str(case) + ": " + language + "\n")
		case += 1
		text = stdin.readline().strip()

	stdout.close()

main()