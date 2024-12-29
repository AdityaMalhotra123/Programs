# The simplest makefile:
#    `make` and `make all` both do the same thing: compile all programs
#    `make loop` compiles the loop_sum program
#    `make palindrome` compiles the is_palindrome program
# For documentation on Makefiles, please see https://www.gnu.org/software/make/


test_set: set.c test_set.c set.h
	gcc -g set.c test_set.c -o test_set

clean:
	rm -f test_set
