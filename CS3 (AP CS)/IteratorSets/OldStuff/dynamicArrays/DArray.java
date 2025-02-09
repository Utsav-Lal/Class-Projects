import java.util.*;  

/**
 * Stores a dynamic array
 */
public class DArray {
    private int size;
    private int capacity;
    private Double[] dArray;

    // Constructors

    /**
     * Constructs a dynamic array and initializes the size, capacity, and the array
     */
     public DArray() {
        size = 0;
        capacity = 4;
        dArray = new Double[capacity];
     }

     // Getters

     public int size() {
        return size;
     }

     public double get(int index) {
        checkBounds(index);
        return dArray[index];
     }

     // Editor Methods

     /**
      * Sets an index to a new element
      * 
      * @param index index of change
      * @param element element to replace with
      */
     public void set(int index, double element) {
        checkBounds(index);
        dArray[index] = element;
     }

     /**
      * Adds a new element at the specified index, moving all following elements forward
      * 
      * @param index index to add element at
      * @param element element to add
      */
     public void add(int index, double element) {
        if (index > size) {
            System.exit(1);
        }
        expandSize();
        Double[] newDArray = copyArray();
        for (int i = index; i < size; i++) {
            newDArray[i+1] = dArray[i];
        }
        newDArray[index] = element;
        dArray = newDArray;
     }

     /**
      * Removes element at index, moves all following elements back
      * 
      * @param index index of element to remove
      */
     public void remove(int index) {
         checkBounds(index);
         shrinkSize();
         Double[] newDArray = copyArray();
         for (int i = index; i < size; i++) {
            newDArray[i] = dArray[i+1];
         }
         dArray = newDArray;
     }

     // Helper Methods

     /** 
      * Makes sure that index is within the bounds of the list, stops the program if not
      * 
      * @param index index to check
      */
     private void checkBounds(int index) {
        if (index > size-1) {
            System.exit(1);
        }
     }

     /** 
      * Expands the capacity of the array by a factor of 2 if the size exceeds it
      */
     private void expandSize() {
        size += 1;
        if (size+1 > capacity) {
            capacity *= 2;
            dArray = copyArray();
        }
     }

     /**
      * Shrinks the array by a factor of 2, not letting it go below 4, if the size is less than a third of the capacity
      */
     private void shrinkSize() {
         size -= 1;
         if (size * 3 < capacity) {
            capacity /= 2;
            if (capacity % 1 != 0) {
               capacity += 1;
            }
            if (capacity < 4) {
               capacity = 4;
            }
         }
     }

     /**
      * Creates a copy of the array
      * 
      * @return a copy of the array
      */
     private Double[] copyArray() {
        Double[] newDArray = new Double[capacity];
        int iterSize = dArray.length;
        if (iterSize > newDArray.length) {
            iterSize = newDArray.length;
        }

        for (int i = 0; i < iterSize; i++) {
            newDArray[i] = dArray[i];
        }

        return newDArray;
     }


}