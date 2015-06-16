package model.data.comparators;

import java.util.Comparator;

import model.data.Client;

public class ClientIDComparator implements Comparator<Object> {
	
	@Override
	public int compare(Object o1, Object o2) {
		Client c1 = (Client)o1, c2 = (Client)o2;
		return ((Integer)(c1.getID())).compareTo(c2.getID());
	}

}
