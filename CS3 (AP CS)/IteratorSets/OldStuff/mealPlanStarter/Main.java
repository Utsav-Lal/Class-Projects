import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.TimeUnit;

/**
 * Hub for command line arguments for the MealPlanner
 */ 
public class Main {
	public static void main(String[] args) {
		ArrayList<Recipe> recipesKnown = new ArrayList<>();
		ArrayList<Store> nearbyStores = new ArrayList<>();
		ArrayList<Ingredient> itemsOwned = new ArrayList<>();

		if (args.length >= 3) {
			// use randomly generated recipes; creates new file
			if (args[2].equals("Random")) {
				BuildData bd = new BuildData();
    			recipesKnown = new ArrayList<Recipe>(bd.getRecipeBox());
    			nearbyStores = new ArrayList<Store>(bd.getStores());
    			itemsOwned = new ArrayList<Ingredient>(bd.getPantry());

    			// makes mealplans
				MealPlanner mealPlanner = new MealPlanner(recipesKnown, itemsOwned, nearbyStores);
				System.out.println(mealPlansToString(mealPlanner.getPlans(Integer.parseInt(args[0]), Integer.parseInt(args[1]))));

			// uses preset test file
			} else if (args[2].equals("Test")) {
				BuildData bd = new BuildData("foodEcosystemThu Sep 29 13:30:03 PDT 2022.txt");
    			recipesKnown = new ArrayList<Recipe>(bd.getRecipeBox());
    			nearbyStores = new ArrayList<Store>(bd.getStores());
    			itemsOwned = new ArrayList<Ingredient>(bd.getPantry());

    			// makes mealplans
				MealPlanner mealPlanner = new MealPlanner(recipesKnown, itemsOwned, nearbyStores);
				System.out.println(mealPlansToString(mealPlanner.getPlans(Integer.parseInt(args[0]), Integer.parseInt(args[1]))));

			// uses editable input file - Takes into account forced recipes and blocked recipes
			// Bonus B1 (I made this before BuildData, it is a user interface, pls give credit)
			} else if (args[2].equals("Input")) {
				readDataFile(args[0], args[1]);

			// uses given file if file exists
			} else if (new File(args[2]).exists()) {
				BuildData bd = new BuildData(args[2]);
    			recipesKnown = new ArrayList<Recipe>(bd.getRecipeBox());
    			nearbyStores = new ArrayList<Store>(bd.getStores());
    			itemsOwned = new ArrayList<Ingredient>(bd.getPantry());

    			// makes mealplans
				MealPlanner mealPlanner = new MealPlanner(recipesKnown, itemsOwned, nearbyStores);
				System.out.println(mealPlansToString(mealPlanner.getPlans(Integer.parseInt(args[0]), Integer.parseInt(args[1]))));
			}

			// returns error if file doesn't exist
			else {
				System.out.println("Unknown File.");
			}
		} else if (args.length > 0) {
			// blocks given recipe name (Bonus A and B2)
			if (args[0].equals("Block")) {
				blockRecipe(args[1]);
			}

			// unblocks given recipe name (Bonus A and B2)
			else if (args[0].equals("Unblock")) {
				unblockRecipe(args[1]);
			}

			// forces given recipe name (Bonus A and B2)
			else if (args[0].equals("Force")) {
				forceRecipe(args[1]);
			}

			// unforces given recipe name (Bonus A and B2)
			else if (args[0].equals("Unforce")) {
				unforceRecipe(args[1]);
			}

			// gives documentation
			else if (args[0].equals("Help")) {
				docHelp();
			}

			// tells user how to use input file
			else if (args[0].equals("InputHelp")) {
				fileHelp();
			}

			// returns error if unknown command
			else {
				System.out.println("Unknown Command.");
			}
		}
		else {
			// ____
			nullStore();
		}
	}

