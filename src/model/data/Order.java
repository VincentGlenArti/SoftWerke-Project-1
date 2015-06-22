package model.data;

import java.util.*;

/**
 * Data class for describing order.
 * 
 * @version a.2
 * @author	Boris Gordeev
 * @since 22-06-2015
 */

public class Order {
	
	private static int globalID = 0;
	
	private int userID;
	private int id;
	private Calendar dateTimeMade;
	private List<ServiceAmountTuple> services;
	
	public Order(int userID, List<ServiceAmountTuple> services) {
		this.userID = userID;
		dateTimeMade = Calendar.getInstance();
		this.services = services;
		id = globalID;
		globalID++;
	}

	public List<ServiceAmountTuple> getServices() {
		return services;
	}

	public Calendar getDateTimeMade() {
		return dateTimeMade;
	}
	
	public int getUserID() {
		return userID;
	}
	
	public int getID() {
		return id;
	}
	
	@Override
	public String toString() {
		StringBuilder returnValue = new StringBuilder();
		returnValue.append(id + " : " + userID + " " + dateTimeMade.toString() +
				", services:" + System.lineSeparator());
		for(int i = 0; i < services.size(); i++) {
			returnValue.append(services.get(i).getService().getType().toString() + 
					": " + services.get(i).getService().toString() +
					" " + services.get(i).getAmount());
		}
		return returnValue.toString();
	}
}
