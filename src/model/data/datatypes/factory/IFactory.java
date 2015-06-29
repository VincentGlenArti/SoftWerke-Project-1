package model.data.datatypes.factory;

import java.util.*;
import model.data.datatypes.*;
import controller.operations.*;
import controller.parsing.*;

/**
 * Interface for factories. Factories are half-controller half-model layer
 * classes that delegate getInstance methods of DataType implementations
 * and parse strings into their parameters.
 * 
 * @version b.2
 * @author	Boris Gordeev
 * @since 29-06-2015
 */

public interface IFactory {
	
	/**
	 * Implements rules for creating instance used in provided operation.
	 */
	public DataType produce(Map <String, String> parameters, 
			IOperation requiredOperation, InputController sender);

}
