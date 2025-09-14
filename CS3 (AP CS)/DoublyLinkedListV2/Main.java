import java.util.Scanner;

public class Main{

 	public static void main(String[] args) {

		// TEST ALL
		if (args.length == 0){
			int count = 0;
			Scanner s = new Scanner(System.in);
			System.out.println("-------------BEGIN-------------");
			if (testEmptyConstructor()) {
				count++;
			}
			String n = s.nextLine();
			System.out.println("--------------------------------");

			if (testArrayConstructor()){
				count++;
			}
			n = s.nextLine();
			System.out.println("--------------------------------");

			if (testGet()){
				count++;
			}
			n = s.nextLine();
			System.out.println("--------------------------------");

			if (testInsert()){
				count++;
			}
			n = s.nextLine();
			System.out.println("--------------------------------");

			if (testRemove()){
				count++;
			}
			n = s.nextLine();
			System.out.println("--------------------------------");

			if (testNextIterator()){
				count++;
			}
			n = s.nextLine();
			System.out.println("--------------------------------");

			if (testPrevIterator()){
				count++;
			}
			n = s.nextLine();
			System.out.println("--------------END---------------");	
			System.out.println("Ran into " + (7-count) + " runtime errors.");	
		}
		// INDIVIDUAL TESTING OPTIONS
		else if (args[0].toLowerCase().contains("emp")){
			testEmptyConstructor();
		}
		else if (args[0].toLowerCase().contains("arr")){
			testArrayConstructor();
		}
		else if (args[0].toLowerCase().contains("get")){
			testGet();
		}
		else if (args[0].toLowerCase().contains("in")){
			testInsert();
		}
		else if (args[0].toLowerCase().contains("rem")){
			testRemove();
		}	
		else if (args[0].toLowerCase().contains("next")){
			testNextIterator();
		}
		else if (args[0].toLowerCase().contains("prev")){
			testPrevIterator();
		}
	}

