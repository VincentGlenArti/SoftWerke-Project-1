package model.data.comparators;

import java.util.Comparator;

import model.data.datatypes.Product;

/**
 * @version b.2
 * @author 	Boris Gordeev
 * @since 29.06.2015
 */

public class ProductNameComparator implements Comparator<Object> {

	@Override
	public int compare(Object o1, Object o2) {
		Product p1 = (Product)o1, p2 = (Product)o2;
		return p1.getModelName().toLowerCase().compareTo(
				p2.getModelName().toLowerCase());
	}
	
}
