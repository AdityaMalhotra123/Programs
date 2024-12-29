//I am the sole author of the work in this repository.
import structure5.*;
import java.util.Comparator;
//$ remember to capitalize the class name
class nameComparator implements Comparator<Student> {
  /** compare(a, b)
  *
  * @param a and b are students
  * @pre a and b are valid students
  * @post determines which of names of a and b come first first alphabetically
  * @return 1 if a should be placed after b, 0  if they're the same and -1 otherwise
  */
  public int compare(Student a, Student b) {
    Assert.pre(a != null || b != null, "a student doesn't exist");
    return a.getName().compareTo(b.getName());
  }
}
