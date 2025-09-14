import java.util.*;

/*
 * In this implementation of IntSet, all items are stored in a Binary Search Tree.
 * 
 * The effiency of each method is as follows:
 *      add is O(log n)  (find and append element using binary search)
 *      contains is O(log n) (binary search)
 *      remove is O(log n)  (find element and delete it using binary search, may have to repeat if two branches)
 *      size is O(1)
 */
public class TreeIntSet implements IntSet {
    // --------------------------------------------------------------------
    // Private classes
    // --------------------------------------------------------------------
    
    /** 
     * Data-    only class to store Nodes. 
     * Only accessible within IntBinarySearchTree.
     */
    private static class Node {
        public int data;
        public Node left;
        public Node right;
        public Node parent;
        
        public Node(int data, Node parent) {
            this.data = data;
            this.left = null;
            this.right = null;
            this.parent = parent;
        }
    }

    /**
     * A binary search tree of integers.
     *
     * @author ()
     */
    private class IntBinarySearchTree{
        
        // --------------------------------------------------------------------
        // Private state variables
        // --------------------------------------------------------------------
        private Node root;

        // --------------------------------------------------------------------
        // Public methods
        // --------------------------------------------------------------------
        /**
         * Construct an empty search tree.
         */
        public IntBinarySearchTree() {
            root = null;
        }

        /**
         * Construct a search tree from a given array of values.
         * 
         * @param  inputArray  array of values to be used to construct the tree
         */
        public IntBinarySearchTree(int[] inputArray) {
            root = null;
            for (int i : inputArray) {
                insert(i);
            }
        }

        /**
         * Checks whether a node is in the tree
         * 
         * @param data value to check
         */
        public boolean find(int data) {
            if (root == null) {
                return false;
            } else {
                return (finder(data) != null);
            }
        }

        /** 
         * Inserts data into the tree
         * 
         * @param data data to insert
         */
        public void insert(int data) {
            if (root == null) {
                root = new Node(data, null);
            } else {
                Node addTo = insertFinder(data);

                if (data < addTo.data) {
                    addTo.left = new Node(data, addTo);
                } else {
                    addTo.right = new Node(data, addTo);
                }
                
            }
        }

        /**
         * Deletes a node
         * 
         * @param data data to delete
         */
        public boolean delete(int data) {
            if (root == null) {
                return false;
            } else if (root.data == data) { // if the data is at the root
                if (root.left == null && root.right == null) { // no children
                    root = null;
                } else if (root.left == null) { // right child only
                    root = root.right;
                    root.parent = null;
                } else if (root.right == null) { // left child only
                    root = root.left;
                    root.parent = null;
                } else { // both children
                    Node lowest = findLowest(root.right);
                    lowest.left = root.left;
                    root.left.parent = lowest;
                    root = root.right;
                    root.parent = null;
                }
                return true;
            } else { // if data is not in root
                Node foundNode = finder(data);
                if (foundNode == null) {
                    return false;
                } else {
                    boolean left;
                    Node parent = foundNode.parent;
                    if (data < parent.data) { // which side of the parent its on
                        left = true;
                    } else {
                        left = false;
                    }
                    if (foundNode.left == null && foundNode.right == null) { // no children
                        if (left) {
                            parent.left = null;
                        } else {
                            parent.right = null;
                        }
                    } else if (foundNode.left == null) { // right child only
                        if (left) {
                            parent.left = foundNode.right;
                        } else {
                            parent.right = foundNode.right;
                        }
                        foundNode.right.parent = parent;
                    } else if (foundNode.right == null) { // left child only
                        if (left) {
                            parent.left = foundNode.left;
                        } else {
                            parent.right = foundNode.left;
                        }
                        foundNode.left.parent = parent;
                    } else { // two children
                        Node lowest = findLowest(foundNode.right);
                        lowest.left = foundNode.left;
                        foundNode.left.parent = lowest;
                        if (left) {
                            parent.left = foundNode.right;
                        } else {
                            parent.right = foundNode.right;
                        }
                        foundNode.right.parent = parent;
                    }
                    return true;
                }
            }
        }

        // finds a node, returns false if not found
        private Node finder(int data) {
            Node curr = root;
            while (true) {
                if (data == curr.data) {
                    return curr;
                } else if (data < curr.data) {
                    if (curr.left == null) {
                        return null;
                    } else {
                        curr = curr.left;
                    }
                }
                else {
                    if (curr.right == null) {
                        return null;
                    } else {
                        curr = curr.right;
                    }
                }
            }
        }

