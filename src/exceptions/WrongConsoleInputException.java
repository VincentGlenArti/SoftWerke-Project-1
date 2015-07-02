package exceptions;

/**
 * Exception that is thrown in case of console input cannot be recognized and
 * parsed.
 * 
 * @version b.3
 * @author	Boris Gordeev
 * @since 02-07-2015
 */

public class WrongConsoleInputException extends BusinessLogicException {
	
	private WrongConsoleInputException() {
		super();
	}
	
	public WrongConsoleInputException(String message) {
		super("Wrong console input: " + message);
	}
}
