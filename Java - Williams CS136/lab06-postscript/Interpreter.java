// We are the sole authors of the work in this repository
//* Everything looks great! Good job.
import structure5.*;
/**
 * An implementation of a basic PostScript interpreter.
 * creates the functionality of the postscript calculator, including adding
 * operations and print methods to be employed by the user
 * @post uses the reader and token classes to allow user calculations
 */
public class Interpreter {
  protected StackList<Token> stack = new StackList<Token>();
  protected SymbolTable table = new SymbolTable();

  /**
  * checks what kind of token the user has inputted and either adds it to the
  * stack or employs the according method if it's a symbol, otherwise fails
  * @pre token is one of the four given "kinds"
  * @post updates the stack based on given user input
  */
  public void interpret(Reader r) {
    Token t;
    // interpret methods runs as long as there is input. Only way to end program is through 'quit'.
    while(r.hasNext()) {
      t = r.next();
      //add appropriate tokens to the stack
      if (t.isNumber() || t.isBoolean() || t.isProcedure() || t.isSymbol() &&
      t.getSymbol().charAt(0) == '/') {
        stack.add(t);
      }
      //use sym method to call approiate command method
      else if(t.isSymbol()){
        sym(t.getSymbol());
      }
      else {
        Assert.fail("invalid token input");

      }
    }
  }
  /**
  * code for default case within the switch in sym method below. Checks whether
  * the given symbol is a procedure or adds it to the stack
  * @pre the symbol exists
  * @post employs the according operation based on the nature of the symbol
  */
  public void switchHelper(String s){
    if (table.contains(s)){
      if (table.get(s).isProcedure()) {
        Reader p = new Reader(table.get(s));
        interpret(p);
      }
      else {
        stack.add(table.get(s));
      }
    }
    else {
      Assert.fail("Symbol does not exist. Please define this symbol first.");
    }
  }

  /**
  * if a given token is a symbol, the sym method will check if this method is
  * is already defined and send it to the appropriate method. If preceded by a
  * '/', it will create a new symbol token that has been defined by the user
  * @pre Token is a symbol
  * @post forwards the token inputted by the user to the appropriate method or
  * creates a new method
  */
  public void sym(String s){
    switch (s){
     case "add": add();
      break;
     case "sub": sub();
      break;
     case "mul": mul();
      break;
     case "div": div();
      break;
     case "exch": exch();
      break;
     case "eq": eq();
      break;
	//$ For what it's worth, it's better to break the lines:
	//$ checkstyle's requirement isn't really about line numbers, it's about splitting into methods
	//$ when possible.
	//$ case statements are straightforward so it's common to combine two of these lines, but rarely three
	//$ e.g.:
	//$ case "pop:"
	//$     pop(); break;
  /** All the following are an the same line so that the method doesn't exceed
   * 30 lines for checkstyle.
   */
     case "pop": pop(); break;
     case "pstack": pstack(); break;
     case "def": def(); break;
     case "lt": lt(); break;
     case "dup": dup(); break;
     case "ne": ne(); break;
     case "quit": quit(); break;
     case "ptable": ptable(); break;
     case "if": commandif(); break;
     default: switchHelper(s);
  }
 }

/**
* checks whether the last two tokens in the stack are of kind number
* @post returns a boolean that reflects whether the last two tokens are of kind number
*/
public boolean checkNumber(){
  Token x = stack.pop();
  Token y = stack.pop();
  stack.push(y);
  stack.push(x);
  return (x.isNumber() && y.isNumber());

}
/**
* removes the last element from the stack
* @post the top element of the stack has been removed and the new top is the
* element underneath
*/
 public void pop(){
   Assert.pre(stack.size() > 0, "There should be at least one token to pop.");
    stack.pop();
 }

 /**
 * Removes the two previous values from the stack and pushes their sum
 * @pre two previous tokens exist
 * @post the sum of the two previous tokens is added to the stack
 */
 public void add(){
    Assert.pre(stack.size() > 1,
     "There should be at least two numbers on the stack for the add command");
    Assert.pre(checkNumber(), "Last two tokens should be numbers.");
    double x = stack.pop().getNumber();
    double y = stack.pop().getNumber();
    stack.push(new Token(x+y));
  }

  /**
  * prints out everything currently on the stack, if the stack is empty,
  * prints nothing
  * @post everything currently in the stack is printed in the terminal
  */
	public void pstack(){
    for (Token t : stack) {
      System.out.print(t + " ");
    }
    System.out.println();
  }

  /**
  * Removes the two previous values from the stack and pushes their product
  * @pre two previous tokens exist
  * @post the product of the two previous tokens is added to the stack
  */
  public void mul(){
    Assert.pre(stack.size() > 1,
    "There should be at least two numbers on the stack for the mul command");
    Assert.pre(checkNumber(), "Last two tokens should be numbers.");
    double x = stack.pop().getNumber();
    double y = stack.pop().getNumber();
    stack.push(new Token(x*y));
  }

