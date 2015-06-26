package controller.operations;

import java.util.*;

import model.data.datatypes.*;
import model.storing.DataStorage;
import controller.datatypeproduction.*;

public class Select implements IOperation {
	
	@Override
	public final OperationEnum getType() {
		return OperationEnum.Select;
	}
	
	@Override
	public List<Object> perform(Map<String, String> mappedArguments, 
			DataTypeEnum targetDataType, DataStorage dataStorage) {
		List<Object> returnValue = new ArrayList<Object>();
		DataTypeFactory factory = new DataTypeFactory();
		
		if (mappedArguments.containsKey("sort")) {
			returnValue.addAll(dataStorage.select(factory.produceDataType(mappedArguments,
					targetDataType),factory.produceComparator(mappedArguments.get("sort"),
							targetDataType)));
		} else {
			returnValue.addAll(dataStorage.select(factory.produceDataType(mappedArguments,
					targetDataType)));
		}
		
		return returnValue;
	}

}
