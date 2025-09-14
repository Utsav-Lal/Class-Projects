import java.util.*;  

/**
 * Class that creates mealplans 
 * and stores the stores, recipes, and ingredients available.
 */
public class MealPlanner {
	private Collection<Recipe> recipesKnown;
	private Collection<Ingredient> itemsOwned;
	private Collection<Store> nearbyStores;

	// Constructors
    
    /**
     * Constructs a MealPlanner with the specified recipes known, items owned, and nearby stores.
     * 
     * @param  recipesKnown  The recipes known
     * @param  itemsOwned  The ingredients owned
     * @param  nearbyStores  The available stores
     */
	public MealPlanner(Collection<Recipe> recipesKnown,
        	Collection<Ingredient> itemsOwned,
        	Collection<Store> nearbyStores) {
		this.recipesKnown = recipesKnown;
		this.itemsOwned = itemsOwned;
		this.nearbyStores = nearbyStores;
	}

	// Main Method

	/**
     * Gives a list of numPlans near-optimal mealPlans with numMeals recipes with the "best" ones listed first.
     * 
     * @param  numPlans  amount of mealplans to give
     * @param numMeals amount of meals per plan
     * @param forcedMeals meals required, should give only zero or one parameters (for Bonus A)
     * 
     * @return a list of mealplans sorted from best to worst
     */
	public List<MealPlan> getPlans(int numPlans, int numMeals, ArrayList<String>... forcedMeals) {
		ArrayList<MealPlan> plans = new ArrayList<>();
		ArrayList<MealPlan> sortedPlans = new ArrayList<>();

		// creates new list of forced meals
		ArrayList<Recipe> forced = new ArrayList<>();
		if (forcedMeals.length == 1) {
			for (Recipe r : recipesKnown) {
				if (forcedMeals[0].contains(r.getName())) {
					forced.add(r);
				}
			}
		}

		// makes plans, using the forced meals
		for (int i = 0; i < numPlans; i++) {
			plans.add(makePlan(numMeals, forced));
		}

		// sorts the plans from best to worst
		int a = 0;
		a += plans.size();
		for (int i = 0; i < a; i++) {
			MealPlan optimal = plans.get(0);
			for (MealPlan m : plans) {
				if (m.storeNameToGroceryList.keySet().size() < optimal.storeNameToGroceryList.keySet().size()) {
					optimal = m;
				}
			}
			sortedPlans.add(optimal);
			plans.remove(optimal);
			
		}

		return sortedPlans;

	}

	// Helper Methods

