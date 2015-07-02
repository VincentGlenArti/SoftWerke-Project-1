package model.data.datatypes.factory;

import java.util.*;

import model.data.datatypes.*;
import controller.operations.*;
import controller.parsing.*;
import exceptions.*;

/**
 * Interface for factories. Factories are half-controller half-model layer
 * classes that delegate getInstance methods of DataType implementations
 * and parse strings into their parameters.
 * 
 * @version b.3
 * @author	Boris Gordeev
 * @since 02-07-2015
 */

public interface IFactory {
	
	/**
	 * Implements rules for creating instance used in provided operation.
	 */
	public DataType produce(Map <String, String> parameters, 
			IOperation requiredOperation, InputController sender)
			throws WrongConsoleInputException, InvalidArgumentException;

}
