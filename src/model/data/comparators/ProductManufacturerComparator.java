package model.data.comparators;

import java.util.Comparator;

import model.data.Product;;

public class ProductManufacturerComparator implements Comparator<Object> {

	@Override
	public int compare(Object o1, Object o2) {
		Product p1 = (Product)o1, p2 = (Product)o2;
		return p1.getManufacturerName().toLowerCase().compareTo(
				p2.getManufacturerName().toLowerCase());
	}
	
}
