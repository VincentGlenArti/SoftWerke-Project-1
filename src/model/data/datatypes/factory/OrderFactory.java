package model.data.datatypes.factory;

import java.util.*;

import controller.operations.*;
import controller.parsing.*;
import model.data.datatypes.*;
import model.data.auxiliary.*;

/**
 * IFactory implementation for "Order" data type.
 * 
 * @version b.2
 * @author	Boris Gordeev
 * @since 29-06-2015
 */

public class OrderFactory implements IFactory {
	
	@Override
	public DataType produce(Map<String, String> parameters, 
			IOperation requiredOperation, InputController sender)
			throws IllegalArgumentException {
		boolean generateID = false;
		if (requiredOperation.getType().equals(OperationEnum.Add)) {
			generateID = true;
		}
		
		Client orderClient = null;
		if(ParsingTools.tryParseLong(parameters.get("client"))) {
			List<Object> searchForClient = sender.modelOperation("GetByID dataType:Client ID:" + 
					ParsingTools.parseLong(parameters.get("client")));
			if (!searchForClient.isEmpty()) {
				orderClient = (Client)searchForClient.get(0);
			} else {
				throw new IllegalArgumentException("Wrong client ID");
			}
		}
		
		Order returnValue = Order.getInstance(generateID, 
				(generateID ? null : 
					ParsingTools.parseLong(parameters.get("ID"))),
					orderClient,
					ParsingTools.parseDate(parameters.get("dateTimeMade")),
					ParsingTools.parseServices(parameters.get("services"), 
							sender));
		
		if(generateID) {
			((Client)returnValue.getClient()).
				getOrders().add(returnValue);
		}
		
		return returnValue;
	}
	
}
