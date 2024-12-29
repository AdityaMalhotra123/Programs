#include <stdio.h>
#include <stdlib.h>

/*
* Swaps integers at index1 and index2 in array.
*/
void swap(int *array, int index1, int index2) 
{
  int swap = array[index1];
  array[index1] = array[index2];
  array[index2] = swap;
}

/*
 * Given an array containing size elements, prints those items to
 * stdout, with single spaces between each element.
 */
void printArray(int *array, int size)
{
  for (int i = 0; i < size; i++) {
    printf("%d ", array[i]);
  }
}

/*
 * Given an int array containing size elements, sorts those elements
 * in place using selection sort.
 */
void sortArray(int *array, int size)
{
  //index of minimum value in unsorted part of array
  int min_index;

  //i represents number of sorted values
  for (int i = 0; i < size; i++) {

    //initialize min_index to first index in unsorted part of array
    min_index = i;

    for (int j = i + 1; j < size; j++) {

      //if current value in unsorted part of array is less than
      //value at min_index, then current value is the minimum value so far
      if (array[j] < array[min_index]) {
        min_index = j;
      }
    }

    //place minimum value of unsorted part of array after sorted values
    swap(array, min_index, i);
  }
}

/* 
 * Requests a positive number from the user via stdin.  Randomly
 * generates that number of ints in the range [0,100).  Prints those
 * numbers before sorting them and prints them again in sorted order.
 */
int main(int argc, char **argv)
{
  //represents size of array (= integer input)
  int size;

  //represents number of valid inputs to scanf
  int v_inputs;

  printf("Please enter a positive integer.\n");
  v_inputs = scanf("%d", &size);

  //handles invalid input
  if (v_inputs < 1) {
    printf("Please enter a positive integer.\n");
    exit(1);
  }

  //srand(time(NULL));

  //if input is positive, allocate memory for array accordingly
  if (size > 0) {
    int *arr = malloc(sizeof(int)*size);

    //generate random numbers in range [0, 100)
    //and store them in arr
    for (int i = 0; i < size; i++) {
      arr[i] = rand() % 100;
    }

    printf("Before: ");
    printArray(arr, size);
    sortArray(arr, size);
    printf("\n");
    printf("After: ");
    printArray(arr, size);

    //free memory allocated for array
    free(arr);
  }

  //invalid input since integer isn't positive
  else {
    printf("Please enter a positive integer.");
  }

  printf("\n");
  return 0;
}
