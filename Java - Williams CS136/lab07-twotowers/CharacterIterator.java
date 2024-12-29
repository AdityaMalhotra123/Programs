import structure5.*;

/**
 * An iterator that yields the consecutive characters of a String, in order
 */
public class CharacterIterator extends AbstractIterator<Character> {
	private int index = 0;
	private String str;
	/**
	*/
	public CharacterIterator(String str) {
		this.str = str;
	}
	/**
	*/
	public Character next() {
		return str.charAt(index++);
	}
	/**
	*/
	public boolean hasNext() {
		return index < str.length();
	}
	/**
	*/
	public void reset() {
		index = 0;
	}
	/**
	*/
	public Character get() {
		return str.charAt(index);
	}
	/**
	*/
	public static void main(String[] args) {
  	CharacterIterator ci = new CharacterIterator("Hello world!");
    for (char c : ci) {
    	System.out.println(c);
    }
  }
}
