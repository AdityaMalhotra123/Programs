#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#define MAX_NAME_LENGTH 50
#define MAX_WORD_LENGTH 80

/*
 * Prints each of the num strings on its own line to stdout.  
 */
void printStrings(char **strings, int num)
{
  for (int i = 0; i < num; i++) {
    printf("%s ", strings[i]);
  }
  printf("\n");
}

/*
 * Frees memory for each of the num strings and frees memory for the
 * entire array.
 */
void freeStrings(char **strings, int num)
{
  for (int i = 0; i < num; i++) {
    free(strings[i]);
  }
  free(strings);
}

/*
* Swaps C-Style strings at index1 and index2 in array.
*/
void swap(char **array, int index1, int index2) 
{
  char *swap = array[index1];
  array[index1] = array[index2];
  array[index2] = swap;
}

/* 
 * Uses selection sort to sort the num strings in increasing order.
 */
void sortStrings(char **strings, int num)
{
  //index of smallest string in unsorted part of array
  int min_index;

  //i represents number of sorted strings
  for (int i = 0; i < num; i++) {

    //initialize min_index to first index in unsorted part of array
    min_index = i;

    for (int j = i + 1; j < num; j++) {
      //if current string in unsorted part of array is smaller than
      //string at min_index, then current string is the smallest string so far
      if (strcmp(strings[j], strings[min_index]) < 0) {
        min_index = j;
      }
    }

    //place smallest string of unsorted part of array after sorted strings
    swap(strings, min_index, i);
  }
}

/*
 * Reads num strings from the file and stores them into an array of C
 * style strings.  If the open file contains fewer than num strings,
 * prints an error message and exits.  If the open file contains more
 * than num strings, reads in the first num strings in the file.
 * Returns the array with the stored strings.
 */
char **readFile(FILE *input, int num)
{
  //allocate memory for array- each element in array is an array of char
  //thus, memory needed = size of each char array * number of strings
  char **array = (char **) malloc(sizeof(char *)*num);

  //counts number of strings read
  int count = 0;

  //represents number of valid inputs to fscanf()
  int v_inputs;

  //represents the string just read
  char str[MAX_WORD_LENGTH];

  //iterate through first num strings
  while (count < num) {

    //end of file before num strings found
    if (feof(input)) {
      printf("Not enough words in file. \n");
      exit(1);
    }
    
    //if string not recognized, ignore it as 
    //there may be more valid strings
    v_inputs = fscanf(input, "%s", str);
    if (v_inputs < 1) {
      continue;
    }

    //allocate memory for each string, which is an array of char
    //include '\0' in length of string for memory allocation
    array[count] = malloc(sizeof(char)*(strlen(str) + 1));

    //store string just read in array
    strcpy(array[count], str);

    //increment number of strings read
    count++;
  }
  return array;
}

/*
 * Asks the user to enter a filename and number of strings to read
 * from the file via stdin.  Prints unsorted strings in the order they
 * were read in to stdout.  Then sorts strings before printing the
 * sorted strings to stdout.
 */
int main(int argc, char **argv)
{
  printf("Please enter a a filename.\n");

  //represents filename input
  char filename[MAX_NAME_LENGTH];

  //represents number of valid inputs to scanf
  int v_inputs = scanf("%s", filename);

  //represents file named as 'filename' input
  FILE *fp;

  //handles invalid input
  if (v_inputs < 1) {
    printf("Please enter a filename. ");
    exit(1);
  }

  //if file doesn't exist or isn't readable, print errror message
  if ((fp = fopen(filename, "r")) == 0) {
    printf("Can't find/open file.\n");
    exit(1);
  }

  //valid filename
  else {
    printf("Please enter the number of strings to read. ");

    //represents size of array of strings (= integer input)
    int size;

    //represents number of valid inputs to new scanf()
    v_inputs = scanf("%d", &size);

    //handles invalid input
    if (v_inputs < 1) {
      printf("Please enter a positive integer.");
      fclose(fp);
      exit(1);
    }

    //valid input
    if (size > 0) {

      //represents array of strings
      char **arr = readFile(fp, size);

      printf("Before: ");
      printStrings(arr, size);
      sortStrings(arr, size);
      printf("After: ");
      printStrings(arr, size);
      freeStrings(arr, size);
      fclose(fp);
    }

    //invalid input since integer isn't positive
    else {
      printf("Please enter a positive integer.");
    }
  }

  return 0;
}



