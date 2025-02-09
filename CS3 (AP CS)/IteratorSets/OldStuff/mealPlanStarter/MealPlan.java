import java.util.*;

/**
 * Class that stores the recipes of a mealplan 
 * and the stores accessible and their inventory.
 */
public class MealPlan {
    Collection<String> recipesToMake;
    Map<String, ArrayList<Ingredient>> storeNameToGroceryList;

    // Constructors
    
    /**
     * Constructs a Mealpan with the specified recipes and stores.
     * 
     * @param  recipesToMake  The recipes of the mealplan
     * @param  storeNameToGroceryList  The stores accessbile and their inventories
     */
    public MealPlan(Collection<String> recipesToMake,
    Map<String, ArrayList<Ingredient>> storeNameToGroceryList) {
        this.recipesToMake = new ArrayList<>(recipesToMake);
        this.storeNameToGroceryList = new HashMap<>(storeNameToGroceryList);
    }

    // Getters

    public Collection<String> getRecipesToMake() {
        return recipesToMake;
    }

    public Collection<String> getStoreNames() {
        return storeNameToGroceryList.keySet();
    }

    public Collection<Ingredient> getGroceryList(String storeName) {
        return storeNameToGroceryList.get(storeName);
    }

    public Map<String, ArrayList<Ingredient>> getStoreNameToGroceryListMap() {
        return storeNameToGroceryList;
    }

    // Other Methods

    /**
     * Returns a list of recipes
     *
     * @param  separator  separator to use
     * 
     * @return names of recipes as a String. The value of separator is placed in between each pair of recipe names.
     */
    public String recipesToMakeString(String separator) {
        return String.join(separator, getRecipesToMake());
    }

    /**
     * Returns a list of stores
     *
     * @param  separator  separator to use
     * 
     * @return names of stores to go to as a String. The value of separator is placed in between each pair of stores.
     */
    public String storeNamesString(String separator) {
        return String.join(separator, getStoreNames());
    }

    /**
     * Returns the inventory of a specific store
     *
     * @param  separator  separator to use
     * @param storeName store to retieve inventory from
     * 
     * @return the grocery list (Ingredients) for the specified store as a String. The value of separator is placed in between each pair of Ingredients.
     */
    public String groceryListString(String separator, String storeName) {
        List<String> nameList = new ArrayList<>();

        for (Ingredient s : getGroceryList(storeName)) {
            nameList.add(s +"");
        }
        return String.join(separator, nameList);
    }

    /**
     * Converts the MealPlan information to a String.
     * 
     * Uses the following format:
     *     Recipes to be used:
     *         bread
     *         pizza
     *         cake
     *     Stores To Go To:
     *         Quick Mart
     *         Safeway
     *     Quick Mart:
     *         1 bread
     *         1 cheese
     *         1 expired milk
     *         1 soda
     *     Safeway:
     *         1 apples
     *         1 water
     * 
     * @return  mealplan information as a String (see description for format)
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String indent = "    ";
        String listIndent = "\n"+indent+indent;
        sb.append(indent+"Recipes To Be Used:");
        sb.append(listIndent+recipesToMakeString(listIndent));
        sb.append("\n"+indent+"Stores To Go To:");
        sb.append(listIndent+storeNamesString(listIndent));
        for (String s : getStoreNames()) {
            sb.append("\n"+indent+s+":");
            sb.append(listIndent+groceryListString(listIndent, s));
        }
        return sb.toString();
    }

}