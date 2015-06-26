package controller.parsing;

import java.util.Calendar;

public abstract class ParsingTools {
	
	public static <E extends Enum<E>> E parseEnum(String input, Class<E> targetEnum) {
		E returnValue = null;
		try {
			returnValue = Enum.valueOf(targetEnum, input);
		} catch (Exception e) {
			throw new IllegalArgumentException("Unrecognised enum " + input);
		}
		return returnValue;
	}
	
	/**
	 * Checks if contains an integer.
	 */
	public static boolean tryParseInt(String value) {  
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
	public static Calendar parseDate(String date) throws IllegalArgumentException{
		if (date == null) return null;
		
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
