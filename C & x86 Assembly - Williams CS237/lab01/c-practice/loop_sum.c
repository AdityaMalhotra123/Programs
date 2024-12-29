
#include <stdio.h>

/*
 * Caclulates the sum of all the integers from 1 to @param n (inclusive)
 * Pre: integer n > 0
 * Post: Returns sum
 * int n is the input integer
*/
int n_sum(int n)
{
  int sum = 0;
  
  for (int i = 1; i <= n; i++)
     {
       sum += i;
     }
  
  return sum;
}

/*
 * Inputs integer n from user and prints n_sum(n)
 * Post: prints calculated sum if n input is a positive integer
 * and error message otherwise
*/
int main(int argc, char *argv[])
{
  printf("%s\n", "Please enter a positive integer");

  //initialize variable to store input integer
  int n = 0;
  scanf("%d", &n);

  // n > 0 takes care of the case when input isn't a valid integer
  // as well because n would remain as 0.
  if (n > 0)
    {
      int sum = n_sum(n);
      printf("%s %d %s %d\n", "Sum from 1 to ", n, ": ", sum);
    }
  else
    {
      printf("%s\n", "Need to enter a positive integer");
    }
  
  return 0;
}
