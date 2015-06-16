package model.data.comparators;

import java.util.Comparator;

import model.data.Order;

public class OrderDateTimeMadeComparator implements Comparator<Object> {
	
	@Override
	public int compare(Object o1, Object o2) {
		Order r1 = (Order)o1, r2 = (Order)o2;
		return r1.getDateTimeMade().compareTo(r2.getDateTimeMade());
	}

}