// We are the sole authors of the work in this repository
// Included Extensions 1 and 2
import java.util.Scanner;
import structure5.*;

public class ExamScheduler {

  protected Graph<String, Integer> g;
  protected Vector<Student> vec;

  /**
	 * Constructor for ExamScheduler
   * @post g and vec are initialized
	 */
  public ExamScheduler() {
    g = new GraphListUndirected<String, Integer>();
    vec = new Vector<Student>();
  }

  /**
	 * a method that reads a file and stores the content into a vector
	 * @param in the file of student names and courses
   * @pre all files are properly formatted (as the lab handout states)
   * @post the file content is stored in the vec
	 */
  public void fileReader(Scanner in) {
    while (in.hasNextLine()) { // till the end
      Vector<String> classes = new Vector<String>();
      String name = in.nextLine();
      for (int i = 0; i < 4; i++){
        classes.add(in.nextLine());
      }
      vec.add(new Student(name, classes));
    }
  }

  /**
	 * a method that adds new vertices from vec to g
   * @pre vec is not null
   * @post g contains all the courses that students take without duplicates
	 */
  public void addVertices() {
    for (Student s : vec) {
      Vector<String> courses = s.getCourses();
      for (int i = 0; i < courses.size(); i++){ // for each course
        if (!g.contains(courses.get(i))){
          g.add(courses.get(i)); // add it to the graph if not already included
        }
      }
    }
  }

  /**
	 * a method that adds edges between courses to g
   * @pre vec is not null
   * @post all the edges are added to g and label shows the duplicates of each edge
	 */
  public void addEdges() {
    for (Student s : vec) {
      Vector<String> courses = s.getCourses();
      for (int i = 0; i < courses.size(); i++){
        for (int j = i + 1; j < courses.size(); j++){
          // if there is no edge between the course and all the other ones in g,
          // then add a new edge to g
          if (!g.containsEdge(courses.get(i), courses.get(j))){
            g.addEdge(courses.get(i), courses.get(j), 1);
          }
          else { // if there is an edge already, increment the label
            int label = g.getEdge(courses.get(i), courses.get(j)).label();
            g.getEdge(courses.get(i), courses.get(j)).setLabel(label + 1);
          }
        }
      }
    }
  }

  /**
	 * a method that creates and returns the exam schedule in a specified format
   * @pre g is not null
   * @return return the exam schedule in a specified format
	 */
  public Vector<Vector<String>> createSchedule() {
    Vector<Vector<String>> slots = new Vector<Vector<String>>();

    for (String s : g) {
      boolean edge = true; // true if there is an edge
      for (Vector<String> slot : slots){
        edge = false;
        for (String course : slot){
          // if there is an edge betwen a course and ones in each slot, do not add
          if (g.containsEdge(s, course)){
            edge = true;
            break;
          }
        }
        if (!edge){ // if there is no edge
          slot.add(s);
          break;
        }
      }
      if (edge){ // if there is an edge, add the course to a new slot
        Vector<String> newSlot = new Vector<String>();
        newSlot.add(s);
        slots.add(newSlot);
      }
    }
    printer(slots);
    return slots;

  }

  /**
	 * a helper method that prints out exam schedule in a specifed format
   * @param slots is a vector of exam slots
   * @pre slots is not null
   * @pre g is not null
   * @post prints out the exam schedule in a specified format
	 */
  public void printer(Vector<Vector<String>> slots) {
    for (int i = 0; i < slots.size(); i++){
      System.out.print("Slot " + (i + 1) + ": ");
      for (int j = 0; j < slots.get(i).size(); j++){
        System.out.print(slots.get(i).get(j) + " ");
      }
      System.out.println();
    }
    System.out.println();
  }

  /**
	 * a helper method for extention 1 that prints out course schedules in a specified format
   * @param courseNames is is an association where its key is a course name and
   * its value is the slot for that course
   * @pre courseNames is not null
   * @post prints out the slot and all the students in a course after the course name
	 */
  public void printCourseSchedule(Vector<Association<String, Integer>> courseNames){
    for (Association<String, Integer> course : courseNames){
      System.out.print(course.getKey() + ": Slot " + course.getValue() + " ");
      for (Student s : vec){
        if (s.getCourses().contains(course.getKey())){
          System.out.print(s.getName() + " ");
        }
      }
      System.out.println();
    }
    System.out.println();
  }

  /**
	 * an exntension 1 method that returns course schedules in a specified format
   * @param slots is a vector of slots that contans courses
   * @pre slots is not null
   * @post prints out the slot and all the students in a course after the course name
	 */
  public Vector<Association<String, Integer>> courseSchedule(Vector<Vector<String>> slots){
    Vector<Association<String, Integer>> courseNames = new Vector<Association<String, Integer>>();
    for (int slot = 0; slot < slots.size(); slot++){
      for (String course : slots.get(slot)){
        for (int i = 0; i < courseNames.size(); i++){
          // add courses in an alphabetical order
          if (course.compareTo(courseNames.get(i).getKey()) < 0) {
            courseNames.add(i, new Association<String, Integer>(course, slot + 1));
            break;
          }
        }
        if (!courseNames.contains(new Association<String, Integer>(course, slot + 1))){
          courseNames.add(new Association<String, Integer>(course, slot + 1));
        }
      }
    }
    printCourseSchedule(courseNames);
    return courseNames;
  }

  //Extension 2
  /**
   * a method for extention 2 that prints out a final schedule for each student
   * @param courseNames is is an association where its key is a course name and
   * its value is the slot for that course
   * @pre courseNames is not null
   * @post prints out the name of students in an alphabetical order and prints out
   * each student's exam slots
   */
  public void studentSchedule(Vector<Association<String, Integer>> courseNames){
    //bubble sort vector of students in an alphabetical order
    for (int i = 0; i < vec.size() - 1; i++) {
      for (int j = 0; j < vec.size() - 1; j++) {
        if (vec.get(j).getName().compareTo(vec.get(j + 1).getName()) > 0) {
          Student swap = vec.get(j);
          vec.set(j, vec.get(j + 1));
          vec.set(j + 1, swap);
        }
      }
    }
    Vector<String> studentNames = new Vector<String>();
    for (Student s : vec){
      System.out.print(s.getName() + ": ");
      // find out exam slots that a student is in and print them out
      for (String course : s.getCourses()){
        for (Association<String, Integer> c : courseNames){
          if (c.getKey().equals(course)){
            System.out.print("Slot " + c.getValue() + " ");
          }
        }
      }
      System.out.println();
    }
  }

  /**
	 * a main method that creates and prints out an exam schedule based on input
   * @post prints out an exam schedule that does not contain any schedule conflict
	 */
  public static void main(String[] args) {
    ExamScheduler x = new ExamScheduler();
    x.fileReader(new Scanner(System.in));
    x.addVertices();
    x.addEdges();
    x.studentSchedule(x.courseSchedule(x.createSchedule()));
  }

}
