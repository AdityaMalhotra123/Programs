//I am the sole author of the work in this repository.
// import structure5 package
import structure5.*;
/**
* A Table holds a collection of strings, each of which has an
* associated FrequencyList
*/
public class Table {
  //declare vector called strings that will contain the k-length text as a key and the respective frequency list as the value
  //$ I find this variable name a bit confusing
  private Vector<Association<String, FrequencyList>> strings;
  /** Construct an empty table */
  public Table() {
    //initialize strings
    strings = new Vector<Association<String, FrequencyList>>();

  }

  /**
  * Updates the table as follows
  * If key already exists in the table, update its FrequencyList
  * by adding value to it
  * Otherwise, create a FrequencyList for key and add value to it
  * @param key is the desired k-letter sequence
  * @param value is the character to add to the FrequencyList of key
  */
  public void add(String key, String value) {
    //$ good
    //declare a new frequency list
    FrequencyList newList = new FrequencyList();
    //run add method from FrequencyList class with value as the argument
    newList.add(value);
    //declare an association variable that contains argument key and a frequency list as value
    Association<String, FrequencyList> subcharacter = new Association<String, FrequencyList>(key, newList);
    //check to see if key exists in strings
    if (strings.contains(subcharacter)) {
      //find index at which key is in strings
      int index = strings.indexOf(subcharacter);
      //run add method from FrequencyList class to increment frequency or add a new letter
      strings.get(index).getValue().add(value);
    } else {
      //add new key and value to strings since it wasn't in it
      strings.add(subcharacter);
    }
  }


  /**
  * If key is in the table, return one of the characters from
  * its FrequencyList with probability equal to its relative frequency
  * Otherwise, determine a reasonable value to return
  * @param key is the k-letter sequence whose frequencies we want to use
  * @return a character selected from the corresponding FrequencyList
  */
  public String choose(String key) {
    //declare a new FrequencyList
    FrequencyList newList = new FrequencyList();
    //declare an association variable that contains argument key and a frequency list as value
    Association<String, FrequencyList> a = new Association<String, FrequencyList>(key, newList);
    //check to see if key has occurred before
    if (strings.contains(a)) {
      //find index at which key occurs
      int i = strings.indexOf(a);
      //use frequency list of the key and choose a letter according to probabalities
      FrequencyList list = strings.get(i).getValue();
      return list.choose();
    } else {
      //return null if key hasn't occurred before
      //$ good, indicating that the key was not found is the right approach
      return "null";
    }

  }

  /** Produce a string representation of the Table
  * @return a String representing this Table
  */
  public String toString() {
    return strings.toString();
  }

  // Use main to test your Table class
  public static void main(String[] args) {

  }

}
