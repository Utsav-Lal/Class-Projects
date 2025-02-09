import java.util.*;

/**
 * A binary search tree of integers.
 *
 * In addition to usual methods like insert and hasValue, this class 
 * also includes some strange methods that wouldn't normally go in a
 * search tree. These are included because they are good practice
 * exercises.
 *
 * @author ()
 */
public class IntBinarySearchTree{
    // --------------------------------------------------------------------
    // Private classes
    // --------------------------------------------------------------------

    /** 
     * Data-only class to store Nodes. 
     * Only accessible within IntBinarySearchTree.
     */
    private static class Node {
        public int data;
        public Node left;
        public Node right;
        
        public Node(int data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    
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
            return (finder(data).data == data);
        }
    }

    /** 
     * Inserts data into the tree
     * 
     * @param data data to insert
     */
    public void insert(int data) {
        if (root == null) {
            root = new Node(data);
        } else {
            Node addTo = insertFinder(data);

            if (data < addTo.data) {
                addTo.left = new Node(data);
            } else {
                addTo.right = new Node(data);
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
        } else if (root.data == data) {
            if (root.left == null && root.right == null) {
                root = null;
            } else if (root.left == null) {
                root = root.right;
            } else if (root.right == null) {
                root = root.left;
            } else {
                findLowest(root.right).left = root.left;
                root = root.right;
            }
            return true;
        } else {
            Node foundNode = parentFinder(data);
            if (foundNode == null) {
                return false;
            } else {
                Node delNode;
                if (data < foundNode.data) {
                    delNode = foundNode.left;
                } else {
                    delNode = foundNode.right;
                }
                if (delNode.left == null && delNode.right == null) {
                    if (data < foundNode.data) {
                        foundNode.left = null;
                    } else {
                        foundNode.right = null;
                    }
                } else if (delNode.left == null) {
                    copyNode(delNode, delNode.right);
                } else if (delNode.right == null) {
                    copyNode(delNode, delNode.left);
                } else {
                    findLowest(delNode.right).left = delNode.left;
                    copyNode(delNode, delNode.right);
                }
                return true;
            }
        }
    }

    /**
     * Adds up the data in all nodes
     */
    public int sum() {
        if (root == null) {
            return 0;
        } else {
            return treeIterator(root, "sum");
        }

    }

    /**
     * Counts the number of nodes
     */
    public int countNodes() {
        if (root == null) {
            return 0;
        } else {
            return treeIterator(root, "nodes");
        }
    }

    /**
     * Counts the number of branches
     */
    public int countBranches() {
        if (root == null) {
            return 0;
        } else {
            return countNodes()-1;
        }
    }

    /**
     * Counts the number of leaves
     */
    public int countLeaves() {
        if (root == null) {
            return 0;
        } else {
            return treeIterator(root, "leaves");
        }
    }

    /**
     * Finds the height of the tree
     */
    public int height() {
        if (root == null) {
            return 0;
        } else {
            return treeIterator(root, "height");
        }
    }

    /**
     * Checks if the tree is full
     */
    public boolean isFull() {
        if (root == null) {
            return true;
        } else {
            return treeIterator(root, "full") == 0;

        }
    }

    /**
     * Checks if the tree is balanced
     */
    public boolean isBalanced() {
        if (root == null) {
            return true;
        } else {
            return treeIterator(root, "balanced") != -1;
        }
    }

    /**
     * Sets up this tree to be a sample binary search tree. This is just for
     * testing purposes.
     */
    public void setToSampleTree1() {
        root = new Node(52);
        root.left = new Node(29);
        root.left.left = new Node(10);
        root.left.right = new Node(37);
        root.left.left.right = new Node(17);
        root.right = new Node(75);
        root.right.left = new Node(62);
        root.right.right = new Node(92);
        root.right.left.left = new Node(58);
        root.right.left.right = new Node(68);
    }
    
    /**
     * Sets up this tree to be a sample binary search tree. This is just for
     * testing purposes.
     */
    public void setToSampleTree2() {
        root = new Node(5);
        root.left = new Node(1);
        root.left.right = new Node(4);
        root.left.right.left = new Node(3);
        root.left.right.left.left = new Node(2);
    }
    
    /**
     * Sets up this tree to be a sample binary search tree. This is just for
     * testing purposes.
     */
    public void setToSampleTree3() {
        // make your own sample tree with 12 Nodes
        // it should be almost balanced, but not quite
        // draw it out on paper first!

    }
    
    /**
     * Return the values as a string.
     * 
     * The format is as follows: "12 (4 16 (13 _))"
     * Left to right, inorder traversal:
     *  Start with the root, each pair of children is enclosed in parentheses
     *  Any empty spaces are represented with an underscore.
     * 
     * sampleTree2 from above is represented by "5 (1 (_ 4 (3 (2 _) _)) _)"
     * @return  String  
     */
    @Override
    public String toString() {
        return toStringRecursion(root);
    }

    /**
     * Prints the tree on its side, root on the left.
     */ 
    public void print(){
        printTree(root, 0);
    }


    // --------------------------------------------------------------------
    // Private methods
    // --------------------------------------------------------------------

    // Hint: All recursive solutions rely on a call to a private method!

    private String toStringRecursion(Node root) {
        
        String contents = "";

        if (root == null) {
            return contents;
        }
        contents += root.data;

        // Conditionals are only here to keep nice spacing 
        // (underscores when a child does not exist)
        if (root.left != null && root.right == null){
            contents += " (" + toStringRecursion(root.left) + " _)";
        } 
        else if (root.left == null && root.right != null){
            contents += " (_ " + toStringRecursion(root.right) + ")";
        }
        else if (root.left != null && root.right != null) {
            contents +=  " (" + toStringRecursion(root.left) + " " + toStringRecursion(root.right) +  ")";
        }
        return contents;
        
    }

    // prints tree on its side, root on the left.
    // right to left inorder traversal
    private void printTree(Node tree, int depth){
        
        if (tree == null){
            return;
        }
        printTree(tree.right, depth + 1);   // Right side printed on top

        for (int i = 0; i < depth; i++){    // Manage indentations
            System.out.print("   ");
        }   
        System.out.println(tree.data);      // Current data
        printTree(tree.left, depth + 1);    // Left side on bottom
    }

    private void copyNode(Node surface, Node template) {
        surface.data = template.data;
        surface.left = template.left;
        surface.right = template.right;
    }

    // returns the closest Node to a data value
    private Node finder(int data) {
        Node curr = root;
        while (true) {
            if (data == curr.data) {
                return curr;
            } else if (data < curr.data) {
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

    private Node findLowest(Node curr) {
        while (true) {
            if (curr.left != null) {
                curr = curr.left;
            } else {
                return curr;
            }
        }
    }
    // Finds the parent of a node
    private Node parentFinder(int data) {
        Node curr = root;
        while (true) {
            if (curr.left != null) {
                if (curr.left.data == data) {
                    return curr;
                }
            }
            if (curr.right != null) {
                if (curr.right.data == data) {
                    return curr;
                }
            }
            if (data < curr.data) {
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

    // Iterates the tree and returns different things depending on mode
    private int treeIterator(Node curr, String mode) {
        int sum = 0;
        int nodes = 0;
        int leftHeight = 0;
        int rightHeight = 0;
        int leaves = 0;
        boolean left = false;
        boolean right = false;
        int full = 0;

        if (curr.left != null) {
            left = true;
            int answer = treeIterator(curr.left, mode);
            sum += answer;
            nodes+= answer;
            leftHeight += answer;
            leaves += answer;
            full += answer;

        }
        sum += curr.data;
        nodes++;
        if (curr.right != null) {
            right = true;
            int answer = treeIterator(curr.right, mode);
            sum += answer;
            nodes += answer;
            rightHeight += answer;
            leaves += answer;
            full += answer;

        }
        if (!((curr.right != null && curr.left != null) || (curr.right == null && curr.left == null))) {
            full++;

        }
        if (!(left || right)) {
            leaves++;
        }
        
        if (mode.equals("sum")) {
            return sum;
        } else if (mode.equals("nodes")) {
            return nodes;
        } else if (mode.equals("leaves")) {
            return leaves;
        } else if (mode.equals("full")) {
            return full;
        } else if (mode.equals("height")) {
            return Math.max(leftHeight, rightHeight)+1;
        } else if (mode.equals("balanced")) {
            if (!(Math.abs(leftHeight-rightHeight) <= 1) || leftHeight == -1 || rightHeight == -1) {
                return -1;
            }
            return Math.max(leftHeight, rightHeight)+1;
        } else {
            return 0;
        }
    }
}
