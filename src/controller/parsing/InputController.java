package controller.parsing;

import java.util.*;
import java.util.regex.Pattern;

import model.storing.*;
import model.data.datatypes.*;
import controller.operations.*;
import exceptions.*;

/**
 * Class for parsing user's input and using model's operations according
 * to user's request
 * 
 * @version b.3
 * @author	Boris Gordeev
 * @since 02-07-2015
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
			throws WrongConsoleInputException, InvalidArgumentException {
		List<Object> returnValue = new ArrayList<Object>();
		
		String[] arguments = commandLineInput.split(" ");
		if (arguments.length < 2) {
			throw new WrongConsoleInputException("Not enough arguments");
		}
			
		Map<String, Object> additionalParameters =
				new HashMap<String, Object>();
		
		IOperation operation = getOperation(arguments[0]);
		
		if (!arguments[1].split(":")[0].equals("dataType") ||
				(arguments[1].split(":").length != 2 )) {
			throw new WrongConsoleInputException(arguments[1].split(":")[0] +
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
	 * Addresses data storage info to get names of all operations available.
	 */
	public List<String> getOperationsNameList() {
		List<String> returnValue = new ArrayList<String>();
		for(OperationEnum operation : DataStorageInfo.operationsUsed.keySet()) {
			returnValue.add(operation.toString());
		}
		return returnValue;
	}
	
	/**
	 * Addresses data storage info to get names of all data types available.
	 */
	public List<String> getDataTypesNameList() {
		List<String> returnValue = new ArrayList<String>();
		for(DataTypeEnum dataType : DataStorageInfo.dataTypesUsed.keySet()) {
			returnValue.add(dataType.toString());
		}
		return returnValue;
	}
	
	/**
	 * Addresses data storage info to get names of all properties you can
	 * pass as arguments to specified data type.
	 */
	public List<String> getDataTypeArgumentsNameList(String commandLineInput)
		throws WrongConsoleInputException {
		String[] split = commandLineInput.split(" ");
		if (split.length != 2) {
			throw new WrongConsoleInputException(
					"Expected data type declaration");
		}
		DataTypeEnum requestedDataType = ParsingTools.
				parseEnum(split[1], DataTypeEnum.class);
		
		List<String> returnValue = DataStorageInfo.dataTypesUsed.
				get(requestedDataType).getPropertyNamesAsList();
		
		return returnValue;
	}
	
	/**
	 * Searches specified DataType class for having specified comparator.
	 */
	private Comparator<Object> getComparator(String comparableProperty, 
			DataTypeEnum comparatorDataType) throws InvalidArgumentException {
		Comparator<Object> search = DataStorageInfo.
				dataTypesUsed.get(comparatorDataType).
				getComparatorsMap().get(comparableProperty);
		
		if(search == null) {
			throw new InvalidArgumentException("Wrong comparator");
		}
		
		return search;
	}
	
	/**
	 * Searches active DataStorage for having specified operation.
	 */
	private IOperation getOperation(String input)
		throws WrongConsoleInputException, InvalidArgumentException {
		
		IOperation returnValue = null;
		
		OperationEnum targetOperation = ParsingTools.parseEnum(input,
				OperationEnum.class);
		
		returnValue = DataStorageInfo.operationsUsed.get(targetOperation);
		
		if(returnValue == null) {
			throw new InvalidArgumentException("Wrong operation");
		}
		return returnValue;
	}
	
	/**
	 * Return list of property names for an implementation of DataType
	 * with specified class name.
	 */
	private List<String> tryGetProperties(DataTypeEnum dataTypeUsed)
			throws InvalidArgumentException {
		
		List<String> returnValue = DataStorageInfo.dataTypesUsed.
				get(dataTypeUsed).getPropertyNamesAsList();
		
		if(returnValue == null) {
			throw new InvalidArgumentException("Wrong data type");
		}
		
		if(returnValue.get(1).equals("No properties info available")) {
			throw new InvalidArgumentException("Wrong data type");
		}
		
		return returnValue;
	}
	
	/**
	 * Parses an array of arguments.
	 */
	private Map<String, String> parseArguments(String[] arguments, int startIndex,
			List<String> dataTypeArguments) throws WrongConsoleInputException {
		Map<String, String> returnValue = new HashMap<String, String>();
		String[] split;
		boolean argumentMetInCycle = false;
		
		for(int i = startIndex; i < arguments.length; i++) {
			if (!Pattern.matches("[a-zA-Z]+[:][a-z0-9A-Z/-]+", arguments[i])) {
				throw new WrongConsoleInputException("Wrong token at " + arguments[i]);
			}
			split = arguments[i].split(":");
			argumentMetInCycle = false;
			
			for(String argument : dataTypeArguments) {
				
				if (argument.equals(split[0]) ||
						split[0].equals("ID") ||
						split[0].equals("sort")) {
					if (returnValue.containsKey(split[0])) {
						throw new WrongConsoleInputException(
								"Duplicate token at " + arguments[i]);
					}
					returnValue.put(split[0], split[1]);
					argumentMetInCycle = true;
					break;
				}
			}
			
			if(!argumentMetInCycle) {
				throw new WrongConsoleInputException(
					"Unknown token at " + arguments[i]);
			}
		}
		
		return returnValue;
	}
}
