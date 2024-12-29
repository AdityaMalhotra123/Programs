# The simplest makefile:
#    `make` and `make all` both do the same thing: compile all programs
#    `make loop` compiles the loop_sum program
#    `make palindrome` compiles the is_palindrome program
# For documentation on Makefiles, please see https://www.gnu.org/software/make/

all:
	gcc -Wall -g -o loop_sum loop_sum.c
	gcc -Wall -g -o is_palindrome is_palindrome.c

loop:
	gcc -Wall -g -o loop_sum loop_sum.c

palindrome:
	gcc -Wall -g -o is_palindrome is_palindrome.c

clean:
	rm -f is_palindrome loop_sum
