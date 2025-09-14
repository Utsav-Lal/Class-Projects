import java.util.*;
import java.nio.file.*;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Creates a random string of text based on another text
 * 
 * @author Utsav Lal
 */
public class RandomWriter {
	public static void main(String[] args) throws IOException {
		// Retrieves inputs (java RandomWriter k length source output)
		// k - length of seed
		int k = Integer.parseInt(args[0]);
		int length = Integer.parseInt(args[1]);
		String source = args[2];
		String result = args[3];

		// Checks inputs, and stores the source file as a list of characters
		// Checks that both the seed size and the length are nonnegative
		if (k < 0 || length < 0) {
			System.err.println("Lengths cannot be negative");
		}
		// Makes sure the output file works
		checkResult(result);
		// Makes sure the source file works and has enough characters, stores it as a list of characters
		char[] charList = readSource(source, k);

		// Makes output character list
		var fullString = new DLinkedList<Character>();

		// sets up first seed
		int currLength = 0;
		var seed = chooseSeed(charList, k);
		var seedIterator = seed.iteratorFromHead();
		while (seedIterator.hasNext()) {
			char x = seedIterator.next();
			fullString.add(x);
		}

		// Writes
		int size = k;
		Random rand = new Random();
		while (size < length) {
			// Finds characters after occurences of seed
			var finds = find(charList, seed);
			if (finds.size() > 0) {
				// Chooses a random character
				char newChar = finds.get(rand.nextInt(finds.size()));

				// Rotates seed forward
				seed.add(newChar);
				seed.remove(0);

				// Appends to output list
				fullString.add(newChar);
				size++;
			} else {
				// Chooses a new seed if no occurences present.
				seed = chooseSeed(charList, k);
			}
		}

		// Writes string to file
		writeOut(fullString, result);

	}

	/**
	 * Reads source file and stores the characters in a list
	 * 
	 * @param source name of source file
	 * @param size the seed size
	 * 
	 * @return the file as a list of characters
	 * 
	 * @throws IndexOutOfBoundsException when the seed size is bigger than the file length
	 * @throws IOException when there is a problem reading the file
	 */
	private static char[] readSource(String source, int size) throws IOException {

		String path = source;

		try {
            String content = Files.readString(Paths.get(path));
            char[] charList = (content).toCharArray();
            if (charList.length >= size) {
            	return charList;
            } else {
            	throw new IndexOutOfBoundsException("Seed size is too big");
            }
        } catch (IOException e) {
            throw new IOException("There is a problem with the input file");
        }
	}

	/**
	 * Makes sure the result file is writable
	 * 
	 * @param result name of result file
	 * 
	 * @throws IOException when there is a problem reading the file
	 */
	private static void checkResult(String result) throws IOException {
		// String path = result;

		// try {
		// 	String checkFile = Files.readString(Paths.get(path));
        // } catch (IOException e) {
        //     throw new IOException("There is a problem with the output file");
        // }
	}

	/**
	 * Chooses a new seed
	 * 
	 * @param sourceList list of characters in the source file
	 * @param size size of seed
	 * 
	 * @return new seed as character list
	 */
	private static DLinkedList<Character> chooseSeed(char[] sourceList, int size) {
		var seed = new DLinkedList<Character>();

		Random rand = new Random();

		int index = rand.nextInt(sourceList.length-size+1);

		for (int i = index; i < index+size; i++) {
			seed.add(sourceList[i]);
		}

		return seed;
	}

	/**
	 * Finds occurences of the seed
	 * 
	 * @param sourceList list of characters in source file
	 * @param seed seed to find
	 * 
	 * @return list of characters after occurences
	 */
	private static DLinkedList<Character> find(char[] sourceList, DLinkedList<Character> seed) {
		var seedIterator = seed.iteratorFromHead();
		var returnList = new DLinkedList<Character>();

		// For each character, iterates through it and the ones after it to see if it matches seed
		for (int i = 0; i < sourceList.length; i++) {
			int j = i;
			seedIterator = seed.iteratorFromHead();
			while (seedIterator.hasNext() && j < sourceList.length-1) {
				if (seedIterator.next() != sourceList[j]) {
					break;
				}
				j++;
			}
			if (j-i == seed.size()) {
				returnList.add(sourceList[j]);
			}
		}

		return returnList;
	}

	/**
	 * Writes the output to the file
	 * 
	 * @param fullString output character list
	 * @param result name of file to write to
	 * 
	 * @throws IOException when there is an unforseen problem with the file
	 */
	private static void writeOut(DLinkedList<Character> fullString, String result) throws IOException {
		String newString = "";
		var stringIterator = fullString.iteratorFromHead();
		while (stringIterator.hasNext()) {
			newString += stringIterator.next();
		}
		try {
      		FileWriter myWriter = new FileWriter(result);
      		myWriter.write(newString);
      		myWriter.close();
    	} catch (IOException e) {
      		System.out.println("An error occurred with the output file.");
      		e.printStackTrace();
    	}
	}
}