//I am the sole author of the work in this repository.
import structure5.*;

public class TwoTowers{
  /** printbestSubset(bestSubset, bestSum)
  * Prints subset with sum closest to (but less than) h/2 in appropriate form along with the sum.
  * @pre bestSubset contains a valid vector of data type long, and bestSum is of data type double
  * @post prints answer in the form: 'The best subset is: [...] = ...'
  */
  public static void printbestSubset(Vector<Long> bestSubset, double bestSum){
    System.out.print("The best subset is: [");
    for (int i = 0; i < bestSubset.size(); i++){
      // don't add comma if it is the last element
      if (i == bestSubset.size() - 1){
        System.out.print(bestSubset.get(i) + "]");
        break;
      }
      System.out.print(bestSubset.get(i) + ", ");
    }
    System.out.println(" = " + bestSum);
  } 

  //$ Small style convention with method naming: if you use camel case, consider naming
  //$ your methods printBestSubset, etc. All words after the first should be capitalized by convention

  /** printsecondSubset(secondSubset, secondSum)
  * Prints subset with sum second closest to (but less than) h/2 in appropriate form along with the sum.
  * @pre secondSubset contains a valid vector of data type long, and secondSum is of data type double
  * @post prints answer in the form: 'The second best subset is: [...] = ...'
  */
  public static void printsecondSubset(Vector<Long> secondSubset, double secondSum){
    System.out.print("The second best subset is: [");
    for (int i = 0; i < secondSubset.size(); i++){
      // don't add comma if it is the last element
      if (i == secondSubset.size() - 1){
        System.out.print(secondSubset.get(i) + "]");
        break;
      }
      System.out.print(secondSubset.get(i) + ", ");
    }
    System.out.println(" = " + secondSum);
  }
  /** findSubsets(best, bestSubset, secondSubset, bestSum, secondSum, h)
  * Checks all 2^n (n = numBlocks) possible subsets to find the two best subsets with sum closest to h/2.
  * @pre bestSubset, secondSubset are empty and bestSum, secondSum = 0. h = sum of heights of all blocks.
  * @post bestSubset contains the best subset according to given conditions and bestSum = sum of values in bestSubset.
  * @post Similar for secondSubset and secondSum
  * @post calls printbestSubset() and printsecondSubset()
  */
  public static void findSubsets(SubsetIterator<Double> best, Vector<Long> bestSubset,
  Vector<Long> secondSubset, double bestSum, double secondSum, double h){
    //best.hasNext() ensures loop is run 2^n times (n = numBlocks)
    while(best.hasNext()){
      double currentSum = 0;
      for (int i = 0; i < best.get().size(); i++){
        currentSum += best.get().get(i);
      }
      //precondition. Additionally, round() is needed for areas to convert doubles to longs.
      if (currentSum <= h/2){
        if (currentSum > bestSum){
          bestSum = currentSum;
          bestSubset.clear();
          for (double a : best.get()){
            bestSubset.add(Math.round(a*a));
          }
        }
        //else ensures bestSubset is not the same as secondSubset
        else {
          if ((currentSum > secondSum)){
            secondSum = currentSum;
            secondSubset.clear();
            for (double a : best.get()){
              secondSubset.add(Math.round(a*a));
            }
          }
        } //$ Nice job on the 2nd best check! Could you add an additional squareHeights helper method?
      }
      best.next();
    }
    System.out.println("Half height (h/2) is: " + h/2);
    printbestSubset(bestSubset, bestSum);
    printsecondSubset(secondSubset, secondSum);
  }
  /**
  * determines total height and sets up SubsetIterator
  * initializes bestSubset, secondSubset, secondSubset, secondSum
  * @pre numBlocks is an integer
  * @post calls findSubsets()
  */
  public static void main(String[] args){
    int numBlocks = Integer.valueOf(args[0]);
    Vector<Double> heights = new Vector<Double>();
    //h reperesents total height of all blocks
    double h = 0;
    //need to square root to find height from area
    //i starts at 1 because there is no block with area, 0. 
    for (int i = 1; i <= numBlocks; i++){
      heights.add(Math.sqrt(i));
      h += heights.get(i - 1);
    }
    SubsetIterator<Double> best = new SubsetIterator<Double>(heights);
    double bestSum = 0;
    double secondSum = 0;
    Vector<Long> bestSubset = new Vector<Long>();
    Vector<Long> secondSubset = new Vector<Long>();
    findSubsets(best, bestSubset, secondSubset, bestSum, secondSum, h);
  } //$ I may be a bit nit-picky here:
  //$ Could do do all of this work in an additional helper method? You have done a really nice job
  //$ making other useful helper methods. Just some food for thought. 
}
