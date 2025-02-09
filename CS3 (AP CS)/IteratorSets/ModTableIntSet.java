import java.util.*;

/*
 * In this implementation of IntSet, all items are stored in a Mod Table Int Set.
 * 
 * The effiency of each method is as follows:
 *      add is O(1)  (hash and insert)
 *      contains is O(1) (check index for element)
 *      remove is O(1)  (find element at index and delete it)
 *      size is O(1)
 */
public class ModTableIntSet implements IntSet {
    private int size;
    private IntSet[] data;
    private int modulus;

    /**
     * Construct an empty set with a user-specified key
     */
    public ModTableIntSet(int modulus) {
        size = 0;
        this.modulus = modulus;
        data = new IntSet[this.modulus];
        for (int i = 0; i < this.modulus; i++) {
            data[i] = new TreeIntSet();
        }
    }

    /**
     * Tries adding an element to the set at the key
     * 
     * @param element - the item to add to the set
     * @return  - true if element was added, false if it already existed.
     */ 
    public boolean add(int element) {
        if (data[Math.floorMod(element, modulus)].add(element)) {
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
        return data[Math.floorMod(element, modulus)].contains(element);
    }

    /**
     * Tries removing an element
     * 
     * @param element - Item to remove from the set
     * @return  - true if element was removed, false if it did not exist in the set
     */ 
    public boolean remove(int element) {
        if (data[Math.floorMod(element, modulus)].remove(element)) {
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
    * The elements are returned in no particular order.
    *
    * @return  an Iterator over the elements in this set
    */
    @Override
    public Iterator<Integer> iterator() {
        return new MyIterator();
    }
    
    private class MyIterator implements Iterator<Integer> {

        private int index = 0;
        private int count = 0;
        private Iterator<Integer> internal = null;

        @Override
        public boolean hasNext() {
            return 0 <= index && index < size;
        }
            
        @Override
        public Integer next() {  
            while (internal == null || !internal.hasNext()) { // move to next index
                internal = data[count].iterator();
                count++;
            }
            index++;
            return internal.next();
        }
    }
}