import java.util.*;

public class TestIterators{
	private IntSet set;
    private static String[] setTypes = {"unordered", "ordered", "tree", "mod"};
    private int mod = 11;
    private String setType = "unordered";
    // Empty, singleton, sample test, 1-10, all in 1 tree in mod table, 3 consecutive mod table trees, 
    private static Integer[][] setValues = {{}, {7}, {25, 12, 36, 10, 19}, {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, {11, 22, 33, 0}, {12, 23, 13, 24, 15, 26, 37}, {43, 32, 21, 10}, {20, 15, 10, 5, 12, 13, 30, 35, 40, 25, 23}};
    
    /**
     * Sets up the test fixtures
     */

    public TestIterators() {
        clear();
    }

    public static void main(String[] args) {
        TestIterators tester = new TestIterators();
        Scanner s = new Scanner(System.in);

        // Run all tests for each type.
        for (String setType : setTypes ) {
            
            tester.setup(setType);
            
            // Keep track of the number of tests that pass
            int count = 0;
            for (int i = 0; i < setValues.length; i++){
            	if (tester.testValues(setValues[i])){
            		count++;
            	}
            	else{  // Print useful info on fail
            		System.out.println("Failed testing these values: " + Arrays.toString(setValues[i]));
            		System.out.println();
            	}
            }
            System.out.println("Passed " + count + "/" + setValues.length + " tests.\n");
        }
    }

    // Setup for each new set type
    public void setup(String setType){
    	changeSetType(setType);
    	System.out.println("\nRunning " + setType + " Tests");

        if (setType.equals("tree") || setType.equals("ordered")){
        	System.out.println("Each set should print in increasing order");
        }
        else if (setType.equals("mod")){
        	System.out.println("Each set should print in increasing order mod " + mod);
        }
    }

    // Used so we can test all the different kinds of sets.
    public void changeSetType(String newType){
        for (String str : setTypes){
            if (str.equals(newType)){
                setType = newType;
                return;
            }
        }
        System.err.println("That is not a valid type. Running with " + setType);
    }

    // In case we want to change the length of the array created in the ModTableIntSet
    public void changeMod(int newMod){
        if (newMod > 0){
            mod = newMod;
        }
    }

    // Create the correct type of IntSet
    public void clear(){
        if (setType.equals("unordered")){
            set = new UnorderedArrayIntSet();
        }
        else if (setType.equals("ordered")){
            set = new OrderedArrayIntSet();
        }
        else if (setType.equals("tree")){
            set = new TreeIntSet();
        }
        else {
            set = new ModTableIntSet(mod);
        }
    }

    public boolean testValues(Integer[] values){
        clear();

        // Add all values to set and to the nums arraylist - used in checking correct next() return values.
        ArrayList<Integer> nums = new ArrayList<Integer>(Arrays.asList(values));
        for (Integer num : nums){
        	set.add(num);
        }
        // Hopefully this try/catch works to find any runtime exceptions...
        try{
        	printIteratorNums();
       	 	return checkContains(nums);
        }catch (Exception e){
        	System.out.println("Error on iterating: " + e);
        }
        return false;
    }

    // Show what is iterated.
    // Useful for visual inspection.
    private void printIteratorNums(){
        Iterator<Integer> iter = set.iterator();
        System.out.print("\t");
        while (iter.hasNext()) {
            Integer item = iter.next();
            System.out.print(item );
            if (iter.hasNext()){
            	System.out.print(", ");
            }
        }
        System.out.println("");
    }

    // Test value correctness of iteration.
    // DOES NOT test for correct output order.
    private boolean checkContains(ArrayList<Integer> nums){

	 	Iterator<Integer> iter = set.iterator();
        while (iter.hasNext()) {
            Integer item = iter.next();
            if (nums.size() == 0){ 
            	System.out.println("Found MORE items than anticipated");
            	return false;
            }
            // Standard case
            if (nums.contains(item)){
            	nums.remove(item);
            }
            // Nums should contain each number we iterate over.
            else{
            	System.out.println("Found unexpected item: " + item);
            	return false;
            }
        }
        if (nums.size() > 0){
        	System.out.println("Found FEWER items than anticipated.");
        	return false;
        }
        return true;
    }
}