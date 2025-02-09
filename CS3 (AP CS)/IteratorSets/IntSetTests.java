/**
 * A non-exhaustive way to test your set implementations.
 * 
 * Use this for final testing only.
 * Write your own tests to figure out if your individual methods work as expected
 * 
 */ 
public class IntSetTests {
    private IntSet set;
    
    
    // Run all the tests - can comment tests out for more detailed testing
    public static void main(String[] args) {

        IntSetTests tester = new IntSetTests();

            System.out.println("Testing ADD: All unique elements:");
            tester.testAdd_UniqueElements();
            System.out.println("\n-------------------DONE----------------------");

            System.out.println("Testing ADD: With duplicate elements:");
            tester.testAdd_Duplicates();
            System.out.println("\n-------------------DONE----------------------");

            System.out.println("Testing ADD: Elements in order:");
            tester.testAdd_inOrder();
            System.out.println("\n-------------------DONE----------------------");
        
            System.out.println("Testing ADD: Many elements:");
            tester.testAdd_ManyElements();
            System.out.println("\n-------------------DONE----------------------");
        
            System.out.println("Testing REMOVE: Some elements in set:");
            tester.testRemove_inSet();
            System.out.println("\n-------------------DONE----------------------");
        
            System.out.println("Testing REMOVE: Elements not in set:");
            tester.testRemove_notInSet();
            System.out.println("\n-------------------DONE----------------------");
        
            System.out.println("Testing REMOVE: Many elements:");
            tester.testRemove_ManyElements();
            System.out.println("\n-------------------DONE----------------------");
        
            System.out.println("Testing ADD/REMOVE: Alternating:");
            tester.testRemove_alternatingAddAndRemove();
            System.out.println("\n-------------------DONE----------------------");
        
            System.out.println("Testing ADD/REMOVE: With negative numbers:");
            tester.test_negatives();
            System.out.println("\n-------------------DONE----------------------");
        
    }

    /**
     * Sets up the tester.
     *
     */
    public IntSetTests() {
        clear();
    }

    // For use in resetting the set in each new test 
    // -- should be updated based on the actual type of the set you are working with.
    private void clear(){
        set = new TreeIntSet();
    }
    
    /* This helper method checks set against a given array of ints.
     * 
     * This will be used in the tests below. It tests that set and the given
     * array have the same contents (which must only include ints from 0 to 99,
     * inclusive). The array expectedArray must be sorted.
     * 
     * @param  expectedArray  the contents we expect in the set, sorted
     */
    private void checkMatchingArray(int[] expectedArray) {
        try{
            if (expectedArray.length != set.size()){
                System.out.print("Expected size: " + expectedArray.length + "\n\t");
                System.out.println(set.size());
                System.out.println();
            }

            int currentArrayIndex = 0;
            
            // expectedArray is ordered - use this to our advantage
            for (int num = 0; num < 100; num++) {
                boolean isExpected = (currentArrayIndex < expectedArray.length
                                      && expectedArray[currentArrayIndex] == num);
                if (isExpected) {
                    currentArrayIndex++;
                    if (!set.contains(num)){
                        System.out.println("Set should contain: " + num);
                    }
                }
                else if (set.contains(num)){
                    System.out.println("Set contains: " + num);
                }
            }

        } catch (Exception e){
            System.out.println("Error...");
            e.printStackTrace();
        }     
    }

    private void checkAddTrue(int val){
        if (!set.add(val)){
            System.out.println("Adding " + val + " should have been true.");
        }
    }
    private void checkAddFalse(int val){
        if (set.add(val)){
            System.out.println("Adding " + val + " should have been false.");
        }
    }
    
    //------------------------------------------------------------------------------
    // add Tests
    //------------------------------------------------------------------------------

    public void testAdd_UniqueElements() {
        clear();
        checkAddTrue(24);
        checkAddTrue(51);
        checkAddTrue(60);
        checkAddTrue(7);
        checkAddTrue(12);
        checkAddTrue(87);
        checkAddTrue(52);
        checkAddTrue(44);
        checkAddTrue(77);
        checkAddTrue(37);

        int[] expectedArray = new int[]{7, 12, 24, 37, 44, 51, 52, 60, 77, 87};
        checkMatchingArray(expectedArray);
    }

    public void testAdd_Duplicates() {
        clear();
        checkAddTrue(52);
        checkAddTrue(44);
        checkAddTrue(77);
        checkAddTrue(37);
        checkAddTrue(24);
        checkAddTrue(51);
        checkAddTrue(60);
        checkAddTrue(7);
        checkAddTrue(12);
        checkAddTrue(87);

        checkAddFalse(77);
        checkAddFalse(37);
        checkAddFalse(24);
        checkAddFalse(52);
        checkAddFalse(44);
        checkAddFalse(51);
        checkAddFalse(60);
        checkAddFalse(7);
        checkAddFalse(12);
        checkAddFalse(87);

        int[] expectedArray = new int[]{7, 12, 24, 37, 44, 51, 52, 60, 77, 87};
        checkMatchingArray(expectedArray);
    }

    public void testAdd_inOrder() {
        clear();
        // Add all integers from 0 to 9 in order.
        for (int num = 0; num < 10; num++) {
            checkAddTrue(num);
        }
        // Add all integers from 20 to 29 in reverse order.
        for (int num = 29; num >= 20; num--) {
            checkAddTrue(num);
        }
        int[] expectedArray = new int[]{ 0,  1,  2,  3,  4,  5,  6,  7,  8,  9,
                                        20, 21, 22, 23, 24, 25, 26, 27, 28, 29,
                                       };
        checkMatchingArray(expectedArray);
    }
    
