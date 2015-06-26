package model.storing;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import model.data.datatypes.DataType;
import model.data.datatypes.DataTypeEnum;

/**
 * Manages data types.
 * 
 * @version b.1
 * @author	Boris Gordeev
 * @since 22-06-2015
 */

public class DataStorage {

	private Map<DataTypeEnum, List<DataType>> storedData;
	private Map<DataTypeEnum, List<String>> propertyNamesLists;
	
	/**
	 * This will use reflecting on every Enum in DataTypeEnum.
	 * You need to make sure that every DataTypeEnum has a class
	 * with same name that has a static "getPropertyNamesAsList"
	 * method that returns a list of property names.
	 */
	public DataStorage() {
		storedData = Collections.synchronizedMap(
				new EnumMap<DataTypeEnum, List<DataType>>(DataTypeEnum.class));
		propertyNamesLists = Collections.synchronizedMap(
				new EnumMap<DataTypeEnum, List<String>>(DataTypeEnum.class));
		List<String> propertyNamesList;
		for(DataTypeEnum type : DataTypeEnum.values()) {
			storedData.put(type, new LinkedList<DataType>());
			try {
				propertyNamesList = (List<String>)Class
					.forName(DataType.class.getPackage().getName()
							+ "." + type.toString())
					.getDeclaredMethod("getPropertyNamesAsList", null)
					.invoke(null, null);
			} catch (InvocationTargetException | 
					IllegalAccessException |
					NoSuchMethodException |
					ClassNotFoundException Exc) {
				propertyNamesList = new ArrayList<String>();
				propertyNamesList.add("No properties info available");
			}
			propertyNamesLists.put(type, propertyNamesList);
		}
	}
	
	
	/**
	 * Performs search within data types, ignoring primary key
	 * and all null values of input argument.
	 * Returns list of matching data type objects.
	 * 
	 * @param DataType compareTo
	 * @return List<DataType> searchResult
	 */
	public List<DataType> select(DataType compareTo) {
		List<DataType> returnValue = new LinkedList<DataType>();
		
		for(DataType comparable : storedData.get(compareTo.getType())) {
			if (comparable.ignoreNullEquals(compareTo)) {
				returnValue.add(comparable);
			}
		}
		
		return returnValue;
	}
	
	/**
	 * Performs search within data types, ignoring primary key
	 * and all null values of input argument.
	 * Returns list of matching data type objects. This list is sorted using
	 * comparator specified.
	 * 
	 * @param DataType compareTo
	 * @param Comparator comparator
	 * @return List<DataType> searchResult
	 */
	public List<DataType> select(DataType compareTo, 
			Comparator<Object> comparator) {
		List<DataType> returnValue = new LinkedList<DataType>();
		
		for(DataType comparable : storedData.get(compareTo.getType())) {
			if (comparable.ignoreNullEquals(compareTo)) {
				returnValue.add(comparable);
			}
		}
		
		if (comparator != null) {
			Collections.sort(returnValue, comparator);
		}
		
		return returnValue;
	}
	
	/**
	 * Adds input argument to list of same data types.
	 * Input argument must not have any null properties, must have
	 * primary key defined and unique.
	 * 
	 * @param DataType addable
	 * @throws IllegalArgumentException
	 */
	public void add(DataType adable) throws IllegalArgumentException {
		if(adable.hasNullProperties() ||
				adable.hasPrimaryKey() ||
				getByPrimaryKey(adable) != null) 
			throw new IllegalArgumentException("Addable must have all " +
				"properties defined and an individual primary key");
		
		storedData.get(adable.getType()).add(adable);
	}
	
	/**
	 * Returns first object of the same data type.
	 * Comparing is done via primary key.
	 * 
	 * @param DataType comparableTo
	 * @return DataType firstOrDefault
	 * @throws IllegalArgumentException
	 */
	public DataType getByPrimaryKey(DataType comparableTo) 
			throws IllegalArgumentException {
		if (!comparableTo.hasPrimaryKey()) 
			throw new IllegalArgumentException("must have primary key defined.");
		
		for(DataType comparable : storedData.get(comparableTo.getType())) {
			if (comparable.equals(comparableTo)) {
				return comparable;
			}
		}
		
		return null;
	}
	
	/**
	 * Returns list of properties that must be specified for search purposes
	 * of specified data type.
	 * 
	 * @param DataTypeEnum requestedDataType
	 * @return List<String> listOfPropertieNames
	 * @throws IllegalArgumentException
	 */
	public List<String> getPropertyNamesList(DataTypeEnum requestedDataType)
			throws IllegalArgumentException {
		if(requestedDataType == null) 
			throw new IllegalArgumentException("Null request");
		
		
		return propertyNamesLists.get(requestedDataType);
	}
	
}
