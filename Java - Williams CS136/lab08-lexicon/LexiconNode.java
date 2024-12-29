//$ Thanks for including the honor code statement at the top of your Java file!

//I am the sole author of the work in this repository.
import structure5.*;
import java.util.Iterator;

//$ It's always a good idea to include a class-level comment here
class LexiconNode implements Comparable<LexiconNode> {

    /* single letter stored in this node */
    protected char letter;

    /* true if this node ends some path that defines a valid word */
    protected boolean isWord;

    /* a vector data structure for keeping track of the children of
    this LexiconNode */
    protected Vector<LexiconNode> children;

    /**
    * Constructor
    * initialize children vector
    * @post letter, isWord set accordingly
    */
    LexiconNode(char letter, boolean isWord) {
      children = new Vector<LexiconNode>();
      this.letter = letter;
      this.isWord = isWord;
    }

    /**
    * getLetter()
    * @return letter
    */
    public char getLetter(){
      return letter;
    }

    /**
    * getLsWord()
    * @return isWord
    */
    public boolean getisWord(){
      return isWord;
    }

    /**
    * setisWord(bollean bool)
    * @post isWord is set to bool
    */
    public void setisWord(boolean bool){
      isWord = bool;
    }

    /**
    * compareTo(LexiconNode 0)
    * Compare this LexiconNode to another.
    * @post returns a negative integer if current node has a smaller letter than
    * o, 0 if equal and positive if the other way round.
    */
    public int compareTo(LexiconNode o) {
      //$ Good!
      return letter - o.getLetter();
    }

    /**
    * addChild(LexiconNode ln)
    * Add LexiconNode child to correct position in vector of children
    * once ln's letter is smaller than the letter of a node in the children vector,
    * add ln at that position of that node.
    * @post ln added to children vector in alphabetical order
    */
    public void addChild(LexiconNode ln) {
      for (int i = 0; i < children.size(); i++){
        if (ln.compareTo(children.get(i)) < 0){
          children.add(i, ln);
          break;
        }
      }
      //if ln's letter is bigger than the letters of all the nodes in the vector
      //or the size of the vector is 0, simply add the node to the vector.
	  //$ why is this check necessary?  Seems like you always want to add the node if you get here
      if (!children.contains(ln)){
        children.add(ln);
	//$ Good!
      }

    }

    /**
    * getChild(char ch)
    * Get LexiconNode child for 'ch' out of children vector
    * @post returns first LexiconNode such that letter = ch; if ch isn't any of the letters, return null
    */
    public LexiconNode getChild(char ch) {
      for (int i = 0; i < children.size(); i++){
        if (ch == children.get(i).getLetter()){
          return children.get(i);
        }
      }
      return null;
    }

    /**
    * removeChild(char ch)
    * Remove LexiconNode child for 'ch' from children Vector.
    * @post removed first node for which ch is the letter
    */
    public void removeChild(char ch) {
      for (int i = 0; i < children.size(); i++){
        if (ch == children.get(i).getLetter()){
          children.remove(i);
          break;
        }
      }
    }

    /**
    * iterator()
    * create an Iterator that iterates over children in alphabetical order
    * @post return iterator of children (children stored in alphabetical order)
    */
    public Iterator<LexiconNode> iterator() {
      return children.iterator();
    }
}

