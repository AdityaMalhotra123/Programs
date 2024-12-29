#include <stdio.h>
#include <stdlib.h>

#define NUM_STRS 5
#define LENGTH 10

// Reads in NUM_STRS strings from stdin in and inserts into specified array
void readStrings(char strs[NUM_STRS][LENGTH])
{
  printf("Please enter %d strings\n", NUM_STRS);
  for(int i = 0; i < NUM_STRS; i++){
    scanf("%s", strs[i]);
  } 
}

// Returns 1 if first string alphabetically preceds second string.
// Returns 0 if strings are equal or first string follows second string.
// Returns -1 if either string is NULL.
int isLessThan(char *str1, char *str2)
{
  // Check if strings are NULL
  if(str1 == NULL || str2 == NULL){
    return -1;
  }

  // Compare strings character by character, returning as soon as
  // characters differ.
  for(int i = 0; i < LENGTH; i++){
    if(str1[i] == '\0' && str2[i] == '\0'){
      // Both strings at end
      return 0;
    }
    else if(str1[i] == '\0' && str2[i] != '\0'){
      // str1 has finished but str2 has more chars
      return 1;
    }
    else if(str1[i] != '\0' && str2[i] == '\0'){
      // str2 has finished but str1 has more chars
      return 0;
    }
    else if((str1[i] - str2[i]) < 0){
      // str1's char is less than str2's char, so less than
      return 1;
    }
    else if((str1[i] - str2[i]) > 0){
      // str1's char is greater than str2's char, so greater than
      return 0;
    }
  }
}

// Returns the index of the string that occurs alphabetically first
int findFirst(char strs[NUM_STRS][LENGTH])
{
  int smallest = 0;

  for(int i = 1; i < NUM_STRS; i++){
    //compare smallest with current string
    if(isLessThan(strs[i], strs[smallest]) == 1){
      // If current string comes before smallest so far, it is now
      // the new smallest
      smallest = i;
    }
  }
  
  return smallest;
}

// This program reads in strings from the stdin and prints out the
// string that occurs first alphabetically.
int main(int argc, char *argv[])
{
  char strings[NUM_STRS][LENGTH];

  readStrings(strings);

  int index = findFirst(strings);

  printf("The first string alphabetically is %s\n", strings[index]);

  return 0;
}
