import java.util.*; 

/**
 * Class that stores the name of an ingredient 
 * and the quantity.
 */
public class Ingredient {
	private String name;
	private double amount;

	// Constructors
    
    /**
     * Constructs an Ingredient with the specified name and quantity.
     * 
     * @param  name  The name of the ingredient
     * @param  amount  The quantity of the ingredient
     */
	public Ingredient(String name, double amount) {
		this.name = name;
		this.amount = amount;
	}

	// Getters

	public String getName() {
		return name;
	}

	public double getAmount() {
		return amount;
	}

	// Other Methods

	/**
     * Reduces the amount of an ingredient.
     *
     * @param  amount  amount to reduce by
     */
	public void useAmount(double amount) {
		this.amount -= amount;

	}

	/**
     * Adds to the amount of an ingredient.
     *
     * @param  amount  amount to add
     */
	public void gainAmount(double amount) {
		this.amount += amount;
	}

	/**
     * Converts the Recipe information to a String.
     * 
     * Uses the following format:
     * 1 bread
     * 
     * @return  ingredient as a String (see description for format)
     */
	@Override
	public String toString() {
		if (amount % 1 == 0) {
			return ""+ (int) amount+" "+name;
		}
		else {
			return ""+ amount+" "+name;
		}
		
	}


}