package controller.operations;

import java.util.*;
import model.data.datatypes.*;
import model.storing.DataStorage;

/**
 * An interface for operation.
 * If you are going to add any new operations to data storage class, you
 * might would like to make one of those and add it to data storage info.
 * This will make this system recognize your operation automatically.
 * 
 * @version b.2
 * @author	Boris Gordeev
 * @since 29-06-2015
 */

public interface IOperation {

	/**
	 * Returns enum type corresponding to operation the derived class 
	 * implements.
	 */
	public OperationEnum getType();
	
	/**
	 * Performs implemented operation for specified input arguments and returns
	 * formated output.
	 */
	public List<Object> perform(DataType targetDataType,
			DataStorage dataStorage, Map<String, Object> additionalParameters);
	
}
