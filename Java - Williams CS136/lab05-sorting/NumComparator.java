//I am the sole author of the work in this repository.
import structure5.*;
import java.util.Comparator;
//$ Good!
class NumComparator implements Comparator<Student> {
  /** compare(a, b)
  *
  * @param a and b are students
  * @pre a and b are valid students
  * @post finds suNumber of a - suNumber of b
  * @return suNumber of a - suNumber of b
  */
  public int compare(Student a, Student b) {
    Assert.pre(a != null || b != null, "a student doesn't exist");
    return a.getSUNumber() - b.getSUNumber();
  }
}