	/**
     * Finds an optimal MealPlan, using the forced recipes and a random one to add variety
     * 
     * At each step, takes the recipe with the least amount of additional stores needed.
     *
     * @param numMeals amount of recipes
     * @param forced meals required
     * 
     * @return a MealPlan
     */
	private MealPlan makePlan(int numMeals, ArrayList<Recipe> forced) {
		ArrayList<Recipe> recipesToMake = new ArrayList<>();
		ArrayList<Ingredient> ingredientsNeeded = new ArrayList<>();

		ArrayList<Recipe> recipesKnownList = new ArrayList<Recipe>(recipesKnown);

		ArrayList<Recipe> modForced = new ArrayList<>(forced);

		// Creates modifiable ingredient list
		ArrayList<Ingredient> iterOwned = new ArrayList<>(itemsOwned);
		ArrayList<Ingredient> modIngredients = new ArrayList<Ingredient>();
		for (Ingredient i : iterOwned) {
			modIngredients.add(new Ingredient(i.getName(), i.getAmount()));
		}

		// makes sure enough recipes exist
		if (recipesKnown.size() < numMeals) {
			System.out.println("Not enough recipes exist.");
			System.exit(0);
		}

		// adds forced recipes, the most optimal first
		int used = 0;
		while (modForced.size() > 0 && used < numMeals) {
			Recipe optimal = null;
			boolean found = false;
			for (Recipe r : modForced) {
				// Sorts by least stores needed, makes sure items in store
				if (recipeOptimal(r, ingredientsNeeded, modIngredients) != -1) {
					found = true;
					if (optimal == null) {
						optimal = r;
					}
					else if (recipeOptimal(optimal, ingredientsNeeded, modIngredients) > recipeOptimal(r, ingredientsNeeded, modIngredients)) {
						optimal = r;
					}
				}
			}
			// makes sure enough possible recipes exist
			if (found == false) {
					break;
			}

			used += 1;
			ArrayList<Ingredient> choiceIngredients = new ArrayList<Ingredient>(optimal.getIngredients());
			ArrayList<Ingredient> newIngredients = new ArrayList<>(recipeNeeded(optimal, modIngredients));
			
			// Adds recipe
			recipesToMake.add(optimal);

			// Removes ingredients used and finds the ones still needed
			addIngredients(recipeNeeded(optimal, modIngredients), ingredientsNeeded);
			addIngredients(newIngredients, modIngredients);
			removeIngredients(choiceIngredients, modIngredients);
			recipesKnownList.remove(optimal);
			modForced.remove(optimal);
		}

		// checks again if enough recipes exist
		if (recipesKnownList.size() < numMeals-used) {
			System.out.println("Not enough recipes exist.");
			System.exit(0);
		}
		boolean found2 = false;
		for (Recipe r : recipesKnownList) {
			if (recipeOptimal(r, ingredientsNeeded, modIngredients) != -1) {
				found2 = true;
			}
		}
		if (found2 == false) {
			System.out.println("Not enough recipes exist.");
			System.exit(0);
		}

		// chooses random recipe for mealplan variation
		if (numMeals > used && true) {
			// Chooses a random first recipe, repeats if not all ingredients present in stores
			int firstChoiceIndex = (int) Math.floor(Math.random()*recipesKnownList.size());
			Recipe firstChoice = recipesKnownList.get(firstChoiceIndex);
			while (recipeOptimal(firstChoice, ingredientsNeeded, modIngredients) == -1) {
				firstChoiceIndex = (int) Math.floor(Math.random()*recipesKnownList.size());
				firstChoice = recipesKnownList.get(firstChoiceIndex);
			}
			ArrayList<Ingredient> choiceIngredients = new ArrayList<Ingredient>(firstChoice.getIngredients());
			ArrayList<Ingredient> newIngredients = new ArrayList<>(recipeNeeded(firstChoice, modIngredients));
			
			// Adds recipe
			recipesToMake.add(firstChoice);

			// Removes ingredients used and finds the ones still needed
			addIngredients(recipeNeeded(firstChoice, modIngredients), ingredientsNeeded);
			addIngredients(newIngredients, modIngredients);
			removeIngredients(choiceIngredients, modIngredients);
			recipesKnownList.remove(firstChoice);	
			used += 1;	
		}

		if (recipesKnownList.size() < numMeals-used) {
			System.out.println("Not enough recipes exist.");
			System.exit(0);
		}
		
		// Finds rest of recipes
		for (int i = 0; i < numMeals - used; i++) {
			Recipe optimal = null;
			boolean found = false;
			for (Recipe r : recipesKnownList) {
				// Sorts by least stores needed, makes sure items in store
				if (recipeOptimal(r, ingredientsNeeded, modIngredients) != -1) {
					found = true;
					if (optimal == null) {
						optimal = r;
					}
					else if (recipeOptimal(optimal, ingredientsNeeded, modIngredients) > recipeOptimal(r, ingredientsNeeded, modIngredients)) {
						optimal = r;
					}
				}
			}
			// makes sure enough possible recipes exist
			if (found == false) {
				System.out.println("Not enough recipes exist.");
				System.exit(0);
			}

			ArrayList<Ingredient> choiceIngredients = new ArrayList<Ingredient>(optimal.getIngredients());
			ArrayList<Ingredient> newIngredients = new ArrayList<>(recipeNeeded(optimal, modIngredients));
			
			// Adds recipe
			recipesToMake.add(optimal);

			// Removes ingredients used and finds the ones still needed
			addIngredients(recipeNeeded(optimal, modIngredients), ingredientsNeeded);
			addIngredients(newIngredients, modIngredients);
			removeIngredients(choiceIngredients, modIngredients);
			recipesKnownList.remove(optimal);						
		}

		// Turns recipes to strings for new MealPlan
		ArrayList<String> recipeStrings = new ArrayList<>();
		for (Recipe r : recipesToMake) {
			recipeStrings.add(r.getName());
		}

		// returns MealPlan with the recipes needed and the optimized store allocation
		return new MealPlan(recipeStrings, optimizeStore(ingredientsNeeded));

	}

	/**
     * Finds the ingredients needed for a recipe that are not available in the pantry
     *
     * @param recipe recipe to check
     * @param ingredients ingredients available
     * 
     * @return a list containing the needed ingredients
     */
	private ArrayList<Ingredient> recipeNeeded(Recipe recipe, Collection<Ingredient> ingredients) {
		ArrayList<Ingredient> ourIngredients = new ArrayList<>(recipe.getIngredients());
		ArrayList<Ingredient> needed = new ArrayList<Ingredient>();
		ArrayList<Ingredient> available = new ArrayList<>(ingredients);

		// Removes the ingredients available from the recipe, finding how much still needed
		for (int i = 0; i < recipe.getIngredients().size(); i++) {
			boolean found = false;
			for (int j = 0; j < ingredients.size(); j++) {
				
				if (ourIngredients.get(i).getName().equals(available.get(j).getName())) {
					Ingredient k = ourIngredients.get(i);
					Ingredient l = available.get(j);
					found = true;

					if (k.getAmount() > l.getAmount()) {
						needed.add(new Ingredient(k.getName(), k.getAmount() - l.getAmount()));
					}
				}
			}
			// creates new ingredient object none present
			if (found == false) {
				needed.add(new Ingredient(ourIngredients.get(i).getName(), ourIngredients.get(i).getAmount()));
			}
		}

		return needed;

	}

