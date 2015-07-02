package model.storing;

import java.util.*;

import model.data.datatypes.DataType;
import model.data.datatypes.DataTypeEnum;
import exceptions.*;

/**
 * Manages data types.
 * 
 * @version b.3
 * @author	Boris Gordeev
 * @since 02-07-2015
 */

public class DataStorage {

	private Map<DataTypeEnum, List<DataType>> storedData;
	
	public DataStorage() {
		storedData = Collections.synchronizedMap(
				new EnumMap<DataTypeEnum, List<DataType>>(DataTypeEnum.class));
		
		for(DataTypeEnum typeUsed : DataStorageInfo.dataTypesUsed.keySet()) {
			storedData.put(typeUsed, new ArrayList<DataType>());
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
			if (comparable.equalsIgnoringNullProperties(compareTo)) {
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
			if (comparable.equalsIgnoringNullProperties(compareTo)) {
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
	public void add(DataType adable) throws InvalidArgumentException {
		if(adable.hasNullProperties() ||
				!adable.hasID() ||
				getByID(adable) != null) 
			throw new InvalidArgumentException("Addable must have all " +
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
	public DataType getByID(DataType comparableTo) 
			throws InvalidArgumentException {
		if (!comparableTo.hasID()) {
			throw new InvalidArgumentException("must have primary key defined.");
		}
		
		for(DataType comparable : storedData.get(comparableTo.getType())) {
			if (comparable.equals(comparableTo)) {
				return comparable;
			}
		}
		
		return null;
	}
	
	/**
	 * getByID operation method that uses Collections.binarySearch().
	 * This is times faster than the linear one implemented in the other
	 * getByID, but I am not allowed to use it.
	 */
	/*
	public DataType getByID(DataType comparableTo)
			throws InvalidArgumentException {
		if (!comparableTo.hasID()) {
			throw new InvalidArgumentException("must have primary key defined.");
		}
		
		int searchResult = Collections.
				binarySearch(storedData.get(comparableTo.getType()), 
						comparableTo);
		
		if (searchResult >= 0) {
			return storedData.get(comparableTo.getType()).get(searchResult);
		} else {
			return null;
		}
	}*/
	
}
