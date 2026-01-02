package model;

public class RetailStore {
	private String name;
	private int[] stock;
	private StoreManager manager;

	public RetailStore(String name) {
		this.name = name;
		this.stock = new int[CustomerProfile.getNumberOfValidProducts()];
	}
	
	public String getName() {
		return this.name;
	}
	
	public int indexOf(String product) {
		int index = -1;
		boolean found = false;
		for (int i = 0; !found && i < CustomerProfile.getNumberOfValidProducts(); i++) {
			if (CustomerProfile.getValidProducts()[i].equals(product)) {
				index = i;
				found = true;
			}
		}
		return index;
	}

	public int getAvailableStock(String product) {
		return this.stock[this.indexOf(product)];
	}
	
	public void setAvailableStock(String product, int amount) {
		this.stock[this.indexOf(product)] = amount;
	}

	public void addStock(String product, int quantity) {
		/* 
		 * Add stocks to the retail store.
		 * 
		 * For simplicity of this test, assume that: 
		 * 	+ The stock of each of the six product kinds can only be added at most once.
		 * 		That is, to store the stocks, it is sufficient to have an array of length 6.
		 * 	+ The argument of product name is always valid (so there's no need to throw an exception).
		 * 	+ The argument quantity of the product is always positive. 
		 */
		this.stock[this.indexOf(product)] += quantity;
	}

	public void submitOrder(CustomerProfile customer, String product, int quantity, String date) throws InsufficientStockException {
		if (this.getAvailableStock(product) - quantity < 0) {
			customer.setStatusOfLastOrder(String.format("Last order of %d %s in %s requested by %s failed", 
															quantity, product, this.name, customer.getName()));
			throw new InsufficientStockException("Insufficient stock");
		}
		else {
			// updates on retail store
			this.setAvailableStock(product, this.getAvailableStock(product) - quantity);
			// updates on customer profile
			customer.setStatusOfLastOrder(String.format("Last order of %d %s in %s requested by %s succeeded", 
															quantity, product, this.name, customer.getName()));
		}
		
	}

	public void setManager(StoreManager manager) throws TooManyStoresException {
		/* 
		 * Assigning a store to a manager adds it into the managers store array
		 * recall, the maximum number of stores a manager can have is 3. 
		 */
		if (manager.getNumberOfStores() == manager.getMaxStores()) {
			throw new TooManyStoresException("Too many stores");
		}
		else {
			this.manager = manager;
			manager.addStore(this);
		}
	}

	public StoreManager getManager() {
		return this.manager;
	}

	public String[] findProductsInOtherStores(String product) {
		/*
		 * Return an array containing the names
		 * of stores that have the desired product in stock. 
		 * 
		 * This array should exclude the store object that the method is called upon. 
		 * 
		 * If no other store has the product, return an empty array. 
		 */
		int count = 0;
		int[] indices = new int[this.manager.getNumberOfStores()];
		for (int i = 0; i < this.manager.getNumberOfStores(); i++) {
			if (this.manager.getStores()[i] != this && this.manager.getStores()[i].getAvailableStock(product) > 0) {
				indices[count] = i;
				count++;
			}
		}
		
		String[] result = new String[count];
		for (int i = 0; i < count; i++) {
			RetailStore rs = this.manager.getStores()[indices[i]];
			result[i] = String.format("%s: %d", rs.getName(), rs.getAvailableStock(product));
		}
		return result;
	}

}
