import java.util.*;

/*
 * In this implementation of IntSet, all items are stored in an ordered array.
 * 
 * The effiency of each method is as follows:
 *      add is O(n)  (shift elements)
 *      contains is O(log n) (binary search)
 *      remove is O(n)  (shift elements)
 *      size is O(1)
 */
public class OrderedArrayIntSet implements IntSet {
    private int[] data;
    private int size;
    
    /**
     * Construct an empty set.
     */
    public OrderedArrayIntSet() {
        size = 0;
        data = new int[10];
    }

    /**
     * Adds an element to the set, ensuring no duplicate elements are added.
     * 
     * @param element - the item to add to the set
     * @return  - true if element was added, false if it already existed.
     */ 
    public boolean add(int element) {
        if (size == 0) {
            data[0] = element;
            size++;
            return true;
        }
        if (contains(element)) {  // Don't add if it already exists
            return false;
        }

        // Double size of underlying array if needed.
        if (size == data.length) {
            int[] copy = data;
            data = new int[size * 2];

            // Copy data back into original array
            for (int i = 0; i < size; i++) {
                data[i] = copy[i];
            }
        }

        // Move elements back until right place for new element found
        for (int i = size-1; i >= 0; i--) {
            if (data[i] > element) {
                data[i+1] = data[i];
            } else {
                data[i+1] = element; // Insert new element
                break;
            }
            if (i == 0) {
                data[0] = element;
            }
        }
        size++;
        return true;
    }

    /**
     * Returns true if the element exists, false if the it does not exist in the set
     * 
     * @param element - item to look for
     * @return  - true if the element exists or false if it does not exist
     */ 
    public boolean contains(int element) {
        if (size == 0) {
            return false;
        }
        return (findIndex(0, size-1, element) != -1);
    }

    /**
     * Removes element from set if it exists in the set already
     * 
     * @param element - Item to remove from the set
     * @return  - true if element was removed, false if it did not exist in the set
     */ 
    public boolean remove(int element) {
        // See if the element is in the set.
        if (size == 0) {
            return false;
        }
        int indexOfElement = findIndex(0, size-1, element);

        // Return false if element not in the set.
        if (indexOfElement == -1) {
            return false;
        }
        
        // Otherwise shift all elements after it back and return true
        for (int i = indexOfElement; i < size-1; i++) {
            data[i] = data[i+1];
        }
        size--;
        return true;
    }

    /**
     * @return  - the number of elements currently in the set
     * 
     * This is NOT the same as the length of the underlying array structure
     */ 
    public int size() {
        return size;
    }
    
    // Return index of the element, or -1 if not found.
    private int findIndex(int begin, int end, int element) {
        // Finds middle element
        int middle = ((end + begin)+(end + begin)%2)/2;
        int midValue = data[middle];
        if (midValue == element) {
            return middle; // Found it!
        } else {
            if (begin == end) {
                return -1;  // Not found
            }
            if (midValue > element) {
                if (begin == middle) {
                    return -1; // Not found
                }
                return findIndex(begin, middle-1, element);
            } else {
                if (middle == end) {
                    return -1; // Not found
                }
                return findIndex(middle+1, end, element);
            }
        }
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

        private int index = 0;

        @Override
        public boolean hasNext() {
            return 0 <= index && index < size;
        }
            
        @Override
        public Integer next() {  
            index++;
            return data[index - 1];
        }
    }

    /**
     * More efficient subset method for two OrderedArrayIntSets or sets that are both in order by iterating through both at the same time
     * Also more efficient method for UnOrderedArray by iterating through it and checking whether all elements are accounted for
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

        if (otherSet instanceof OrderedArrayIntSet) { // when both are OrderedArrayIntSets, can directly access data instead of iterating to position
            // checks whether the set is empty
            if (size == 0) {
                return true;
            }

            // finds where the first element of this set is in the other set
            OrderedArrayIntSet other = (OrderedArrayIntSet)otherSet;
            int index = other.findIndex(0, other.size-1, data[0]);
            if (index == -1) {
                return false;
            }

            // iterates from that index until a discrepancy is found or this set is exhausted
            int ourIndex = 0;
            if (data.length+index <= other.data.length) {
                while (ourIndex < data.length) {
                    if (data[ourIndex] != other.data[index]) {
                        return false;
                    }
                    index++;
                    ourIndex++;
                }
                return true;
            } else {
                return false;
            }
        } else if (otherSet instanceof TreeIntSet) {
            // checks whether either set is empty
            if (this.size() == 0) {
                return true;
            }

            // Finds the position where the first element of this set is in the other set,
            // iterates to it, and then iterates through both until a discrepancy is found or this set is exhausted
            if (otherSet.contains(data[0])) {
                Iterator j = otherSet.iterator();
                while ((int) j.next() != data[0]) {
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
        } else if (otherSet instanceof UnorderedArrayIntSet) { // turns an UnorderedArrayIntSet into a TreeIntSet for n^2 -> nlogn
            TreeIntSet tempIntSet = new TreeIntSet();
            for (int i : otherSet) {
                tempIntSet.add(i);
            }
            return IntSet.super.subset(tempIntSet);
        } else {
            return IntSet.super.subset(otherSet);
        }
    }
}
