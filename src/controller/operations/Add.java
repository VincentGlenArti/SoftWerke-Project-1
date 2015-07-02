package controller.operations;

import java.util.*;

import model.data.datatypes.*;
import model.storing.DataStorage;
import exceptions.*;

/**
 * IOperation for adding.
 * 
 * @version b.3
 * @author	Boris Gordeev
 * @since 02-07-2015
 */

public class Add implements IOperation {

	@Override
	public final OperationEnum getType() {
		return OperationEnum.Add;
	}
	
	@Override
	public List<Object> perform(DataType targetDataType,
			DataStorage dataStorage, Map<String, Object> additionalParameters)
			throws InvalidArgumentException {
		dataStorage.add(targetDataType);
		return new ArrayList<Object>();
	}
	
}
