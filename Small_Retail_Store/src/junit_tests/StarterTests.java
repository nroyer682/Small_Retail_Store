package junit_tests;

import static org.junit.Assert.*;

import org.junit.Test;

import model.*;

public class StarterTests {

	/*
	 * Tests related to the CustomerProfile class.
	 */

	@Test
	public void test_customer_profile_01() {
		/* 
		 * Create the profile for a new customer.
		 *  
		 * A new profile is set with the customer's name and 
		 * 	a limit on the number of orders they are allowed to place.
		 * 
		 * e.g., A new profile is created for Alan, and he may have up to (and including) 5 orders.
		 */
		CustomerProfile obj = new CustomerProfile("Alan", 5);

		/* No orders have been placed yet for the customer. */
		String s1 = obj.getProfileReport();
		assertEquals("Alan has not yet placed any orders", s1);

		/* Get the status of the last-placed order.
		 * In this initial stage, there hasn't been any order placed for Alan. 
		 */
		String s2 = obj.getStatusofLastOrder();
		assertEquals("No order has been placed for Alan yet", s2);
	}

	@Test
	public void test_customer_profile_02a() { 
		CustomerProfile obj = new CustomerProfile("Alan", 5); 

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

		try {
			obj.addOrder("iPad Mini", 10, "Best Buy, Richmond Hill", "November-7-2022");
		}
		catch(InvalidProductKindException icpke) {
			fail();
		} 

		/* 
		 * A profile report contains:
		 * 	- The number of orders the customer has placed.
		 * 	- A semi-colon-separated list, surrounded by a pair of angle brackets, where each item gives information about:
		 * 		+ quantity (i.e., how many are ordered) 
		 * 		+ the product name
		 * 		+ the name of retail store
		 * 		+ the date of order
		 */
		assertEquals("Alan has placed 1 orders <10 Apple product iPad Mini in Best Buy, Richmond Hill on November-7-2022>", obj.getProfileReport());
	}

	@Test
	public void test_customer_profile_02b() { 
		CustomerProfile obj = new CustomerProfile("Alan", 5); 

		/* 
		 * Add two orders into the customer's profile.
		 */

		try {
			obj.addOrder("iPad Mini", 10, "Best Buy, Richmond Hill", "November-7-2022");
			obj.addOrder("Surface Pro 9", 20, "Canada Computers, Centre Point Mall", "November-8-2022");
		}
		catch(InvalidProductKindException icpke) {
			fail();
		} 

		assertEquals("Alan has placed 2 orders <10 Apple product iPad Mini in Best Buy, Richmond Hill on November-7-2022; 20 Microsoft product Surface Pro 9 in Canada Computers, Centre Point Mall on November-8-2022>", obj.getProfileReport());
	}

	@Test
	public void test_customer_profile_02c() { 
		CustomerProfile obj = new CustomerProfile("Alan", 5);
		
		/*
		 * At this initial stage, no order has been placed yet.
		 * 	Therefore, there's no status for the last-placed order.
		 */
		assertEquals("No order has been placed for Alan yet", obj.getStatusofLastOrder());
	}

	@Test
	public void test_customer_profile_02d() { 
		CustomerProfile obj = new CustomerProfile("Alan", 5); 

		try {
			/*
			 * Attempt to add an oder to some invalid product.
			 */
			obj.addOrder("Chromebook", 22, "Best Buy, Richmond Hill", "November-5-2022");
			fail();
		}
		catch(InvalidProductKindException icpke) {

		} 
	}
	
	@Test
	public void test_customer_profile_03a() { 
		CustomerProfile obj1 = new CustomerProfile("Alan", 5); 
		CustomerProfile obj2 = new CustomerProfile("Alan", 5); 

		try {
			obj1.addOrder("iPad Mini", 10, "Best Buy, Richmond Hill", "November-7-2022");
			obj2.addOrder("Surface Pro 9", 20, "Canada Computers, Centre Point Mall", "November-8-2022");
		}
		catch(InvalidProductKindException icpke) {
			fail();
		} 
		
		/*
		 * Check if the customer profiles are equal
		 */	
		assertFalse(obj1.equals(obj2));
	}
	@Test
	public void test_customer_profile_03b() { 
		CustomerProfile obj1 = new CustomerProfile("Alan", 5); 
		CustomerProfile obj2 = new CustomerProfile("Alan", 5); 

		try {
			obj1.addOrder("iPad Mini", 10, "Best Buy, Richmond Hill", "November-7-2022");
			obj2.addOrder("iPad Mini", 10, "Best Buy, Richmond Hill", "November-7-2022");
		}
		catch(InvalidProductKindException icpke) {
			fail();
		} 
		
		assertTrue(obj1.equals(obj2));
	}
	
