//We are the sole authors of the work in this repository.
/**
 * Implementation of lists, using doubly linked elements, and dummy nodes.
*/

import structure5.*;
import java.util.Iterator;

public class LinkedList<E> extends DoublyLinkedList<E> {

	// Use these variables inherited from DoublyLinkedList
	// Do not uncomment this!  Just use the variables as if they were uncommented
	/**
	* Number of elements within the list.
	*	protected int count;
	*/

	/**
	* Reference to head of the list.
	*
	protected DoublyLinkedNode<E> head;
	*/

	/**
	* Reference to tail of the list.
	*
	protected DoublyLinkedNode<E> tail;
	*/


	/**
	* Constructs an empty list.
	* constructor method
	* @post constructs an empty list
	* method takes O(c) times; creates linkedList object
	*/
	public LinkedList() {
		// empty linkedList's head points (next) to the tail.
		// the tail's previous node is head
		head = new DoublyLinkedNode<E>(null, tail, null);
		tail = new DoublyLinkedNode<E>(null, null, head);
		count = 0; // initial count is 0
	}

	/**
	* Determine the number of elements in the list.
	*
	* @post returns the number of elements in list
	*
	* @return The number of elements found in the list.
	* method takes O(c) time
	*/
	public int size() {
		return count;
	}

	/**
	* Determine if the list is empty.
	*
	* @post returns true iff the list has no elements.
	*
	* @return True iff list has no values.
	* method takes O(c) time
	*/
	public boolean isEmpty() {
		return size() == 0; // is count is equal to 0
	}

	/**
	* Remove all values from list.
	* @pre
	* @post removes all the elements from the list
	* method takes O(c) time
	*/
	public void clear() {
		head.setNext(tail); // set head's tip to point to tail
		tail.setPrevious(head); // set tail's previous to point to head
		count = 0; // set count to 0
	}
	/**
	* A private routine to add an element after a node.
	* @param value the value to be added
	* @param previous the node the come before the one holding value
	* @pre previous is non null
	* @post list contains a node following previous that contains value
	* method takes O(c) (constant) time
	*/
	protected void insertAfter(E value, DoublyLinkedNode<E> previous) {
		// create a new node with its previous node pointing to the node i am
		// adding after; and its next node the next node.
		DoublyLinkedNode<E> node = new DoublyLinkedNode<E>(value, previous.next(), previous);
		// calling DoublyLinkedNode makes the necessary changes to the previous and nect node by itself
		// see DoublyLinkedNode's source code for details

		//$ don't need to comment lines of code that are self-explanatory
		count++; // count is increased by one
	}

	/**

	* A private routine to remove a node.
	* @param node the node to be removed
	* @pre node is non null
	* @post node node is removed from the list
	* @post returns the value of the node removed
	* @return the value of the node removed
	* method takes O(c) constant time since method involves updating node values
	*/
	protected E remove(DoublyLinkedNode<E> node) {
		//$ should use Assert.pre to check that node is non null
		node.previous().setNext(node.next()); // the previous node's next points to current's node's next
		node.next().setPrevious(node.previous()); // next node's previous points to current's node's previous
		count--;
		return node.value();

	}


	/**
	* Add a value to the head of the list.
	*
	* @param value The value to be added.
	* @pre value is not null
	* @post adds element to head of list
	* * method takes O(c) constant time
	*/
	public void addFirst(E value) {
		//$ Assert.pre
		// construct a new element, making it the actual head. Count is updated through insertAfter.
		insertAfter(value, head);

	}

	/**
	* Add a value to the tail of the list.
	*
	* @param value The value to be added.
	* @pre value is not null
	* @post adds new value to tail of list
	* * method takes O(c) constant time
	*/
	public void addLast(E value) {
		//$ Assert.pre
		insertAfter(value, tail.previous());
	}

	/**
	* Remove a value from the head of the list.
	* Value is returned.
	*
	* @pre list is not empty
	* @post removes first value from list
	*
	* @post Returns the value removed from the list.
	* @return The value removed from the list.
	* method takes O(c) constant time
	*/
	public E removeFirst() {
		Assert.pre(!isEmpty(), "List is empty.");
		return remove(head.next());
	}

