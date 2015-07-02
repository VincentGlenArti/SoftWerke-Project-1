package exceptions;

/**
 * Abstract class for inner logic exceptions.
 * 
 * @version b.3
 * @author	Boris Gordeev
 * @since 02-07-2015
 * 
 */

public abstract class BusinessLogicException extends Exception {

	public BusinessLogicException() {
		super();
	}
	
	public BusinessLogicException(String message) {
		super(message);
	}
}
