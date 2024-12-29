// Import random and scanner classes in order to create instances for gemerating a random number or reading input
import java.util.Random;
import java.util.Scanner;

public class CoinStrip {
    private static final int MAX_COINS = 7;
    private static final int MIN_COINS = 3;
    //Maximum number of empty squares between two coins. Helps with randomization.
    private static final int SPACES = 3;

    /**
     * Function to create the board
     * @param coins holds the set of coins
     */
    public static void createBoard(int[] coins) {
        //init Random
        Random a = new Random();
        //coins[] contains index positions of coins. Randomize position of first coin such that it isn't in the first square to prevent a winning start.
        coins[0] = a.nextInt(SPACES - 1) + 1;
        //Randomize positions of remaining coins. As you move through coins[], values increase by as much as SPACES.
        for (int i = 1; i < coins.length; i++) {
          coins[i] = coins[i - 1] + a.nextInt(SPACES - 1) + 1;
        }
    }

    /**
    * Checks if Coin Number and Number of Spaces to move is valid in the context of the game situation.
    * @param coins holds the set of coins
    * @param whichCoin the number of the coin to move
    * @param numSPACES number of SPACES to move the coin (left)
    * @return true if the move is valid
    */
    public static boolean isValidMove(int[] coins, int whichCoin, int numSPACES) {
        //  Coin Number must be positive and <= number of coins
        if ((whichCoin <= 0) || (whichCoin > coins.length)) {
          return false;
          //coin must move to the left
        } else if (numSPACES <= 0) {
          return false;
          //cannot do coins[whichCoin - 2] if whichCoin = 1, so separate condition. Number of spaces behind first coin = first index of coins[].
        } else if ((whichCoin == 1) && (numSPACES > coins[0])) {
          return false;
          //coins shouldn't be allowed to jump above other coins or land on other coins.
        } else if ((whichCoin != 1) && (numSPACES >= coins[whichCoin - 1] - coins[whichCoin - 2])) {
          return false;
        }
        return true;
    }

    /** Updates the game board to reflect a move.
     * Behavior is undefined if the described move is invalid.
     * @param coins holds the set of coins
     * @param whichCoin the number of the coin
     * @param numSPACES number of SPACES to move the coin (left)
     */
    public static void makeMove(int[] coins, int whichCoin, int numSPACES) {
      //Since coins[] is a 0-index array but coin numbers start with 1, (whichCoin - 1) is the appropriate index for the coin.
      coins[whichCoin - 1] -= numSPACES;
    }

    /**
    * Returns true if the game is over.
    * @param coins holds the set of coins
    * @return True if the game is over, false otherwise.
    */
    public static boolean isGameOver(int[] coins) {
      //First numCoins squares must have a coin. numCoins = coins.length.
        for (int i = 0; i < coins.length; i++) {
          if (i != coins[i]) {
            return false;
          }
        }
        return true;
    }

    /**
     * Don't need this function.
     * Returns the number of coins on the CoinStrip gameboard.
     * @param coins holds the set of coins
     * @return the number of coins on the CoinStrip gameboard.
     */
    public static int getNumCoins(int[] coins) {
        return coins.length;
    }

    /**
     * Don't need this function.
     * Returns the 0-indexed location of a specific coin. Coins are
     * also zero-indexed. So, if the CoinStrip had 3 coins, the coins
     * would be indexed 0, 1, or 2.
     * @param coins holds the set of coins
     * @param coinNum the 0-indexed coin number
     * @return the 0-indexed location of the coin on the CoinStrip
     */
    public static int getCoinPosition(int[] coins, int coinNum) {
        return coins[coinNum];
    }
   /**
     *Prompts the user for the coin number they want to move.
     *Returns the integer in their input, i.e. whichCoin.
     *@param playerNum the player number.
     *@param s scanner to read user input
   */
    private static int promptCoinNum(int playerNum, Scanner s) {
      System.out.println("Player " + playerNum + ", Which Coin do you want to move?");
      return s.nextInt();
    }
    /**
      *Prompts the user for the number of spaces they want the coin to move.
      *Returns the integer in their input, i.e. numSpaces.
      *@param playerNum the player number.
      *@param s scanner to read user input
    */
	//$ use camelcase (Should be promptNumSpaces)
    private static int promptNumSPACES(int playerNum, Scanner s) {
      System.out.println("Player " + playerNum + ", How many SPACES to the left you want to move?");
      return s.nextInt();
    }
    /**
      *Flip the player number.
      * @param The current player.
      * @returns The new player.
    */
    private static int switchPlayer(int playerNum) {
	    if (playerNum == 1) {
	       return 2;
	    } else {
	       return 1;
	    }
    }
    /**
      *Prints out the coinstrip.
      @param coins[] holds the set of coins.
    */
    private static void printBoard(int[] coins) {
      //count represents the coin number to print
      int count = 1;
      //SPACEs * MAX_COINS ensures the loop runs the max possible times to iterate through every square in the coinstrip.
      for (int i = 0; i < (SPACES * MAX_COINS); i++) {
        //print a coin if i = index of coin.
        if (i == coins[count - 1]) {
          System.out.print("[" + count + "]");
          count++;
          //print an empty square otherwise.
        } else {
          System.out.print("[ ]");
        }
        //if all coins have been printed, no more squares need to be printed.
        if (count > coins.length) {
          break;
        }
      }
      System.out.println();
    }
    /**
     * `public static void main(String[] args)` is a program's entry point.
     * This main method implements the functionality to play the Coinstrip
     * game.
     *
     * @param Command-line arguments are ignored.
     */
    public static void main(String[] args) {
        System.out.println("Welcome to the Silver Dollar Game!");
        //init random
        Random r = new Random();
        //randomize number of coins between 3 and 7
        int numCoins = r.nextInt(MAX_COINS - MIN_COINS) + MIN_COINS;
        //setup coins[] of type int
        int[] coins = new int[numCoins];
        //allocate coins
        createBoard(coins);
        //current player
        int playerNum = 1;
        //init Scanner
        Scanner s = new Scanner(System.in);
        //keep playing while game not won
        while (!isGameOver(coins)) {
          //print board
          printBoard(coins);
          //input coin number and number of spaces to move
          int whichCoin = promptCoinNum(playerNum, s);
          int numSPACES = promptNumSPACES(playerNum, s);
          //if move isn't valid, tell user and prompt again
          while (!isValidMove(coins, whichCoin, numSPACES)) {
            System.out.println("Invalid move, player " + playerNum + "!");
            break;
          }
          //if move is valid, move the coin accordingly and change player number so that next set of entries is made by other player
          if (isValidMove(coins, whichCoin, numSPACES)) {
            makeMove(coins, whichCoin, numSPACES);
            playerNum = switchPlayer(playerNum);
          }
        }
        //since player number is switched at the end to set up for next entries, it needs to be switched again if the loop doesn't run again.
        playerNum = switchPlayer(playerNum);
        //Indicate winner
        System.out.println("Player " + playerNum + " wins!");
    }
}
//$nice!