	/*
	 * Tests related to the RetailStore class.
	 */

	@Test
	public void test_retail_store_01() {
		/* 
		 * Create a retail store with its name.
		 */
		RetailStore obj = new RetailStore("Best Buy, Richmond Hill");

		/* 
		 * Initially, no stock has been added to any of the six kinds of products.
		 * 
		 * Assume that the argument string is always a valid product name.
		 * 
		 * See above for the list of assumed valid product names.
		 */
		assertEquals(0, obj.getAvailableStock("iPad Mini"));
		assertEquals(0, obj.getAvailableStock("iPad Pro 12.9"));
		assertEquals(0, obj.getAvailableStock("Apple Magic Keyboard"));
		assertEquals(0, obj.getAvailableStock("Surface Pro 9"));
		assertEquals(0, obj.getAvailableStock("Surface Laptop 5"));
		assertEquals(0, obj.getAvailableStock("Surface Pro Keyboard with Slim Pen 2"));

		/* 
		 * Add stocks to the retail store.
		 * 
		 * For simplicity of this test, assume that: 
		 * 	+ The stock of each of the six product kinds can only be added at most once.
		 * 		That is, to store the stocks, it is sufficient to have an array of length 6.
		 * 	+ The argument of product name is always valid (so there's no need to throw an exception).
		 * 	+ The argument quantity of the product is always positive. 
		 */

		obj.addStock("iPad Pro 12.9", 200);
		obj.addStock("Surface Laptop 5", 400);
		obj.addStock("Surface Pro Keyboard with Slim Pen 2", 600);
		
		assertEquals(0, obj.getAvailableStock("iPad Mini"));
		assertEquals(200, obj.getAvailableStock("iPad Pro 12.9"));
		assertEquals(0, obj.getAvailableStock("Apple Magic Keyboard"));
		assertEquals(0, obj.getAvailableStock("Surface Pro 9"));
		assertEquals(400, obj.getAvailableStock("Surface Laptop 5"));
		assertEquals(600, obj.getAvailableStock("Surface Pro Keyboard with Slim Pen 2"));
	}

	@Test
	public void test_retail_store_02a() {
		RetailStore obj = new RetailStore("Best Buy, Richmond Hill");
	
		obj.addStock("iPad Pro 12.9", 200);
		obj.addStock("Surface Laptop 5", 400);
		obj.addStock("Surface Pro Keyboard with Slim Pen 2", 600);
		
		CustomerProfile obj2 = new CustomerProfile("Alan", 5);
		
		try {
			/*
			 * Assume that the argument quantity, when submitting an order, is always positive.
			 * 
			 * The two order submissions below should succeed,
			 * 	and the stocks should be reduced accordingly.
			 */
			
			obj.submitOrder(obj2, "iPad Pro 12.9", 100, "November-7-2022");
			assertEquals(0, obj.getAvailableStock("iPad Mini"));
			assertEquals(200 - 100, obj.getAvailableStock("iPad Pro 12.9"));
			assertEquals(0, obj.getAvailableStock("Apple Magic Keyboard"));
			assertEquals(0, obj.getAvailableStock("Surface Pro 9"));
			assertEquals(400, obj.getAvailableStock("Surface Laptop 5"));
			assertEquals(600, obj.getAvailableStock("Surface Pro Keyboard with Slim Pen 2"));
			
			obj.submitOrder(obj2, "Surface Pro Keyboard with Slim Pen 2", 120, "November-7-2022");
			assertEquals(0, obj.getAvailableStock("iPad Mini"));
			assertEquals(200 - 100, obj.getAvailableStock("iPad Pro 12.9"));
			assertEquals(0, obj.getAvailableStock("Apple Magic Keyboard"));
			assertEquals(0, obj.getAvailableStock("Surface Pro 9"));
			assertEquals(400, obj.getAvailableStock("Surface Laptop 5"));
			assertEquals(600 - 120, obj.getAvailableStock("Surface Pro Keyboard with Slim Pen 2"));
		}
		catch(InsufficientStockException ise) {
			fail();
		}
	}
	
