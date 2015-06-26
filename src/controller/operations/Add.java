package controller.operations;

import java.util.*;

import model.data.datatypes.*;
import model.storing.DataStorage;
import controller.datatypeproduction.*;

public class Add implements IOperation {

	@Override
	public final OperationEnum getType() {
		return OperationEnum.Select;
	}
	
	@Override
	public List<Object> perform(Map<String, String> mappedArguments, 
			DataTypeEnum targetDataType, DataStorage dataStorage) {
		DataTypeFactory factory = new DataTypeFactory();
		dataStorage.add(factory.produceDataType(mappedArguments, targetDataType));
		return new ArrayList<Object>();
	}
	
}
