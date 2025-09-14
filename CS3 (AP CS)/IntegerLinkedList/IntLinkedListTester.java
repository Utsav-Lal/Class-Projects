import java.util.*;

/**
 * Tests IntLinkedList class
 */
public class IntLinkedListTester {
// 	public static void main(String[] args) {
// 		System.out.println("Testing new list creation ...");
// 		IntLinkedList emptyList = new IntLinkedList();
// 		emptyList.display();
// 		System.out.println();

// 		System.out.println("\nTesting element insertion ...");
// 		emptyList.insert(1, 0);
// 		emptyList.insert(5, 1);
// 		emptyList.insert(3, 1);
// 		emptyList.insert(1, 2);
// 		emptyList.display();
// 		System.out.println();

// 		System.out.println("\nTesting element removal ...");
// 		emptyList.remove(1);
// 		emptyList.remove(0);
// 		emptyList.display();
// 		System.out.println();

// 		System.out.println("\nTesting preset list creation ...");
// 		IntLinkedList preSetList = new IntLinkedList(new int[]{1, 2, 3, 4, 5});
// 		preSetList.insert(60, 2);
// 		preSetList.display();
// 		System.out.println();

// 		System.out.println("\nTesting element retrieval ...");
// 		System.out.println(preSetList.get(4));

// 		System.out.println("\nTesting cumulativeSum() ...");
// 		preSetList.cumulativeSum().display();
// 		System.out.println();

// 		System.out.println("\nTesting dealing ...");
// 		for (IntLinkedList i : preSetList.deal(2, 2)) {
// 			i.display();
// 			System.out.println();
// 		}

// 		System.out.println("\nTesting combining ...");
// 		System.out.println(("this.size < other.size"));
// 		emptyList.shuffleWith(preSetList);
// 		emptyList.display();
// 		System.out.println();
// 		System.out.println(("this.size > other.size"));
// 		emptyList.shuffleWith(new IntLinkedList(new int[]{1, 2, 3}));
// 		emptyList.display();
// 		System.out.println();

// 		System.out.println("\nTesting reversal ...");
// 		emptyList.reverse();
// 		emptyList.display();
// 		System.out.println();

// 		System.out.println("\nTesting input ...");
// 		emptyList.inputList();
// 		emptyList.display();
// 		System.out.println();

// 	}
// }

	public static void main(String[] args) {
		
		// TEST ALL
		if (args.length == 0){
			Scanner s = new Scanner(System.in);
			System.out.println("-------------BEGIN-------------");
			testEmptyConstructor();
			String n = s.nextLine();
			System.out.println("--------------------------------");

			testArrayConstructor();
			n = s.nextLine();
			System.out.println("--------------------------------");

			testInsert();
			n = s.nextLine();
			System.out.println("--------------------------------");

			testRemove();
			n = s.nextLine();
			System.out.println("--------------------------------");

			testCumulativeSum();
			n = s.nextLine();
			System.out.println("--------------------------------");

			testDeal();
			n = s.nextLine();
			System.out.println("--------------------------------");

			testShuffle();
			n = s.nextLine();
			System.out.println("--------------------------------");

			testReverse();
			System.out.println("--------------END---------------");		
		}
		// INDIVIDUAL TESTING OPTIONS
		else if (args[0].toLowerCase().contains("emp")){
			testEmptyConstructor();
		}
		else if (args[0].toLowerCase().contains("arr")){
			testArrayConstructor();
		}
		else if (args[0].toLowerCase().contains("in")){
			testInsert();
		}
		else if (args[0].toLowerCase().contains("rem")){
			testRemove();
		}	
		else if (args[0].toLowerCase().contains("sum")){
			testCumulativeSum();
		}
		else if (args[0].toLowerCase().contains("deal")){
			testDeal();
		}
		else if (args[0].toLowerCase().contains("shuf")){
			testShuffle();
		}
		else if (args[0].toLowerCase().contains("rev")){
			testReverse();
		}
	}

	public static void testEmptyConstructor(){
		System.out.println("\nEMPTY LIST CONSTRUCTOR...");
		IntLinkedList lst = new IntLinkedList();
		lst.display();
		System.out.println("size: " + lst.size());
	}

	public static void testArrayConstructor(){
		System.out.println("\nARRAY CONSTRUCTOR...");
		int[] nums = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		IntLinkedList myList = new IntLinkedList(nums);
		myList.display();
		System.out.println("size: " + myList.size());
	}

	public static void testInsert(){
		System.out.println("\nINSERT...");

		// Insert into empty
		IntLinkedList lst = new IntLinkedList();
		System.out.println("\nInsert 7 into empty list: ");
		lst.insert(7, 0);
		lst.display();
		System.out.println("size: " + lst.size());

		// Insert at head
		IntLinkedList lst2 = new IntLinkedList();
		System.out.println("\nInsert 0-9 into empty list, reverse order: ");
		for (int i = 0; i < 10; i++){
			lst2.insert(i, 0);
		}
		System.out.print("Expected: <9, 8, 7, 6, 5, 4, 3, 2, 1, 0>\n\t  ");
		lst2.display();
		System.out.println("size: " + lst2.size());
		
		// Insert at end
		IntLinkedList lst3 = new IntLinkedList();
		System.out.println("\nInsert 0-9 into empty list: ");
		for (int i = 0; i < 10; i++){
			lst3.insert(i, i);
		}
		System.out.print("Expected: <0, 1, 2, 3, 4, 5, 6, 7, 8, 9>\n\t  ");
		lst3.display();
		System.out.println("size: " + lst3.size());

		// Insert in middle
		IntLinkedList lst4 = new IntLinkedList();
		System.out.println("\nInsert 0-9 into empty list, in middle: ");
		System.out.print("Expected: <1, 3, 5, 7, 9, 8, 6, 4, 2, 0>\n\t  ");
		for (int i = 0; i < 10; i++){
			lst4.insert(i, i/2);
		}
		lst4.display();
		System.out.println("size: " + lst4.size());
	}

