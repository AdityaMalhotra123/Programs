//I am the sole author of the work in this repository.
import structure5.*;
import java.util.Comparator;

//$ remember to capitalize the class name
class addressComparator implements Comparator<Association<String, Integer>> {
  /** compare(a, b)
  *
  * @param a and b are associations
  * @pre a and b are valid associations
  * @post prints frequency of b address - frequency of a address
  */
  public int compare(Association<String, Integer> a, Association<String, Integer> b) {
    Assert.pre(a != null || b != null, "an association doesn't exist");
    return b.getValue() - a.getValue();
  }
}
