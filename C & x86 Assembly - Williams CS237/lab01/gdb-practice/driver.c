#include <stdio.h>
#include <stdlib.h>
#include <time.h>

// This creates a constant "SIZE", but it IS NOT a variable.
// The preproccessor copies/pastes 5 in place of all instances
// of "SIZE" in the code. You cannot `p SIZE` using gdb
#define SIZE 5

void printArray(int array[SIZE][SIZE])
{
  for(int i = 0; i < SIZE; i++){
    for(int j = 0; j < SIZE; j++){
      printf("%d ", array[i][j]);
    }
    printf("\n");
  }
  printf("\n");
}

void doStuff(int arg[SIZE][SIZE])
{
  int tmp[SIZE][SIZE];

  for(int i = 0; i < SIZE; i++){
    for(int j = 0; j < SIZE; j++){
      tmp[i][j] = arg[j][i];
    }
  }

  for(int i = 0; i < SIZE; i++){
    for(int j = 0; j < SIZE; j++){
      arg[i][j] = tmp[i][j];
    }
  }
}

int main(int argc, char *argv[])
{
  srand(time(NULL));

  int array[SIZE][SIZE];
  for(int i = 0; i < SIZE; i++){
    for(int j = 0; j < SIZE; j++){
      array[i][j] = rand() % 10;
    }
  }

  doStuff(array);

  return 0;
}