	public static void testRemove(){
		System.out.println("\nREMOVE...");

		int[] nums = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		IntLinkedList myList = new IntLinkedList(nums);

		// Remove from head
		myList.remove(0);
		System.out.print("Expected: <1, 2, 3, 4, 5, 6, 7, 8, 9, 10>\n\t  ");
		myList.display();
		System.out.println("size: " + myList.size());

		// Remove from end
		myList = new IntLinkedList(nums);
		myList.remove(myList.size() - 1);
		System.out.print("Expected: <0, 1, 2, 3, 4, 5, 6, 7, 8, 9>\n\t  ");
		myList.display();
		System.out.println("size: " + myList.size());

		// Remove from middle
		myList = new IntLinkedList(nums);
		myList.remove(myList.size() / 2);
		System.out.print("Expected: <0, 1, 2, 3, 4, 6, 7, 8, 9, 10>\n\t  ");
		myList.display();
		System.out.println("size: " + myList.size());

		// Remove all
		System.out.println("Remove all, print after each removal: ");
		while(myList.size() > 0){
			myList.remove(0);
			myList.display();
		}
	}

	public static void testCumulativeSum(){
		System.out.println("\nCUMULATIVE SUM... ");
		
		// System.out.println("\nEmpty");
		// (new IntLinkedList(new int[]{})).cumulativeSum().display();
		System.out.println("\nSingleton");
		(new IntLinkedList(new int[]{1})).cumulativeSum().display();
		System.out.println("\nTwo ones");
		(new IntLinkedList(new int[]{1,1})).cumulativeSum().display();
		System.out.print("\n1-5, \nExpected: <1, 3, 6, 10, 15>\n\t  ");
		(new IntLinkedList(new int[]{1,2,3,4,5})).cumulativeSum().display();		
	}

	public static void testDeal(){
		System.out.println("\nDEAL...");

		// Partial list
		System.out.println("\nDeal 2 hands of 4");
		IntLinkedList deck = new IntLinkedList(new int[]{1,2,3,4,5,6,7,8,9,10,11,12});
		IntLinkedList[] hands = deck.deal(2, 4);
		for (IntLinkedList hand : hands){
			hand.display();
		}
		System.out.println("Leftover deck: ");
		deck.display();

		// Full list
		System.out.println("\nDeal 3 hands of 4");
		IntLinkedList deck2 = new IntLinkedList(new int[]{1,2,3,4,5,6,7,8,9,10,11,12});
		IntLinkedList[] hands2 = deck2.deal(3, 4);
		for (IntLinkedList hand : hands2){
			hand.display();
		}
		System.out.println("Leftover deck:");
		deck2.display();
	}

	public static void testShuffle(){	
		System.out.println("SHUFFLE... ");

		// shuffle two lists of same length
		System.out.println("\nEven length lists");
		System.out.print("Expected: <1, 2, 3, 4, 5, 6, 7, 8>\n\t  ");
		int[] odds = {1, 3, 5, 7};
		int[] evens = {2, 4, 6, 8};
		IntLinkedList oddList = new IntLinkedList(odds);
		IntLinkedList evenList = new IntLinkedList(evens);
		oddList.shuffleWith(evenList);
		oddList.display();
		evenList.display();

		// starting list is longer
		System.out.println("\nLonger host list... ");
		System.out.print("Expected: <1, 2, 3, 4, 5, 6, 7, 9, 11>\n\t  ");
		int[] longOdds = {1, 3, 5, 7, 9, 11};
		int[] shortEvens = {2, 4, 6};
		IntLinkedList longOddList = new IntLinkedList(longOdds);
		IntLinkedList shortEvenList = new IntLinkedList(shortEvens);
		longOddList.shuffleWith(shortEvenList);
		longOddList.display();
		shortEvenList.display();

		// other list is longer
		System.out.println("\nLonger inserted list... ");
		System.out.print("Expected: <1, 2, 3, 4, 6, 8, 10, 12>\n\t  ");
		int[] shortOdds = {1, 3};
		int[] longEvens = {2, 4, 6, 8, 10, 12};
		IntLinkedList shortOddList = new IntLinkedList(shortOdds);
		IntLinkedList longEvenList = new IntLinkedList(longEvens);
		shortOddList.shuffleWith(longEvenList);
		shortOddList.display();
		longEvenList.display();
	}

	public static void testReverse(){
		System.out.println("REVERSE... ");

		// reverse empty list
		System.out.println("\nEmpty list");
		System.out.print("Expected: <>\n\t  ");
		IntLinkedList emptyList = new IntLinkedList();
		emptyList.reverse();
		emptyList.display();

		// reverse list with 1 element
		System.out.println("\nSingleton list");
		System.out.print("Expected: <1>\n\t  ");
		IntLinkedList singleton = new IntLinkedList(new int[]{1});
		singleton.reverse();
		singleton.display();

		// reverse list with 2 elements
		System.out.println("\nPair");
		System.out.print("Expected: <2, 1>\n\t  ");
		IntLinkedList pair = new IntLinkedList(new int[]{1, 2});
		pair.reverse();
		pair.display();

		// reverse long-ish list
		System.out.println("\n0 - 10");
		System.out.print("Expected: <10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0>\n\t  ");
		int[] nums = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		IntLinkedList myList = new IntLinkedList(nums);
		myList.reverse();
		myList.display();	
	}	
}