	/**
     * Finds the number of stores needed for a recipe, checks if stores have all ingredients needed
     * Acts as a weight for the algorithim
     *
     * @param recipe recipe to check
     * @param ingredients ingredients available
     * 
     * @return the number of needed stores if ingredients present in stores
     * @return -1 if no stores have the ingredients for a recipe
     */
	private int recipeOptimal(Recipe recipe, Collection<Ingredient> using, Collection<Ingredient> ingredients) {
		ArrayList<Ingredient> ourIngredients = new ArrayList<>(recipe.getIngredients());
		ArrayList<Ingredient> needed = recipeNeeded(recipe, ingredients);
		ArrayList<Ingredient> use = new ArrayList<>(using);

		// Checks if all the ingredients are in the store
		HashSet<Ingredient> stored = new HashSet<>();
		for (Store s : nearbyStores) {
			for (Ingredient i : needed) {
				if (s.hasItem(i.getName())) {
					stored.add(i);
				}
			}
		}

		// returns -1 if not
		if (stored.size() != needed.size()) {
			return -1;
		}

		// finds the stores needed for the recipe and the ingredients needed for the ones already in use
		Map<String, ArrayList<Ingredient>> ourStores = optimizeStore(needed);
		Map<String, ArrayList<Ingredient>> storesNeeded = optimizeStore(use);

		// removes the stores already used for the ingredients for other recipes from the ones needed for this recipe
		for (String s : storesNeeded.keySet()) {
			if (ourStores.containsKey(s)) {
				ourStores.remove(s);
			}
		}

		// returns the number of stores needed
		return ourStores.keySet().size();

	}
	
	/**
     * Removes some ingredients from a list
     *
     * @param takeList ingredients to take
     * @param ingredients list to take from
     */
	private void removeIngredients(ArrayList<Ingredient> takeList, ArrayList<Ingredient> fromList) {
		ArrayList<Ingredient> needed = new ArrayList<>();

		for (Ingredient i : takeList) {
			boolean found = false;

			for (Ingredient j : fromList) {
				if (i.getName().equals(j.getName())) {
					j.useAmount(i.getAmount());
					found = true;
				}
			
			}

			if (found == false) {
				needed.add(new Ingredient(i.getName(), -i.getAmount()));
			}
		}

		fromList.addAll(needed);
	}	

	/**
     * Adds some ingredients to a list
     *
     * @param takeList ingredients to add
     * @param ingredients list to add to
     */
	private void addIngredients(ArrayList<Ingredient> takeList, ArrayList<Ingredient> fromList) {
		ArrayList<Ingredient> needed = new ArrayList<>();

		for (Ingredient i : takeList) {
			boolean found = false;

			for (Ingredient j : fromList) {
				if (i.getName().equals(j.getName())) {
					j.gainAmount(i.getAmount());
					found = true;
				}
			}

			if (found == false) {
				needed.add(new Ingredient(i.getName(), i.getAmount()));
			}
		}

		fromList.addAll(needed);
	}	

	/**
     * Finds most optimal way to split ingredients between stores
     * Chooses store that adds most additional ingredients
     *
     * @param ingredients ingredients to buy
     * 
     * @return a map containing the ingredients to buy from each store
     */
	private Map<String, ArrayList<Ingredient>> optimizeStore(ArrayList<Ingredient> ingredients) {
		ArrayList<String> fullList = ingredientNameList(ingredients);
		ArrayList<String> doneList = new ArrayList<>();
		Map<Store, ArrayList<String>> storeMap = new HashMap<Store, ArrayList<String>>();
		Map<String, ArrayList<Ingredient>> returnMap = new HashMap<String, ArrayList<Ingredient>>();

		// Finds the store that covers the most ingredients until all are covered.
		while (doneList.size() != ingredients.size()) {
			Store optimal = new Store("Placeholder");
			int optimalSize = 0;

			for (Store s : nearbyStores) {
				int newSize = 0;

				for (String t : fullList) {
					if (s.hasItem(t)) {
						newSize += 1;
					}
				}

				if (newSize > optimalSize) {
					optimalSize = newSize;
					optimal = s;
				}
			}

			ArrayList<String> mapList = new ArrayList<>();

			for (String t : fullList) {
				if (optimal.hasItem(t)) {
					mapList.add(t);
				}
			}

			storeMap.put(optimal, mapList);
			fullList.removeAll(mapList);
			doneList.addAll(mapList);


		}

		// Turns <Store, ArrayList<String>> to <String, ArrayList<Ingredient>>
		for (Store s : storeMap.keySet()) {
			ArrayList<Ingredient> mapList = new ArrayList<>();

			for (String t : storeMap.get(s)) {
				for (Ingredient i : ingredients) {
					if (i.getName().equals(t)) {
						mapList.add(i);
					}
				}
			}

			returnMap.put(s.getName(), mapList);
		}

		return returnMap;

	}

	/**
	 * Turns ingredient list to string list
	 * 
	 * @param ingredients ingredients to turn to strings
	 * 
	 * @return list of names of ingredients
	 */
	private ArrayList<String> ingredientNameList(ArrayList<Ingredient> ingredients) {
		ArrayList<String> returnList = new ArrayList<>();

		for (Ingredient i : ingredients) {
			returnList.add(i.getName());
		}

		return returnList;
	}
}