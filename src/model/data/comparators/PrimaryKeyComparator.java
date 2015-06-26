package model.data.comparators;

import java.util.Comparator;

import model.data.datatypes.DataType;;

/**
 * @version b.1
 * @author	Boris Gordeev
 * @since 22-06-2015
 */

public class PrimaryKeyComparator implements Comparator<Object> {
	
	@Override
	public int compare(Object o1, Object o2) {
		DataType c1 = (DataType)o1, c2 = (DataType)o2;
		return c1.getPrimaryKey().compareTo(c2.getPrimaryKey());
	}

}
