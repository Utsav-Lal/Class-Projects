import java.util.*;
import java.nio.file.*;
import java.io.FileWriter;

/**
 * This program builds the following: 
 * 
 * Out of ~200 possible ingredients, it fills 8 stores, each with between 20 and 150 of the possible ingredients
 * It is currently set to 150 recipes, each using between 5 and 12 ingredients, 
 * currently have between 10 and 60 ingredients in the pantry, 
 */
public class BuildData {

	// These are the variables the user will need
	private Collection<Ingredient> pantry;
	private Collection<Recipe> recipeBox;
	private List<Store> stores;
	
	// These are for use within the data building
	private Collection<String> allIngredients;
	// A future, better version, will distribute pantry items/recipe needs among aisles more evenly
	private Map<String, Set<String>> groceryAisles;  
	
	// For Randomly choosing ingredients
	private Random r;
	
	/**
	 * Unchangeable - just for use generating values.
	 */ 
	private static final String[] storeNames = {"Safeway", "Wegman's", "HMart", "Trader Joe's", "Aldi", "Sprouts", "Mariano's", "Stop n' Shop"};
	private static final String[] adjectives = {"Crispy", "Steamed", "Baked", "Refreshing", "Creamy", "Artisanal", "Sauted", "Fried", "Boiled", "Warm", "Cold"};
	

	/**
	 * If the user gives a file as a command line arg, then extract the food ecosystem from that file
	 * Otherwise, generate a new one and write it to its own output file.
	 * 
	 * @param args either a filename or nothing
	 */ 
	public static void main(String[] args) {
		BuildData data;
		if (args.length == 1){
			data = new BuildData(args[0]);	
		}
		else{
			data = new BuildData();	
		}

		// You may access the pantry, recipeBox, and stores of the data object with the getters from below.
		MealPlanner mp = new MealPlanner(data.getRecipeBox(), data.getPantry(), data.getStores());
		List<MealPlan> plans = mp.getPlans(3, 3);
		
		for (int i = 0; i < plans.size(); i++){
			System.out.println(plans.get(i));
			System.out.println();
		}
	}

	/** 
	 * Build ecosystem from file - So you can do non-random testing
	 * 
	 * @param filename the file that the ecosystem comes from. In the exact format that the program outputs an ecosystem
	 */ 
	public BuildData(String filename){

		pantry = new ArrayList<Ingredient>();
		recipeBox = new ArrayList<>();
		stores = new ArrayList<Store>();

	 	extractDataFromFile(filename);
	}