	/**
	 * Reads data directly from input file, user-friendly
	 * Prints out a plan using the parameters given and the presets in the file.
	 * For Bonus B1 (I made this before BuildData, it is a user interface, pls give credit)
	 * 
	 * @param numPlans number of plans to make
	 * @param numMeals number of meals per plan
	 */
	private static void readDataFile(String numPlans, String numMeals) {
		ArrayList<Recipe> recipesKnown = new ArrayList<>();
		ArrayList<Store> nearbyStores = new ArrayList<>();
		ArrayList<Ingredient> itemsOwned = new ArrayList<>();

		// From W3Schools
		// Reads data from input file to fill in the recipesKnown, nearbyStores, and itemsOwned
		/**
		 * File kept in this format:
		 * 
		 * Recipes:
		 * 5(number of recipes)
		 * =======
		 * 
		 * Bread
		 * 4(number of ingredients)
		 * -------
		 * Flour
		 * 5 (amount)
		 * ...
		 * 
		 * Omelette
		 * ...
		 * 
		 * Stores:
		 * 6 (number of stores)
		 * =======
		 * 
		 * Bakery
		 * 5 (number of ingredients)
		 * -------
		 * Flour
		 * ...
		 * 
		 * Farm
		 * ...
		 * 
		 * Items:
		 * 5 
		 */
		// also ignores unliked recipes in blocked file.
		try {
      		File inFile = new File("Data/inFile.txt");
      		Scanner myReader = new Scanner(inFile);
      		while (myReader.hasNextLine()) {
        		String data = myReader.nextLine();

        		if (data.length() == 8) {
        			if (data.charAt(7) == ':') {
	        			int amountRecipes = Integer.parseInt(myReader.nextLine());
	        			myReader.nextLine();
	        			for (int j = 0; j < amountRecipes; j++) {
	        				myReader.nextLine();
		        			String recipeName = myReader.nextLine();
		        			int numIngredients = Integer.parseInt(myReader.nextLine());
		        			ArrayList<Ingredient> ingredientList = new ArrayList<>();
		        			myReader.nextLine();
		        			for (int i = 0; i < numIngredients; i++) {
		        				String ingredientName = myReader.nextLine();
		        				double ingredientAmount = Double.parseDouble(myReader.nextLine());
		        				ingredientList.add(new Ingredient(ingredientName, ingredientAmount));
		        			}
		        			
		        			recipesKnown.add(new Recipe(recipeName, ingredientList));
		        		}
		        		myReader.nextLine();
		        	}
        		}
        		if (data.length() == 7) {
        			if (data.charAt(6) == ':') {
	        			int amountStores = Integer.parseInt(myReader.nextLine());
	        			myReader.nextLine();
	        			for (int j = 0; j < amountStores; j++) {
	        				myReader.nextLine();
	        				String storeName = myReader.nextLine();
	        				int numIngredients = Integer.parseInt(myReader.nextLine());
		        			ArrayList<String> ingredientList = new ArrayList<>();
		        			myReader.nextLine();
		        			for (int i = 0; i < numIngredients; i++) {
		        				String ingredientName = myReader.nextLine();

		        				ingredientList.add(ingredientName);
		        			}
		        			nearbyStores.add(new Store(storeName, ingredientList));
	        			}
	        			myReader.nextLine();
	        		}
        		}
        		if (data.length() == 6) {
        			if (data.charAt(5) == ':') {
	        			int amountItems = Integer.parseInt(myReader.nextLine());
	        			myReader.nextLine();
	        			myReader.nextLine();
	        			for (int i = 0; i < amountItems; i++) {
	        				String ingredientName = myReader.nextLine();
		        			double ingredientAmount = Double.parseDouble(myReader.nextLine());
		        			itemsOwned.add(new Ingredient(ingredientName, ingredientAmount));
	        			}
	        		}
        		}
      		}
      		myReader.close();
    	} catch (FileNotFoundException e) {
      		System.out.println("An error occurred.");
      		e.printStackTrace();
    	}

    	// removes blocked recipes (Bonus A)
    	ArrayList<String> blockedRecipeNames = new ArrayList<>();
    	ArrayList<Recipe> copyRecipe = new ArrayList<>(recipesKnown);
    	try {
      		File inFile = new File("Data/blocked.txt");
      		Scanner myReader = new Scanner(inFile);
      		while (myReader.hasNextLine()) {
        		String data = myReader.nextLine();
        		blockedRecipeNames.add(data);
        	}
        	myReader.close();
        } catch (FileNotFoundException e) {
      		System.out.println("An error occurred.");
      		e.printStackTrace();
    	}
    	for (Recipe r : copyRecipe) {
    		if (blockedRecipeNames.contains(r.getName())) {
    			recipesKnown.remove(r);
    		}
    	}

    	// makes a list of recipes to be required (Bonus A)
    	ArrayList<String> forcedRecipeNames = new ArrayList<>();
    	ArrayList<String> forcedRecipes = new ArrayList<>();
    	try {
      		File inFile = new File("Data/forced.txt");
      		Scanner myReader = new Scanner(inFile);
      		while (myReader.hasNextLine()) {
        		String data = myReader.nextLine();
        		forcedRecipeNames.add(data);
        	}
        	myReader.close();
        } catch (FileNotFoundException e) {
      		System.out.println("An error occurred.");
      		e.printStackTrace();
    	}
    	for (Recipe r : recipesKnown) {
    		if (forcedRecipeNames.contains(r.getName())) {
    			forcedRecipes.add(r.getName());
    		}
    	}

    	// makes mealplans
		MealPlanner mealPlanner = new MealPlanner(recipesKnown, itemsOwned, nearbyStores);
		System.out.println(mealPlansToString(mealPlanner.getPlans(Integer.parseInt(numPlans), Integer.parseInt(numMeals), forcedRecipes)));
	}

