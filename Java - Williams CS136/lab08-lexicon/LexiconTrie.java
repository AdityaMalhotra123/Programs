//I am the sole author of the work in this repository.
import structure5.*;
import java.util.Iterator;
import java.util.Scanner;


public class LexiconTrie implements Lexicon {
  //number of words in trie
  protected int size;
  //root node
  protected LexiconNode root;

  /**
  * Constructor
  * @post initialized root node and size set to 0
  */
  public LexiconTrie(){
    root = new LexiconNode(' ', false);
    size = 0;
  }

  /**
  * addWord(String word)
  * if trie doesn't contain subsequent letter of word, add it. Set isWord of last
  * letter's node to true.
  * @post each subsequent letter's node is a child of previosus letter's node.
  * @post Returns true if word wasn't already in trie.
  */
  public boolean addWord(String word) {
    if (containsWord(word)){
      return false;
    }
    LexiconNode node = root;
    for (int i = 0; i < word.length(); i++){
      char a = word.toLowerCase().charAt(i);
      if (node.getChild(a) == null){
        node.addChild(new LexiconNode(a, false));
      }
      node = node.getChild(a);
    }
    node.setisWord(true);
    size++;
    return true;
  }

  /**
  * addWordsFromFile(String filename)
  * add a word from each line of textfile
  * @pre text file contains one word per line
  * @post each word is added to trie.
  * @post return number of new words added
  */
  public int addWordsFromFile(String filename) {
    int initialSize = size;
    Scanner in = new Scanner(new FileStream(filename));
    while(in.hasNext()){
      String a = in.next().toLowerCase();
      if (a.equals("")){
        break;
      }
      addWord(a);
    }
    //$ Another option would be to only increase the size if addWord returns
    //$ true, though this approach works as well
    return size - initialSize;
  }

  /**
  * removeWord(String word)
  * remove a particular word from tree (i.e. set isWord of last letter's node to false)
  * @post isWord of word's last letter's node is false.
  * @post returns true if trie originally contained the word and false otherwise.
  */
  public boolean removeWord(String word) {
    if (!containsWord(word)){
      return false;
    }
    LexiconNode node = root;
    for (int i = 0; i < word.length(); i++){
      char a = word.toLowerCase().charAt(i);
      node = node.getChild(a);
    }
    node.setisWord(false);
    size--;
    return true;
  }

  /**
  * numWords()
  * @post return size
  */
  public int numWords() {
    return size;
  }

  /**
  * containsWord(String word)
  * check if the letters of the word exist in the tree in the right order and the
  * last letter's node has isWord = true.
  * @post returns true if isWord of last letter's node is true and false otherwise
  */
  public boolean containsWord(String word){
    LexiconNode node = root;
    for (int i = 0; i < word.length(); i++){
      if (node.getChild(word.toLowerCase().charAt(i)) == null){
        return false;
      }
      node = node.getChild(word.toLowerCase().charAt(i));
    }
    return node.getisWord();
  }

  //$ this is very very similar to containsWord 
  //$ How could a helper method have simplified this?
  /**
  * containsPrefix(String prefix)
  * check if the letters of the prefix exist in the tree in the right order.
  * @post returns true if the path of letters according to word exists in trie
  * and false otherwise.
  */
  public boolean containsPrefix(String prefix){
    LexiconNode node = root;
    for (int i = 0; i < prefix.length(); i++){
      if (node.getChild(prefix.toLowerCase().charAt(i)) == null){
        return false;
      }
      node = node.getChild(prefix.toLowerCase().charAt(i));
    }
    return true;
  }

  /**
  * iterator()
  * iterates through all words in alphabetical order
  * @post returns the iterator of a vector containing words sorted alphabetically
  */
  public Iterator<String> iterator() {
    Vector<String> words = new Vector<String>();
    findwords(words, root, "");
    return words.iterator();
  }

  /**
  * findwords(Vector<String> words, LexiconNode node, String w)
  * adds all words to a vector<String. in alphabetical order since all children
  * are stored alphabetically
  * @post Vector<String> words contains all words in alphabetical order
  */
  //$ Good helper method!
  public void findwords(Vector<String> words, LexiconNode node, String w){
    //iterator for children of current node
    Iterator<LexiconNode> n = node.iterator();
    //n.hasNext() implies number of children of current node is > 0
    while (n.hasNext()){
      node = n.next();
      //if the the letter of current node has isWord == true and vector doesn't already
      //contain the word formed (extra check to prevent duplicates), add word formed by path.
      if (node.getisWord() && (!words.contains(w + node.getLetter()))){
        words.add(w + node.getLetter());
      }
      findwords(words, node, w + node.getLetter());
    }
  }

  //Optional (extra credit) implementation
  public Set<String> suggestCorrections(String target, int maxDistance) {return null;}

  //Optional (extra credit) implementation
  public Set<String> matchRegex(String pattern){return null;}

  /**
  * main(String[] args)
  * used for testing
  */
  public static void main(String[] args){
    LexiconTrie x = new LexiconTrie();
  }
}
