#include <stdio.h>
#include <string.h>

//Maximum length of character array
#define MAX_LENGTH 50

/*
 * Determines whether a substring (char array) within certain index values
 * is a palindrome
 * Pre: integers initial_index and final_index are >= 0
 * char word[] represents input stirng
 * initial_index represents starting index within string
 * final_index represents last index withinin string
 * returns 1 if word is a palindrome and 0 if it isn't
*/
int is_substring_palindrome(char word[], int initial_index, int final_index)
{
  //base case: if current portion of string contains one or zero characters,
  //it is a palindrome
  if (final_index == initial_index || final_index < initial_index)
    {
      return 1;
    }
  
  if (word[initial_index] == word[final_index])
    {
      return is_substring_palindrome(word, initial_index + 1, final_index - 1);
    }
  
  return 0;
}

/*
 * Determines whether a string (char array) within certain index values
 * is a palindrome
 * char word[] represents input stirng
 * returns 1 if word is a palindrome and 0 if it isn't
*/
int is_palindrome(char word[])
{
  return is_substring_palindrome(word, 0, strlen(word) - 1);
}

/*
 * Inputs string word from user and prints whether it is a palindrome or not
 * Post: Prints appropriate messages for when word is a palindrome and when not
*/
int main(int argc, char *argv[])
{
  printf("%s\n", "Enter a string");
  char word[MAX_LENGTH];

  //represents number of valid string inputs
  int v_inputs = scanf("%s", word);

  //handles empty string (Ctrl-D)
  if (v_inputs == -1)
    {
       printf("%s \n", "Empty string is a palindrome.");
    }
  else if ((is_palindrome(word) == 1) && (v_inputs == 1))
    {
      printf("%s %s\n", word, "is a palindrome.");
    }
  else
    {
       printf("%s %s\n", word, "is not a palindrome.");
    }
  
  return 0;
}
