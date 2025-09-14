import java.util.*;

/**
 * A class that tests all our work.
 */
public class Tester {

    
    public static void main(String[] args) {
        System.out.println("\n\nTesting Store ...\n");
        storeTester();
        System.out.println("\n\nTesting Ingredient ...\n");
        ingredientTester();
        System.out.println("\n\nTesting Recipe ...\n");
        recipeTester();
        System.out.println("\n\nTesting Mealplan ...\n");
        mealPlanTester();
    }
    /**
     * Tries out all methods for the Store class.
     */
    public static void storeTester() {
        // Create a Store starting with no items, then add one item at a time.
        Store safeway = new Store("Safeway");
        Store tj = new Store("Trader Joes", Arrays.asList("???"));
        safeway.addItem("oil");
        safeway.addItem("bread");
        safeway.removeItem("oil");
        
        safeway.addItems(Arrays.asList("honey", "water"));
        safeway.removeItems(Arrays.asList("honey", "oil"));
        System.out.println("Store name: " + safeway.getName());
        System.out.println("Has bread (should be true): " + safeway.hasItem("bread"));
        System.out.println("Has bombs (should be false): " + safeway.hasItem("bombs"));
        System.out.println(safeway);  // calls toString() to convert to String
        System.out.println(tj);
    }
    /**
     * Tries out all methods for the Ingredient class.
     */
    public static void ingredientTester() {
        // Creates an Ingredient, tests all methods.
        Ingredient money = new Ingredient("money", 12341);
        System.out.println("Ingredient: "+ money.getName());
        System.out.println("Quantity: " +money.getAmount());
        money.gainAmount(1);
        money.useAmount(2);
        System.out.println(money);
        
    }
    /**
     * Tries out all methods for the Recipe class.
     */
    public static void recipeTester() {
        // Create a recipe starting with no items, then add one item at a time.
        Recipe disaster = new Recipe("disaster");
        Recipe window = new Recipe("window", Arrays.asList(new Ingredient("glass", 100), new Ingredient("heat", 1000), new Ingredient("experience", 10)));
        disaster.addIngredient(new Ingredient("stupidity", 100));
        disaster.addIngredient(new Ingredient("lack of precautions", 100));
        System.out.println("Recipe name: " + disaster.getName());
        System.out.println("Ingredients " + disaster.getIngredients());
        System.out.println(disaster);  // calls toString() to convert to String
        System.out.println(window);
    }
    /**
     * Tries out all methods for the MealPlan class.
     */
    public static void mealPlanTester() {
        // Creates an MealPlan, tests all methods.
        Map<String, ArrayList<Ingredient>> inMap = new HashMap<String, ArrayList<Ingredient>>();
        Ingredient[] food = {new Ingredient("8rdf", 124), new Ingredient("iuyfsd", 12), new Ingredient("sdf789", 100)};
        ArrayList<Ingredient> inList = new ArrayList<>(Arrays.asList(new Ingredient("8rdf", 124), new Ingredient("iuyfsd", 12), new Ingredient("sdf789", 100)));
        ArrayList<Ingredient> inList2 = new ArrayList<>(Arrays.asList(new Ingredient("sfg", 5), new Ingredient("w34a", 1)));
        inMap.put("7fasgghao", inList);
        inMap.put("78t6ae", inList2);
        MealPlan no = new MealPlan(Arrays.asList("Please", "just", "stop"), inMap);
        System.out.println("Recipes: "+ no.recipesToMakeString(", "));
        System.out.println("Stores: " +no.storeNamesString(", "));
        System.out.println("Inventory of 7fasgghao: " +no.groceryListString(", ", "7fasgghao"));


        System.out.println(no);
        
    }
}
