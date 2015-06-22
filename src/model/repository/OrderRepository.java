package model.repository;

import java.util.*;

import model.data.*;
import model.data.comparators.*;

/**
 * IRepository implemented for "Order" class
 *
 * @version a.2
 * @author	Boris Gordeev
 * @since 22-06-2015
 */

public class OrderRepository implements IRepository {
	
	private static String ordersType = "orders";
	private static String idAttribute = "id";
	private static String userIDAttribute = "userID";
	private static String servicesAttribute = "services";
	private static String sortAttribute = "sort";
	private static String dateTimeMadeAttribute = "dateTimeMade";
	
	private List<Order> orders;
	private IRepository clients;
	
	public OrderRepository(IRepository clients) {
		orders = new ArrayList<Order>();
		this.clients = clients;
	}
	
	/**
	 * Large "and" predicate that check if "comparable" matches search options.
	 * Ternary expressions are used for "ignoring" null (unspecified in
	 * attributeTable) search options.
	 */
	private boolean matchesRequest(Order comparable, Integer id, Integer userID,
			Calendar dateTimeMade) {
		return 	((id == null) ? true : comparable.getID() == id) &&
				((userID == null) ? true : comparable.getUserID() == userID) &&
				((dateTimeMade == null) ? true : 
					comparable.getDateTimeMade().equals(dateTimeMade));
	}
	
	/**
	 * Finds which comparator to use.
	 */
	private void sort(String sortMode, List<Object> sortable) {
		if (sortMode.equals(userIDAttribute)) {
			Collections.sort(sortable, new OrderUserIDComparator());
		} else if (sortMode.equals(dateTimeMadeAttribute)) {
			Collections.sort(sortable, new OrderDateTimeMadeComparator());
		} else if (sortMode.equals(idAttribute)) {
			Collections.sort(sortable, new OrderIDComparator());
		}
	}
	
	private void addOrderToClient(Order order, Integer userID) {
		HashMap<String, Object> clientGetOrder = new HashMap<String, Object>();
		clientGetOrder.put("id", userID);
		((Client)(clients.select(clientGetOrder).get(0)))
			.getOrders().add(order);
	}
	
	@Override
	public String getDataType() {
		return ordersType;
	}
	
	@Override
	public List<Object> select(HashMap<String, Object> attributeTable) {
		List<Object> returnValue = new ArrayList<Object>();
		Integer userID = (Integer)attributeTable.get(userIDAttribute);
		Integer id = (Integer)attributeTable.get(idAttribute);
		String sortMode = (String)attributeTable.get(sortAttribute);
		Calendar dateTimeMade = (Calendar)attributeTable.get(dateTimeMadeAttribute);
		
		for(int i = 0; i < orders.size(); i++) {
			if (matchesRequest(orders.get(i), id, userID, dateTimeMade)) {
				returnValue.add(orders.get(i));
			}
		}
		
		if (sortMode != null) {
			sort(sortMode, returnValue);
		}
		
		return returnValue;
	}
	
	@Override
	public void add(HashMap<String, Object> attributeTable) {
		Integer userID = (Integer)attributeTable.get(userIDAttribute);
		List<ServiceAmountTuple> services = 
				(List<ServiceAmountTuple>)attributeTable.get(servicesAttribute);
		
		if (userID == null) 
			throw new IllegalArgumentException("Not enough attributes"); 
		Order order = new Order(userID, services);
		orders.add(order);
		
		addOrderToClient(order, userID);
	}
	
	@Override
	public List<String> getAttributeList() {
		List<String> returnValue = new ArrayList<String>();
		returnValue.add(userIDAttribute);
		returnValue.add(servicesAttribute);
		returnValue.add(idAttribute);
		returnValue.add(sortAttribute);
		returnValue.add(dateTimeMadeAttribute);
		return returnValue;
	}

}
