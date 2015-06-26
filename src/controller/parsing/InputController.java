package controller.parsing;

import java.util.*;
import java.util.regex.Pattern;

import model.storing.DataStorage;
import model.data.datatypes.DataTypeEnum;
import controller.operations.*;

/**
 * Class for parsing user's input and using model's operations according
 * to user's request
 * 
 * @version b.1
 * @author	Boris Gordeev
 * @since 25-06-2015
 */

public class InputController {
	
	private final static List<IOperation> operationsList =
			Collections.unmodifiableList(new ArrayList<IOperation>() {{
				add(new Select());
				add(new Add());
				add(new GetByPK());
			}});
	
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
		if (arguments.length < 2) 
			throw new IllegalArgumentException("Not enough arguments");
			
		
		IOperation operation = getOperation(arguments[0]);
		
		List<String> properties = tryGetProperties(arguments[1]);
		
		Map<String, String> argumentsParsed = parseArguments(arguments, 2, properties);
		
		returnValue = operation.perform(argumentsParsed,
				ParsingTools.parseEnum(arguments[1].split(":")[1],
						DataTypeEnum.class), dataModel);
		
		return returnValue;
	}
	
	private IOperation getOperation(String input) {
		for(IOperation s : operationsList) {
			if (input.equals(s.getType())) return s;
		}
		throw new IllegalArgumentException("Wrong operation");
	}
	
	private List<String> tryGetProperties(String input)
			throws IllegalArgumentException {
		String[] split = input.split(":");
		if (split.length != 2 || !split[0].equals("dataType"))
			throw new IllegalArgumentException("Wrong token at " + split[0]);
		
		DataTypeEnum dataType = ParsingTools.parseEnum(input, DataTypeEnum.class);
		
		List<String> returnValue = dataModel.getPropertyNamesList(dataType);
		
		if(returnValue == null)
			throw new IllegalArgumentException("Wrong data type");
		
		if(returnValue.get(1).equals("No properties info available"))
			throw new IllegalArgumentException("Wrong data type");
		
		return returnValue;
	}
	
	private Map<String, String> parseArguments(String[] arguments, int startIndex,
			List<String> dataTypeArguments) throws IllegalArgumentException {
		Map<String, String> returnValue = new HashMap<String, String>();
		String[] split;
		
		for(int i = startIndex; i < arguments.length; i++) {
			if (!Pattern.matches("[a-zA-Z]:[a-z0-9A-Z/]", arguments[i]))
				throw new IllegalArgumentException("Wrong token at " + arguments[i]);
			split = arguments[i].split(":");
			
			for(int j = 0; j < dataTypeArguments.size(); j++) {
				
				if (dataTypeArguments.get(j).equals(split[0]) ||
						split[0].equals("PK") ||
						split[0].equals("sort")) {
					if (returnValue.containsKey(split[0]))
						throw new IllegalArgumentException(
								"Duplicate token at " + arguments[i]);
					returnValue.put(split[0], split[1]);
					break;
				}
				
				throw new IllegalArgumentException(
						"Unknown token at " + arguments[i]);
			}
		}
		
		return returnValue;
	}
}
