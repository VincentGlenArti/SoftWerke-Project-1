package model.repository;

import java.util.*;

/**
 * An interface for "Repository" - a class that implements logic
 * for data-type classes.
 *
 * @version a.2
 * @author	Boris Gordeev
 * @since 22-06-2015
 */

public interface IRepository {
	
	/**
	 * Returns name of data type that this repository operates with.
	 */
	public String getDataType();
	
	/**
	 * Performs search ( "select" ) operation inside of repository,
	 * where all search options are written inside of "attributeTable".
	 * Use getAttributeList to get all attribute names.
	 * Returns sorted request match.
	 */
	public List<Object> select(HashMap<String,Object> attributeTable);
	
	/**
	 * Performs adding to repository operation,
	 * where all parameters of new object must be written inside 
	 * of "attributeTable". If they are not, exception of type 
	 * IllegalArgumentException will be thrown.
	 * Use getAttributeList to get all attribute names.
	 */
	public void add(HashMap<String,Object> attributeTable);
	
	/**
	 * Returns all attributes you can specify in attributeTable.
	 */
	public List<String> getAttributeList();
}
