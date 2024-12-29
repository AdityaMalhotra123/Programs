//We are the sole authors of the work in this repository.
/*
Test for the LinkedList class from CS136 "Dummy Lists" lab
original version by Bill Jannen
*/

import structure5.*;
import java.util.*;

public class TestLinkedList {

	/**
	 * a simple helper method to display a list, and compare the list's
	 * state to its expected state. This can be improved (and we encourage
	 * it!)
	 *
	 * This method relies on the formatting of the LinkedList
	 * class's toString method and knowledge of the expected list.
	 * It takes two parameters: The first is a String
	 * representation of the contents of the list; the caller must
	 * provide this String based on knowledge of the expected
	 * behavior program behavior---that string is not something
	 * that Java can provide or infer. The second argument is the
	 * actual list.  The method then prints the "expected" list
	 * contents followed by the "provided" list contents so that
	 * you can visually compare them.
	 *
	 * @pre list != null
	 * @param expected the String representation of the list contents
	 * @param list the target list to print
	 */

	private static <E> void compareList(String expected, LinkedList<E> list) {
		System.out.println("\tExpected: <LinkedList (" + list.size() + "):"
		                   + expected + ">");
		System.out.println("\tReceived: " + list.toString());
	}

	/**
	 * Tests addFirst functionality on an empty list
	 * @param list an empty list
	 * @pre list list is empty and not null
	 */
	public static void addFirstTest(LinkedList<String> list) {
		assert list.size() == 0;

		System.out.println("addFirst test:");

		for (int i = 5; i >= 0; i--) {
			list.addFirst(String.valueOf(i));
		}
		compareList(" 0 1 2 3 4 5", list);
	}

	/**
	 * Tests clear functionality
	 * @param list any list, but hopefully a nonempty one
	 * @pre list is not null
	 * @post list is empty
	 */
	public static void clearTest(LinkedList<String> list) {
		System.out.println("clear test:");

		list.clear();
		compareList("", list);
	}

	/**
	 * Tests addLast functionality on an empty list
	 * @param list an empty list
	 * @pre list list is empty and not null
	 */
	public static void addLastTest(LinkedList<String> list) {
		assert list.size() == 0;

		System.out.println("addLast test:");

		for (int i = 0; i <= 5; i++) {
			list.addLast(String.valueOf(i));
		}
		compareList(" 0 1 2 3 4 5", list);
	}

	/**
	 * Tests add(i, o) functionality
	 * This test relies on addList, so please thoroughly
	 * test that method first.
	 *
	 * @param list an empty list
	 * @pre list list is empty and not null
	 */
	public static void addTest(LinkedList<String> list) {
		assert list.size() == 0;


		System.out.println("add(i, o) test:");

		for (int i = 0; i <= 5; i++) {
			list.addLast(String.valueOf(i));
		}
		//expected list: 0 1 2 3 4 5

		for (int i = 0; i <= 5; i++) {
			list.add(i * 2, String.valueOf(i));
		}
		compareList(" 0 0 1 1 2 2 3 3 4 4 5 5", list);
	}




		/**
		 * Tests add(i, o) functionality
		 * This test relies
		 * @param list an empty list
		 * @pre list list is empty and not null
		 */

	public static void addbetweenTest(LinkedList<String> list) {
		assert list.size() == 0;
		System.out.println("add(i, o) between test:");

		for (int i = 0; i <= 5; i++) {
			list.add(i, String.valueOf(i));
		}
		//expected list: 0 1 2 3 4 5
		list.add(3, "||");
		compareList(" 0 1 2 || 3 4 5", list);
	}



	/**
	 * Tests remove(o) functionality
	 * This test relies on addFirst and addList, so please thoroughly
	 * test those methods first.
	 *
	 * @param list an empty list
	 * @pre list list is empty and not null
	 */
	public static void removeTest(LinkedList<String> list) {
		assert list.size() == 0;

		System.out.println("remove(o) test:");

		for (int i = 0; i <= 5; i++) {
			list.addFirst(String.valueOf(i));
			list.addLast(String.valueOf(i));
		}
		// expected list: 5 4 3 2 1 0 0 1 2 3 4 5

		for (int i = 0; i <= 5; i++) {
			list.remove(String.valueOf(i));
		}
		compareList(" 0 1 2 3 4 5", list);
	}


