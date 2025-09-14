import java.util.*;

public class SetMethodTests{

	private IntSet set1;
	private IntSet set2;
    private static String[] setTypes = {"unordered", "ordered", "tree", "mod"};
    private int mod1 = 11;
    private int mod2 = 5;
    private int set1Index = 0;
    private int set2Index = 0;
    // Empty, singleton, sample test, 1-10, all in 1 tree in mod table, 3 consecutive mod table trees, 
    private static Integer[][] setValues = {{}, {7}, {25, 12, 36, 10, 19}, {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, {11, 22, 33, 0}, {12, 23, 13, 24, 15, 26, 37}, {43, 32, 21, 10}, {20, 15, 10, 5, 12, 13, 30, 35, 40, 25, 23}};

	public static void main(String[] args) {
		SetMethodTests smt = new SetMethodTests();
		Scanner in = new Scanner(System.in);
		String toTest = "all";
		if (args.length > 0){
			toTest = args[0];
		}

		do {
			System.out.println("Running " + setTypes[smt.set1Index].toUpperCase() + " and " + setTypes[smt.set2Index].toUpperCase() + " tests");
			if (toTest.contains("all") || toTest.contains("un"))
				smt.testUnion();
			if (toTest.contains("all") || toTest.contains("int"))
				smt.testIntersection();
			if (toTest.contains("all") || toTest.contains("sub"))
				smt.testSubset();
			if (toTest.contains("all") || toTest.contains("eq"))
				smt.testEquals();
			System.out.println("-----------------------------------");
			in.nextLine();

		} while(smt.incrementIndices()); // This changes set type pairings.	
	}

	public SetMethodTests() {
        clear();
    } 

    public void testUnion(){
    	System.out.println("UNION");
    	
    	// Values to test + anticipated results. All values at same index correspond
    	Integer[][] set1Vals = {{}, {25, 12, 36, 10, 19}, 				 {25, 12, 36, 10, 19}, {}, 					 {7, 43, 23, 45, 10}};
    	Integer[][] set2Vals = {{}, {7, 43, 23, 45, 10}, 				 {}, 				   {25, 12, 36, 10, 19}, {7, 43, 23, 45, 10}};
    	Integer[][] results =  {{}, {7, 10, 12, 19, 23, 25, 36, 43, 45}, {25, 12, 36, 10, 19}, {25, 12, 36, 10, 19}, {7, 43, 23, 45, 10}};
    	int count = 0;
    	
    	// For each test
    	for (int i = 0; i < set1Vals.length; i++){
    		// Reset the sets & runs the code to test
    		populateSets(set1Vals[i], set2Vals[i]);
			set1.union(set2);
			
    		if (checkMatchingArray(set1, results[i])){  		// Check that the result is as expected
    			if (checkMatchingArray(set2, set2Vals[i])){		// Make sure other set was not altered
					count++;
    			}
    			else{
    				System.out.println("\tUnion test should not alter the set passed as a parameter.");
    				System.out.println("\tThis set should still contain " + "\n\t" + Arrays.toString(set2Vals[i]));
    			}
    		}
    		else{
    			System.out.println("\tFailed union test for sets built from:\n\t" + Arrays.toString(set1Vals[i]) + "\n\t" + Arrays.toString(set2Vals[i]));
    		}
    	}
    	System.out.println("\tPassed " + count + "/" + set1Vals.length + " tests.\n");
    }

    public void testIntersection(){
    	System.out.println("INTERSECTION");
    	// Values to test + anticipated results. All values at same index correspond
    	// empty-empty, standard case (some overlap), all same, no overlap, param empty, caller empty
    	Integer[][] set1Vals = {{}, {25, 12, 36, 10, 19}, {3,4,5,2,1}, {3,4,6,1,7,9}, {34,6,7,12,39}, {}, 			  {1,2,3,4,5}};
    	Integer[][] set2Vals = {{}, {7, 43, 36, 45, 19},  {1,2,3,4,5}, {2,8,0}, 	  {}, 			  {34,6,7,12,39}, {2,3,6}};
    	Integer[][] results =  {{}, {19, 36}, 			  {1,2,3,4,5}, {}, 			  {}, 			  {}, 			  {2,3}};
    	int count = 0;
    	
    	// For each test
    	for (int i = 0; i < set1Vals.length; i++){
    		// Reset the sets & runs the code to test
    		populateSets(set1Vals[i], set2Vals[i]);
    		set1.intersection(set2);
    		
    		if (checkMatchingArray(set1, results[i])){  		// Check that the result is as expected
    			if (checkMatchingArray(set2, set2Vals[i])){		// Make sure other set was not altered
					count++;
    			}
    			else{
    				System.out.println("\tInteresection test should not alter the set passed as a parameter.");
    				System.out.println("\tThis set should still contain " + "\n\t" + Arrays.toString(set2Vals[i]));
    			}
    		}
    		else{
    			System.out.println("\tFailed intersection test for sets built from:\n\t" + Arrays.toString(set1Vals[i]) + "\n\t" + Arrays.toString(set2Vals[i]));
    		}
    	}
    	System.out.println("\tPassed " + count + "/" + set1Vals.length + " tests.\n");
    }

    public void testSubset(){
    	System.out.println("SUBSET");
		// Values to test + anticipated results. All values at same index correspond
		// empty-empty, fully contained, all but last, some not all, equal, param empty, caller empty
    	Integer[][] set1Vals = {{}, {1,2,3,4,5,6,7,8}, {6,7,8,1,2,3,4,5}, {2,0,4,6,5}, {5,3,6,1}, {3,4,1}, 	   {5,3,6,1}, {}};
    	Integer[][] set2Vals = {{}, {2,3,5,6}, 		   {3,4,6,2,9}, 	  {5,3,6,1},   {5,3,6,1}, {3,4,1,5,6}, {}, 		  {5,3,6,1}};
    	boolean[] results = {true, 	true, 			   false, 			  false, 		true, 	  false, 	   true, 	  false};
    	int count = 0;
    	
    	// For each test
    	for (int i = 0; i < set1Vals.length; i++){
    		// Reset the sets
    		populateSets(set1Vals[i], set2Vals[i]);
    		
    		if (set1.subset(set2) == results[i]){  	// Check that the result is as expected
    			count++;
    		}
    		else{
    			System.out.println("\tFailed subset test for sets built from:\n\t" + Arrays.toString(set1Vals[i]) + "\n\t" + Arrays.toString(set2Vals[i]));
    		}
    	}
    	System.out.println("\tPassed " + count + "/" + set1Vals.length + " tests.\n");
    }

    public void testEquals(){
    	System.out.println("EQUALS");
    	// Values to test + anticipated results. All values at same index correspond
    	Integer[][] set1Vals = {{}, {7}, {7}, {7}, {2, 1, 3}, {1, 2, 3}};
    	Integer[][] set2Vals = {{}, {}, {5}, {7}, {1, 2, 3}, {1, 2}};
    	boolean[] results = {true, false, false, true, true, false};
    	int count = 0;
    	
    	// For each test
    	for (int i = 0; i < set1Vals.length; i++){
    		// Reset the sets
    		populateSets(set1Vals[i], set2Vals[i]);
    		
    		if (set1.equals(set2) == results[i]){  	// Check that the result is as expected
    			count++;
    		}
    		else{
    			System.out.println("\tFailed equality test for sets built from:\n\t" + Arrays.toString(set1Vals[i]) + "\n\t" + Arrays.toString(set2Vals[i]));
    		}
    	}
    	System.out.println("\tPassed " + count + "/" + set1Vals.length + " tests.\n");
    }
    
	// Helps test all combos of set types.
	private boolean incrementIndices(){
		// Good to get next set2 type
		if (set2Index < SetMethodTests.setTypes.length-1){
			set2Index++;
			return true;
		}
		// Have done all set2 types, must do next set1, reset set2 to 0
		else if (set1Index < SetMethodTests.setTypes.length-1){
			set1Index++;
			set2Index = 0;
			return true;
		}
		return false;  // No more combos to test
	}

	 // Create the correct types of IntSet
    private void clear(){
    	// Reset set1
        if (setTypes[set1Index].equals("unordered")){
            set1 = new UnorderedArrayIntSet();
        }
        else if (setTypes[set1Index].equals("ordered")){
            set1 = new OrderedArrayIntSet();
        }
        else if (setTypes[set1Index].equals("tree")){
            set1 = new TreeIntSet();
        }
        else if (setTypes[set1Index].equals("mod")){
            set1 = new ModTableIntSet(mod1);
        }
        // Reset set2
        if (setTypes[set2Index].equals("unordered")){
          	set2 = new UnorderedArrayIntSet();
        }
        else if (setTypes[set2Index].equals("ordered")){
            set2 = new OrderedArrayIntSet();
        }
        else if (setTypes[set2Index].equals("tree")){
            set2 = new TreeIntSet();
        }
        else if (setTypes[set2Index].equals("mod")){
            set2 = new ModTableIntSet(mod2);
        }
    }

	/* This helper method checks set against a given array of ints.
     * 
     * This will be used in the tests below. It tests that set and the given
     * array have the same contents (which must only include ints from 0 to 99,
     * inclusive). The array expectedArray must be sorted.
     * 
     * @param  expectedArray  the contents we expect in the set, sorted
     * @return allGood  whether there were any issues.
     */
    private boolean checkMatchingArray(IntSet set, Integer[] expectedArray) {
        boolean allGood = true;
        try{

            if (expectedArray.length != set.size()){
                System.out.print("\tExpected size: " + expectedArray.length + "\n\t");
                System.out.println(set.size());
                System.out.println();
                allGood = false;
            }
            Arrays.sort(expectedArray);

            int currentArrayIndex = 0;
            
            for (int num = 0; num < 100; num++) {
                boolean isExpected = (currentArrayIndex < expectedArray.length
                                      && expectedArray[currentArrayIndex] == num);
                if (isExpected) {
                    currentArrayIndex++;
                    if (!set.contains(num)){
                        System.out.println("\tSet should contain: " + num);
                        allGood = false;
                    }
                }
                else if (set.contains(num)){
                    System.out.println("\tSet contains: " + num);
                    allGood = false;
                }
            }

        } catch (Exception e){
            System.out.println("Error...");
            e.printStackTrace();
            allGood = false;
        }
        return allGood;     
    }

	private void populateSets(Integer[] nums1, Integer[] nums2){
		clear(); 
		for (Integer num : nums1){
			set1.add(num);
		}
		for (Integer num : nums2){
			set2.add(num);
		}
	}


}