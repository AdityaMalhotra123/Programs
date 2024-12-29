//I am the sole author of the work in this repository.
import java.util.Scanner;
import structure5.*;
import java.util.Comparator;
//$ Looks good to me!
public class Questions {
  /** addStudents(students, in)
  * Fill students vector and assign values to all attributes for each student by reading lines of text file.
  * @pre text file contains text
  * @param students is a vector of students, in is a scanner to read input
  * @post students vector contains all students
  */
  public static void addStudents(MyVector<Student> students, Scanner in) {
    while (in.hasNextLine()) {
      String name = in.nextLine();
      String address = in.nextLine();
      long telNumber = in.nextLong();
      int suNumber = in.nextInt();
      long homeNumber = in.nextLong();
      String space = in.nextLine();
      String dashes = in.nextLine();
      //use constructor from student class to set up student
      Student s = new Student(name, address, telNumber, suNumber, homeNumber);
      //add student to students vector
      students.add(s);
    }
  }
  /** question1(students)
  * Sort students vector by first name (alphabetically) and find first student name
  * @param students is a vector of students
  * @post print first student name
  */
  public static void question1(MyVector<Student> students) {
    //create an object of nameComparator class
    nameComparator obj = new nameComparator();
    students.sort(obj);
    System.out.println("1: " + students.get(0).getName());
  }
  /** question2(students)
  * Sort students vector bu SU box number (smallest first) and find smallest and largest SU number
  * @param students is a vector of students
  * @post print smallest and largest SU number
  */
  public static void question2(MyVector<Student> students) {
    //create an object of NumComparator class
    NumComparator obj2 = new NumComparator();
    students.sort(obj2);
    System.out.println("2: Smallest SU Number = " + students.get(0).getSUNumber() + ", Largest SU Number = " + students.get(students.size() - 1).getSUNumber());
  }
  /** question3(students)
  * Sort students vector by number of vowels in their name (largest first) and find student with most vowels in name
  * @param students is a vector of students
  * @post print name of student with most vowels
  */
  public static void question3(MyVector<Student> students) {
    //create an object of vowelComparator class
    vowelComparator obj3 = new vowelComparator();
    students.sort(obj3);
    System.out.println("3: Student with most vowels in their name is " + students.get(students.size() - 1).getName());
  }
  /** question4(students, addrs)
  * Sort students vector by commonality of address and find most common addresss
  * @param students is a vector of students, addrs is an empty vector of associations<String, Integer>
  * @post addrs is a sorted vector and most common address is printed
  * @return most common address
  */
  public static String question4(MyVector<Student> students, MyVector<Association<String, Integer>> addrs) {
    for (int i = 0; i < students.size(); i++) {
      //create assocaition variable with address of student as key
      Association<String, Integer> a = new Association<String, Integer>(students.get(i).getAddress(), 1);
      //don't add "UNKNOWN" to addrs vector
      if (!a.getKey().equals("UNKNOWN")) {
        //check if current address has been read before
        if (addrs.contains(a)) {
          //find index at which current address (key) occurs
          int index = addrs.indexOf(a);
          //at that index in addrs, increment the frequency
          addrs.get(index).setValue(addrs.get(index).getValue() + 1);
        } else {
          //create a new element in the vector with the current address as key since isn't already there
          addrs.add(a);
        }
      }
    }
    //create an object of addressComparator class
    addressComparator obj4 = new addressComparator();
    addrs.sort(obj4);
    System.out.println("4: Most Common Address is " + addrs.get(0).getKey());
    return addrs.get(0).getKey();
  }
  /** question4names(ad, students)
  * Prints names of students with most common address
  * @param ad is most common address, students is a vector of students
  * @post print student names
  */
  public static void question4names(String ad, MyVector<Student> students) {
    System.out.print("Students with that address are ");
    for (int i = 0; i < students.size(); i++) {
      //check if current student's address is the most common address and print if yes
      if (ad.equals(students.get(i).getAddress())) {
        System.out.print(students.get(i).getName() + ", ");
      }
    }
  }
  /** question5(students, areaCodes)
  * Sort students vector by most common area codes and fins 10 most common areas codes and number of students associated with each one.
  * @param students is a vector of students, areaCodes is an empty vector of associations<integer, integer>
  * @post print 10 most common areas codes and number of students associated with each one.
  */
  public static void question5(MyVector<Student> students, MyVector<Association<Integer, Integer>> areaCodes) {
    for (int i = 0; i < students.size(); i++) {
      //create association variable that contains areaCode as key
      Association<Integer, Integer> a = new Association<Integer, Integer>(students.get(i).getareaCode(), 1);
      //don't consider unknown homeNumbers
      if (a.getKey() != -1) {
        //check if current area code has been read before
        if (areaCodes.contains(a)) {
          //find index at which current area code (key) occurs
          int index = areaCodes.indexOf(a);
          //at that index in areaCodes, increment the frequency
          areaCodes.get(index).setValue(areaCodes.get(index).getValue() + 1);
        } else {
          //create a new element in the vector with the current area codes as key since isn't already there
          areaCodes.add(a);
        }
      }
    }
    //create an object of areaComparator class
    areaComparator obj5 = new areaComparator();
    areaCodes.sort(obj5);
    System.out.println("5: Top 10 Most Common Area Codes and Number of Students Associated with them are ");
    //print first 10 area codes in vector and corresponding number of students
    for (int i = 0; i < 10; i++) {
      System.out.println(areaCodes.get(i).getKey() + ": " + areaCodes.get(i).getValue());
    }
  }
  /**
  * create a student vector and add all students to it by reading text file
  * use sort method of MyVector
  * Answer Questions
  * @pre text file contains text
  * @post all questions answered
  */
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    MyVector<Student> students = new MyVector<Student>();
    addStudents(students, in);
    question1(students);
    question2(students);
    question3(students);
    MyVector<Association<String, Integer>> addrs = new MyVector<Association<String, Integer>>();
    String ad = question4(students, addrs);
    question4names(ad, students);
    System.out.println("");
    MyVector<Association<Integer, Integer>> areaCodes = new MyVector<Association<Integer, Integer>>();
    question5(students, areaCodes);
  }
}
