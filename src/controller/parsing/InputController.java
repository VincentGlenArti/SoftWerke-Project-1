package controller.parsing;

import java.util.*;
import java.util.regex.Pattern;

import model.storing.*;
import model.data.datatypes.*;
import controller.operations.*;

/**
 * Class for parsing user's input and using model's operations according
 * to user's request
 * 
 * @version b.2
 * @author	Boris Gordeev
 * @since 29-06-2015
 */

public class InputController {
	
	private DataStorage dataModel;
	
	public InputController(DataStorage dataModel) {
		this.dataModel = dataModel;
	}
	
	/**
	 * Parses input in command line syntax
	 */
	public List<Object> modelOperation(String commandLineInput)
		throws IllegalArgumentException {
		List<Object> returnValue = new ArrayList<Object>();
		
		String[] arguments = commandLineInput.split(" ");
		if (arguments.length < 2) {
			throw new IllegalArgumentException("Not enough arguments");
		}
			
		Map<String, Object> additionalParameters =
				new HashMap<String, Object>();
		
		IOperation operation = getOperation(arguments[0]);
		
		if (!arguments[1].split(":")[0].equals("dataType") ||
				(arguments[1].split(":").length != 2 )) {
			throw new IllegalArgumentException(arguments[1].split(":")[0] +
					" : dataType specification was expected");
		}
		
		DataTypeEnum dataTypeUsed = ParsingTools.
				parseEnum(arguments[1].split(":")[1], DataTypeEnum.class);
		
		List<String> properties = tryGetProperties(dataTypeUsed);
		
		Map<String, String> argumentsParsed = parseArguments(arguments,
				2,
				properties);
		
		if(argumentsParsed.containsKey("sort")) {
			additionalParameters.put("comparator", 
					getComparator(argumentsParsed.get("sort"),
							dataTypeUsed));
		}
		
		DataType operationDataType = DataStorageInfo.
				dataTypesUsed.get(dataTypeUsed).getFactory().
						produce(argumentsParsed, operation, this);
		
		returnValue = operation.perform(
				operationDataType,
				dataModel,
				additionalParameters);
		
		return returnValue;
	}
	
	/**
	 * Searches specified DataType class for having specified comparator.
	 */
	private Comparator<Object> getComparator(String comparableProperty, 
			DataTypeEnum comparatorDataType)
			throws IllegalArgumentException{
		Comparator<Object> search = DataStorageInfo.
				dataTypesUsed.get(comparatorDataType).
				getComparatorsMap().get(comparableProperty);
		
		if(search == null) {
			throw new IllegalArgumentException("Wrong comparator");
		}
		
		return search;
	}
	
	/**
	 * Searches active DataStorage for having specified operation.
	 */
	private IOperation getOperation(String input)
		throws IllegalArgumentException {
		
		IOperation returnValue = null;
		
		OperationEnum targetOperation = ParsingTools.parseEnum(input,
				OperationEnum.class);
		
		returnValue = DataStorageInfo.operationsUsed.get(targetOperation);
		
		if(returnValue == null) {
			throw new IllegalArgumentException("Wrong operation");
		}
		return returnValue;
	}
	
	/**
	 * Return list of property names for an implementation of DataType
	 * with specified class name.
	 */
	private List<String> tryGetProperties(DataTypeEnum dataTypeUsed)
			throws IllegalArgumentException {
		
		List<String> returnValue = DataStorageInfo.dataTypesUsed.
				get(dataTypeUsed).getPropertyNamesAsList();
		
		if(returnValue == null) {
			throw new IllegalArgumentException("Wrong data type");
		}
		
		if(returnValue.get(1).equals("No properties info available")) {
			throw new IllegalArgumentException("Wrong data type");
		}
		
		return returnValue;
	}
	
	/**
	 * Parses an array of arguments.
	 */
	private Map<String, String> parseArguments(String[] arguments, int startIndex,
			List<String> dataTypeArguments) throws IllegalArgumentException {
		Map<String, String> returnValue = new HashMap<String, String>();
		String[] split;
		boolean argumentMetInCycle = false;
		
		for(int i = startIndex; i < arguments.length; i++) {
			if (!Pattern.matches("[a-zA-Z]+[:][a-z0-9A-Z/-]+", arguments[i])) {
				throw new IllegalArgumentException("Wrong token at " + arguments[i]);
			}
			split = arguments[i].split(":");
			argumentMetInCycle = false;
			
			for(String argument : dataTypeArguments) {
				
				if (argument.equals(split[0]) ||
						split[0].equals("ID") ||
						split[0].equals("sort")) {
					if (returnValue.containsKey(split[0])) {
						throw new IllegalArgumentException(
								"Duplicate token at " + arguments[i]);
					}
					returnValue.put(split[0], split[1]);
					argumentMetInCycle = true;
					break;
				}
			}
			
			if(!argumentMetInCycle) {
				throw new IllegalArgumentException(
					"Unknown token at " + arguments[i]);
			}
		}
		
		return returnValue;
	}
}
