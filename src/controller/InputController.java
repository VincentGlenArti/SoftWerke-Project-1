package controller;

import java.util.*;

import model.Model;

/**
 * Class for parsing user's input and using model's operations according
 * to user's request
 * 
 * @version a.2
 * @author	Boris Gordeev
 * @since 22-06-2015
 */

public class InputController {
	
	private static String selectOperation = "select";
	private static String addOperation = "add";
	private static String dataType = "dataType";
	
	private Model dataModel;
	
	public InputController(Model dataModel) {
		this.dataModel = dataModel;
	}
	
	/**
	 * Parses input in command line syntax
	 */
	public List<Object> modelOperation(String commandLineInput) {
		List<Object> returnValue = new ArrayList<Object>();
		
		String[] arguments = commandLineInput.split(" ");
		if (arguments.length < 1) 
			throw new IllegalArgumentException("Not enough arguments");
			
		String operation = arguments[0];
		
		String[] split = arguments[1].split(":");
		if (split.length != 2 || !split[0].equals(dataType))
			throw new IllegalArgumentException("Wrong token at " + split[0]);
		
		List<String> attributes = dataModel.getAttributeList(split[1]);
		if (attributes == null)
			throw new IllegalArgumentException("Wrong token at " + arguments[1]);
		
		HashMap<String, Object> attributeTable = new HashMap<String, Object>();
		attributeTable.put(split[0], split[1]);
		
		for(int i = 2; i < arguments.length; i++) {
			split = arguments[i].split(":");
			if (split.length != 2)
				throw new IllegalArgumentException("Wrong token at " + split[0]);
			for(int j = 0; j < attributes.size(); j++) {
				if (attributes.get(j).equals(split[0])) {
					if (attributeTable.containsKey(split[0]))
						throw new IllegalArgumentException("Duplicate attribute at " +
								split[0]);
					if (split[0].toLowerCase().contains("id")) {
					if (tryParseInt(split[1])) {
						attributeTable.put(split[0], Integer.parseInt(split[1])); }
					} else if (split[0].toLowerCase().contains("date")) {
						attributeTable.put(split[0], parseDate(split[1]));
					} else {
						attributeTable.put(split[0], split[1]);
					}
				}
			}
		}
		
		if (operation.equals(selectOperation)) {
			returnValue = dataModel.select(attributeTable);
		} else if (operation.equals(addOperation)) {
			dataModel.add(attributeTable);
		} else {
			throw new IllegalArgumentException("Unknown operation");
		}
		
		return returnValue;
	}
	
	/**
	 * Checks if contains an integer.
	 */
	private boolean tryParseInt(String value) {  
	     try {  
	         Integer.parseInt(value);  
	         return true;  
	      } catch(NumberFormatException nfe) {  
	          return false;  
	      }  
	}
	
	/**
	 * Parses year/month/day notation into "Calendar" object.
	 */
	private Calendar parseDate(String date) {
		Calendar returnValue = Calendar.getInstance();
		
		String[] split = date.split("/");
		if (split.length != 3)
			throw new IllegalArgumentException("Wrong date format");
		for(int i = 0; i < 3; i++) {
			if (!tryParseInt(split[i]))
				throw new IllegalArgumentException("Wrond date format");
		}
		
		returnValue.set(Integer.parseInt(split[0]), Integer.parseInt(split[1]) - 1,
				Integer.parseInt(split[2]));
		
		return returnValue;
	}
}
