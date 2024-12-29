//I am the sole author of the work in this repository.
import structure5.*;

public class SubsetIterator<E> extends AbstractIterator<Vector<E>> {
  private long counter = 0;
  private Vector<E> v;
  /**
  * constructor extracts vector from parameter
  * @post v = vec
  */
  public SubsetIterator(Vector<E> vec){
    v = vec;
  }
  /**
  * resets counter
  * @post counter = 0
  */
  public void reset(){
    counter = 0;
  }
  /**
  * checks whether there is another subset possible
  * @pre numBlocks (i.e. v.size()) > 0
  * @post returns true if counter < 2^n (n = numBlocks)
  */
  public boolean hasNext(){
    return counter < (1L << v.size());
  }
  /**
  * determine subset according to binary representation of counter\
  * @pre v.size() > 0
  * @post returns current subset according to 1's in binary representation of counter
  */
  public Vector<E> get(){
    Vector<E> subset = new Vector<E>();
    for (int i = 0; i < v.size(); i++){
      if ((counter & (1L << i)) == 1L << i){
        subset.add(v.get(i));
      }
    }
    return subset;
  } //$ Nicely done on get()!
  /**
  * increments counter
  * @pre hasNext() is true
  * @post returns current Subset
  */
  public Vector<E> next(){
    Vector<E> x = get();
    counter++;
    return x;
  }
  /**
  * tests whether all subsets are generated for vector of first 8 non-negative integers
  * @post prints all 2^8 possible subsets
  */
  public static void main(String[] args){
    Vector<Integer> a = new Vector<Integer>();
    for (int i = 0; i < 8; i++){
      a.add(i);
    }
    SubsetIterator<Integer> test = new SubsetIterator<Integer>(a);
    while(test.hasNext()){
      System.out.println(test.next());
    }
  }
}
