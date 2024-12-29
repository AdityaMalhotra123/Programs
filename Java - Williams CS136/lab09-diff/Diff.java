//"I am the sole author of the work in this repository."
/**
 * Class to calculate the diff between two files
 * */

import structure5.*;
import java.util.Scanner;

/**
 * Class to calculate the diff between two files */
public class Diff {

	//original file
	protected Vector<String> file1;
	//new version of the file
	protected Vector<String> file2;

	/**
	 * Constructor for diff
	 * @param file1Name is the path to the original file
	 * @param file2Name is the path to the new version of the file
	 */
	public Diff(String file1Name, String file2Name) {
		file1 = readInFile(file1Name);
		file2 = readInFile(file2Name);
	}

	/**
	 * Reads in the fine given by fileName.
	 * Note that this method requires Java 11.
	 * @param  fileName name of the file
	 * @return each line of the file as elements of a Vector */
	protected Vector<String> readInFile(String fileName) {
		Vector<String> ret = new Vector<String>();
		Scanner sc = new Scanner(new FileStream(fileName));
		while (sc.hasNext()) {
			ret.add(sc.nextLine());
		}
		return ret;
	}

	/**
	 * toString method
	 * @return the concatenation of the two files
	 */
	public String toString() {
		String ret = "-----\nFile 1:\n-----\n";
		for (String line : file1) {
			ret += line + "\n";
		}
		ret += "-----\nFile2:\n-----\n";
		for (String line : file2) {
			ret += line + "\n";
		}
		return ret;
	}

	/**
	 * Finds the diff of two files
	 * @pre 	file1 and file2 are strings holding the
	 * files we want to compare
	 * @post 	the diff is printed to the terminal
	 */
	public void findDiff() {
		System.out.println(diffHelper(0, 0).getValue());
	}

	/**
	* baseCaseHelper determines which file has lines left to interpret at the end
	* @pre remainingFile1, remainingFile2 are not null
	* @param remainingFile1Index the first line of file 1 not yet diffed
	* @param remainingFile2Index the first line of file 2 not yet diffed
	* @return An association corresponding to the lines left in the appropriate file
	*/
	public Association<Integer, String> baseCaseHelper(int remainingFile1Index, int remainingFile2Index, String difference){
		if (remainingFile2Index == file2.size()){
			for (int i = remainingFile1Index; i < file1.size(); i++){
				difference += "< " + String.valueOf(i + 1) + ": " + file1.get(i) + "\n";
			}
			return new Association<Integer, String>(file1.size() - remainingFile1Index, difference);
		}
		if (remainingFile1Index == file1.size()){
			for (int i = remainingFile2Index; i < file2.size(); i++){
				difference += "> " + String.valueOf(i + 1) + ": " + file2.get(i) + "\n";
			}
			return new Association<Integer, String>(file2.size() - remainingFile2Index, difference);
		}
		return null;
	}

	/**
	* This helper method compares the three associations and returns the one with
	* minimum cost. It breask ties as explained in the lab handout.
	* @param match is the assocaition returned when lines match
	* @param removef1 is the assocaition returned when line from file1 is removed
	* @param removef2 is the assocaition returned when line from file2 is removed
	* @return association with least cost
	*/
	public Association<Integer, String> returner(Association<Integer, String> match, Association<Integer, String> removef1,
	Association<Integer, String> removef2){
		// calculate the minimum between the three associations
		// Add 1 to the cost for taking a line from file1 or file2.
		// Break ties as described in the lab handout
		if (match != null){
			if (removef1.getKey() <= removef2.getKey() && removef1.getKey() <= match.getKey()){
				return removef1;
			}
			else if (removef2.getKey() < removef1.getKey() && removef2.getKey() <= match.getKey()){
				return removef2;
			}
			else {
				return match;
			}
		}
		else {
			if ((removef1.getKey() <= removef2.getKey())){
				return removef1;
			}
			else {
				return removef2;
			}
		}
	}

	/**
	 * The recursive helper method for calulating the diff
	 * @pre remainingFile1, remainingFile2, and table are not null
	 * @param remainingFile1Index the first line of file 1 not yet diffed
	 * @param remainingFile2Index the first line of file 2 not yet diffed
	 * @return An association corresponding to the diff between remainingFile1
	 * and remainingFile2.  The key is the cost of the diff (number of changes
	 * necessary).  The value is the diff output. */
	public Association<Integer, String> diffHelper(int remainingFile1Index, int remainingFile2Index) {

		/**Students: implement this method
		 * Comments have been added to help guide your implementation
		 * You'll likely want to add helper methods to keep diffHelper
		 * relatively short and clean.  */

		//base case: one file has no remaining lines
		if (remainingFile2Index == file2.size() || remainingFile1Index == file1.size()){
			return baseCaseHelper(remainingFile1Index, remainingFile2Index, "");
		}

		//check if first lines match
		//If so, calculate recursively the optimal result with matching lines
		//Store that result in an Association
		Association<Integer, String> match = null;
		if (file1.get(remainingFile1Index).equals(file2.get(remainingFile2Index))){
			match = diffHelper(remainingFile1Index + 1, remainingFile2Index + 1);
		}
		//calculate the cost of removing a line from file1 (store solution in an Association)
		Association<Integer, String> temp1 = diffHelper(remainingFile1Index + 1, remainingFile2Index);
		Association<Integer, String> removef1 = new Association<>(temp1.getKey() + 1, "< " +
		String.valueOf(remainingFile1Index + 1) + ": " + file1.get(remainingFile1Index) + "\n" + temp1.getValue());

		//calculate the cost of removing a line from file2 (store solution in an Association)
		Association<Integer, String> temp2 = diffHelper(remainingFile1Index, remainingFile2Index + 1);
		Association<Integer, String> removef2 = new Association<>(temp2.getKey() + 1, "> " +
		String.valueOf(remainingFile2Index + 1) + ": " + file2.get(remainingFile2Index) + "\n" + temp2.getValue());

		//calculate the return value using the best recursive solution and return it
		return returner(match, removef1, removef2);



	}

	/**
	 * main method: two command line arguments; the first is the original file,
	 * the second is the new version to be compared to. */
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Usage: java Diff <file1> <file2>");
			System.exit(1);
		}
		Diff diff = new Diff(args[0], args[1]);
		diff.findDiff();
	}
}