  /**
  * Removes the two previous values from the stack and pushes their difference
  * @pre two previous tokens exist
  * @post the difference of the two previous tokens is added to the stack
  */
  public void sub(){
    Assert.pre(stack.size() > 1,
    "There should be at least two numbers on the stack for the sub command");
    Assert.pre(checkNumber(), "Last two tokens should be numbers.");
    double x = stack.pop().getNumber();
    double y = stack.pop().getNumber();
    stack.push(new Token(y-x));
  }

  /**
  * Removes the two previous values from the stack and pushes their quotient
  * @pre two previous tokens exist
  * @post the quotient of the two previous tokens is added to the stack
  */
  public void div(){
    Assert.pre(stack.size() > 1,
    "There should be at least two numbers on the stack for the div command");
    Assert.pre(checkNumber(), "Last two tokens should be numbers.");
    double x = stack.pop().getNumber();
    double y = stack.pop().getNumber();
    stack.push(new Token(y/x));
  }

  /**
  * Switches the two latest values in the stack by popping them and pushing them
  * in reverse order
  * @pre two previous tokens exist
  * @post the 2nd last token on the stack is now the last and vice versa
  */
  public void exch(){
    Assert.pre(stack.size() > 1,
    "There should be at least two tokens on the stack for the exch command");
    Token x = stack.pop();
    Token y = stack.pop();
    stack.push(x);
    stack.push(y);
  }

  /**
  * Removes the two previous values from the stack and pushes a boolean
  * @pre two previous tokens exist
  * @post checks whether the last two tokens exist are equal and returns the
  * appropriate boolean
  */
  public void eq(){
    Assert.pre(stack.size() > 1,
    "There should be at least two tokens on the stack for the eq command");
    Token x = stack.pop();
    Token y = stack.pop();
    stack.push(new Token(x.equals(y)));
  }

  /**
  * pops the last token from the stack and then pushes it back twice
  * @pre a previous token exists
  * @post the last token is duplicated and appears in the stack twice
  */
  public void dup(){
    Assert.pre(stack.size() > 0,
    "There should be at least one token on the stack for the dup command");
    Token x = stack.pop();
    stack.push(x);
    stack.push(x);
  }

  /**
  * Removes the two previous values from the stack and pushes a boolean
  * @pre two previous tokens exist
  * @post checks whether the last two tokens exist are not equal and returns the
  * appropriate boolean
  */
  public void ne(){
    Assert.pre(stack.size() > 1,
    "There should be at least two tokens on the stack for the ne command");
    Token x = stack.pop();
    Token y = stack.pop();
    stack.push(new Token(!(x.equals(y))));
  }

  /**
  * Removes the two previous values from the stack and pushes a boolean
  * @pre two previous numbers exist
  * @post checks whether the last token is less than the second to last token and
  * returns appropriate boolean
  */
  public void lt(){
    Assert.pre(stack.size() > 1,
    "There should be at least two numbers on the stack for the lt command");
    Assert.pre(checkNumber(), "Last two tokens should be numbers.");
    double x = stack.pop().getNumber();
    double y = stack.pop().getNumber();
    stack.push(new Token(y<x));
  }

  /**
  * ends the program
  * @post terminates the program
  */
  public void quit(){
    System.exit(0);
  }

  /**
  * prints out the symbols currently stored within the table
  * @post the symbols from the symbol table are all printed
  */
  public void ptable(){
    System.out.print(table);
  }

  /**
  * allows user to create a new symbol with an associated number, boolean, or procedure
  * @pre newly defined symbol is preceded by a '/'
  * @pre newly defined symbol does not already exist with the same name
  * @post adds the new symbol to the symboltable
  */
  public void def(){
    Token value = stack.pop();
    String symbol = stack.pop().getSymbol();

    if (!symbol.substring(0, 1).equals("/")) {
      Assert.fail("def needs a slash before the symbol. ");
    }
    if (table.contains(symbol.substring(1))) {
      Assert.fail("Symbol alreasy exists. Please define a new symbol");
    }
    table.add(symbol.substring(1), value);
  }

  /**
  * if the second last token on the stack is true then it carries out the
  * next command(s)
  * @pre the second last token on the stack is a boolean
  * @post carries out the following commands if the boolean is true
  */
  public void commandif(){
    Token x = stack.pop();
    Token testing = stack.pop();
    Assert.pre(testing.isBoolean(), "Second last token should be a boolean");
    Boolean y = testing.getBoolean();
    if (y == true){
      Reader a = new Reader(x);
      interpret(a);
    }
  }

  /**
  * Creates an interpreter where a user can input tokens onto the terminal
  * @post every token typed by the user will either be pushed onto the stack
  * or taken to the appropriate method
  */
  public static void main(String[] args){
    Interpreter test = new Interpreter();
    Reader start = new Reader();
    test.interpret(start);
  }
}
