import java.util.*;

/**
 * A doubly linked list
 * 
 * @author Utsav Lal
 */
public class DLinkedList<T> {
	// --------------------------------------------------------------------
    // Private classes
    // --------------------------------------------------------------------

    // Data-only class to store Nodes. Only accessible within DoublyLinkedList.
    private class Node {
        public T value;
        public Node next;
        public Node prev;
        public Node(T value, Node prev, Node next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

    // --------------------------------------------------------------------
    // Private state variables
    // --------------------------------------------------------------------
    private Node head;
    private Node tail;
    private int size;

    // --------------------------------------------------------------------
    // Constructors
    // --------------------------------------------------------------------
    /**
     * Construct an empty list.
     */
    public DLinkedList() {
        size = 0;
        head = new Node(null, null, null);
        head.next = new Node(null, head, null);
        tail = head.next;
    }

    /**
     * Construct a list from a given array of values.
     * 
     * @param  inputArray  array of values to be used to construct the list
    */
    public DLinkedList(T[] inputArray) {
    	size = 0;
    	head = new Node(null, null, null);
    	head.next = new Node(null, head, null);
        tail = head.next;
    	Node curr = head;
    	for (T t : inputArray) {
    		curr.next = new Node(t, curr, tail);
    		curr = curr.next;
    		size += 1;
    	}
    	tail.prev = curr;
    }

    // --------------------------------------------------------------------
    // Getters
    // --------------------------------------------------------------------
    /**
     * Gets the size of the list
     * 
     * @return the size of the list
     */
    public int size() {
    	return size;
    }

    /** 
     * Gets the value of the node at an index
     * 
     * @param index index of node (0 - size-1)
     * 
     * @return value of node at index
     * 
     * @throws NoSuchElementException when index is out of bounds
     */
    public T get(int index) {
    	if (index >= 0 && index < size) {
    		return getNodeAt(index).value;
    	} else {
    		throw new NoSuchElementException("Index out of bounds");
    	}
    }

    /**
     * Prints out the list in reverse
     * 
     * @return String in format valueN, valueN-1, ... value1, 
     */
    public String toReverseOrderString() {
    	StringBuilder sb = new StringBuilder();
    	for (Node curr = tail.prev; curr.prev != null; curr = curr.prev) {
    		sb.append(curr.value+", ");
    	}
    	return sb.toString();
    }

    /**
     * Prints out the list
     * 
     * @return String in format value1, value2, ... valueN, 
     */
    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	for (Node curr = head.next; curr.next != null; curr = curr.next) {
    		sb.append(curr.value+", ");
    	}
    	return sb.toString();
    }

    // --------------------------------------------------------------------
    // Operators
    // --------------------------------------------------------------------
    /**
     * Adds a new node with the given value at the given index
     * 
     * @param value value of element added
     * @param index index to add element at (0 - size)
     * 
     * @throws NoSuchElementException when index is out of bounds
     */
    public void insert(T value, int index) {
    	if (index >= 0 && index <= size) {
    		Node curr = getNodeAt(index);
    		curr.prev = new Node(value, curr.prev, curr);
    		curr.prev.prev.next = curr.prev;
    		size++;
    	} else {
    		throw new NoSuchElementException("Index out of bounds");
    	}
    }

    /**
     * Removes node at given index
     * 
     * @param index index of element to remove (0 - size-1)
     * 
     * @return value of element removed
     * 
     * @throws NoSuchElementException when index is out of bounds
     */
    public T remove(int index) {
    	if (index >= 0 && index <= size) {
    		Node curr = getNodeAt(index);
    		curr.prev.next = curr.next;
    		curr.next.prev = curr.prev;
    		size--;
    		return curr.value;
    	} else {
    		throw new NoSuchElementException("Index out of bounds");
    	}
    }

    // --------------------------------------------------------------------
    // Helpers
    // --------------------------------------------------------------------
    /**
     * Gets the node at the given index
     * 
     * @param index index of requested node
     * 
     * @return requested node
     */
    private Node getNodeAt(int index) {
    	if (index < size/2) {
	        int iter = 0;
	        Node curr = head.next;
	        for (curr = head.next; iter != index; curr = curr.next) {
	            iter++;
	        }
	      	return curr;
	    } else {
	    	int iter = size;
	        Node curr = tail;
	        for (curr = tail; iter != index; curr = curr.prev) {
	            iter--;
	        }
	      	return curr;
	    }
    }
}