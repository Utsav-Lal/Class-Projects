import java.util.*;

/**
 * Tests DLinkedList class with iterator
 */
public class DLinkedListTester {
	public static void main(String[] args) {
		DLinkedList<Integer> myList = new DLinkedList(new Integer[]{10, 20, 30});
		System.out.println("Integer list: ");
		System.out.println(myList);
		System.out.println();

		// Iterator forward, adding 1 to each element.
		var iterator = myList.iteratorFromHead();
		System.out.println("Add 1 to each element via next() and set(): ");
		while (iterator.hasNext()) {
			int x = iterator.next();
			iterator.set(x + 1);
		}
		System.out.println(myList);
		System.out.println();

		// The iterator is now at the end. We will go backward and print as
		// we go. (If we wanted to go forward again instead, we would just
		// call myList.iteratorFromHead() to get the iterator to the head.)
		System.out.println("Print in reverse order: ");
		while (iterator.hasPrevious()) {
			int x = iterator.previous();
			System.out.print(x + ", ");
		}
		System.out.println();
		System.out.println();

		System.out.println("Insert element: ");
		iterator.insert(0);
		System.out.println(myList);
		System.out.println();

		System.out.println("Remove element: ");
		iterator.next();
		iterator.remove();
		System.out.println(myList);
		System.out.println();
	}
}