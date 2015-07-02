package model.data.datatypes;

import java.util.*;

import model.data.comparators.*;
import model.data.datatypes.factory.*;
import model.data.auxiliary.ServiceAmountTuple;
import exceptions.*;

/**
 * Data class for describing order.
 * 
 * @version b.3
 * @author	Boris Gordeev
 * @since 02-07-2015
 */

public final class Order extends DataType {
	
	private static final List<String> propertyNamesAsList =
			Collections.unmodifiableList(new ArrayList<String>() {{
				add("dateTimeMade"); 
				add("client");
				add("services");
			}});
	private static final Map<String, Comparator<Object>> comparatorsMap =
			Collections.unmodifiableMap(new HashMap<String, 
					Comparator<Object>>() {{
				put("ID", new IDComparator());
				put("dateTimeMade", new OrderDateTimeMadeComparator());
				put("client", new OrderUserIDComparator());
			}});
	
	private DataType client;
	private Date dateTimeMade;
	private List<ServiceAmountTuple> services;
	
	private Order(boolean generateID, Number manualID, Client client,
			Date dateTimeMade, List<ServiceAmountTuple> services)  {
		super(generateID, manualID, setPropertiesAsMap(dateTimeMade, client));
		this.client = client;
		this.dateTimeMade = dateTimeMade;
		this.services = services;
	}
	
	/**
	 * Constructs a new instance of this class.
	 * If generateID is true, manualID will be ignored.
	 * if generateID is true but any input parameters besides manualID
	 * are null, then an exception will be thrown.
	 * 
	 * @throws IllegalArgumentException
	 */
	public static Order getInstance(boolean generateID, 
			Number manualID, Client client, 
			Date dateTimeMade, List<ServiceAmountTuple> services) 
					throws InvalidArgumentException {
		if (generateID) {
			if (client == null ||
					dateTimeMade == null ||
					services == null) {
				throw new InvalidArgumentException("ID will be " +
					"generated only if all required fields are not null");
			}
		}
		
		
		return new Order(generateID, manualID, client, dateTimeMade, services);
	}
	
	/**
	 * Returns an empty instance, that can be used to access implementations
	 * of abstract getters. Does not throw business logic exceptions.
	 */
	public static Order getEmptyInstance() {
		Order returnValue;
		try {
			returnValue = getInstance(false,
					null,
					null,
					null,
					null);
		} catch (InvalidArgumentException iae) {
			returnValue = null;
		}
		return returnValue;
	}
	
	private final static Map<String, Object> setPropertiesAsMap(
			final Date dateTimeMade, final DataType client) {
		Map<String, Object> returnValue = new HashMap<String, Object>();
		returnValue.put("dateTimeMade", dateTimeMade);
		returnValue.put("client", client);
		return returnValue;
	}

	public List<ServiceAmountTuple> getServices() {
		return services;
	}

	public Date getDateTimeMade() {
		return dateTimeMade;
	}
	
	public DataType getClient() {
		return client;
	}
	
	@Override
	public final Map<String, Comparator<Object>> getComparatorsMap() {
		return comparatorsMap;
	}
	
	@Override
	public final IFactory getFactory() {
		return new OrderFactory();
	}
	
	@Override
	public final DataTypeEnum getType() {
		return DataTypeEnum.Order;
	}
	
	@Override
	public final List<String> getPropertyNamesAsList() {
		return propertyNamesAsList;
	}
	
	@Override
	public String toString() {
		StringBuilder returnValue = new StringBuilder("");
		returnValue.append(this.getID())
			.append(" : ")
			.append(client.getID())
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
