//I am the sole author of the work in this repository.
//import scanner class
import java.util.Scanner;

public class WordGen {
  //declare k which represents the level of analysis and a table variable
  private static int k;
  private static Table newTable = new Table();

  /** add(String text)
  * for every k-length sequence of characters in text, add k-length substring and subsequent character to table
  * Otherwise add ch to FrequencyList with frequency 1
  * @param text is the input text
  */
  public static void add(String text) {
    //run add for each k-length sequnce of letters in text and the subsequent character
    for (int i = 0; i < text.length() - k; i++) {
      newTable.add(text.substring(i, i + k), text.substring(i + k, i + k + 1));
    }
  }
  /** choose(String ktext)
  * Choose a value for the ktext key in the table
  * @param ktext is the k-letter sequence that represents the key in the table
  * @return the character chosen from table based on probability or null
  */
  public static String choose(String ktext) {
    return newTable.choose(ktext);
  }

  /** getText()
  * allows user to type input - though this doesn't work for me due to to issue with gitbash
  * reads file line by line and adds each line to a StringBuffer textBuffer
  * @returns text String
  */
  public static String getText(){
    Scanner in = new Scanner(System.in);
    StringBuffer textBuffer = new StringBuffer();
    while (in.hasNextLine()) {
      String line = in.nextLine();
      textBuffer.append(line);
      textBuffer.append("\n");
    }
    return textBuffer.toString();
  }

  /** getK(String a)
  * attempts to convert args[0] to integer
  * If args[0] isn't an integer, prints an error message
  * @param a is args[0]
  * @pre a = args[0]
  * @post k = args[0]
  */
  public static void getK(String a){
    try {
      // convert first argument to k
      k = Integer.parseInt(a);
    } catch (Exception e) {
      System.out.println("Usage: java WordGen k < input.txt. k must be an integer.");
      System.exit(0);
    }
  }

  /** formNewText(String nText, String text, int k)
  * forms new text to be printed based on k=level analysis
  * @param nText is the new text to be printed
  * @param text is input text
  * @param k is args[0]
  * @pre nText has k letters
  * @return modified nText
  */
  public static String formNewText(String nText, String text, int k){
    for (int i = 0; i < text.length() - k; i++) {
      //combText stores character returned from choose function based on probability
      String combText = choose(nText.substring(i, i + k));
      //if combText is still null, add first k letters and increment i accordingly so total length of text is same
      if (combText.equals("null")) {
        combText = text.substring(0, k);
        i += k - 1;
      }
      //add the next character chosen to nText
      nText += combText;

    }
    return nText;
  }

  /** main(String[] args)
  * gets k value from user
  * stores input text as a String
  * creates a table and respective frequency lists
  * generates new text pased on k-level probabilistic analysis
  * @post prints generated new text
  */
  public static void main(String[] args) {
    if (args.length == 0) {
        // no args, so print usage line and do nothing else
        System.out.println("Usage: java WordGen k < input.txt");
    } else {
        getK(args[0]);
        String text = getText();
        // 'text' now contains the full contents of the input.
        //call add function
        add(text);
        //nText represents new text that is forming. Start with first k characters in text
        String nText = text.substring(0, k);
        nText = formNewText(nText, text, k);

        //print the new text formed
        System.out.println(nText);
    }

  }
}
