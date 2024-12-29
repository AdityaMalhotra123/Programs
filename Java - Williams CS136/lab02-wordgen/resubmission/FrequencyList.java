//I am the sole author of the work in this repository.
// import Random class and structure5 package
import structure5.*;
import java.util.Random;

/**
* A FrequencyList stores a set of characters each of which has
* an associated integer frequency
*/

public class FrequencyList {
  //Declare Vector of associations called charater that contains a letter as the key and its frequency as the value.
  private Vector<Association<String, Integer>> character;
  /** Construct an empty FrequencyList */
  public FrequencyList() {
    //initialize character
    character = new Vector<Association<String, Integer>>();
  }

  /** add(String ch)
  * If ch is in the FrequencyList, increment it's associated frequency
  * Otherwise add ch to FrequencyList with frequency 1
  * @param ch is the String to add to the FrequencyList
  */
  public void add(String ch) {
    //create association variable that contains (key, 1)
    Association<String, Integer> characterCount = new Association<String, Integer>(ch, 1);
    //check to see if character already contains the key
    if (character.contains(characterCount)) {
      //find index at which key occurs
      int index = character.indexOf(characterCount);
      //at that index in character, increment the frequency
      character.get(index).setValue(character.get(index).getValue() + 1);
    } else {
      //create a new element in the vector with the new key since isn't already there
      character.add(characterCount);
    }
  }

  /** Selects a character from this FrequencyList
  * @return a character from the FrequencyList with probabality equal to its relative frequency
  */
  public String choose() {
    //total represents the sum of values associated with each key in character
    int total = 0;
    for (int i = 0; i < character.size(); i++) {
      //add the value of each key to total
      total += character.get(i).getValue();
    }
    //count helps assign a letter to each index position in the frequency array in the next line
    int count = 0;
    //declare an array called frequency that will store each key n number of times where n is the key's corresponding value
    String[] frequency = new String[total];
    //iterate through the keys
    for (int i = 0; i < character.size(); i++) {
      //run this loop as amany times as the value of the particular key at the ith index
      for (int j = 0; j < character.get(i).getValue(); j++) {
        //assign the key to the frequency array at a new index every time
        frequency[count++] = character.get(i).getKey();
      }
    }
    //init random
    Random r = new Random();
    //pick a random number between 0 and (total-1) and return the letter at that index in the frequency array
    return frequency[r.nextInt(total)];
  }

  /** Produce a string representation of the FrequencyList
   * @return a String representing the FrequencyList
   */
  public String toString() {
    String s = "[ ";
    //for each element in the character vector
    for (Association<String, Integer> a : character) {
      s += "'" + a.getKey() + "' = " + a.getValue() + " ";
    }
    return s + "]";
  }

  // Use main to test your FrequencyList class
  public static void main(String[] args) {

  }

}
