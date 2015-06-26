package model.data.datatypes;

import java.util.*;

/**
 * This class represents an implementation for basic logic for any data type.
 * If you need to use you custom data type inside of this system, you will need
 * to derive it from this one, implement it's abstract methods, 
 * and add a new enum for your type in DataTypeEnum.
 * 
 * @version b.1
 * @author	Boris Gordeev
 * @since 22-06-2015
 */

public abstract class DataType {
	
	private Integer primaryKey;
	private Map<String, Object> propertiesAsMap; 
	
	/**
	 * Basic constructor, sets primary key and input properties in a way
	 * so that derived class can use implemented logic.
	 */
	protected DataType(int primaryKey, Map<String, Object> propertiesAsMap) {
		this.primaryKey = primaryKey;
		this.propertiesAsMap = propertiesAsMap;
	}
	
	/**
	 * This equals compares all non-null properties of "comparableTo"
	 * to this.properties and ignores comparison of null values of
	 * "comparableTo".
	 */
	public boolean ignoreNullEquals(DataType comparableTo) {
		boolean returnValue = true;
		
		if(!this.getType().equals(comparableTo.getType())) return false;
		
		Map<String, Object> thisProperties = this.getPropertiesAsMap(),
					comparableToProperties = comparableTo.getPropertiesAsMap();
		
		for (String property : thisProperties.keySet()) {
			returnValue = returnValue && ignoreNullEquality(
					thisProperties.get(property),
					comparableToProperties.get(property));
		}
		
		return returnValue;
	};
	
	/**
	 * Returns true if there are null properties in derived class.
	 */
	public boolean hasNullProperties() {
		boolean nullProperties = false;
		
		Map<String, Object> properties = this.getPropertiesAsMap();
		for(Object propertyValue : properties.values()) {
			nullProperties = nullProperties || (propertyValue == null);
		}
		return nullProperties;
	}
	
	/**
	 * Returns true if PK is not null.
	 */
	public boolean hasPrimaryKey() {
		return primaryKey != null;
	}
	
	public Integer getPrimaryKey() {
		return new Integer(primaryKey);
	};
	
	public Map<String, Object> getPropertiesAsMap() {
		return propertiesAsMap;
	}
	
	/**
	 * Shows which data type derived class responds to.
	 */
	public abstract DataTypeEnum getType();
	
	/**
	 * Javadoc equality obliges to return false if object.equals(null)
	 * is called. This one does otherwise.
	 */
	protected static boolean ignoreNullEquality(Object comparableFirst,
			Object comparableSecond) {
		return (comparableSecond == null) ? 
				true : comparableFirst.equals(comparableSecond);
	}
	
	@Override
	public boolean equals(Object comparableTo) {
		if (comparableTo == this) return true;
		if (comparableTo == null) return false;
		if (!comparableTo.getClass().equals(this.getClass())) return false;
		if (!((DataType)comparableTo).hasPrimaryKey()) return false;
		if (this.getPrimaryKey() != ((DataType)comparableTo).getPrimaryKey())
			return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.getPrimaryKey());
	}

}
