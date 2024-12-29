//I am the sole author of the work in this repository.
//import scanner class
import java.util.Scanner;

public class WordGen {
  //declare k which represents the level of analysis and a table variable
  private static int k;
  //$ extra semicolon
  private static Table newTable = new Table();;

  /** add(String text)
  * for every k-length sequence of characters in text, add k-length substring and subsequent character to table
  * Otherwise add ch to FrequencyList with frequency 1
  * @param text is the input text
  */
  //$ this method name was a bit confusing (something like buildTable would be clearer)
  public static void add(String text) {
    //run add for each k-length sequnce of letters in text and the subsequent character
    for (int i = 0; i <= text.length() - k - 1; i++) {
      newTable.add(text.substring(i, i + k), text.substring(i + k, i + k + 1));
    }
  }
  /** choose(String ktext)
  * Choose a value for the ktext key in the table
  * @param ktext is the k-letter sequence that represents the key in the table
  * @return the character chosen from table based on probability or null
  */
  public static String choose(String ktext) {
    String newString = "null";
    //if table choose method doesn't return null, return the letter chosen
    if (!newTable.choose(ktext).equals(newString)) {
      return newTable.choose(ktext);
    } else {
      //return null
      return newString;
    }
  }

  public static void main(String[] args) {
    if (args.length == 0) {
        // no args, so print usage line and do nothing else
        System.out.println("Usage: java WordGen ");
    } else {
        // convert first argument to k
        k = Integer.parseInt(args[0]);
        // ... the rest of your code here ...
    }
    //allows user to type input - though this doesn't work for me due to to issue with gitbash
    //$ I have this issue as well, don't worry about it
    Scanner in = new Scanner(System.in);
    StringBuffer textBuffer = new StringBuffer();
    while (in.hasNextLine()) {
      String line = in.nextLine();
      textBuffer.append(line);
      textBuffer.append("\n");
    }
    String text = textBuffer.toString();

    // 'text' now contains the full contents of the input.
    //call add function
    add(text);
    //nText represents new text that is forming. Start with first k characters in text
    String nText = text.substring(0, k);

    for (int i = 0; i < text.length() - k; i++) {
      //combText stores character returned from choose function based on probability
      String combText = choose(nText.substring(i, i + k));
      //if value of combText is null, try the program for k - 1
      //$ this approach is not ideal, and also slows down the runtime quite a lot
      if (combText.equals("null") && k > 1) {
        k -= 1;
        add(text);
        combText = choose(text.substring(i + 1, i + k + 1));
        k += 1;
      }
      //if combText is still null, run choose for first k characters
      if (combText.equals("null")) {
        combText = choose(text.substring(0, k));

      }
      //add the next character chosen to nText
      nText += combText;

    }
    //print the new text formed
    System.out.println(nText);
  }
}