	/**
	* Remove a value from the tail of the list.
	*
	* @pre list is not empty
	* @post removes value from tail of list
	* @post Returns the value removed from the list.
	*
	* @return The value removed from the list.
	* method takes O(c) constant time
	*/
	public E removeLast() {
		Assert.pre(!isEmpty(), "List is empty.");
		return remove(tail.previous());
	}

	/**
	* Get a copy of the first value found in the list.
	*
	* @pre list is not empty
	* @post returns first value in list.
	*
	* @return A reference to first value in list.
	* method takes O(c) constant time
	*/
	public E getFirst() {
		//$ it's important to check that the list is non-empty to avoid returning null
		return head.next().value();
	}

	/**
	* Get a copy of the last value found in the list.
	*
	* @pre list is not empty
	* @post returns last value in list.
	*
	* @return A reference to the last value in the list.
	* method takes O(c) constant time
	*/
	public E getLast() {
		return tail.previous().value();
	}


  /** Given an index finds the node at that index
	* @pre 0 <= i <= size()
	* @param i the index (position)
	* @post return the node at a particular index in order
	* takes O(n) time proportional to size of the list;
	* @return returns the node at a particular index
	*/
  protected DoublyLinkedNode<E> findNode(int i) {
		Assert.pre((0 <= i) && (i <= size()), "Index out of range.");
		DoublyLinkedNode<E> node = head.next();
			while (i > 0) {
				node = node.next();
				i--;
			}
			return node;
	}


	/**
	* Insert the value at location.
	* @pre 0 <= i <= size()
	* @post adds the ith entry of the list to value o
	* @param i the index of this new value
	* @param o the the value to be stored

	* Finding the node at index takes takes O(n) time proportional to the list,
	* inserting the element takes O(c) time. Big O time is O(n);
	*/

	public void add(int i, E o) {
		DoublyLinkedNode<E> node = findNode(i); // find node
		insertAfter(o, node.previous()); // insert it after previous
	}

	/**
	* Remove and return the value at location i.
	* @pre 0 <= i < size()
	* @post removes and returns the object found at that location.
	*
	* @param i the position of the value to be retrieved.
	* @return the value retrieved from location i (returns null if i invalid)
	* Finding the node at index takes takes O(n) time proportional to the list,
	* removing the element takes O(c) time. Big O time is O(n);
	*/

	public E remove(int i) {
		DoublyLinkedNode<E> node = findNode(i); // find node
		return remove(node); // remove object at that node
	}


	/**
	* Get the value at location i.
	*
	* @pre 0 <= i < size()
	* @post returns the object found at that location.
	*
	* @param i the position of the value to be retrieved.
	* @return the value retrieved from location i (returns null if i invalid)
	* Finding the node at index takes takes O(n) time proportional to the list,
	* getting the element takes O(c) time. Big O time is O(n);
	*/
	public E get(int i) {
		DoublyLinkedNode<E> node = findNode(i); //find node
		return node.value(); // return value
	}

	/**
	* Set the value stored at location i to object o, returning the old value.
	*
	* @pre 0 <= i < size()
	* @post sets the ith entry of the list to value o, returns the old value.
	* @param i the location of the entry to be changed.
	* @param o the new value
	* @return the former value of the ith entry of the list.
	* Finding the node at index takes takes O(n) time proportional to the list,
	* setting a new value at the index takes O(c) time. Big O time is O(n);
	*/

	public E set(int i, E o) {
		DoublyLinkedNode<E> node = findNode(i); // find node
		E old = node.value(); // save node's old value in old variable of generic type
		node.setValue(o); // set value of node at the index to o
		return old; // return th previous value of node
	}


