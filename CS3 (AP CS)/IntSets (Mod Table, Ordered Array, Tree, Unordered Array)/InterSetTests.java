import java.util.*;

public class InterSetTests {
	public static void main(String[] args) {
		IntSet set1 = new UnorderedArrayIntSet();
		IntSet set2 = new OrderedArrayIntSet();
		for (int i = 0; i < 10; i++) {
			set1.add(i);
		}
		for (int i = 10; i < 20; i++) {
			set2.add(i);
		}
		System.out.println("Creating Sets ... ");
		System.out.println("Set 1");
		for (int i : set1) {
			System.out.print(i+", ");
		}
		System.out.print("\n");
		System.out.println("Set 2");
		for (int i : set2) {
			System.out.print(i+",");
		}
		System.out.print("\n");
		System.out.println();
		System.out.println("Testing Subset and Equals ... (should be false)");
		System.out.println(set2.subset(set1));
		System.out.println(set1.equals(set2));
		System.out.println();

		System.out.println("Testing Union ...");
		set1.union(set2);
		for (int i : set1) {
			System.out.print(i+", ");
		}
		System.out.print("\n");
		System.out.println();
		System.out.println("Testing Subset ... (should be true)");
		System.out.println(set2.subset(set1));
		System.out.println();
		System.out.println("Testing Intersection ...");
		set1.intersection(set2);
		for (int i : set1) {
			System.out.print(i+", ");
		}
		System.out.print("\n");
		System.out.println();
		System.out.println("Making Sets Equal ... ");
		for (int i = 0; i < 10; i++) {
			set2.add(i);
		}
		set2.intersection(set1);
		for (int i : set2) {
			System.out.print(i+", ");
		}
		System.out.print("\n");
		System.out.println();
		System.out.println("Testing Equals ... (should be true)");
		System.out.println(set1.equals(set2));
	}
}