	/**
	 * Turns the mealplan list into a printable string
	 * 
	 * Given in the following format:
	 * MealPlans:
     * 1:
     * [mealplan 1]
     * 2:
     * [mealplan 2]
     * ...
	 * 
	 * @param plans list of mealplans
	 * 
	 * @return string representation of list
	 */
	private static String mealPlansToString(List<MealPlan> plans) {
		StringBuilder sb = new StringBuilder();
		sb.append("MealPlans:");
		for (int i = 0; i < plans.size(); i++) {
			sb.append("\n"+(i+1)+":"+"\n"+plans.get(i));
		}

		return sb.toString();

	}

	// Methods that block/force recipes (for bonus A and B2)

	/**
	 * Blocks a recipe from being used
	 * Bonus A and B2
	 * 
	 * @param recipe name of recipe to block
	 */
	private static void blockRecipe(String recipe) {
		// From StackOverflow
		// Adds the name to a new line in the file
		Path p = Paths.get("Data/blocked.txt");
		String s = System.lineSeparator() + recipe;
		try {
    		Files.write(p, s.getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {
    		System.err.println(e);
		}
	}

	/**
	 * Unblocks recipe
	 * Bonus A and B2
	 * 
	 * @param recipe name of recipe to unblock
	 */
	private static void unblockRecipe(String recipe) {
		// gets list of recipes in file, removes recipe name from list, writes list to file
		Path p = Paths.get("Data/blocked.txt");
		try {
			List<String> fullFile = Files.readAllLines(p);
			fullFile.removeIf(i -> i.equals(recipe));
			String newFileString = String.join("\n", fullFile);
			try {
			FileWriter myWriter = new FileWriter("Data/blocked.txt");
			myWriter.write(newFileString);
			myWriter.close();
			} catch (IOException e) {
    			System.err.println(e);
			}
		} catch (IOException e) {
    		System.err.println(e);
		}
		
	}

	/**
	 * Forces recipe to be used
	 * Bonus A and B2
	 * 
	 * @param recipe name of recipe
	 */
	private static void forceRecipe(String recipe) {
		Path p = Paths.get("Data/forced.txt");
		String s = System.lineSeparator() + recipe;
		try {
    		Files.write(p, s.getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {
    		System.err.println(e);
		}
	}

	/**
	 * Unforces the recipe
	 * Bonus A and B2
	 * 
	 * @param recipe name of recipe
	 */
	private static void unforceRecipe(String recipe) {
		Path p = Paths.get("Data/forced.txt");
		try {
			List<String> fullFile = Files.readAllLines(p);
			fullFile.removeIf(i -> i.equals(recipe));
			String newFileString = String.join("\n", fullFile);
			try {
			FileWriter myWriter = new FileWriter("Data/forced.txt");
			myWriter.write(newFileString);
			myWriter.close();
			} catch (IOException e) {
    			System.err.println(e);
			}
		} catch (IOException e) {
    		System.err.println(e);
		}
		
	}

	// Documentation printers 

	/**
	 * Prints out user documentation
	 */
	private static void docHelp() {
		System.out.println("java Main [int numPlans] [int numMeals] [String file]\ngives mealplans using the inputs from the file.");
		System.out.println("Files:");
		System.out.println("\tRandom - creates random input data in a new BuildData file.");
		System.out.println("\tInput - uses user inputs from input file.");
		System.out.println("\tType 'java Main InputHelp' for Input File Form");
		
		System.out.println("\njava Main [String command] [String recipeName]\ndoes command on recipe");
		System.out.println("Commands:");
		System.out.println("\tBlock - bans recipe");
		System.out.println("\tUnblock - unbans recipe");
		System.out.println("\tForce - requires recipe");
		System.out.println("\tUnforce - unrequires recipe");

	}

	/**
	 * Prints out instructions for using input file
	 */
	private static void fileHelp() {
		System.out.println("Enter input data into input.txt in this format:");
		System.out.println("Note: Do not ignore the empty linespaces.\n");
		System.out.println("Recipes:\n[amount of recipes]\n=======\n");
		System.out.println("[recipe name]\n[amount of ingredients]\n-------");
		System.out.println("[ingredient name]\n[ingredient amount]\n...\n");
		System.out.println("...\n");
		System.out.println("Stores:\n[amount of stores]\n=======\n");
		System.out.println("[store name]\n[amount of ingredients]\n-------");
		System.out.println("[ingrdient name]\n...\n");
		System.out.println("...\n");
		System.out.println("Items:\n[amount of items]\n=======\n");
		System.out.println("[ingredient name]\n[ingrdient amount]\n...");
	}

	/**
	 * Opens T̸̗̫̓̎͂̓̄͘͝h̵̪̼̿͑͌̔̊̑̏̆̎̍͋̉͑̃̐e̷̛̘͎͉̯̘̖̬̎͆͛̀̈́̾͐ ̶̢͕̲̖̘̙͙͇̞̻̹̣͂̀̓̋̀͛͂͠Ņ̶̢͔̥̪͔̝͔̤͉̺͕̰͔̬̀̄̏u̷̬͓͎̗̟͎͔̯̤̓̊̉̉̒̕͘l̵̨̧͍̠̥̬̼̱͈̘̙͎̈́̋͊͋̋͆̈́͛̓̔̀̀̚l̸̡̨̼͈̘̗̝̪̤̦̗̙͉̖̝̀ ̶̩͔̼͙̪̀̇S̵̪̥̮͐t̵̡͎̖̭̠͌͗̽̽̈́̒͛͛͗͗̂ö̶̧̦͇̫͔̰̙̲͈͙͓͓͖̹̎̎ͅr̴̛͕̻͈͈͉̖̺̫̖̎͒̂̒̕̚e̵̱̩͇͊̈́̌̓̎̒́̽̚͘
	 */
	private static void nullStore() {
		String[] cmd = new String[] {"open", "T̸̗̫̓̎͂̓̄͘͝h̵̪̼̿͑͌̔̊̑̏̆̎̍͋̉͑̃̐e̷̛̘͎͉̯̘̖̬̎͆͛̀̈́̾͐ ̶̢͕̲̖̘̙͙͇̞̻̹̣͂̀̓̋̀͛͂͠Ņ̶̢͔̥̪͔̝͔̤͉̺͕̰͔̬̀̄̏u̷̬͓͎̗̟͎͔̯̤̓̊̉̉̒̕͘l̵̨̧͍̠̥̬̼̱͈̘̙͎̈́̋͊͋̋͆̈́͛̓̔̀̀̚l̸̡̨̼͈̘̗̝̪̤̦̗̙͉̖̝̀ ̶̩͔̼͙̪̀̇S̵̪̥̮͐t̵̡͎̖̭̠͌͗̽̽̈́̒͛͛͗͗̂ö̶̧̦͇̫͔̰̙̲͈͙͓͓͖̹̎̎ͅr̴̛͕̻͈͈͉̖̺̫̖̎͒̂̒̕̚e̵̱̩͇͊̈́̌̓̎̒́̽̚͘.txt"};
		try {
			Runtime.getRuntime().exec(cmd);
		} catch (IOException e) {
    		System.err.println(e);
		}
	}
}