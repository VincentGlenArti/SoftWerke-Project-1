package controller.datatypeproduction;

import java.util.*;

import model.data.auxiliary.ColorDiscrete;
import model.data.auxiliary.ProductType;
import model.data.comparators.*;
import model.data.datatypes.*;
import controller.parsing.*;

public class DataTypeFactory {
	
	public DataTypeFactory() {}
	
	public DataType produceDataType(Map<String, String> arguments, 
			DataTypeEnum targetDataType) {
		
		if(targetDataType == DataTypeEnum.Client)
			return produceClient(arguments);
		
		if(targetDataType == DataTypeEnum.Product)
			return produceProduct(arguments);
		
		if(targetDataType == DataTypeEnum.Order)
			return produceOrder(arguments);
		
		return null;
	}
	
	private DataType produceClient(Map<String, String> arguments) {
		return new Client(arguments.get("name"),
					arguments.get("lastName"),
					ParsingTools.parseDate(arguments.get("birthDate")),
					arguments.get("pk") == null ? null : Integer.getInteger(arguments.get("pk")));
	}
	
	private DataType produceProduct(Map<String, String> arguments) {
		return new Product(arguments.get("pk") == null ? null : Integer.getInteger(arguments.get("pk")),
				arguments.get("manufacturerName"),
				ParsingTools.parseEnum(arguments.get("productType"), ProductType.class),
				arguments.get("modelName"),
				ParsingTools.parseEnum(arguments.get("color"), ColorDiscrete.class),
				ParsingTools.parseDate(arguments.get("releaseDate"))
				);
	}
	
	private DataType produceOrder(Map<String, String> arguments) {
		return null;
	}
	
	public Comparator<Object> produceComparator(String type, 
			DataTypeEnum targetDataType) {
		return null;
	}
	
}
