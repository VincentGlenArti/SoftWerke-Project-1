package controller.parsing;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import model.data.auxiliary.*;
import model.data.datatypes.IService;
import exceptions.*;

/**
 * A "static" class that contains only useful boilerplate functions for
 * parsing strings into specific data. Captures standard exceptions and
 * instead returns ones that would make sense to user.
 * 
 * @version b.3
 * @author	Boris Gordeev
 * @since 02-07-2015
 */

public final class ParsingTools {
	
	/**
	 * Parses string to an instance of specified enumeration. Returns null
	 * if null input specified and throws an exception if input is
	 * unrecognized.
	 */
	public static <E extends Enum<E>> E parseEnum(String input, Class<E> targetEnum) 
		throws WrongConsoleInputException {
		if (input == null) return null;
		E returnValue = null;
		try {
			returnValue = Enum.valueOf(targetEnum, input);
		} catch (Exception e) {
			throw new WrongConsoleInputException("unrecognised token " + input +
					" of type " + targetEnum.getName());
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
		throws WrongConsoleInputException {
		if (value == null) return null;
		if (tryParseLong(value)) { 
			return Long.parseLong(value); 
		}
		else { 
			throw new WrongConsoleInputException(value + " is not a number"); 
		}
	}
	
	/**
	 * Parses year/month/day notation into "Calendar" object.
	 */
	public static Date parseDate(String date) 
			throws WrongConsoleInputException {
		if (date == null) return null;
		Date returnValue = null;
		
		String[] split = date.split("/");
		if (split.length != 3)
			throw new WrongConsoleInputException("Wrong date format");
		for(int i = 0; i < 3; i++) {
			if (!tryParseLong(split[i]))
				throw new WrongConsoleInputException("Wrong date format");
		}
		
		try {
			SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy/MM/dd'T'HH:mm:ss");
	    	isoFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
	    	returnValue = isoFormat.parse(date + "T00:00:00");
		} catch (ParseException pe) {
			throw new WrongConsoleInputException("Wrong date format");
		}
	
		return returnValue;
	}
	
	public static List<ServiceAmountTuple> parseServices(String input,
			InputController sender) 
				throws WrongConsoleInputException, InvalidArgumentException {
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
					throw new WrongConsoleInputException("Wrong syntax at" 
								+ service);
				}
				serviceAmmount = service.substring(1).split("-");
				id = parseLong(serviceAmmount[0]);
				amount = parseLong(serviceAmmount[1]).intValue();
				
				searchResult =
						sender.modelOperation("GetByID dataType:Product ID:" + id);
				if(searchResult.isEmpty()) {
					throw new WrongConsoleInputException("Service " + service + 
							" not recognized");
				}
				
				returnValue.add(new ServiceAmountTuple(
						(IService)searchResult.get(0), amount));
			}
		}
		return returnValue;
	}

}
