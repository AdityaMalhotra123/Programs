//I am the sole authors of the work in this repository.
/*
 * Recursion.java
 *
 * Starter code for the recursion lab.
 *
 */
import structure5.*;

public class Recursion {

  // Note: Warmup questions are not graded, but you may wish to
  // complete & test them since later, graded questions build
  // on them.

  /***** Warmup 0.1 ********************************************/

  /**
   * Takes a non-negative integer and returns the sum of its digits.
   * @param n a non-negative integer
   * @return the sum of n's digits
   * @pre number is >= 0
   * @post return sum of digits
   */
  public static int digitSum(int n) {
    if (n < 10) {
      return n;
    }
    return digitSum(n / 10) + digitSum(n % 10);
  }

  /***** Warmup 0.2 ********************************************/

  /**
   * Given a set of integers and a target number, determines
   * whethere there is a subset of those numbers that sum to the
   * target number.
   *
   * @param setOfNums a set of numbers that may appear in the subset
   * @param targetSum the target value for the subset
   * @return true if some subset of numbers in setOfNums sums to targetSum
   * @pre index = 0 to begin with
   * @post returns whether or not there is a valid subset
   */
  public static boolean canMakeSum(int[] setOfNums, int targetSum, int index) {

    if (setOfNums.length == index) {
    	   // base case:
    	   // the sum either is or is not the target
    	   return targetSum == 0;
    } else {
    	   // recursive case:
    	   // we must choose to include or exclude the current
    	   // number from the running total
    	   boolean include = canMakeSum(setOfNums, targetSum - setOfNums[index], index + 1);
    	   boolean exclude = canMakeSum(setOfNums, targetSum, index + 1);
    	   return include || exclude;
    }


  }

  /*****  1  ***************************************************/

  /**
   * Return number of cannoballs in pyramid with the given `height`.
   *
   * @param height the height of the cannonball tower
   * @return the number of cannonballs in the entire tower
   * @pre height >= 1
   * @post returns the number of cannonballs in the entire tower
   */
  //$ height must be non-negative, which means the base case is actually
  //$ if (height == 0) 
  public static int countCannonballs(int height) {

    Assert.pre(height >= 1, "height must be at least 1");

    if(height == 1) {
      // base case:
      // there is only one cannonball in pyramid of height 1
      return 1;
    }

    // recursive case:
    // number of cannonballs in each square is equal to height squared
    // add together number of cannonballs in each square through recursive call
    return height*height + countCannonballs(height - 1);
  }


  /*****  2  ***************************************************/

  /**
   * A method that determines if a string reads the same forwards
   * and backwards.
   *
   * @param str the string to check
   * @return true if `str` is a palindrome.
   * @pre `str` contains no spaces
   * @post returns true if `str` is a palindrome.
   */
  public static boolean isPalindrome(String str) {

    Assert.pre(str.trim().length() == str.length(), "str must not contain spaces");

    int len = str.length();
    //$ good job getting both base cases!
    if(len <= 1) {
      // base case:
      // str is a palindrome if empty or made up of one character
      return true;
    }

    if(str.charAt(0) == str.charAt(len - 1)) {
      // recursive case:
      // check if first and last charcacter of str are equal to each together
      // create substring without those characters and check if the next characters are equal to each other
      return isPalindrome(str.substring(1, len - 1));
    }

    return false;
  }

  /*****  3  ***************************************************/

  /**
   * Checks whether `str` is a string of properly nested and matched
   * parens, brackets, and braces.
   *
   * @param str a string of parens, brackets, and braces
   * @return true if str is properly nested and matched
   * @pre str contains no spaces and only parens, brackets, and braces.
   * @post return true if str is properly nested and matched.
   */
  public static boolean isBalanced(String str) {
      //$ The string doesn't have to have matched elements  -- it just needs to contain
      //$ only parens, brackets, and braces and have no spaces. What you have is
      //$ okay though!
    Assert.pre(str != null, "provide a str with properly nested and matched parens, brackets, and braces.");

    if(str.length() == 0) {
      // base case:
      return true;
    }

    // recursive case:
    // check if there are pairs of parens, brackets, and braces, and create string without them,
    // call isBalanced on new string without pairs.
    if(str.indexOf("()") != -1) {
      return isBalanced(str.substring(0,str.indexOf("()")) + str.substring(str.indexOf("()") + 2));
    }
    if(str.indexOf("[]") != -1) {
      return isBalanced(str.substring(0,str.indexOf("[]")) + str.substring(str.indexOf("[]") + 2));
    }
    if(str.indexOf("{}") != -1) {
      return isBalanced(str.substring(0,str.indexOf("{}")) + str.substring(str.indexOf("{}") + 2));
    }
    return false;
  }

