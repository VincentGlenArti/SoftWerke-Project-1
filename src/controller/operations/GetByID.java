package controller.operations;

import java.util.*;

import model.data.datatypes.*;
import model.storing.DataStorage;

/**
 * IOperation for "get by primary key" - an operation that searches for a
 * single instance of the specified data type that has a specified ID.
 * 
 * @version b.2
 * @author	Boris Gordeev
 * @since 29-06-2015
 */

public class GetByID implements IOperation {

	@Override
	public final OperationEnum getType() {
		return OperationEnum.GetByID;
	}
	
	@Override
	public List<Object> perform(DataType targetDataType,
			DataStorage dataStorage, Map<String, Object> additionalParameters) {
		List<Object> returnValue = new ArrayList<Object>();
		
		DataType result = dataStorage.getByID(targetDataType);
		
		if (result != null) {
			returnValue.add(result);	
		}
				
		return returnValue;
	}
	
}
