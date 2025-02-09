import java.util.*; 

/**
 * Class that stores the name of a recipe 
 * and a set of ingredients that the recipe needs.
 */
public class Recipe {
	private String name;
	private List<Ingredient> ingredients;

	// Constructors
    
    /**
     * Constructs a Recipe with the specified name and no ingredients.
     * 
     * @param  name  The name of the recipe
     */
	public Recipe(String name) {
		this.name = name;
		ingredients = new ArrayList<Ingredient>();
	}

	/**
     * Constructs a Recipe with the specified name and list of ingredients.
     * 
     * @param  name  The name of the recipe
     * @param  ingredients  The ingredients that the recipe needs
     */
	public Recipe(String name, Collection<Ingredient> ingredients) {
		this.name = name;
		this.ingredients = new ArrayList<>(ingredients);
	}

	// Getters

	public String getName() {
		return name;
	}

	public Collection<Ingredient> getIngredients() {
		return ingredients;
	}

	// Other Methods

	/**
     * Adds an ingredient to the recipe inventory.
     *
     * @param  ingredient  name of ingredient to add
     */
	public void addIngredient(Ingredient ingredient) {
		ingredients.add(ingredient);
	}

	/**
     * Converts the Recipe information to a String.
     * 
     * Uses the following format:
     * Recipe: Bread
     * Ingredients: 
     *     5 flour
     * 	   2 water
     *     1 yeast
     * 
     * @return  recipe information as a String (see description for format)
     */
	@Override
	public String toString() {
		String returnString =  "Recipe: "+name+"\nIngredients:";
		for (int i = 0; i < ingredients.size(); i++) {
			returnString += "\n\t"+ingredients.get(i);
		}
		return returnString;

	}
}