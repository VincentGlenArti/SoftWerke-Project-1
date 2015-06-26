package model.data.datatypes;

import java.util.*;

/**
 * Data class for describing client.
 * 
 * @version b.1
 * @author	Boris Gordeev
 * @since 22-06-2015
 */

public class Client extends DataType {
	
	private static int globalID = 0;
	private static final List<String> propertyNamesAsList =
			Collections.unmodifiableList(new ArrayList<String>() {{
				add("name");
				add("lastName");
				add("birthDate");
			}});
	
	private String name;
	private String lastName;
	private List<Order> orders;
	private Calendar birthDate;
	
	/**
	 * Default constructor.
	 * Does not allow null input.
	 * Sets primary key.
	 * 
	 * @throws IllegalArgumentException
	 */
	public Client(String name, String lastName, Calendar birthDate)
		throws IllegalArgumentException {
		super(globalID, setPropertiesAsMap(name, lastName, birthDate));
		if (name == null || lastName == null || birthDate == null)
			throw new IllegalArgumentException();
		this.name = name;
		this.lastName = lastName;
		this.birthDate = birthDate;
		orders = new ArrayList<Order>();
		globalID++;
	}
	
	/**
	 * Nullable constructor. Use to build object that you are going to use
	 * to compare with.
	 * Allows null input.
	 * Manual primary key.
	 */
	public Client(String name, String lastName, Calendar birthDate, Integer id) {
		super(id, setPropertiesAsMap(name, lastName, birthDate));
		this.name = name;
		this.lastName = lastName;
		this.birthDate = birthDate;
	}
	
	/**
	 * Copy constructor.
	 * Throws an exception if "addItem" has any null fields, which is checked
	 * using "hasNullProperties". Throws an exception if so.
	 * Sets primary key.
	 * 
	 * @throws IllegalArgumentException
	 */
	public Client(Client addItem) throws IllegalArgumentException {
		super(globalID, setPropertiesAsMap(addItem.getName(), 
				addItem.getLastName(), addItem.getBirthDate()));
		if (addItem.hasNullProperties()) throw new IllegalArgumentException();
		this.name = addItem.getName();
		this.lastName = addItem.getLastName();
		this.birthDate = addItem.getBirthDate();
		orders = new ArrayList<Order>();
		globalID++;
	}
	
	/**
	 * Returns a map<key, value> object, where "key" is
	 * supplied property name and "value" is supplied property value.
	 */
	private static Map<String, Object> setPropertiesAsMap(final String name, 
			final String lastName, final Calendar birthDate) {
		return Collections.unmodifiableMap(
			new HashMap<String, Object>(){{ 
				put("name", name);
				put("lastName", lastName);
				put("birthDate", birthDate);
		}});
	}
	
	public Calendar getBirthDate() {
		return birthDate;
	}

	public String getLastName() {
		return lastName;
	}

	public String getName() {
		return name;
	}
	
	public List<Order> getOrders() {
		return orders;
	}
	
	@Override
	public final DataTypeEnum getType() {
		return DataTypeEnum.Client;
	}
	
	public final static List<String> getPropertyNamesAsList() {
		return propertyNamesAsList;
	}
	
	@Override
	public String toString() {
		StringBuilder returnValue = new StringBuilder("");
		returnValue.append(super.getPrimaryKey())
			.append(": ")
			.append(name)
			.append(" ")
			.append(lastName)
			.append(" ")
			.append(birthDate)
			.append(", orders:")
			.append(System.lineSeparator());
		for(int i = 0; i < orders.size(); i++) {
			returnValue.append(orders.get(i).getPrimaryKey())
				.append(System.lineSeparator());
		}
		return returnValue.toString();
	}

}
