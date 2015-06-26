package controller.operations;

import java.util.*;
import model.data.datatypes.DataTypeEnum;
import model.storing.DataStorage;

public interface IOperation {

	public OperationEnum getType();
	
	public List<Object> perform(Map<String, String> mappedArguments, 
			DataTypeEnum targetDataType, DataStorage dataStorage);
	
}