  /*****  4  ***************************************************/

  /**
   * A method to print all subsequences of str (order does not matter).
   *
   * @param str string to print all subsequences of
   * @pre str is a string
   * @post prints all subsequences
   */
  public static void subsequences(String str) {
    subsequenceHelper(str, "");
  }

  /**
   * Helper method for subsequences method
   * `soFar` keeps track of the characters currently in the substring
   *   being built
   * @param str [fill this in]
   * @param soFar [fill this in]
   * @pre str is a string
   * @post prints all subsequences
   */
  protected static void subsequenceHelper(String str, String soFar) {
    // base case: string lenght is 0
    if (str.length() == 0) {
      System.out.print("\"\"");
    }
    // prevents substring index error. if string length is 1, print empty string and the one letter
    if (str.length() == 1) {
      System.out.print("\"" + soFar + "\"" + ", ");
      System.out.print("\"" + soFar + str.charAt(0) + "\"" + ", ");
    // recursive case: work with rest of string and add used string to soFar
    } else if (str.length() > 1) {
      subsequenceHelper(str.substring(1), soFar);
      subsequenceHelper(str.substring(1), soFar + str.charAt(0));
    }

  }

  /*****  5  ***************************************************/

  /**
   * A method to print the binary digits of a number.
   *
   * @param number the number to print in binary
   * @pre int >= 0
   * @post prints binary representation of a given decimal
   */
  public static void printInBinary(int number) {
      //$ good job handling negative input
    Assert.pre(number >= 0, "number must be non-negative");
    if (number < 2) {
      // base case:
      // if number is 0 or 1, binary number is 0 or 1 respectively
      System.out.print(number);
    } else {
      // recursive case:
      // call printInBinary on number divided by 2 to give you remaining portion of integer
      // print number % 2 to print whether number is even or odd
      printInBinary(number / 2);
      System.out.print(number % 2);
    }
  }

  /*****  6a  ***************************************************/


  /**
   * Return whether a subset of the numbers in nums add up to sum,
   * and print them out.
   *
   * @param setOfNums contains full set of numbers to print a subset from
   * @param targetSum represents sum of the subset of numbers
   * @param index always starts with 0.
   * @return true if a valid subset is found and false otherwise
   * @pre index is 0 to begin with
   * @post print one possible subset of numbers
   */

  /*
   public static boolean printSubsetSum(int[] setofNums, int targetSum){
	    return printSubsetSumHelper(setofNums, targetSum, 0);
	}
	
  public static boolean printSubsetSumHelper(int[] setOfNums, int targetSum, int index) {
    //base case: once index = length of setOfNums, all numbers have been checked
    if (setOfNums.length == index) {
      //sum is either targetSum or not
      return targetSum == 0;
    }
    //recursive case:
    //try to include the current number in the running total
    boolean include = printSubsetSumHelper(setOfNums, targetSum - setOfNums[index], index + 1);
    //if current number is in subset, print it and return true one one possible subset is printed
    if (include) {
      System.out.print(setOfNums[index] + ", ");
      return true;
  }
    //try to exclude the current number in the running total
    boolean exclude = printSubsetSumHelper(setOfNums, targetSum, index + 1);
    return exclude;
  }*/
  
 public static boolean printSubsetSum(int[] setOfNums, int targetSum, int index) {
    //base case: once index = length of setOfNums, all numbers have been checked
    if (setOfNums.length == index) {
      //sum is either targetSum or not
      return targetSum == 0;
    }
    //recursive case:
    //try to include the current number in the running total
    boolean include = printSubsetSum(setOfNums, targetSum - setOfNums[index], index + 1);
    //if current number is in subset, print it and return true one one possible subset is printed
    if (include) {
      System.out.print(setOfNums[index] + ", ");
      return true;
    }
    //try to exclude the current number in the running total
    boolean exclude = printSubsetSum(setOfNums, targetSum, index + 1);
    return exclude;
  }

  /*****  6b  ***************************************************/

