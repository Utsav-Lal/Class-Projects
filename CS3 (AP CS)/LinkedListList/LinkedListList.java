import java.util.*;


/**
 * A singly-linked list of singly-linked lists.
 *
 * @author Utsav Lal
 */
public class LinkedListList {
    // --------------------------------------------------------------------
    // Private classes
    // --------------------------------------------------------------------

    // Data-only class to store Nodes. Only accessible within LinkedListList.
    private class Node {
        public LinkedListList value;
        public Node next;
        public Node(LinkedListList value, Node next) {
            this.value = value;
            this.next = next;
        }
    }

    // --------------------------------------------------------------------
    // Private state variables
    // --------------------------------------------------------------------
    private Node head;
    private int size;
    
    // --------------------------------------------------------------------
    // Public methods
    // --------------------------------------------------------------------
    /**
     * Construct an empty list.
     */
    public LinkedListList() {
        size = 0;
    }

    /**
     * Construct a list from a given array of values.
     * 
     * @param  inputArray  array of values to be used to construct the list
    */
    public LinkedListList(LinkedListList[] inputArray) {
        initialize(inputArray);
    }

    /**
     * Print the list to the screen.
     * 
     * The format is as follows: "1, 2, 35, 12, "
     */
    public void display() {
        for (Node n = head; n != null; n = n.next) {
            System.out.print(n.value);
            System.out.print(", ");
        }
    }

    public void set(int index, LinkedListList value) {
        if (index < size) {
            getNodeAt(index).value = value;
        } else {
            throw new NoSuchElementException("Index out of bounds");
        }
    }

    /**
     * Asks the user for a string containing a list to replace the
     * current list.
     * 
     * The prompt will look like:
     * <pre>
     * Enter a list of numbers, separated by commas:
     * > </pre>
     * The input should include integers separated by commas. Spacing
     * does not matter.
     * 
     * The program will crash if the user messes up. This is only
     * meant for flawless users.
     */
    /**
    public void inputList() {
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter a list of numbers, separated by commas:");
        System.out.print("> ");  // print prompt
        String input = reader.nextLine();
        // strip spaces
        input = input.replaceAll("\\s+", "");
        // split on commas
        String[] numbersAsStrings = input.split(",");
        // convert to integers
        int[] numbers = new int[numbersAsStrings.length];
        for (int i = 0; i < numbersAsStrings.length; i++) {
            numbers[i] = Integer.parseInt(numbersAsStrings[i]);
        }
        
        initialize(numbers);
    }
    **/
    /**
     * Inserts an item at the specified location.
     * 
     * @param  index  the location to insert the new item (0 - size)
     * @param  value  the value of the new item
     * 
     * @throws NoSuchElementException when index > size
     */
    public void insert(LinkedListList value, int index) {
        if (index <= size) {
            if (index == 0) {
                if (size == 0) {
                    head = new Node(value, null);
                }
                else {
                    head = new Node(value, head.next);
                }
            } else {
                Node curr = getNodeAt(index-1);
                curr.next = new Node(value, curr.next);
            }
            size++;
        } else {
            throw new NoSuchElementException("Index out of bounds");
        }
    }

    /**
     * Removes the item at the specified location.
     * 
     * @param  index  the location to item to remove (0 - size-1)
     * 
     * @return the value of the removed item
     * 
     * @throws NoSuchElementException when index >= size
     */
    public LinkedListList remove(int index) {
        if (index < size) {
            LinkedListList value;
            if (index == 0) {
                Node remove = getNodeAt(index);
                value = remove.value;
                head = remove.next;
            } else {
                Node before = getNodeAt(index-1);
                value = before.next.value;
                before.next = before.next.next;
            }
            size--;
            return value;
        } else {
            throw new NoSuchElementException("Index out of bounds");
        }
    }
    
    /**
     * Returns the number of items in the list.
     * 
     * @return  the number of items in the list
     */
    public int size() {
        return size;
    }

    /**
     * Gets the value of the element at an index
     * 
     * @param index index of element (0 - size-1)
     * 
     * @return value of element at index
     * 
     * @throws NoSuchElementException when index is out of bounds
     */
    public LinkedListList get(int index) {
        if (index < size) {
            return getNodeAt(index).value;
        }
        else {
            throw new NoSuchElementException("Index out of bounds");
        }
    }
    
