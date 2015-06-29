package controller.parsing;

import java.util.*;

import model.data.auxiliary.*;
import model.data.datatypes.IService;

import java.util.regex.Pattern;

/**
 * A "static" class that contains only useful boilerplate functions for
 * parsing strings into specific data. Captures standard exceptions and
 * instead returns ones that would make sense to user.
 * 
 * @version b.2
 * @author	Boris Gordeev
 * @since 29-06-2015
 */

public final class ParsingTools {
	
	/**
	 * Parses string to an instance of specified enumeration. Returns null
	 * if null input specified and throws an exception if input is
	 * unrecognized.
	 */
	public static <E extends Enum<E>> E parseEnum(String input, Class<E> targetEnum) {
		if (input == null) return null;
		E returnValue = null;
		try {
			returnValue = Enum.valueOf(targetEnum, input);
		} catch (Exception e) {
			throw new IllegalArgumentException("Unrecognised enum " + input);
		}
		return returnValue;
	}
	
	/**
	 * Checks if contains a decimal number.
	 */
	public static boolean tryParseLong(String value) {  
	    try {  
	        Long.parseLong(value);  
	        return true;  
	    } catch(NumberFormatException nfe) {  
	        return false;  
	    }  
	}
	
	/**
	 * Return a decimal number if correct input string specified,
	 * throws an exception if incorrect input string specified,
	 * returns null if null specified.
	 */
	public static Long parseLong(String value)
		throws IllegalArgumentException {
		if (value == null) return null;
		if (tryParseLong(value)) { 
			return Long.parseLong(value); 
		}
		else { 
			throw new IllegalArgumentException(value + " is not a number"); 
		}
	}
	
	/**
	 * Parses year/month/day notation into "Calendar" object.
	 */
	public static Date parseDate(String date) throws IllegalArgumentException{
		if (date == null) return null;
		
		Date returnValue = new Date();
		
		String[] split = date.split("/");
		if (split.length != 3)
			throw new IllegalArgumentException("Wrong date format");
		for(int i = 0; i < 3; i++) {
			if (!tryParseLong(split[i]))
				throw new IllegalArgumentException("Wrond date format");
		}
		
		returnValue.setYear(Integer.parseInt(split[0]) - 1900);
		returnValue.setMonth(Integer.parseInt(split[1]));
		returnValue.setDate(Integer.parseInt(split[2]));
		returnValue.setSeconds(0);
		returnValue.setMinutes(0);
		returnValue.setHours(0);
		
		return returnValue;
	}
	
	public static List<ServiceAmountTuple> parseServices(String input,
			InputController sender) 
			throws IllegalArgumentException {
		List<ServiceAmountTuple> returnValue = 
				new ArrayList<ServiceAmountTuple>();
		List<Object> searchResult;
		int amount;
		long id;
		if(input != null) {
			String[] serviceAmmount;
			String[] split = input.split("/");
			for(String service : split) {
				if(!service.matches("[p][0-9]+[-][0-9]+")) {
					throw new IllegalArgumentException("Wrong syntax at" 
								+ service);
				}
				serviceAmmount = service.substring(1).split("-");
				id = parseLong(serviceAmmount[0]);
				amount = parseLong(serviceAmmount[1]).intValue();
				
				searchResult =
						sender.modelOperation("GetByID dataType:Product ID:" + id);
				if(searchResult.isEmpty()) {
					throw new IllegalArgumentException("Service " + service + 
							" not recognized");
				}
				
				returnValue.add(new ServiceAmountTuple(
						(IService)searchResult.get(0), amount));
			}
		}
		return returnValue;
	}

}
