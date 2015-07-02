package controller.operations;

import java.util.*;

import model.data.datatypes.*;
import model.storing.DataStorage;
import exceptions.*;

/**
 * IOperation for "get by primary key" - an operation that searches for a
 * single instance of the specified data type that has a specified ID.
 * 
 * @version b.3
 * @author	Boris Gordeev
 * @since 02-07-2015
 */

public class GetByID implements IOperation {

	@Override
	public final OperationEnum getType() {
		return OperationEnum.GetByID;
	}
	
	@Override
	public List<Object> perform(DataType targetDataType,
			DataStorage dataStorage, Map<String, Object> additionalParameters)
			throws InvalidArgumentException{
		List<Object> returnValue = new ArrayList<Object>();
		
		DataType result = dataStorage.getByID(targetDataType);
		
		if (result != null) {
			returnValue.add(result);	
		}
				
		return returnValue;
	}
	
}
