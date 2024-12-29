//"I am the sole author of the work in this repository."
/**
* Class to calculate the diff between two files
* */

import structure5.*;
import java.util.Scanner;

/**
* Class to calculate the diff between two files */
public class BetterHashDiff extends Diff {


	/**
	 * hash table to store previously-calculated diffs.
	 * Unlike task 2, this hash table should use BetterIntPair
	 * to obtain improved performance */
	protected Hashtable<BetterIntPair, Association<Integer, String>> store;

	/**
	* Constructor for diff
	* @param file1Name is the path to the original file
	* @param file2Name is the path to the new version of the file
	*/
	public BetterHashDiff(String file1Name, String file2Name) {
		super(file1Name, file2Name);
		/**Students: Add any required initializations */
		store = new Hashtable<BetterIntPair, Association<Integer, String>>();
	}

	/**
	* This helper method compares the three associations and returns the one with
	* minimum cost. It breask ties as explained in the lab handout.
	* @pre recorded and store are not null
	* @param store is the hashtablet that stores previous calls
	* @param recorded is the intpair of the remainingFile indexes
	* @param match is the assocaition returned when lines match
	* @param removef1 is the assocaition returned when line from file1 is removed
	* @param removef2 is the assocaition returned when line from file2 is removed
	* @return association with least cost
	*/
	public Association<Integer, String> returner(Hashtable<BetterIntPair, Association<Integer, String>> store,
	BetterIntPair recorded, Association<Integer, String> match, Association<Integer, String> removef1,
	Association<Integer, String> removef2){
		//calculate the minimum between the three associations
		// Add 1 to the cost for taking a line from file1 or file2.
		// Break ties as described in the lab handout
		if (match != null){
			if (removef1.getKey() <= removef2.getKey() && removef1.getKey() <= match.getKey()){
				store.put(recorded, removef1);
				return removef1;
			}
			else if (removef2.getKey() < removef1.getKey() && removef2.getKey() <= match.getKey()){
				store.put(recorded, removef2);
				return removef2;
			}
			else {
				store.put(recorded, match);
				return match;
			}
		}
		else {
			if ((removef1.getKey() <= removef2.getKey())){
				store.put(recorded, removef1);
				return removef1;
			}
			else {
				store.put(recorded, removef2);
				return removef2;
			}
		}
	}

	/** The recursive helper method for calulating the diff
	 * @pre remainingFile1, remainingFile2, and table are not null
	 * @param remainingFile1Index the first line of file 1 not yet diffed
	 * @param remainingFile2Index the first line of file 2 not yet diffed
	 * @return An association corresponding to the diff between remainingFile1 and
	 * remainingFile2. The key is the cost of the diff (number of changes
	 * necessary). The value is the diff output. */
	public Association<Integer, String> diffHelper(int remainingFile1Index, int
			remainingFile2Index) {
		/**Students: in task 3, implement diffHelper, using your code from
		 * BetterDiff.java as a starting point.  This iteration of  diffHelper
		 * should use a hash table with BetterIntPair to obtain better hash
		 * performance.  Be sure to implement BetterIntPair
		 * to ensure that this works. */
		if (remainingFile2Index == file2.size() || remainingFile1Index == file1.size()){
  		return baseCaseHelper(remainingFile1Index, remainingFile2Index, "");
  	}

 		BetterIntPair recorded = new BetterIntPair(remainingFile1Index, remainingFile2Index);
 		if (store.containsKey(recorded)){
 			return store.get(recorded);
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

		return returner(store, recorded, match, removef1, removef2);


	}

	/**
	* main method: two command line arguments; the first is the original file,
	* the second is the new version to be compared to. */
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Usage: java BetterHashDiff <file1> <file2>");
			System.exit(1);
		}
		Diff diff = new BetterHashDiff(args[0], args[1]);
		diff.findDiff();
	}
}