	@Test
	public void test_retail_store_02b() {
		RetailStore obj = new RetailStore("Best Buy, Richmond Hill");
	
		obj.addStock("iPad Pro 12.9", 200);
		obj.addStock("Surface Laptop 5", 400);
		obj.addStock("Surface Pro Keyboard with Slim Pen 2", 600);
		
		CustomerProfile obj2 = new CustomerProfile("Alan", 5);
		
		try {
			/*
			 * By submitting an order successfully (if there's enough stock),
			 * 	the last order status of the customer's profile is ALSO updated accordingly.
			 */
			obj.submitOrder(obj2, "iPad Pro 12.9", 100, "November-7-2022");
			assertEquals("Last order of 100 iPad Pro 12.9 in Best Buy, Richmond Hill requested by Alan succeeded", obj2.getStatusofLastOrder());
			
			obj.submitOrder(obj2, "Surface Pro Keyboard with Slim Pen 2", 120, "November-7-2022");
			/*
			 * 	Now the last order status of the customer's profile corresponds to the second order.
			 */
			assertEquals("Last order of 120 Surface Pro Keyboard with Slim Pen 2 in Best Buy, Richmond Hill requested by Alan succeeded", obj2.getStatusofLastOrder());
		}
		catch(InsufficientStockException ise) {
			fail();
		}
	}
	
	@Test
	public void test_retail_store_04a() {
		RetailStore obj = new RetailStore("Best Buy, Richmond Hill");
	
		obj.addStock("iPad Pro 12.9", 200);
		obj.addStock("Surface Laptop 5", 400);
		obj.addStock("Surface Pro Keyboard with Slim Pen 2", 600);
		
		CustomerProfile obj2 = new CustomerProfile("Alan", 5);
		
		try {
			obj.submitOrder(obj2, "iPad Pro 12.9", 300, "November-7-2022");
			fail();
		}
		catch(InsufficientStockException ise) {
			/*
			 * By failing to submit an order(if there's not enough stock),
			 * 	all stocks stay unchanged.
			 */
			assertEquals(0, obj.getAvailableStock("iPad Mini"));
			assertEquals(200, obj.getAvailableStock("iPad Pro 12.9"));
			assertEquals(0, obj.getAvailableStock("Apple Magic Keyboard"));
			assertEquals(0, obj.getAvailableStock("Surface Pro 9"));
			assertEquals(400, obj.getAvailableStock("Surface Laptop 5"));
			assertEquals(600, obj.getAvailableStock("Surface Pro Keyboard with Slim Pen 2"));
		}
	}
	
	@Test
	public void test_retail_store_04b() {
		RetailStore obj = new RetailStore("Best Buy, Richmond Hill");
	
		obj.addStock("iPad Pro 12.9", 200);
		obj.addStock("Surface Laptop 5", 400);
		obj.addStock("Surface Pro Keyboard with Slim Pen 2", 600);
		
		CustomerProfile obj2 = new CustomerProfile("Alan", 5);
		
		try {
			obj.submitOrder(obj2, "iPad Mini", 120, "November-7-2022");
			fail();
		}
		catch(InsufficientStockException ise) {
			/*
			 * By failing to submit an order(if there's not enough stock),
			 * 	all stocks stay unchanged.
			 * 
			 * Here "iPad Mini" not having been added to the retail store explicitly
			 * 	means it has stock 0.
			 */
			assertEquals(0, obj.getAvailableStock("iPad Mini"));
			assertEquals(200, obj.getAvailableStock("iPad Pro 12.9"));
			assertEquals(0, obj.getAvailableStock("Apple Magic Keyboard"));
			assertEquals(0, obj.getAvailableStock("Surface Pro 9"));
			assertEquals(400, obj.getAvailableStock("Surface Laptop 5"));
			assertEquals(600, obj.getAvailableStock("Surface Pro Keyboard with Slim Pen 2"));
		}
	}
	
	@Test
	public void test_retail_store_04c() {
		RetailStore obj = new RetailStore("Best Buy, Richmond Hill");
	
		obj.addStock("iPad Pro 12.9", 200);
		obj.addStock("Surface Laptop 5", 400);
		obj.addStock("Surface Pro Keyboard with Slim Pen 2", 600);
		
		CustomerProfile obj2 = new CustomerProfile("Alan", 5);
		
		try { 
			obj.submitOrder(obj2, "iPad Pro 12.9", 300, "November-7-2022");
			fail();
		}
		catch(InsufficientStockException ise) {
			/* 
			 * By failing to submit an order (if there's not enough stock),
			 * 	the last order status of the customer's profile is ALSO updated accordingly.
			 */
			assertEquals("Last order of 300 iPad Pro 12.9 in Best Buy, Richmond Hill requested by Alan failed", obj2.getStatusofLastOrder());
		}
		
		try {
			obj.submitOrder(obj2, "iPad Mini", 120, "November-7-2022");
			fail();
		}
		catch(InsufficientStockException ise) {
			/* 
			 * By failing to submit an order (if there's not enough stock),
			 * 	the last order status of the customer's profile is again updated accordingly.
			 */
			assertEquals("Last order of 120 iPad Mini in Best Buy, Richmond Hill requested by Alan failed", obj2.getStatusofLastOrder());
		}
	} 
	
