import java.util.*;

/**
 * Tests LinkedListList class
 */
public class LinkedListListTester {
	public static void main(String[] args) {
		LinkedListList presetList = new LinkedListList(new LinkedListList[]{null, null, null});
		presetList.set(1, new LinkedListList());
		presetList.get(1).addNode(presetList.getNodeAt(0));
	}
}