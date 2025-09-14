import java.util.*;  

/**
 * Tests DArray class
 */
public class DArrayTester {
	public static void main(String[] args) {
		System.out.println("Testing Dynamic Array...");
		DArray testDArray = new DArray();
		System.out.println("\nTesting Element Addition...");
		testDArray.add(0, 3);
		testDArray.add(1, 4);
		testDArray.add(1, 5);
		System.out.println("Success!");
		System.out.println("\nTesting Element Replacement...");
		testDArray.set(1, 2);
		System.out.println("Success!");
		System.out.println("\nTesting Element Retrieval...");
		System.out.println("Element at Indice 1: "+testDArray.get(1));
		System.out.println("Success!");
		System.out.println("\nTesting Element Removal...");
		testDArray.remove(1);
		testDArray.remove(0);
		System.out.println("Success!");
		System.out.println("\nTesting Size Retrieval...");
		System.out.println("Size of Array: "+testDArray.size());
		System.out.println("Success!");
		test();
	}

	 public static void test() { // now I need some weird methods that seem like they should break but acutally shouldn't
        DArray whatevs = new DArray();
        for (int i = 0; i < 100; i++){
            whatevs.add(i, i);
        }
        System.out.println(whatevs.get(83) == 83.0); // should be 83.0
        for (int i = 0; i < 100; i++){
            whatevs.set(i, 100-i);
        }
        System.out.println(whatevs.get(83) == 17.0); // should be 17.0
        for (int i = 0; i < 100; i++){
            whatevs.set(i, i);
        }
        System.out.println(whatevs.get(83) == 83.0); // should be 83.0
        for (int i = 99; i >= 25; i--){ // removes everything from 99 to 25
            whatevs.remove(i); // checks to make sure that shrinking capacity works.
        }
        System.out.println(whatevs.get(2) == 2.0); // should be 2.0
        whatevs.remove(0);
        whatevs.remove(15);
        System.out.println(whatevs.size() == 23); // should be 23 (25 - 2)
        System.out.println(whatevs.get(2) == 3.0); // should be 3.0
        System.out.println(whatevs.get(15) == 17.0); // should be 17.0
        whatevs.add(0, 2.0); // testing adding stuff to the start
        System.out.println(whatevs.get(0) == 2.0); // should be 2.0
    }
}