    public void testAdd_ManyElements() {
        clear();
        // Add all integers from 0 to 99 except for multiples of 10, but don't add
        // them in order.
        for (int num = 0; num < 100; num++) {
            int toAdd = (num * 37) % 100;  // 37 and 100 relatively prime
            if (toAdd % 10 != 0) {
                checkAddTrue(toAdd);
            }
        }
        int[] expectedArray = new int[]{ 1,  2,  3,  4,  5,  6,  7,  8,  9,
                                        11, 12, 13, 14, 15, 16, 17, 18, 19,
                                        21, 22, 23, 24, 25, 26, 27, 28, 29,
                                        31, 32, 33, 34, 35, 36, 37, 38, 39,
                                        41, 42, 43, 44, 45, 46, 47, 48, 49,
                                        51, 52, 53, 54, 55, 56, 57, 58, 59,
                                        61, 62, 63, 64, 65, 66, 67, 68, 69,
                                        71, 72, 73, 74, 75, 76, 77, 78, 79,
                                        81, 82, 83, 84, 85, 86, 87, 88, 89,
                                        91, 92, 93, 94, 95, 96, 97, 98, 99,
                                       };
        checkMatchingArray(expectedArray);
    }

    
    //------------------------------------------------------------------------------
    // remove Tests
    //------------------------------------------------------------------------------

    private void checkRemoveTrue(int val){
        if (!set.remove(val)){
            System.out.println("Removing " + val + " should have been true.");
        }
    }
    private void checkRemoveFalse(int val){
        if (set.remove(val)){
            System.out.println("Removing " + val + " should have been false.");
        }
    }

    public void testRemove_inSet() {
        clear();
        // Add all even numbers from 2 to 20, but not in order.
        for (int num = 0; num < 10; num++) {
            set.add(((num * 9) % 10) * 2 + 2);  // 9 and 10 relatively prime
        }
        checkRemoveTrue(4);
        checkRemoveTrue(14);
        checkRemoveTrue(10);
        checkRemoveTrue(20);
        checkRemoveTrue(2);
        int[] expectedArray = new int[]{6, 8, 12, 16, 18};
        checkMatchingArray(expectedArray);
    }

    public void testRemove_notInSet() {
        clear();
        // Add all even numbers from 2 to 20, but not in order.
        for (int num = 0; num < 10; num++) {
            set.add(((num * 7) % 10) * 2 + 2);  // 7 and 10 relatively prime
        }
        checkRemoveFalse(13);
        checkRemoveFalse(7);
        checkRemoveFalse(3);
        checkRemoveFalse(1);
        checkRemoveFalse(-1);
        checkRemoveFalse(88);
        int[] expectedArray = new int[]{2, 4, 6, 8, 10, 12, 14, 16, 18, 20};
        checkMatchingArray(expectedArray);
    }

    public void testRemove_ManyElements() {
        clear();
        // Add all integers from 0 to 99, but not in order.
        for (int num = 0; num < 100; num++) {
            set.add((num * 89) % 100);  // 89 and 100 relatively prime
        }
        // Remove multiples of 10.
        for (int num = 0; num < 10; num++) {
            checkRemoveTrue(num * 10);
        }
        int[] expectedArray = new int[]{ 1,  2,  3,  4,  5,  6,  7,  8,  9,
                                        11, 12, 13, 14, 15, 16, 17, 18, 19,
                                        21, 22, 23, 24, 25, 26, 27, 28, 29,
                                        31, 32, 33, 34, 35, 36, 37, 38, 39,
                                        41, 42, 43, 44, 45, 46, 47, 48, 49,
                                        51, 52, 53, 54, 55, 56, 57, 58, 59,
                                        61, 62, 63, 64, 65, 66, 67, 68, 69,
                                        71, 72, 73, 74, 75, 76, 77, 78, 79,
                                        81, 82, 83, 84, 85, 86, 87, 88, 89,
                                        91, 92, 93, 94, 95, 96, 97, 98, 99,
                                       };
        checkMatchingArray(expectedArray);
    }

    public void testRemove_alternatingAddAndRemove() {
        clear();
        checkAddTrue(1);
        checkAddFalse(1);  // attempt to add duplicate of 1
        checkAddTrue(3);
        checkAddTrue(5);
        checkAddTrue(6);
        checkRemoveFalse(4);
        checkRemoveTrue(6);
        checkAddFalse(1);  // attempt to add duplicate of 1
        checkAddTrue(2);
        checkRemoveTrue(1);  // remove 1
        checkAddTrue(9);
        checkAddTrue(10);
        checkRemoveFalse(8);
        int[] expectedArray = new int[]{2, 3, 5, 9, 10};
        checkMatchingArray(expectedArray);
    }
    
    
    //------------------------------------------------------------------------------
    // negative Tests
    //------------------------------------------------------------------------------

    private void testContains(int val, boolean result){
        if (set.contains(val) != result){
            System.out.println("For value: " + val + ", expected: " + result);
            System.out.println();
        }
    }
    public void test_negatives() {
        clear();
        checkAddTrue(-3);
        checkAddTrue(3);
        checkAddTrue(-4);
        checkRemoveTrue(-3);
        testContains(-4, true);
        testContains(-3, false);
        testContains(3, true);
        
        if (2 != set.size()){
            System.out.print("Expected size: 2\n\t");
            System.out.println(set.size());
            System.out.println();
        }

    }
}