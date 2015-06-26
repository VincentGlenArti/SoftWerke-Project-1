package model.data.comparators;

import java.util.Comparator;

/**
 * @version a.1 17 June 2015
 * @author 	Boris Gordeev
 */



import model.data.datatypes.Client;

/**
 * @version a.1
 * @author 	Boris Gordeev
 * @since 17.06.2015
 */

public class ClientBirthDateComparator implements Comparator<Object> {
	
	@Override
	public int compare(Object o1, Object o2) {
		Client c1 = (Client)o1, c2 = (Client)o2;
		return c1.getBirthDate().compareTo(c2.getBirthDate());
	}

}
