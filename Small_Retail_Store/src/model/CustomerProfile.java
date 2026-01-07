package model;

public class CustomerProfile {
	private String name;
	private int orderLimit;
	private String status;
	private static final String[] VALID_PRODUCTS = {"iPad Mini", "iPad Pro 12.9", "Apple Magic Keyboard", "Surface Pro 9", "Surface Laptop 5", "Surface Pro Keyboard with Slim Pen 2"};
	private static final int NUM_OF_VALID_PRODUCTS = 6;
	private String[] products;
	private int[] quantities;
	private String[] stores;
	private String[] dates;
	private String[] brands;
	private int noo; // number of orders;

	public CustomerProfile(String name, int orderLimit) {
		/* 
		 * Create the profile for a new customer.
		 */
		this.name = name;
		this.orderLimit = orderLimit;
		this.status = String.format("No order has been placed for %s yet", this.name);
		this.products = new String[this.orderLimit];
		this.quantities = new int[this.orderLimit];
		this.stores = new String[this.orderLimit];
		this.dates = new String[this.orderLimit];
		this.brands = new String[this.orderLimit];
		this.noo = 0;
	}

	public String getName() {
		return this.name;
	}

	public int getOrderLimit() {
		return this.orderLimit;
	}
	
	public static String[] getValidProducts() {
		return VALID_PRODUCTS;
	}
	
	public static int getNumberOfValidProducts() {
		return NUM_OF_VALID_PRODUCTS;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOrderLimit(int orderLimit) {
		this.orderLimit = orderLimit;
	}

	public String getProfileReport() {
		String report = null;
		if (this.noo == 0) {
			report = String.format("%s has not yet placed any orders", this.name);
		}
		else {
			String list = "<";
			for (int i = 0; i < this.noo; i++) {
				list += String.format("%s %s product %s in %s on %s", 
										this.quantities[i], this.brands[i], this.products[i], this.stores[i], this.dates[i]);
				if (i < this.noo - 1) {
					list += "; ";
				}
			}
			list += ">";
			report = String.format("%s has placed %d orders %s", 
									this.name, this.noo, list);
		}
		return report;
	}

	public String getStatusofLastOrder() {
		return this.status;
	}
	
	public void setStatusOfLastOrder(String status) {
		this.status = status;
	}
	
	public boolean isValid(String product) {
		boolean isValid = false;
		for (int i = 0; !isValid && i < NUM_OF_VALID_PRODUCTS; i++) {
			if (VALID_PRODUCTS[i].equals(product)) {
				isValid = true;
			}
		}
		return isValid;
	}
	
	public String getBrand(String product) {
		String brand = null;
		if (isValid(product)) {
			if (product.equals("iPad Mini") || product.equals("iPad Pro 12.9") || product.equals("Apple Magic Keyboard")) {
				brand = "Apple";
			}
			else {
				brand = "Microsoft";
			}
		}
		return brand;
	}

	public void addOrder(String product, int quantity, String store, String date) throws InvalidProductKindException {
		/* 
		 * Add a new first order into the customer's profile.
		 * 
		 * Each order contains:
		 * 	a) the name of a computer product
		 * 	b) the quantity of the product to be ordered
		 * 	c) the name of retail store
		 * 	d) the date of order
		 * 
		 * For a) above, assume that there are ***only six*** kinds of products that are valid:
		 * (Attend to their spellings which are case sensitive.)
		 * 
		 * 	1. "iPad Mini"
		 * 	2. "iPad Pro 12.9"
		 * 	3. "Apple Magic Keyboard"
		 * 	4. "Surface Pro 9"
		 * 	5. "Surface Laptop 5"
		 * 	6. "Surface Pro Keyboard with Slim Pen 2"
		 * 
		 * Kinds 1 to 3 are "Apple" products, whereas Kinds 4 to 6 are "Microsoft" products.
		 * 
		 */
		if (!isValid(product)) {
			throw new InvalidProductKindException("Invalid product kind");
		}
		else {
			this.products[this.noo] = product;
			this.quantities[this.noo] = quantity;
			this.stores[this.noo] = store;
			this.dates[this.noo] = date;
			this.brands[this.noo] = this.getBrand(product);
			this.noo++;
		}
	}
	
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || this.getClass() != obj.getClass()) {
			return false;
		}
		
		CustomerProfile other = (CustomerProfile) obj;
		boolean equal = this.getName().equals(other.getName()) && this.getOrderLimit() == other.getOrderLimit() && this.noo == other.noo;
		if (equal) {
			for (int i = 0; equal && i < this.noo; i++) {
				equal = this.products[i].equals(other.products[i]) && this.quantities[i] == other.quantities[i] && this.stores[i].equals(other.stores[i]) && this.dates[i].equals(other.dates[i]);
			}
		}
		return equal;
	}

}