	/** 
	 * Builds a random ecosystem by creating a new world with a pantry, recipes, and grocery stores. 
	 * Outputs the information into a .txt file for potential future use.
	 */ 
	public BuildData(){

		// Initialize all instance variables
		r = new Random();
		allIngredients = new HashSet<>();
		groceryAisles = new HashMap<>();

		pantry = new ArrayList<Ingredient>();
		recipeBox = new ArrayList<>();
		stores = new ArrayList<Store>();
		
		// Get ingredients from ingredients file
		try{
			readIngredients();	
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
		// Build pantry, recipes, and store inventories
		buildPantry();
		buildRecipeBox(150);
		buildInventories();

		// So you can use this world to test again in the future.
		// Comment this out if you don't want to keep building these files. 
		writeDataToFile();
	}
	
	// GETTERS
	// These are the 3 things you need from this class!

	/**
	 * @return the collection of ingredients that exist within the pantry
	 */ 
	public Collection<Ingredient> getPantry(){
		return pantry;
	}

	/**
	 * @return the collection of recipes that the user has access to
	 */
	public Collection<Recipe> getRecipeBox(){
		return recipeBox;
	}
	
	/**
	 * @return the collection of stores that the user has access to
	 */ 
	public Collection<Store> getStores(){
		return stores;
	}

	/**
	 * From the current pantry, recipe box, and store list, write all data to a file.
	 * This file can then be loaded back into this program calling the 1 parameter constructor 
	 * to recreate this particular food ecosystem for future testing.
	 * 
	 * Left as public in case the programmer wants to do this data writing from somewhere else. 
	 * This method is already called at the end of the 0 parameter constructor (building a new ecosystem)
	 */ 
	public void writeDataToFile(){
		try{
			// File name is built with date/time to differentiate different runs
			FileWriter outFile = new FileWriter("foodEcosystem"+new java.util.Date()+".txt");
			
			// Pantry first
			for (Ingredient ing : pantry){
				outFile.write(ing.toString() + ", ");
			}
			outFile.write("\n");
			
			// Followed by all recipes
			for (Recipe r : recipeBox){
				outFile.write(r.toString());
				outFile.write("\n");
			}

			// Followed by all stores + inventories
			for (Store s : stores){
				outFile.write(s.toString());
				outFile.write("\n");
			}
			
			outFile.close();  	// Can't forget to close - weird dangling file ends without this. Didn't write everything needed

		} catch (Exception e){				// Something went wrong with the file writing
			System.out.println("Error!");
			e.printStackTrace();
		}
	}


	/** 
	 * For a pre-existing food world, pull
	 * 
	 * @param filename the file the data comes from. In the exact format of this program's output files.
	 */ 
	private void extractDataFromFile(String filename){
		
		// Read in all chars from the file into a string. 
		// This requires catching any exceptions - won't compile otherwise
		String data = "";
	 	try {
	 		data = new String(Files.readAllBytes(Paths.get(filename))); 
	 	} catch (Exception e){
	 		e.printStackTrace();
	 	}
        
        int startRecipes = data.indexOf("\n") + 1;		// First newline indicates end of pantry
        int startStores = data.indexOf("Store: ");		// First "Store: " indicates end of recipes

        // All of the data for each of the 3 values is in the corresponding string
        String pantryString = data.substring(0, startRecipes-1);
        String recipeString = data.substring(startRecipes, startStores);
        String storeString = data.substring(startStores);
        
        // Must pull apart each string and construct Collections of the correct object-types
        extractPantry(pantryString);		// Ingredient objects
        extractRecipeList(recipeString);	// Recipe objects
        extractStoreList(storeString);		// Store objects
	}

	/**
	 * Pantry items are in the file in the form 
	 * 		N item name, N2 item2 name, N3 item3 name
	 * Each is separated by a comma and a space.
	 * extractPantry splits these into individual Ingredient objects, 
	 * storing them in the pantry collection
	 * 
	 * @param pantryString - all the items/quantities in the pantry
	 */ 
	private void extractPantry(String pantryString){

		String[] pantryArray = pantryString.split(", ");  // Items are separated by commas
		
		for (String item : pantryArray){

			int separator = item.indexOf(" ");	// First space always comes after the number
			double count = Double.parseDouble(item.substring(0, separator));
			String name = item.substring(separator + 1);

			pantry.add(new Ingredient(name, count));
		}
	}

	/**
	 * Recipes are in the file in the form 
	 * 		Recipe: recipe name
	 * 		Ingredients:
	 * 			N ingredient1
	 * 			N ingredient2
	 * 			...
	 * extractRecipeList splits these into individual Recipe objects, 
	 * storing them in the recipeBox collection
	 * 
	 * @param recipeString - all the recipes the program has access to
	 */
	private void extractRecipeList(String recipeString){
		
		String[] recipeArray = recipeString.split("Recipe: ");		// Each recipe separated
		
		for (String rec : recipeArray){ 	
			if (rec.equals("")) continue;  // Ignore empty values
			
			String[] splitit = rec.split("Ingredients:");			// Name vs ingredients
			String name = splitit[0].substring(0, splitit[0].length() - 1);	// Remove newline char at end of name
			
			Collection<Ingredient> ings = new ArrayList<>();
			String[] ingredientArray = splitit[1].split("\n\t");	// Each ingredient separated by newline + tab
			
			for (String item : ingredientArray){
				if (item.equals("") || item.equals("\n")) continue;	// Ignore empty values

				
				int separator = item.indexOf(" ");	// First space always comes after the number
				double count = Double.parseDouble(item.substring(0, separator));
				String ing = item.substring(separator + 1);
				
				// Removes newline from end of last item
				if (ing.endsWith("\n")){
					ing = ing.substring(0, ing.length()-1);
				}
				
				ings.add(new Ingredient(ing, count));
			}
			recipeBox.add(new Recipe(name, ings));
		}
	}

	/**
	 * Stores are in the file in the form 
	 * 		Store: store name
	 * 		products: product 1, product 2, product3, ...
	 * 
	 * extractStoreList splits these into individual Store objects, 
	 * storing them in the stores collection
	 * 
	 * @param storeString - all the stores the program has access to
	 */
	private void extractStoreList(String storeString){
		
		String[] storeArray = storeString.split("Store: ");  // Each store separated 
		
		for (String st : storeArray){
			if (st.equals("")) continue;	// Ignore empty values
			
			String[] splitit = st.split("products: ");		// Name vs products
			
			String name = splitit[0].substring(0, splitit[0].length() - 1);  // Remove newline char at end of name
			
			// Removes newline from end of last item
			if (splitit[1].endsWith("\n")){
				splitit[1] = splitit[1].substring(0, splitit[1].length()-1);
			}
			
			Set<String> products = new HashSet<>(Arrays.asList(splitit[1].split(", ")));  // All products go into the HashSet
			stores.add(new Store(name, products));
		}
	}

	/**
     * reads through the text file of all the ingredients
     * creates a map of grocery aisles & ingredients, and a Set of allIngredients
     */
    private void readIngredients() throws Exception { 
        String data = "";
        data = new String(Files.readAllBytes(Paths.get("meal planning data.txt"))); //replace this with the name of the file we want
        String[] storeData = data.split("\\\\");
        
        for (String ingr : storeData) {
        	// Aisle name is first element
        	// Ingredients are all subsequent elements
        	if (ingr.equals("")) continue;  // Ignore empty values
    		
    		int firstEOL = ingr.indexOf("\n");
    		String aisle = ingr.substring(0, firstEOL);
    		
            Set<String> ingredients = new HashSet<>(Arrays.asList(ingr.substring(firstEOL).split("\n")));
            if (ingredients.contains("")){  // Ignore empty values
            	ingredients.remove("");
            }
            allIngredients.addAll(ingredients);
            groceryAisles.put(aisle, ingredients);	

        }
    }
    
    private void buildPantry(){
    	
    	int numIngredients = r.nextInt(50) + 10;		// number of ingredients to put in the pantry
    	Object[] ingArray = allIngredients.toArray(); 	// so we can randomly choose ingredients
    	Set<String> putInPantry = new HashSet<>();  	// to check for duplicates

    	for (int n = 0; n < numIngredients; n++){
    		int i = r.nextInt(ingArray.length);			// Randomly add ingredient
    		
    		while (putInPantry.contains((String)ingArray[i])){	// Double check you haven't put same ingredient in before
    			i = r.nextInt(ingArray.length);
    		}

    		int amt = r.nextInt(4) + 1;					// How much of the ingredient?  Should probably change this to a double
    		putInPantry.add((String)ingArray[i]);
    		pantry.add(new Ingredient((String)ingArray[i], amt));
    	}
    }

    /**
     * each uses between 5 and 12 ingredients
     */ 
    private Recipe buildOneRecipe(){
    	int numIngredients = r.nextInt(7) + 5;

    	Object[] ingArray = allIngredients.toArray();  	// so we can randomly choose ingredients
    	Set<String> putInRecipe = new HashSet<>();  	// to check for duplicates
    	List<Ingredient> ings = new ArrayList<>();

    	for (int n = 0; n < numIngredients; n++){

    		int i = r.nextInt(ingArray.length);
    		while (putInRecipe.contains((String)ingArray[i])){ 	// Double check you haven't put same ingredient in before
    			i = r.nextInt(ingArray.length);
    		}
    		int amt = r.nextInt(5) + 1;					// How much of the ingredient?  Should probably change this to a double
    		putInRecipe.add((String)ingArray[i]);
    		ings.add(new Ingredient((String)ingArray[i], amt));
    	}
    	String name = adjectives[r.nextInt(adjectives.length)] + " " + ings.get(r.nextInt(ings.size())).getName();
    	return new Recipe(name, ings);
    }

    /**
     * 8 stores, each with between 20 and 180 of the possible ingredients
     */ 
    private void buildInventories(){

    	Object[] ingArray = allIngredients.toArray();  // so we can randomly choose ingredients
    	for (String store : storeNames){
    		Set<String> inventory = new HashSet<>();  	// Inventory for the current store
    		stores.add(new Store(store, inventory));
		}

		// Put all ingredients in at least 1 store
    	for(Object ing : ingArray){
    		Set<String> inventory = stores.get(r.nextInt(storeNames.length)).getItems();  // (may need to change to getItems)
    		inventory.add((String)ing);
    	}
    	
    	for (int j = 0; j < storeNames.length; j++){
    		String store = storeNames[j];
    		Set<String> inventory = stores.get(j).getItems();  // Inventory for the current store 
	    	int inventorySize = r.nextInt(100);	// Between 20 - 150 products

	    	for(int i = 0; i < inventorySize; i++){
	    		String product = (String)ingArray[r.nextInt(ingArray.length)];

	    		while (inventory.contains(product)){		// Double check you haven't put same ingredient in before
	    			product = (String)ingArray[r.nextInt(ingArray.length)];
	    		}
	    		inventory.add(product);
	    	}
    	}
    }

    /**
     * Create size recipes
     * 
     * @param size number of recipes to build
     */ 
    private void buildRecipeBox(int size){
    	for (int i = 0; i < size; i++){
    		recipeBox.add(buildOneRecipe());
    	}
    }
}