	public static boolean testEmptyConstructor(){
		try{
			System.out.println("\nEMPTY LIST CONSTRUCTOR...");
			DLinkedList<Integer> lst = new DLinkedList<Integer>();
			System.out.print("Expected: <>\n\t  ");
			System.out.println(lst);

			System.out.print("Expected: <>\n\t  ");
			System.out.println(lst.toReverseOrderString());
			System.out.println("size: " + lst.size());
		}
		catch (Exception e){
			System.out.println("Error in EMPTY LIST CONSTRUCTOR...");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean testArrayConstructor(){
		try{
			System.out.println("\nARRAY CONSTRUCTOR...");
			Integer[] nums = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
			DLinkedList<Integer> myList = new DLinkedList<Integer>(nums);
			System.out.print("Expected: <0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10>\n\t  ");
			System.out.println(myList);
			
			System.out.print("Expected: <10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0>\n\t  ");
			System.out.println(myList.toReverseOrderString());
			System.out.println("size: " + myList.size());
		}
		catch (Exception e){
			System.out.println("Error in ARRAY CONSTRUCTOR...");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// IMPLEMENT THIS
	public static boolean testGet(){
		try{
			System.out.println("\nGET...");

			Integer[] nums = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
			DLinkedList<Integer> myList = new DLinkedList<Integer>(nums);

			// Get from head
			System.out.println("\nGet 0 from index 0: ");
			
			System.out.print("Expected: 0\n\t  ");
			System.out.println(myList.get(0));

			// Get from tail
			System.out.print("Expected: 10\n\t  ");
			System.out.println(myList.get(myList.size()-1));
			
			// Get from middle
			System.out.print("Expected: 5\n\t  ");
			System.out.println(myList.get(myList.size() / 2));

			// Get all
			System.out.println("Get all list elements, in order: ");
			for (int i = 0; i < myList.size(); i++){
				System.out.print(myList.get(i) + " ");

			}
		}
		catch (Exception e){
		System.out.println("Error in GET...");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean testInsert(){
		try{

			System.out.println("\nINSERT...");

			// Insert into empty
			DLinkedList<Integer> lst = new DLinkedList<>();
			System.out.println("\nInsert 7 into empty list: ");
			lst.insert(7, 0);
			System.out.print("Expected: <7>\n\t  ");
			System.out.println(lst);

			System.out.print("Expected: <7>\n\t  ");
			System.out.println(lst.toReverseOrderString());
			System.out.println("size: " + lst.size());

			// Insert at head
			DLinkedList<Integer> lst2 = new DLinkedList<>();
			System.out.println("\nInsert 0-9 into empty list, reverse order: ");
			for (int i = 0; i < 10; i++){
				lst2.insert(i, 0);
			}
			System.out.print("Expected: <9, 8, 7, 6, 5, 4, 3, 2, 1, 0>\n\t  ");
			System.out.println(lst2);

			System.out.print("Expected: <0, 1, 2, 3, 4, 5, 6, 7, 8, 9>\n\t  ");
			System.out.println(lst2.toReverseOrderString());
			System.out.println("size: " + lst2.size());
			
			// Insert at end
			DLinkedList<Integer> lst3 = new DLinkedList<Integer>();
			System.out.println("\nInsert 0-9 into empty list: ");
			for (int i = 0; i < 10; i++){
				lst3.insert(i, i);
			}
			System.out.print("Expected: <0, 1, 2, 3, 4, 5, 6, 7, 8, 9>\n\t  ");
			System.out.println(lst3);

			System.out.print("Expected: <9, 8, 7, 6, 5, 4, 3, 2, 1, 0>\n\t  ");
			System.out.println(lst3.toReverseOrderString());
			System.out.println("size: " + lst3.size());

			// Insert in middle
			DLinkedList<Integer> lst4 = new DLinkedList<Integer>();
			System.out.println("\nInsert 0-9 into empty list, in middle: ");
			
			for (int i = 0; i < 10; i++){
				lst4.insert(i, i/2);
			}
			System.out.print("Expected: <1, 3, 5, 7, 9, 8, 6, 4, 2, 0>\n\t  ");
			System.out.println(lst4);

			System.out.print("Expected: <0, 2, 4, 6, 8, 9, 7, 5, 3, 1>\n\t  ");
			System.out.println(lst4.toReverseOrderString());
			System.out.println("size: " + lst4.size());

		}
		catch (Exception e){
			System.out.println("Error in INSERT...");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean testRemove(){
		try{
			System.out.println("\nREMOVE...");

			Integer[] nums = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
			DLinkedList<Integer> myList = new DLinkedList<Integer>(nums);

			// Remove from head
			System.out.println("\nRemove 0 from index 0: ");
			myList.remove(0);
			System.out.print("Expected: <1, 2, 3, 4, 5, 6, 7, 8, 9, 10>\n\t  ");
			System.out.println(myList);
			
			System.out.print("Expected: <10, 9, 8, 7, 6, 5, 4, 3, 2, 1>\n\t  ");
			System.out.println(myList.toReverseOrderString());
			System.out.println("size: " + myList.size());

			// Remove from end
			myList = new DLinkedList<Integer>(nums);
			System.out.println("\nRemove 10 from end: ");
			myList.remove(myList.size() - 1);
			System.out.print("Expected: <0, 1, 2, 3, 4, 5, 6, 7, 8, 9>\n\t  ");
			System.out.println(myList);
			
			System.out.print("Expected: <9, 8, 7, 6, 5, 4, 3, 2, 1, 0>\n\t  ");
			System.out.println(myList.toReverseOrderString());
			System.out.println("size: " + myList.size());

			// Remove from middle
			myList = new DLinkedList<Integer>(nums);
			System.out.println("\nRemove 5 from middle: ");
			myList.remove(myList.size() / 2);
			System.out.print("Expected: <0, 1, 2, 3, 4, 6, 7, 8, 9, 10>\n\t  ");
			System.out.println(myList);
			
			System.out.print("Expected: <10, 9, 8, 7, 6, 4, 3, 2, 1, 0>\n\t  ");
			System.out.println(myList.toReverseOrderString());
			System.out.println("size: " + myList.size());

			// Remove all
			myList = new DLinkedList<Integer>(nums);
			System.out.println("Remove all (index size-1), print after each removal: ");
			while(myList.size() > 0){
				myList.remove(myList.size() - 1);
				System.out.println(myList);
			}
		}
		catch (Exception e){
		System.out.println("Error in REMOVE...");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean testNextIterator(){
		try{
			System.out.println("\nNEXT ITERATOR...");

			// Just iteration and printing
			DLinkedList<Integer> myList = new DLinkedList<>(new Integer[]{10, 20, 30});
			System.out.println("\nPrinting all values (next)");
			// Iterator forward, adding 1 to each element.
			System.out.print("Expected: 10 20 30\n\t  ");
			var iterator = myList.iteratorFromHead();
			while (iterator.hasNext()) {
			    int x = (Integer)iterator.next();
			    System.out.print(x + " ");
			}
			System.out.println();

			// Iteration and setting
			myList = new DLinkedList<>(new Integer[]{10, 20, 30, 40, 50});

			// Iterator forward, adding 1 to each element.
			System.out.println("\nIncrementing all values (set)");
			iterator = myList.iteratorFromHead();
			while (iterator.hasNext()) {
			    int x = (Integer)iterator.next();
			    iterator.set(x + 1);
			    
			}
			System.out.print("\nExpected: <11, 21, 31, 41, 51>\n\t  ");
			System.out.println(myList);

			// Iteration and removing odd values
			myList = new DLinkedList<>(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8});
			System.out.println("\nRemoving odd values");
			iterator = myList.iteratorFromHead();
			while (iterator.hasNext()) {
			    int x = (Integer)iterator.next();
			    if (x % 2 == 1){
			    	iterator.remove();
			    }   
			}
			System.out.print("\nExpected: <2, 4, 6, 8>\n\t  ");
			System.out.println(myList);

			// Iteration and inserting negative versions of odd values
			myList = new DLinkedList<>(new Integer[]{1, 2, 3, 4, 5});
			System.out.println("\nInserting negative odd values");
			iterator = myList.iteratorFromHead();
			while (iterator.hasNext()) {
			    Integer x = (Integer)iterator.next();
			    if (x % 2 == 1){
			    	iterator.insert((Integer)(-x));
			    }   
			}
			System.out.print("\nExpected: <1, -1, 2, 3, -3, 4, 5, -5>\n\t  ");
			System.out.println(myList);

		}
		catch (Exception e){
		System.out.println("Error in NEXT ITERATOR...");
			e.printStackTrace();
			return false;
		}
		return true;		
	}

	public static boolean testPrevIterator(){	
		try{
			System.out.println("\nPREV ITERATOR...");

			// Just iteration and printing
			DLinkedList<Integer> myList = new DLinkedList<>(new Integer[]{10, 20, 30});

			System.out.println("\nPrinting all values (previous)");
			// Iterator forward, adding 1 to each element.
			System.out.print("\nExpected: 30 20 10\n\t  ");
			var iterator = myList.iteratorFromTail();
			while (iterator.hasPrevious()) {
			    int x = (Integer)iterator.previous();
			    System.out.print(x + " ");
			}
			System.out.println();

			// Iteration and setting
			myList = new DLinkedList<>(new Integer[]{10, 20, 30, 40, 50});

			// Iterator forward, adding 1 to each element.
			System.out.println("\nIncrementing all values (set)");
			iterator = myList.iteratorFromTail();
			while (iterator.hasPrevious()) {
			    int x = (Integer)iterator.previous();
			    iterator.set(x + 1);
			    
			}
			System.out.print("\nExpected: <11, 21, 31, 41, 51>\n\t  ");
			System.out.println(myList);

			// Iteration and removing odd values
			myList = new DLinkedList<>(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8});
			System.out.println("\nRemoving even values");
			iterator = myList.iteratorFromTail();
			while (iterator.hasPrevious()) {
			    int x = (Integer)iterator.previous();
			    if (x % 2 == 0){
			    	iterator.remove();
			    }   
			}
			System.out.print("\nExpected: <1, 3, 5, 7>\n\t  ");
			System.out.println(myList);

			// Iteration and inserting negative versions of odd values
			myList = new DLinkedList<>(new Integer[]{1, 2, 3, 4, 5});
			System.out.println("\nInserting negative odd values");
			iterator = myList.iteratorFromTail();
			while (iterator.hasPrevious()) {
			    Integer x = (Integer)iterator.previous();
			    if (x % 2 == 1){
			    	iterator.insert((Integer)(-x));
			    }   
			}
			System.out.print("Expected: <-1, 1, 2, -3, 3, 4, -5, 5>\n\t  ");
			System.out.println(myList);
		}
		catch (Exception e){
		System.out.println("Error in PREV ITERATOR...");
			e.printStackTrace();
			return false;
		}
		return true;
	}
}