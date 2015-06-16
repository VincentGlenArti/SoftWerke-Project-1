package model.data.comparators;

import java.util.Comparator;

import model.data.Order;

public class OrderUserIDComparator implements Comparator<Object> {
	
	@Override
	public int compare(Object o1, Object o2) {
		Order r1 = (Order)o1, r2 = (Order)o2;
		return ((Integer)r1.getUserID()).compareTo(r2.getUserID());
	}

}