    /**
     * Returns the cumulative sum at each point in the calling list.
     * 
     * Returns a new list containing the cumulative sums. The
     * calling list is not modified.
     * 
     * @return  a new list in which the kth item is the sum of the
     *          first k items of the calling list
     */
    /**
    public LinkedListList cumulativeSum() {
        LinkedListList returnList = new LinkedListList();
        int currentSum = head.value;
        returnList.head = new Node(head.value, null);
        Node tail = returnList.head;
        if (head.next != null) {
            for (Node current = head.next; current != null; current = current.next) {
                currentSum += current.value;
                tail.next = new Node(currentSum, null);
                tail = tail.next;
                returnList.size++;
            }
        }
        return returnList;
    }
    **/
    /**
     * "Deals" an array of lists using elements from this list.
     * 
     * Creates numHands lists of size handSize using the first
     * numHands * handSize items of this list. For example, if
     * numHands is 3 and handSize is 4, then the first item in the
     * returned array of lists will contain <1st item of this list,
     * 4th item, 7th item, 10th item>. The items "dealt" to the
     * returned lists will be removed from the calling list.
     * 
     * @param  numHands  number of new lists to return
     * @param  handSize  the size of each new list to return
     * 
     * @return  array of length numHands each containing handSize
     *          items originating from the calling list
     * 
     * @throws IllegalArgumentException when numhands * handsize > size
     */
    /**
    public LinkedListList[] deal(int numHands, int handSize) {
        if (numHands * handSize <= size) {
            LinkedListList[] returnList = new LinkedListList[numHands];
            Node[] lastList = new Node[numHands];
            for (int i = 0; i < numHands; i++) {
                returnList[i] = new LinkedListList();
                lastList[i] = returnList[i].head;
            }

            int count = 0;
            for (Node curr = head; count < numHands*handSize; curr = head) {
                if (count/numHands < 1) {
                    returnList[count%numHands].head = curr;
                } else {
                    lastList[count%numHands].next = curr;
                }

                lastList[count%numHands] = curr;
                returnList[count%numHands].size += 1;
                head = curr.next;
                curr.next = null;

                size--;
                count++;
            }
            return returnList;
        } else {
            throw new IllegalArgumentException("Not enough elements to deal.");
        }
    }
    **/
    
    /**
     * Shuffle the given list into the calling list.
     * 
     * The new list will alternate between those of this list and
     * otherList: <first node of this list, first node of otherList, second
     * node of this list, second node of otherList, ... >. If one list runs
     * out of items early, the rest of the items will all come from the
     * other list.
     * 
     * @param  otherList  the list to be shuffled into this one
     */
    /**
    public void shuffleWith(LinkedListList otherList) {
        Node othercurr = otherList.head;
        int count = 0;
        for (Node curr = head; curr != null; curr = curr.next.next) {
            if (othercurr == null) {
                break;
            }
            Node next = curr.next;
            Node othernext = othercurr.next;
            curr.next = othercurr;
            curr.next.next = next;
            othercurr = othernext;
            count++;
            size++;
        }
        if (count < otherList.size()-1) {
            getNodeAt(size-1).next = othercurr;
        }
        otherList.head = null;
        otherList.size = 0;
    }
    **/
    /**
     * Reverses the order of the list.
     */
    /**
    public void reverse() {
        Node prev = null;
        Node next = head;
        for (Node curr = head; curr != null; curr = next) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            if (next == null) {
                head = curr;
            }
        }
    }
    **/
    // --------------------------------------------------------------------
    // Private methods
    // --------------------------------------------------------------------
    /**
     * Initializes list based on array of elements
     * 
     * @param inputArray array of elements to set list to
     */
    private void initialize(LinkedListList[] inputArray) {
        size = 1;
        head = new Node(inputArray[0], null);
        for (int i = 1; i < inputArray.length; i++) {
            add(inputArray[i]);
        }
    }

    /**
     * Gets the node at an index
     * 
     * @param index index of element
     * 
     * @return Node at the index
     */
    public Node getNodeAt(int index) {
        Node current = head;
        int iter = 0;
        while (iter != index) {
            current = current.next;
            iter++;
        }
        return current;
    }

    /**
     * Adds an element to the end of the list
     * 
     * @param element element to add
     */
    public void add(LinkedListList element) {
        if (head == null) {
            head = new Node(element, null);
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = new Node(element, null);
        }
        size++;
    }

    public void addNode(Node element) {
        if (head == null) {
            head = element;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = element;
        }
        size++;
    }
}
