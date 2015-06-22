package model.data;

import java.util.*;

/**
 * Data class for describing client.
 * 
 * @version a.2
 * @author	Boris Gordeev
 * @since 22-06-2015
 */

public class Client {
	
	private static int globalID = 0;
	
	private int id;
	private String name;
	private String lastName;
	private List<Order> orders;
	private Calendar birthDate;
	
	public Client(String name, String lastName, Calendar birthDate) {
		this.name = name;
		this.lastName = lastName;
		this.birthDate = birthDate;
		orders = new ArrayList<Order>();
		id = globalID;
		globalID++;
	}
	
	public int getID() {
		return id;
	}
	
	public Calendar getBirthDate() {
		return birthDate;
	}

	public String getLastName() {
		return lastName;
	}

	public String getName() {
		return name;
	}
	
	public List<Order> getOrders() {
		return orders;
	}
	
	@Override
	public String toString() {
		StringBuilder returnValue = new StringBuilder("");
		returnValue.append(id + ": " + name + " " + lastName + " " +
		birthDate.toString() + ", orders:" + System.lineSeparator());
		for(int i = 0; i < orders.size(); i++) {
			returnValue.append(orders.get(i).getID() + System.lineSeparator());
		}
		return returnValue.toString();
	}

}
