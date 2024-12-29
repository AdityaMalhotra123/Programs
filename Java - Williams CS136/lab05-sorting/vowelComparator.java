//I am the sole author of the work in this repository.
import structure5.*;
import java.util.Comparator;

//$ remember to capitalize the class name
class vowelComparator implements Comparator<Student> {
  /** compare(a, b)
  *
  * @param a and b are students
  * @pre a and b are valid students
  * @post finds student with more vowels
  * @return number of vowels in a - number of vowels in b
  */
  public int compare(Student a, Student b) {
    int avowels = 0;
    //checks for vowels in each letter of name
    //$ You have repeated code here - it would be useful to make on method to
    //$ compute the number of vowels.
    //$ Also, it would be more concise to make an array of all vowels and check if the 
    //$ current letter is included in the array.
    for (int i = 0; i < a.getName().length(); i++) {
      if (a.getName().charAt(i) == 'a' || a.getName().charAt(i) == 'e' || a.getName().charAt(i) == 'i' || a.getName().charAt(i) == 'o' || a.getName().charAt(i) == 'u' ||
      a.getName().charAt(i) == 'A' || a.getName().charAt(i) == 'E' || a.getName().charAt(i) == 'I' || a.getName().charAt(i) == 'O' || a.getName().charAt(i) == 'U') {
        avowels++;
      }
    }
    int bvowels = 0;
    //checks for vowels in each letter of name
    for (int i = 0; i < b.getName().length(); i++) {
      if (b.getName().charAt(i) == 'a' || b.getName().charAt(i) == 'e' || b.getName().charAt(i) =='i' || b.getName().charAt(i) == 'o' || b.getName().charAt(i) == 'u' ||
      b.getName().charAt(i) == 'A' || b.getName().charAt(i) == 'E' || b.getName().charAt(i) =='I' || b.getName().charAt(i) == 'O' || b.getName().charAt(i) == 'U') {
        bvowels++;
      }
    }
    return avowels - bvowels;
  }
}
