package model.data.datatypes;

import java.util.*;
import model.data.datatypes.factory.*;

/**
 * This class represents an implementation for basic logic for any data type.
 * If you need to use you custom data type inside of this system, you will need
 * to derive it from this one, implement it's abstract methods, 
 * and add a new enum for your type in DataTypeEnum.
 * 
 * @version b.3
 * @author	Boris Gordeev
 * @since 02-07-2015
 */

public abstract class DataType implements Comparable<DataType> {
	
	private Number id;
	private Map<String, Object> propertiesAsMap;
	private static final Map<DataTypeEnum, Number> globalIDList = 
			new EnumMap<DataTypeEnum, Number>(DataTypeEnum.class);
	
	/**
	 * Basic constructor, sets primary key and input properties in a way
	 * so that derived class can use implemented logic.
	 * Manual ID will be ignore if generateID is true.
	 */
	protected DataType(boolean generateID, Number manualID,
			Map<String, Object> propertiesAsMap) {
		this.id = (generateID ? setID() : manualID);
		this.propertiesAsMap = propertiesAsMap;
	}
	
	/**
	 * This equals compares all non-null properties of "comparableTo"
	 * to this.properties and ignores comparison of null values of
	 * "comparableTo".
	 */
	public boolean equalsIgnoringNullProperties(DataType comparableTo) {
		boolean returnValue = true;
		
		if(!this.getType().equals(comparableTo.getType())) return false;
		
		Map<String, Object> thisProperties = this.getPropertiesAsMap(),
					comparableToProperties = comparableTo.getPropertiesAsMap();
		
		for (String property : thisProperties.keySet()) {
			returnValue = returnValue && propertiesAreEqualIgnoringNull(
					thisProperties.get(property),
					comparableToProperties.get(property));
			if (!returnValue) break;
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
	 * Returns true if ID is not null.
	 */
	public boolean hasID() {
		return id != null;
	}
	
	public Number getID() {
		return new Long(id.longValue());
	};
	
	public Map<String, Object> getPropertiesAsMap() {
		return propertiesAsMap;
	}
	
	/**
	 * Returns factory - means of producing an instance.
	 */
	public abstract IFactory getFactory();
	
	/**
	 * Shows which data type derived class responds to.
	 */
	public abstract DataTypeEnum getType();
	
	/**
	 * Shows list of property names.
	 */
	public abstract List<String> getPropertyNamesAsList();
	
	/**
	 * Shows list of comparators available.
	 */
	public abstract Map<String, Comparator<Object>> getComparatorsMap();
	
	/**
	 * Uses global id map to get new id for new instance.
	 */
	private Number setID() {
		if (!globalIDList.containsKey(this.getType())) {
			globalIDList.put(this.getType(), new Long(1));
			return new Long(0);
		} else {
			Long returnValue = new Long((long)globalIDList.get(this.getType()));
			globalIDList.put(this.getType(), new Long(returnValue + 1));
			return returnValue;
		}
	}
	
	/**
	 * Javadoc equality obliges to return false if object.equals(null)
	 * is called. This one does otherwise.
	 */
	protected static boolean propertiesAreEqualIgnoringNull(Object comparableFirst,
			Object comparableSecond) {
		return (comparableSecond == null) ? 
				true : comparableFirst.equals(comparableSecond);
	}
	
	@Override
	public boolean equals(Object comparableTo) {
		if (comparableTo == this) return true;
		if (comparableTo == null) return false;
		if (!comparableTo.getClass().equals(this.getClass())) return false;
		if (!((DataType)comparableTo).hasID()) return false;
		if (this.getID().equals(((DataType)comparableTo).getID()))
			return true;
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.getID());
	}
	
	@Override
	public int compareTo(DataType comparableTo) {
		return ((Long)this.getID()).compareTo((Long)comparableTo.getID());
	}

}
