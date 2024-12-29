//I am the sole author of the work in this repository.
import structure5.*;
import java.util.Comparator;

//$ remember to capitalize the class name
class areaComparator implements Comparator<Association<Integer, Integer>> {
  /** compare(a, b)
  *
  * @param a and b are associations
  * @pre a and b are valid associations
  * @post prints frequency of b address - frequency of a address
  */
  public int compare(Association<Integer, Integer> a, Association<Integer, Integer> b) {
    Assert.pre(a != null || b != null, "an association doesn't exist");
    return b.getValue() - a.getValue();
  }
}
