package model;

@SuppressWarnings("serial")
public class InsufficientStockException extends Exception {
	public InsufficientStockException(String s) {
		super(s);
	}
}
