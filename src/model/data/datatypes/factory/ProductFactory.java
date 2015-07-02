package model.data.datatypes.factory;

import java.util.*;

import controller.operations.*;
import controller.parsing.*;
import model.data.auxiliary.*;
import model.data.datatypes.*;
import exceptions.*;

/**
 * IFactory implementation for "Product" data type.
 * 
 * @version b.3
 * @author	Boris Gordeev
 * @since 02-07-2015
 */

public class ProductFactory implements IFactory {
	
	@Override
	public DataType produce(Map<String, String> parameters, 
			IOperation requiredOperation, InputController sender)
				throws WrongConsoleInputException, InvalidArgumentException {
		boolean generateID = false;
		if (requiredOperation.getType().equals(OperationEnum.Add)) {
			generateID = true;
		}
		
		return Product.getInstance(generateID,
				(generateID ? null : 
					ParsingTools.parseLong(parameters.get("ID"))),
				parameters.get("manufacturerName"), 
				ParsingTools.parseEnum(parameters.get("productType"), 
						ProductType.class), 
				parameters.get("modelName"),
				ParsingTools.parseEnum(parameters.get("color"),
						ColorDiscrete.class),
				ParsingTools.parseDate(parameters.get("releaseDate")));
	}
}
