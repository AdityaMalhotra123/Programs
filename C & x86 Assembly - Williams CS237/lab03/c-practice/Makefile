# The simplest makefile:
#    `make` and `make all` both do the same thing: compile sortInts
# For documentation on Makefiles, please see https://www.gnu.org/software/make/

CC = gcc
CFLAGS = -g -Wall
ASFLAGS = -fsanitize=address

all: sortInts readFile asReadFile

sortInts: sortInts.c
	$(CC) $(CFLAGS) -o sortInts sortInts.c

readFile: readFile.c
	$(CC) $(CFLAGS) -o readFile readFile.c

asReadFile: readFile.c
	$(CC) $(CFLAGS) $(ASFLAGS) -o asReadFile readFile.c

clean:
	rm -f sortInts *.o readFile *.o asReadFile *.o
