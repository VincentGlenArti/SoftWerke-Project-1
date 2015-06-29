package controller.operations;

import java.util.*;

import model.data.datatypes.*;
import model.storing.DataStorage;

/**
 * IOperation for adding.
 * 
 * @version b.2
 * @author	Boris Gordeev
 * @since 29-06-2015
 */

public class Add implements IOperation {

	@Override
	public final OperationEnum getType() {
		return OperationEnum.Add;
	}
	
	@Override
	public List<Object> perform(DataType targetDataType,
			DataStorage dataStorage, Map<String, Object> additionalParameters) {
		dataStorage.add(targetDataType);
		return new ArrayList<Object>();
	}
	
}
