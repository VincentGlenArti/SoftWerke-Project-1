package model.data.datatypes.factory;

import java.util.*;

import controller.operations.*;
import controller.parsing.InputController;
import controller.parsing.ParsingTools;
import model.data.datatypes.*;

/**
 * IFactory implementation for "Client" data type.
 * 
 * @version b.2
 * @author	Boris Gordeev
 * @since 29-06-2015
 */

public class ClientFactory implements IFactory {
	
	@Override
	public DataType produce(Map <String, String> parameters, 
			IOperation requiredOperation, InputController sender)
			throws IllegalArgumentException {
		boolean generateID = false;
		if (requiredOperation.getType().equals(OperationEnum.Add)) {
			generateID = true;
		}
		
		return Client.getInstance(generateID, 
				parameters.get("name"), 
				parameters.get("lastName"),
				ParsingTools.parseDate(parameters.get("birthDate")),
				(generateID ? null : 
					ParsingTools.parseLong(parameters.get("ID"))));
	}
}