	/*
	 * Tests related to the StoreManager class. 
	 */
	
	@Test
	public void test_store_manager_01() {
		/* 
		 * Create a store manager with its name and maximum number of Retail Store.
		 * The static accessor getInstance() should be implemented such that the same object reference is returned in each call.
		 */
		StoreManager manager1 = StoreManager.getInstance(); 
		StoreManager manager2 = StoreManager.getInstance(); 
		assertSame(manager1, manager2); 
		assertTrue(manager1.getName() == null); 
		assertTrue(manager2.getName() == null); 
		
		manager1.setName("Jackie");
		
		assertEquals(manager1.getName(), "Jackie"); 
		assertEquals(manager2.getName(), "Jackie"); 
	}
	
	@Test
	public void test_store_manager_02() {
		/* 
		 * Reset StoreManager object. 
		 */
		StoreManager.resetManager();
		StoreManager manager1 = StoreManager.getInstance();
		StoreManager manager2 = StoreManager.getInstance();
		StoreManager manager3 = StoreManager.getInstance();
		StoreManager manager4 = StoreManager.getInstance();
		
		
		assertTrue(manager1 == manager2 && manager2 == manager3 && manager3 == manager4); 
		
		manager2.setName("Jackie");
		
		RetailStore obj1 = new RetailStore("Best Buy, Richmond Hill");
		RetailStore obj2 = new RetailStore("Best Buy, Halton Hills");
		RetailStore obj3 = new RetailStore("Best Buy, Mississauga");
		RetailStore obj4 = new RetailStore("Best Buy, Brampton");
		
		/* 
		 * Assigning a store to a manager adds it into the managers store array
		 * recall, the maximum number of stores a manager can have is 3. 
		 */
		
		try {
			obj1.setManager(manager1);
			obj2.setManager(manager2);
			obj3.setManager(manager3);
			assertEquals("Jackie", obj1.getManager().getName()); 
			assertEquals("Jackie", obj2.getManager().getName()); 
			assertEquals("Jackie", obj3.getManager().getName()); 
		}catch (TooManyStoresException e) {
			fail();
		}
		
		try {
			obj4.setManager(manager4);
			fail(); 
		}catch (TooManyStoresException e) {
			assertNull(obj4.getManager());
		}
		
		RetailStore[] stores = manager1.getStores(); 
		
		assertEquals(stores[0], obj1); 
		assertEquals(stores[1], obj2); 
		assertEquals(stores[2], obj3); 
	}
	
	@Test
	public void test_retail_Store_05() {
		
		StoreManager.resetManager();
		StoreManager manager = StoreManager.getInstance(); 
		manager.setName("Jackie");
		
		RetailStore obj1 = new RetailStore("Best Buy, Richmond Hill");
		RetailStore obj2 = new RetailStore("Best Buy, Halton Hills");
		RetailStore obj3 = new RetailStore("Best Buy, Mississauga");
	
		try {
			obj1.setManager(manager);
			obj2.setManager(manager);
			obj3.setManager(manager);
		}catch (TooManyStoresException e) {
			fail();
		}
		
		obj1.addStock("iPad Pro 12.9", 200);
		obj2.addStock("iPad Pro 12.9", 30);
		obj3.addStock("iPad Pro 12.9", 40);
		
		/*
		 * Return an array containing the names
		 * of stores that have the desired product in stock. 
		 * 
		 * This array should exclude the store object that the method is called upon. 
		 * 
		 * If no other store has the product, return an empty array. 
		 */
		
		String[] result = obj1.findProductsInOtherStores("iPad Pro 12.9"); // the returned array should exclude the name of obj1 
		assertTrue(result.length == 2); 
		assertEquals(result[0], "Best Buy, Halton Hills: 30"); 
		assertEquals(result[1], "Best Buy, Mississauga: 40");
	}

} 