package controller.operations;

import java.util.*;

import model.data.datatypes.*;
import model.storing.DataStorage;
import exceptions.*;

/**
 * IOperation for "Select" - an operation that searches for data types
 * that soft-match specified instance.
 * 
 * @version b.3
 * @author	Boris Gordeev
 * @since 02-07-2015
 */

public class Select implements IOperation {
	
	@Override
	public final OperationEnum getType() {
		return OperationEnum.Select;
	}
	
	@Override
	public List<Object> perform(DataType targetDataType,
			DataStorage dataStorage, Map<String, Object> additionalParameters)
			throws InvalidArgumentException {
		List<Object> returnValue = new ArrayList<Object>();
		
		if (additionalParameters.containsKey("comparator")) {
			returnValue.addAll(dataStorage.select(targetDataType,
					(Comparator<Object>)additionalParameters.get("comparator")));
		} else {
			returnValue.addAll(dataStorage.select(targetDataType));
		}
		
		return returnValue;
	}

}
