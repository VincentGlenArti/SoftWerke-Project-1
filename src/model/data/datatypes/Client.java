package model.data.datatypes;

import java.util.*;
import model.data.datatypes.factory.*;
import model.data.comparators.*;

/**
 * Data class for describing client.
 * 
 * @version b.2
 * @author	Boris Gordeev
 * @since 29-06-2015
 */

public final class Client extends DataType {
	
	private static final List<String> propertyNamesAsList =
			Collections.unmodifiableList(new ArrayList<String>() {{
				add("name");
				add("lastName");
				add("birthDate");
			}});
	private static final Map<String, Comparator<Object>> comparatorsMap =
			Collections.unmodifiableMap(new HashMap<String, 
					Comparator<Object>>() {{
				put("ID", new IDComparator());
				put("birthDate", new ClientBirthDateComparator());
				put("name", new ClientNameComparator());
			}});
	
	private String name;
	private String lastName;
	private List<Order> orders;
	private Date birthDate;

	private Client(boolean generateID,
			String name, String lastName,
			Date birthDate, Number manualID) {
		super(generateID, manualID, setPropertiesAsMap(name, lastName, birthDate));
		this.name = name;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.orders = new ArrayList<Order>();
	}
	
	/**
	 * Constructs a new instance of this class.
	 * If generateID is true, manualID will be ignored.
	 * if generateID is true but any input parameters besides manualID
	 * are null, then an exception will be thrown.
	 * 
	 * @throws IllegalArgumentException
	 */
	public static Client getInstance(boolean generateID, 
			String name, String lastName,
			Date birthDate, Number manualID)
			throws IllegalArgumentException {
		if (generateID) {
			if (name == null ||
					lastName == null ||
					birthDate == null) {
				throw new IllegalArgumentException("ID will be " +
					"generated only if all required fields are not null");
			}
		}
		
		
		return new Client(generateID, name, lastName, birthDate, manualID);
	}
	
	/**
	 * Returns a map<key, value> object, where "key" is
	 * supplied property name and "value" is supplied property value.
	 */
	private final static Map<String, Object> setPropertiesAsMap(final String name, 
			final String lastName, final Date birthDate) {
		return Collections.unmodifiableMap(
			new HashMap<String, Object>(){{ 
				put("name", name);
				put("lastName", lastName);
				put("birthDate", birthDate);
		}});
	}
	
	public Date getBirthDate() {
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
	
	public final IFactory getFactory() {
		return new ClientFactory();
	}
	
	@Override
	public final Map<String, Comparator<Object>> getComparatorsMap() {
		return comparatorsMap;
	}
	
	@Override
	public final DataTypeEnum getType() {
		return DataTypeEnum.Client;
	}
	
	@Override
	public final List<String> getPropertyNamesAsList() {
		return propertyNamesAsList;
	}
	
	@Override
	public String toString() {
		StringBuilder returnValue = new StringBuilder("");
		returnValue.append(super.getID())
			.append(": ")
			.append(name)
			.append(" ")
			.append(lastName)
			.append(" ")
			.append(birthDate)
			.append(", orders:")
			.append(System.lineSeparator());
		for(int i = 0; i < orders.size(); i++) {
			returnValue.append(orders.get(i).getID())
				.append(System.lineSeparator());
		}
		return returnValue.toString();
	}

}
