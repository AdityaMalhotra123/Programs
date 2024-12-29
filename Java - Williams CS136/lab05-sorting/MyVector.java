//I am the sole author of the work in this repository.
import structure5.*;
import java.util.Comparator;
//$ Good
public class MyVector<E> extends Vector<E> {
  /**
  * allows use of Vector class constructor methods
  * @post calls parent constructor
	*/
  public MyVector() {
	   super();
  }
  /**
	* sorts vector through bubble sort. O(n^2) time complexity.
  * @param c is a comparator
	* @pre c is a valid comparator
	* @post sort this vector in the order determined by c
	*
	*/
  public void sort(Comparator<E> c) {
    E swap;
    for (int i = 0; i < size() - 1; i++) {
      for (int j = 0; j < size() - 1; j++) {
        //compares current element with next element and sorts accordingly
        if (c.compare(get(j), get(j + 1)) > 0) {
          swap = get(j);
          set(j, get(j + 1));
          set(j + 1, swap);
        }
      }
    }
  }
}
