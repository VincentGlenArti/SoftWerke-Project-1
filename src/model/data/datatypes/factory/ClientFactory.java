package model.data.datatypes.factory;

import java.util.*;

import controller.operations.*;
import controller.parsing.InputController;
import controller.parsing.ParsingTools;
import model.data.datatypes.*;
import exceptions.*;

/**
 * IFactory implementation for "Client" data type.
 * 
 * @version b.3
 * @author	Boris Gordeev
 * @since 02-07-2015
 */

public class ClientFactory implements IFactory {
	
	@Override
	public DataType produce(Map <String, String> parameters, 
			IOperation requiredOperation, InputController sender)
			throws WrongConsoleInputException, InvalidArgumentException {
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