		/**
		 * Tests remove(i) (index) functionality
		 * @param list an empty list
		 * @pre list list is empty and not null
		 */
	public static void removeTest2(LinkedList<String> list) {
		assert list.size() == 0;

		System.out.println("remove(i) test:");

		for (int i = 0; i <= 5; i++) {
			list.addFirst(String.valueOf(i));
			list.addLast(String.valueOf(i));
		}
		// expected list: 5 4 3 2 1 0 0 1 2 3 4 5

		for (int i = 0; i <= 5; i++) {
			list.remove(i);
		}
		//Note that every time an element is removed, the indexed positions of each element change.
		compareList(" 4 2 0 1 3 5", list);
	}


		/** tests remove functionality when it returns null
		 * @param list an empty list
		 * @pre list list is empty and not null
		 */

	public static void removeTest3(LinkedList<String> list) {
		assert list.size() == 0;

		System.out.println(" null remove(o) test:");

		for (int i = 0; i <= 5; i++) {
			list.addFirst(String.valueOf(i));
			list.addLast(String.valueOf(i));
		}
		// expected list: 5 4 3 2 1 0 0 1 2 3 4 5

		list.remove("hello");
		compareList(" 5 4 3 2 1 0 0 1 2 3 4 5", list);
	}

	/**
	 * Tests set(i, o) functionality
	 * This test relies on addFirst, so please thoroughly
	 * test that method first.
	 *
	 * @param list an empty list
	 * @pre list list is empty and not null
	 */
	public static void setTest(LinkedList<String> list) {
		assert list.size() == 0;

		System.out.println("set(i, o) test:");

		for (int i = 0; i <= 5; i++) {
			list.addFirst(String.valueOf(i));
		}

		// expected list: 5 4 3 2 1 0

		for (int i = 0; i <= 5; i++) {
			list.set(i, String.valueOf(i));
		}
		compareList(" 0 1 2 3 4 5", list);
	}

	/**
	 * Tests set(i, o) functionality
	 * This test relies on addFirst, so please thoroughly
	 * test that method first.
	 *
	 * @param list an empty list
	 * @pre list list is empty and not null
	 */

	public static void setandgetTest2(LinkedList<String> list) {
		assert list.size() == 0;

		System.out.println("set(i, o) and get(i) test 2:");

		for (int i = 0; i <= 5; i++) {
			list.addFirst(String.valueOf(i));
		}

		// expected list: 5 4 3 2 1 0

		for (int i = 0; i <= 5; i++) {
			list.set(i, String.valueOf(Integer.valueOf(list.get(i)) * 2));
		}
		compareList(" 10 8 6 4 2 0", list);
	}


	/**
	 * Tests indexOf(o) and lastIndexOf(o) functionality
	 * This test relies on addList, so please thoroughly
	 * test that method first.
	 *
	 * @param list an empty list
	 * @pre list list is empty and not null
	 */
	public static void indexingTest(LinkedList<String> list) {
		assert list.size() == 0;

		System.out.println("indexing test:");

		for (int i = 0; i <= 5; i++) {
			list.addFirst(String.valueOf(i));
			list.addLast(String.valueOf(i));
		}
		// expected list: 5 4 3 2 1 0 0 1 2 3 4 5

		System.out.println("\tExpected: 4");
		System.out.println("\tReceived: " + list.indexOf("1"));
		System.out.println("\tExpected: 7");
		System.out.println("\tReceived: " + list.lastIndexOf("1"));
    // additional tests ...
		System.out.println("\tExpected: -1");
		System.out.println("\tReceived: " + list.indexOf("9"));
		System.out.println("\tExpected: -1");
		System.out.println("\tReceived: " + list.lastIndexOf("a"));
	}

	/**
	 *	THIS WILL NOT WORK UNTIL YOU HAVE DUMMY NODES IN YOUR LIST!!!
	 *	The LinkedList.iterator() method assumes dummy nodes are present.
	 */
	public static void main(String[] args) {
		LinkedList<String> list = new LinkedList<String>();

		addFirstTest(list);
		clearTest(list);

		addLastTest(list);
		clearTest(list);

		addTest(list);
		clearTest(list);

		removeTest(list);
		clearTest(list);

		setTest(list);
		clearTest(list);

		indexingTest(list);
		clearTest(list);

		addbetweenTest(list);
		clearTest(list);

		setandgetTest2(list);
		clearTest(list);

		removeTest2(list);
		clearTest(list);

		removeTest3(list);

	}
}