  /**
   * Return the number of different ways elements in nums can be
   * added together to equal sum (you do not need to print them all).
   *
   * @param nums array of numbers to choose from
   * @param targetSum sum of each possible subset of numbers
   * @return number of solutions
   * @pre index is 0 to begin with
   * @post return number of possible subsets
   */
  public static int countSubsetSumSolutions(int[] nums, int targetSum) {
    //use helper function to add initial index
    return countSolutionsHelper(nums, targetSum, 0);
  }

  protected static int countSolutionsHelper(int[] setOfNums, int targetSum, int index) {
      //$ see comment in rubric about base case!
 //check all numbers in setOfNums (prevents index out of bounds)
    if (setOfNums.length > index) {
      //base case:
      //if targetSum = runningTotal, a solution is found
      if (targetSum == 0) {
        return 1;
      }
      //try including and excluding the current number
	  //$ should use better variable names
      int x = countSolutionsHelper(setOfNums, targetSum - setOfNums[index], index + 1);
      int y = countSolutionsHelper(setOfNums, targetSum, index + 1);
      //return total number of solutions
      return x + y;
    } else {
      //no solutions found
      return 0;
    }



  }

  /***********************************************************/

  /**
   * Add testing code to main to demonstrate that each of your
   * recursive methods works properly.
   *
   * Think about the so-called corner cases!
   *
   * Remember the informal contract we are making: as long as all
   * predconditions are met, a method should return with all
   * postconditions met.
   */

  protected static void testCannonballs() {
    System.out.println("Testing cannonballs: ....");
    System.out.println(countCannonballs(3));
    System.out.println(countCannonballs(10));
  }

  protected static void testPalindrome() {
    System.out.println("Testing isPalindrome: ....");
    System.out.println(isPalindrome("mom"));
    System.out.println(isPalindrome("deeded"));
    System.out.println(isPalindrome("ablewasIereIsawelba"));
  }

  protected static void testBalanced() {
    System.out.println("Testing isBalanced: ....");
    System.out.println(isBalanced("[{[()()]}]"));
    System.out.println(isBalanced("[{[()()]}][{[()()]}]"));
    System.out.println(isBalanced("[{[()()]}{]{[()()]}]"));
  }

  protected static void testSubsequence() {
    System.out.println("Testing subsequences: ....");
    subsequences("abc");
    System.out.println();
    subsequences("CSCI136");
    System.out.println();
    subsequences("a");
    System.out.println();
    subsequences("");
    System.out.println();
  }

  protected static void testBinary() {
    System.out.println("Testing printInBinary: ....");
    printInBinary(0);
    System.out.println();
    printInBinary(30);
    System.out.println();
    printInBinary(1);
    System.out.println();
    printInBinary(110);
    System.out.println();
    printInBinary(2048);
    System.out.println();
    printInBinary(43);
    System.out.println();
      }

  protected static void testCanMakeSum() {
    System.out.println("Testing canMakeSum: ....");
    int[] numSet = {2, 5, 7, 12, 16, 21, 30};
    System.out.println(canMakeSum(numSet, 21, 0));
    System.out.println(canMakeSum(numSet, 22, 0));
    System.out.println(canMakeSum(numSet, 3, 0));
    System.out.println(canMakeSum(numSet, 30, 0));
  }
    
  protected static void testPrintSubsetSum() {
    System.out.println("Testing printSubsetSum: ....");
    int[] numSet = {2, 5, 7, 12, 16, 21, 30};
    //$System.out.println(printSubsetSum(numSet, 21, 0));
    //$System.out.println(printSubsetSum(numSet, 22, 0));
    //$System.out.println(printSubsetSum(numSet, 3, 0));
    //$System.out.println(printSubsetSum(numSet, 30, 0));
  }
    
  protected static void testCountSubsetSum() {
    System.out.println("Testing countSubsetSumSolutions: ....");
    int[] numSet = {2, 5, 7, 12, 16, 21, 30};
    System.out.println(countSubsetSumSolutions(numSet, 21));
    System.out.println(countSubsetSumSolutions(numSet, 22));
    System.out.println(countSubsetSumSolutions(numSet, 3));
    System.out.println(countSubsetSumSolutions(numSet, 30));
  }

  /**
   * Main method that calls testing methods to verify
   * the functionality of each lab exercise.
   *
   * Please supplement the testing code with additional
   * correctness tests as needed.
   */
  public static void main(String[] args) {
    testCannonballs();
    testPalindrome();
    testBalanced();
    testSubsequence();
    testBinary();
    testCanMakeSum();
    testPrintSubsetSum();
    testCountSubsetSum();
  }
}
