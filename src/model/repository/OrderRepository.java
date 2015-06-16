package model.repository;

import java.util.*;

import model.data.*;
import model.data.comparators.*;

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
	
	@Override
	public String getDataType() {
		return ordersType;
	}
	
	@Override
	public List<Object> select(HashMap<String, Object> attributeTable) {
		List<Object> target = new ArrayList<Object>();
		Integer userID = (Integer)attributeTable.get(userIDAttribute);
		Integer id = (Integer)attributeTable.get(idAttribute);
		String sort = (String)attributeTable.get(sortAttribute);
		Calendar dateTimeMade = (Calendar)attributeTable.get(dateTimeMadeAttribute);
		Order order;
		
		for(int i = 0; i < orders.size(); i++) {
			order = orders.get(i);
			if ( ((id == null) ? true : order.getID() == id) &&
					((userID == null) ? true : order.getUserID() == userID) &&
					((dateTimeMade == null) ? true :
						order.getDateTimeMade().equals(dateTimeMade)) ) {
				target.add(orders);
			}
		}
		
		if (sort.equals(userIDAttribute)) {
			Collections.sort(target, new OrderUserIDComparator());
		} else if (sort.equals(dateTimeMadeAttribute)) {
			Collections.sort(target, new OrderDateTimeMadeComparator());
		} else if (sort.equals(idAttribute)) {
			Collections.sort(target, new OrderIDComparator());
		}
		
		return target;
	}
	
	@Override
	public void add(HashMap<String, Object> attributeTable) {
		Integer userID = (Integer)attributeTable.get(userIDAttribute);
		List<Tuple> services = (List<Tuple>)attributeTable.get(servicesAttribute);
		
		if (userID == null) throw new IllegalArgumentException(); 
		Order order = new Order(userID, services);
		orders.add(order);
		
		HashMap<String, Object> clientGetOrder = new HashMap<String, Object>();
		clientGetOrder.put("id", userID);
		((Client)(clients.select(clientGetOrder).get(0)))
			.getOrders().add(order);
	}

}
