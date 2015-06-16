package model.data;

import java.util.*;

public class Order {
	
	private static int globalID = 0;
	
	private int userID;
	private int id;
	private Calendar dateTimeMade;
	private List<Tuple> services;
	
	public Order(int userID, List<Tuple> services) {
		this.userID = userID;
		dateTimeMade = Calendar.getInstance();
		this.services = services;
		id = globalID;
		globalID++;
	}

	public List<Tuple> getServices() {
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
		StringBuilder target = new StringBuilder();
		target.append(id + " : " + userID + " " + dateTimeMade.toString() +
				", services:%n");
		for(int i = 0; i < services.size(); i++) {
			target.append(services.get(i).getService().getType().toString() + 
					": " + services.get(i).getService().toString() +
					" " + services.get(i).getAmount());
		}
		return target.toString();
	}
}
