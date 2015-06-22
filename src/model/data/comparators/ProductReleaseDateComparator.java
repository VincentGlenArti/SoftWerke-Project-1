package model.data.comparators;

import java.util.Comparator;

import model.data.Product;

/**
 * @version a.1 17 June 2015
 * @author 	Boris Gordeev
 */

public class ProductReleaseDateComparator implements Comparator<Object> {

	@Override
	public int compare(Object o1, Object o2) {
		Product p1 = (Product)o1, p2 = (Product)o2;
		return p1.getReleaseDate().compareTo(p2.getReleaseDate());
	}
	
}