public class Odd {
  public static void main(String[] args) {
    /**Start with 1 since that is the first odd number.
      *Since half of first 20 integers are odd, run loop till i is 20.
      *Increment i by 2 so that 10 odd numbers are printed.
    */
    for (int i = 1; i < 20; i = i + 2) {
      System.out.println(i);
    }
  }
}
