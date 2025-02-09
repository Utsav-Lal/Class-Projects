import java.util.*;

/**
 * An IntSet is a set of integers.
 * 
 * When implementing an IntSet, any constructors provided must create a set that
 * contains no duplicate elements.
 */
public interface IntSet extends Iterable<Integer> {
    
    /**
     * Adds the specified element to this set if it is not already present.
     * 
     * If this set already contains the element, the call leaves the set unchanged and
     * returns false. In combination with the restriction on constructors, this ensures
     * that sets never contain duplicate elements.
     * 
     * @param  element  number to be added to this set, if not already present
     * 
     * @return  true if this set did not already contain the specified element
     */
    boolean add(int element);

    /**
     * Returns true if this set contains the specified element.
     * 
     * @param  element  number whose presence in this set is to be tested
     * 
     * @return  true if this set contains the specified element
     */
    boolean contains(int element);

    /**
     * Removes the specified element from this set if it is present.
     * 
     * @param  element  number to be removed from this set, if present
     * 
     * @return  true if this set contained the specified element
     */
    boolean remove(int element);

    /**
     * Returns the number of elements in this set.
     * 
     * @return  the number of elements in this set
     */
    int size();

    /**
     * Adds all elements of one set into another
     * 
     * @param otherSet set to add
     */
    default void union(IntSet otherSet) {
        for (int i : otherSet) {
            add(i);
        }
    }

    /**
     * Removes all elements from one set that are not in the other
     * 
     * @param otherSet set of elements to compare
     */
    default void intersection(IntSet otherSet) {
        int[] numArray = new int[this.size()];
        int index = 0;
        for (int i : this) {
            if (!otherSet.contains(i)) {
                numArray[index] = i;
                index++;
            }
        }
        for (int i : numArray) {
            remove(i);
        } 
    }

    /**
     * Checks if this is a subset of the other set
     * 
     * @param otherSet set of elements to check
     * 
     * @return whether all elements from one set are in the other
     */
    default boolean subset(IntSet otherSet) {
        if (this.size() > otherSet.size()) {
            return false;
        }
        for (int i : this) {
            if (!(otherSet.contains(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if two sets are equal
     * 
     * @param otherSet set of elements to compare
     * 
     * @return whether two sets have all the same elements
     */
    default boolean equals(IntSet otherSet) {
        if (this.size() == otherSet.size()) {
            return subset(otherSet);
        } else {
            return false;
        }
    }
}
