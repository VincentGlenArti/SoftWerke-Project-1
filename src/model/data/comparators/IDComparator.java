package model.data.comparators;

import java.util.Comparator;

import model.data.datatypes.DataType;;

/**
 * @version b.1
 * @author	Boris Gordeev
 * @since 22-06-2015
 */

public class IDComparator implements Comparator<Object> {
	
	@Override
	public int compare(Object o1, Object o2) {
		DataType c1 = (DataType)o1, c2 = (DataType)o2;
		return ((Long)c1.getID()).compareTo((Long)c2.getID());
	}

}