        // finds a place to insert a value
        private Node insertFinder(int data) {
            Node curr = root;
            while (true) {
                if (data < curr.data) {
                    if (curr.left == null) {
                        return curr;
                    } else {
                        curr = curr.left;
                    }
                }
                else {
                    if (curr.right == null) {
                        return curr;
                    } else {
                        curr = curr.right;
                    }
                }
            }
        }

        // finds the lowest node connected to a given node
        private Node findLowest(Node curr) {
            if (curr == null) {
                return null;
            }
            while (true) {
                if (curr.left != null) {
                    curr = curr.left;
                } else {
                    return curr;
                }
            }
        }
    }

    private int size;
    private IntBinarySearchTree tree;

    /**
     * Construct an empty set.
     */
    public TreeIntSet() {
        size = 0;
        tree = new IntBinarySearchTree();
    }

    /**
     * Adds an element to the tree, ensuring no duplicate elements are added.
     * 
     * @param element - the item to add to the set
     * @return  - true if element was added, false if it already existed.
     */ 
    public boolean add(int element) {
        if (!(tree.find(element))) {
            tree.insert(element);
            size++;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns true if the element exists, false if the it does not exist in the set
     * 
     * @param element - item to look for
     * @return  - true if the element exists or false if it does not exist
     */ 
    public boolean contains(int element) {
        return tree.find(element);
    }

    /**
     * Removes element from set if it exists in the set already
     * 
     * @param element - Item to remove from the set
     * @return  - true if element was removed, false if it did not exist in the set
     */ 
    public boolean remove(int element) {
        if (tree.delete(element)) {
            size--;
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return  - the number of elements currently in the set
     */ 
    public int size() {
        return size;
    }

    /**
    * Returns an iterator over the elements in this set.
    * The elements are returned in increasing order.
    *
    * @return  an Iterator over the elements in this set
    */
    @Override
    public Iterator<Integer> iterator() {
        return new MyIterator();
    }
    
    private class MyIterator implements Iterator<Integer> {

        private Node currNode = tree.findLowest(tree.root);
        private int index = 0;

        @Override
        public boolean hasNext() {
            if (currNode == null) {
                currNode = tree.findLowest(tree.root);
            }
            return 0 <= index && index < size;
        }
            
        @Override
        public Integer next() {  
            if (index == size-1) { // when at the end of the tree, move the node to null
                index++;
                Node trueCurr = currNode;
                currNode = null;
                return trueCurr.data;
            } else if (currNode.right != null) { // when leaving a node with a right, move right then move left till end
                Node trueCurr = currNode;
                currNode = currNode.right;
                while (currNode.left != null) {
                    currNode = currNode.left;
                }
                index++;
                return trueCurr.data;
            } else { // otherwise, go back until a node with a right node is found (that has not been crossed)
                Node trueCurr = currNode;
                while (currNode.parent.right == currNode) {
                    currNode = currNode.parent;
                }
                currNode = currNode.parent;
                index++;
                return trueCurr.data;
            }
        }
    }

    /**
     * More efficient subset method for two sets that are both in order by iterating through both at the same time
     * Checks if this is a subset of the other set
     * 
     * @param otherSet set of elements to check
     * 
     * @return whether all elements from one set are in the other
     */
    @Override
    public boolean subset(IntSet otherSet) {
        // Makes sure the other set is not smaller
        if (size > otherSet.size()) {
            return false;
        }

        if (otherSet instanceof TreeIntSet || otherSet instanceof OrderedArrayIntSet) {
            // checks whether this set is empty
            if (size == 0) {
                return true;
            }

            // finds the leftmost element to start with
            int findLowest = tree.findLowest(tree.root).data;

            // Finds the position where the first element of this set is in the other set,
            // iterates to it, and then iterates through both until a discrepancy is found or this set is exhausted
            if (otherSet.contains(findLowest)) {
                Iterator j = otherSet.iterator();
                while ((int) j.next() != findLowest) {
                }
               
                Iterator i = iterator();
                i.next();
                while (i.hasNext() && j.hasNext()) {
                    if (i.next() != j.next()) {
                        return false;
                    }
                }
                return true;
            } else {
                return false;
            }
        } else {
            return IntSet.super.subset(otherSet);
        }
    }
}