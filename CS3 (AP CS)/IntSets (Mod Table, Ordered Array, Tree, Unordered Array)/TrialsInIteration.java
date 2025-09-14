
import java.util.*;


public class TrialsInIteration {
    
    public static void main(String[] args) {
        IntSet set = new ModTableIntSet(3);
        set.add(25);
        set.add(12);
        set.add(36);
        set.add(10);
        set.add(18);

        // System.out.println(set);

        // When we implement the oficial Java Iterator interface, we can do
        // fancy loops like this:
        for (Integer item : set) {
            System.out.print(item + ", ");
        }
        System.out.println("");
        
        // Note that the code above is equivalent to the following:
        Iterator<Integer> iter = set.iterator();
        while (iter.hasNext()) {
            Integer item = iter.next();
            System.out.print(item + ", ");
        }
        System.out.println("");
    }
}
