import java.util.ArrayList;
class Product {
	private String name;
	private double price;
	private ArrayList<Double> priceHistory = new ArrayList<Double>();
	Product(String inName, double inPrice) {
		// checks validity of inputs, and stores them
		name = "";
		if (checkValidString(inName)) {
			name = inName;
		}
		price = 0;
		if (inPrice >= 0) {
			price = inPrice;
		}
		// adds in the first price in the list
		priceHistory.add(price);
	}

	public String ToString() {
		// returns the name and the rounded price
		return name+": $"+roundPrice(price);
	}

	public void printPriceHistory() {
		// prints each item in pricehistory, adding 1 to the list item so 0 doesn't show up in the first.
		for (int i = 0; i<priceHistory.size(); i++) {
			System.out.println((i+1)+" - $"+roundPrice(priceHistory.get(i)));
		}
	}

	public void reducePrice(double change, char type) {
		// first checks if the characters are part of the two options
		String validChars = "%$";
		if (validChars.contains(type+"")) {
			// if so, figures out which one it is and does the correct change
			if (type == '$') {
				price -= change;
			}
			else {
				price -= price * change / 100;
			}
			// updates the list
			priceHistory.add(price);

		}
	}

	private double roundPrice(double inPrice) {
		// multiplies price by 100, rounds it to the nearest integer, then divides it again by 100
		// rounds to nearest hundreth this way

		// for first statement, makes sure to assign var to new value
		// so it doesn't affect the actual price, which can still be unrounded
		inPrice = inPrice * 100;
		inPrice = Math.round(inPrice);

		inPrice /= 100;

		return inPrice;
	}
	private boolean checkValidString(String inString) {
		// for each character, checks if it is in the valid characters or is a letter
		String validChars = " ,.'!1234567890-;:()/";
		for (int i = 0; i < inString.length(); i++) {
			char ourChar = inString.charAt(i);
			if (!(inString.contains(ourChar+"") || ('a' <= ourChar && ourChar <= 'z') || ('A' <= ourChar && ourChar <= 'Z'))) {
				return false;
			}
		}
		return true;
	}
}