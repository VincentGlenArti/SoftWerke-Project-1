package model.data.datatypes;

import java.util.*;

import model.data.auxiliary.ServiceAmountTuple;

/**
 * Data class for describing order.
 * 
 * @version b.1
 * @author	Boris Gordeev
 * @since 25-06-2015
 */

public class Order extends DataType {
	
	private static int globalID = 0;
	private static final List<String> propertyNamesAsList =
			Collections.unmodifiableList(new ArrayList<String>() {{
				add("dateTimeMade"); 
				add("client");
			}});
	
	private DataType client;
	private Calendar dateTimeMade;
	private List<ServiceAmountTuple> services;
	
	/**
	 * Default constructor.
	 * Does not allow null input.
	 * Sets primary key.
	 * 
	 * @throws IllegalArgumentException
	 */
	public Order(Client client, List<ServiceAmountTuple> services) 
		throws IllegalArgumentException {
		super(globalID, setPropertiesAsMap(Calendar.getInstance(), client));
		
		if (client == null || services == null) 
			throw new IllegalArgumentException();
		
		this.client = client;
		this.dateTimeMade = (Calendar)this.getPropertiesAsMap().
				get("dateTimeMade");
		this.services = services;
		
		globalID++;
	}
	
	/**
	 * Nullable constructor. Use to build object that you are going to use
	 * to compare with.
	 * Allows null input.
	 * Manual primary key.
	 */
	public Order(Integer id, Client client, Calendar dateTimeMade)  {
		super(id, setPropertiesAsMap(dateTimeMade, client));
		this.client = client;
		this.dateTimeMade = dateTimeMade;
	}
	
	/**
	 * Copy constructor.
	 * Throws an exception if "addItem" has any null fields, which is checked
	 * using "hasNullProperties". Throws an exception if so.
	 * Sets primary key.
	 * 
	 * @throws IllegalArgumentException
	 */
	public Order(Order addItem) throws IllegalArgumentException {
		super(globalID, setPropertiesAsMap(addItem.getDateTimeMade(),
				addItem.getClient()));
		
		if (addItem.hasNullProperties()) throw new IllegalArgumentException();
		
		this.client = addItem.getClient();
		this.dateTimeMade = addItem.getDateTimeMade();
		this.services = addItem.getServices();
		
		globalID++;
	}
	
	/**
	 * Returns a map<key, value> object, where "key" is
	 * supplied property name and "value" is supplied property value.
	 */
	private static Map<String, Object> setPropertiesAsMap(
			final Calendar dateTimeMade, final DataType client) {
		return Collections.unmodifiableMap(
			new HashMap<String, Object>(){{ 
				put("dateTimeMade", dateTimeMade);
				put("client", client);
		}});
	}

	public List<ServiceAmountTuple> getServices() {
		return services;
	}

	public Calendar getDateTimeMade() {
		return dateTimeMade;
	}
	
	public DataType getClient() {
		return client;
	}
	
	@Override
	public final DataTypeEnum getType() {
		return DataTypeEnum.Order;
	}
	
	public final static List<String> getPropertyNamesAsList() {
		return propertyNamesAsList;
	}
	
	@Override
	public String toString() {
		StringBuilder returnValue = new StringBuilder("");
		returnValue.append(this.getPrimaryKey())
			.append(" : ")
			.append(client.getPrimaryKey())
			.append(" ")
			.append(" ")
			.append(dateTimeMade.toString())
			.append(", services:")
			.append(System.lineSeparator());
		for(int i = 0; i < services.size(); i++) {
			returnValue.append(services.get(i).getService().getServiceType().toString())
				.append(": ")
				.append(services.get(i).getService().toString())
				.append(" ")
				.append(services.get(i).getAmount());
		}
		return returnValue.toString();
	}
}
