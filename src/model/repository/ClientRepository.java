package model.repository;

import java.util.*;

import model.data.*;
import model.data.comparators.*;

/**
 * IRepository implemented for "Client" class
 *
 * @version a.2
 * @author	Boris Gordeev
 * @since 22-06-2015
 */

public class ClientRepository implements IRepository {
	
	private static String clientsType = "clients";
	private static String nameAttribute = "name";
	private static String lastNameAttribute = "lastName";
	private static String idAttribute = "id";
	private static String sortAttribute = "sort";
	private static String birthDateAttribute = "birthDate";
	
	private List<Client> clients;
	
	public ClientRepository() {
		clients = new ArrayList<Client>();
	}
	
	/**
	 * Large "and" predicate that check if "comparable" matches search options.
	 * Ternary expressions are used for "ignoring" null (unspecified in
	 * attributeTable) search options.
	 */
	private boolean matchesRequest(Client comparable, String name, String lastName,
			Integer id,	Calendar birthDate) {
		return 	((name == null) ? true : comparable.getName().equals(name)) &&
				((lastName == null) ? 
						true : comparable.getLastName().equals(lastName)) &&
				((id == null) ? true : comparable.getID() == id) &&
				((birthDate == null) ? true : comparable.getBirthDate().equals(birthDate));
	}
	
	/**
	 * Finds which comparator to use.
	 */
	private void sort(String sortMode, List<Object> sortable) {
		if (sortMode.equals(nameAttribute)) {
			Collections.sort(sortable, new ClientNameComparator());
		} else if (sortMode.equals(idAttribute)) {
			Collections.sort(sortable, new ClientIDComparator());
		} else if (sortMode.equals(birthDateAttribute)) {
			Collections.sort(sortable, new ClientBirthDateComparator());
		}
	}
	
	@Override
	public String getDataType() {
		return clientsType;
	}
	
	@Override
	public List<Object> select(HashMap<String, Object> attributeTable) {
		List<Object> returnValue = new ArrayList<Object>();
		String name = (String)attributeTable.get(nameAttribute);
		String lastName = (String)attributeTable.get(lastNameAttribute);
		Integer id = (Integer)attributeTable.get(idAttribute);
		String sortMode = (String)attributeTable.get(sortAttribute);
		Calendar birthDate = (Calendar)attributeTable.get(birthDateAttribute);
		
		for(int i = 0; i < clients.size(); i++) {
			if (matchesRequest(clients.get(i), name, lastName, id, birthDate)) {
				returnValue.add(clients.get(i));
			}
		}
		
		if (sortMode != null) {
			sort(sortMode, returnValue);
		}
		
		return returnValue;
	}
	
	@Override
	public void add(HashMap<String, Object> attributeTable) {
		String name = (String)attributeTable.get(nameAttribute);
		String lastName = (String)attributeTable.get(lastNameAttribute);
		Calendar birthDate = (Calendar)attributeTable.get(birthDateAttribute);
		
		if (name == null || lastName == null || birthDate == null) 
			throw new IllegalArgumentException("Not enough attributes"); 
		Client returnValue = new Client(name, lastName, birthDate);
		
		clients.add(returnValue);
	}
	
	@Override
	public List<String> getAttributeList() {
		List<String> returnValue = new ArrayList<String>();
		returnValue.add(nameAttribute);
		returnValue.add(lastNameAttribute);
		returnValue.add(idAttribute);
		returnValue.add(sortAttribute);
		returnValue.add(birthDateAttribute);
		return returnValue;
	}
	
}
