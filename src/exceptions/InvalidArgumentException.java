package exceptions;

/**
 * Exception that is thrown in case of input argument can be parsed but
 * is invalid (e.g. id of nonexistent data type).
 * 
 * @version b.3
 * @author	Boris Gordeev
 * @since 02-07-2015
 */

public class InvalidArgumentException extends Exception {
	
	private InvalidArgumentException() {
		super();
	}
	
	public InvalidArgumentException(String message) {
		super("Wrong data passed: " + message);
	}

}
