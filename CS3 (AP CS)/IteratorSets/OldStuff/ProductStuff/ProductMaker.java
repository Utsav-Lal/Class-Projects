class ProductMaker {
	public static void main(String[] args) {
		Product ourProduct = new Product("Computer", 145.99);
		System.out.println(ourProduct.ToString());
		ourProduct.reducePrice(5, '$');
		ourProduct.reducePrice(5, '%');
		ourProduct.printPriceHistory();

	}
}