package model.data.comparators;

import java.util.Comparator;

import model.data.Client;;

public class ClientNameComparator implements Comparator<Object> {
	
	@Override
	public int compare(Object o1, Object o2) {
		Client c1 = (Client)o1, c2 = (Client)o2;
		return (c1.getName().toLowerCase() + " " + 
				c1.getLastName().toLowerCase()).compareTo(
				c2.getName().toLowerCase() + " " + 
				c2.getLastName().toLowerCase());
	}

}