	/**
	* Determine the first location of a value in the list.
	* @pre value is not null
	* @post returns the (0-origin) index of the value, or -1 if the value is not found
	* @param value The value sought.
	* @return the index (0 is the first element) of the value, or -1

	* Finding the index given a value takes O(n) time proportional to the list, as
	* every element's value is checked and the count (index) where it matches is
	* returned.
	*/
	public int indexOf(E value) {
		int i = 0; // starting from 0

		DoublyLinkedNode<E> current = head.next(); //a DoublyLinkedNode that keeps track of current node

		while (current != null) { // stop when we tail
			if (value.equals(current.value())) { // compare value and current node's value
				return i; // if the values are the same, exit out of the loop and return at the index when it happens
			}
			current = current.next(); // else go the next node repeat process
			i++;
		}
		return -1; // if value not found return -1
	}

	/**
	* Determine the last location of a value in the list.
	* @pre value is not null
	* @post returns the (0-origin) index of the value,
	*   or -1 if the value is not found
	*
	* @param value the value sought.
	* @return the index (0 is the first element) of the value, or -1

	* Finding the Last index given a value takes O(n) time proportional to the list, as
	* every element's value is checked and the count (in reverse) where it matches is
	* returned.
	*/
	public int lastIndexOf(E value) {

		int i = size() - 1; // start from top; count - 1

		DoublyLinkedNode<E> current = tail.previous(); // create a node called current
		// that keeps track of nodes, we start from the end

		while (current != null) { // exit while loop if we reach head
			if (value.equals(current.value())) { // compare value
				return i; //  if value matches, exit method and return the index where it happens (from opposite side)
			}

			current = current.previous(); // else update current to previous node and repeat process
			i--;
		}
		return -1; // else return -1, if value not found
	}


	/**
	* Check to see if a value is within the list.
	*
	* @pre value not null
	* @post returns true iff value is in the list
	*
	* @param value A value to be found in the list.
	* @return True if value is in list.

	* Checking to see if a values is in a list takes O(n) time proportional to the
	* list as every value in the list is checked.
	*/
	public boolean contains(E value) {
		if (indexOf(value) != -1) { // check if it is in the linkedList, i.e it has a >= 0
			// index which is not equal to -1
			return true; // return truw if index found
		}
		return false; // false otherwise
	}

	/**
	* Remove a value from the list.  At most one value is removed.
	* Any duplicates remain.  Because comparison is done with "equals,"
	* the actual value removed is returned for inspection.
	*
	* @pre value is not null.  List can be empty.
	* @post first element matching value is removed from list
	*
	* @param value The value to be removed.
	* @return The value actually removed.

	* Finding the index given a value takes O(n) time proportional to the list, as
	* every element's value is checked and the value at the which the index matches is
	* returned.
	*/
	public E remove(E value) {
		/* Code below works and it is short, and simple but slow, since it loops through twice.
       first to find the index and second to find the node at that index and then removes it
		int index = indexOf(value);
		return remove(index); // first finds the node at the index and then removes it.

		Alternative code - returns the node given a value and then removes it - one loop
		*/

		DoublyLinkedNode<E> current = head.next(); // current node keeps track of the current node

		//$ you could check if current == tail (or current.value == null) instead;
		//$ this is more direct (and decreases the number of loops)
		while (current != null) { // stop when we hit tail i.e end of the linkedlist
			if (value.equals(current.value())) { // compare values
				return remove(current); // if they match, remove it and return value at that node; exit method
			}
			current = current.next(); // else go to the next node and repeat
		}
		return null; // if did not find value return null
	}

	/**
	* Construct an iterator to traverse the list.
	*
	* @post returns iterator that allows the traversal of list.
	*
	* @return An iterator that traverses the list from head to tail.
	*/
	public Iterator<E> iterator() {

		return new DoublyLinkedListIterator<E>(head, tail);

	}

	/**
	* Construct a string representation of the list.
	*
	* @post returns a string representing list
	*
	* @return A string representing the elements of the list.
	*/
	public String toString() {
		StringBuffer s = new StringBuffer();
		s.append("<LinkedList (" + count + "):");

		Iterator<E> li = iterator();
		while (li.hasNext()) {
			s.append(" " + li.next());
		}
		s.append(">");

		return s.toString();
	}
}
