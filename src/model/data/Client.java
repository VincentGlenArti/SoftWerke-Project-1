package model.data;

import java.util.*;

public class Client {
	
	static int globalID = 0;
	
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
		StringBuilder target = new StringBuilder("");
		target.append(id + ": " + name + " " + lastName + ", orders: %n");
		for(int i = 0; i < orders.size(); i++) {
			target.append(orders.get(i).getID() + "%n");
		}
		return target.toString();
	}

}
