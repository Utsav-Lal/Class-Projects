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
     * Adds a new Node at the end of the list
     * 
     * @param value value of element
     */
    public void add(T value) {
        tail.prev.next = new Node(value, tail.prev, tail);
        tail.prev = tail.prev.next;
        size++;
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

    // --------------------------------------------------------------------
    // Iterator Stuff
    // --------------------------------------------------------------------
    /**
     * Creates new iterator at head
     */
    public LLIterator iteratorFromHead() {
        return new LLIterator(true);
    }

    /**
     * Creates new iterator at tail
     */
    public LLIterator iteratorFromTail() {
        return new LLIterator(false);
    }

    // Object that iterates through the list
    public class LLIterator {
        private Node next;  // accessed next time next() is called
        private Node prev;  // accessed next time previous() is called
        // the last node to be returned by previous() or next()
        // this is set to null if remove() or insert() is called
        private Node lastAccessed;

        // Private constructor ensures that you can only make an Iterator
        // using the outer class's method.
        private LLIterator(boolean startsAtHead) {
            if (startsAtHead) {
                next = head.next;
                prev = head;
            } else {
                next = tail;
                prev = tail.prev;
            }
            lastAccessed = null;
        }

        /**
         * Checks if the next node exists
         */
        public boolean hasNext() {
            return next != tail;
        }

        /**
         * Checks if the previous node exists
         */
        public boolean hasPrevious() {
            return prev != head;
        }

        /**
         * Moves the iterator forward one node
         * 
         * @return next element value
         * 
         * @throws NoSuchElementException when there is no next element
         */
        public T next() {
            if (hasNext()) {
                prev = next;
                next = next.next;
                lastAccessed = prev;
                return prev.value;
            } else {
                throw new NoSuchElementException("Iterator out of bounds");
            }
        }

        /**
         * Moves the iterator back one node
         * 
         * @return previous element value
         * 
         * @throws NoSuchElementException when there is no previous element
         */
        public T previous() {
            if (hasPrevious()) {
                next = prev;
                prev = prev.prev;
                lastAccessed = next;
                return next.value;
            } else {
                throw new NoSuchElementException("Iterator out of bounds");
            }
        }

        /**
         * Sets the last accessed element to a new value
         * 
         * @param newValue value to set element to
         * 
         * @throws IllegalStateException when no last accessed element.
         */
        public void set(T newValue) {
            if (lastAccessed != null) {
                lastAccessed.value = newValue;
            } else {
                throw new IllegalStateException("No target element");
            }
        }

        /**
         * Removes the last accessed element
         * 
         * @throws IllegalStateException when no last accessed element.
         */
        public void remove() {
            if (lastAccessed != null) {
                lastAccessed.prev.next = lastAccessed.next;
                lastAccessed.next.prev = lastAccessed.prev;
                lastAccessed = null;
            } else {
                throw new IllegalStateException("No target element");
            }
        }

        /**
         * Inserts an element between next and prev
         * 
         * @param newValue value to insert
         */
        public void insert(T newValue) {
            prev = new Node(newValue, prev, next);
            prev.prev.next = prev;
            next.prev = prev;
            lastAccessed = null;
        }
    }

}