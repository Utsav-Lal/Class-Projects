import java.util.*;

/**
 * Tests DLinkedList class
 */
public class DLinkedListTester {
	public static void main(String[] args) {
		System.out.println("Testing preset list creation with elments '1', 'g', 'gsf, 'sasdas' ...");
		DLinkedList<String> testList = new DLinkedList<String>(new String[]{"1", "g", "gsf", "sasdas"});

		System.out.println("\nTesting list display ...");
		System.out.println(testList);

		System.out.println("\nTesting reversed list display ...");
		System.out.println(testList.toReverseOrderString());

		System.out.println("\nTesting element retrieval of element at index 1 ...");
		System.out.println(testList.get(1));

		System.out.println("\nTesting element insertion of 'Hello' at index 1 ...");
		testList.insert("Hello", 1);
		System.out.println(testList);

		System.out.println("\nTesting element removal at index 3 ...");
		System.out.println(testList.remove(3));
		System.out.println(testList);

		System.out.println("\nTesting size retireval ...");
		System.out.println(testList.size());

		System.out.println("\nTesting empty list creation and insertion of the preset list ...");
		DLinkedList<DLinkedList<String>> emptyList = new DLinkedList<DLinkedList<String>>();
		emptyList.insert(testList, 0);
		System.out.println(emptyList);
	}
}