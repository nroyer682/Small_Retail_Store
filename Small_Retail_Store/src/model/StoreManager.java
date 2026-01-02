package model;

public class StoreManager {
	private static StoreManager instance;
	private String name;
	private final int MAX_STORES = 3;
	private int nos; // number of stores
	private RetailStore[] stores;
	
	public StoreManager() {
		this.nos = 0;
		this.stores = new RetailStore[MAX_STORES];
	}

	public static StoreManager getInstance() {
		if (instance == null) { 
			instance = new StoreManager();
		}
		return instance;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getNumberOfStores() {
		return this.nos;
	}
	
	public int getMaxStores() {
		return MAX_STORES;
	}
	
	public void addStore(RetailStore store) {
		this.stores[this.nos] = store;
		this.nos++;
	}

	public static void resetManager() {
		instance = new StoreManager();
	}

	public RetailStore[] getStores() {
		return this.stores;
	}

}
