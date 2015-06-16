package model.repository;

import java.util.*;

import model.data.*;
import model.data.comparators.*;

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
	
	@Override
	public String getDataType() {
		return clientsType;
	}
	
	@Override
	public List<Object> select(HashMap<String, Object> attributeTable) {
		List<Object> target = new ArrayList<Object>();
		String name = (String)attributeTable.get(nameAttribute);
		String lastName = (String)attributeTable.get(lastNameAttribute);
		Integer id = (Integer)attributeTable.get(idAttribute);
		String sort = (String)attributeTable.get(sortAttribute);
		Calendar birthDate = (Calendar)attributeTable.get(birthDateAttribute);
		Client client;
		
		for(int i = 0; i < clients.size(); i++) {
			client = clients.get(i);
			if ( ((name == null) ? true : client.getName().equals(name)) &&
					((lastName == null) ? true : client.getLastName().equals(lastName)) &&
					((id == null) ? true : client.getID() == id) &&
					((birthDate == null) ? true : client.getBirthDate().equals(birthDate)) ) {
				target.add(client);
			}
		}
		
		if (sort.equals(nameAttribute)) {
			Collections.sort(target, new ClientNameComparator());
		} else if (sort.equals(idAttribute)) {
			Collections.sort(target, new ClientIDComparator());
		} else if (sort.equals(birthDateAttribute)) {
			Collections.sort(target, new ClientBirthDateComparator());
		}
		
		return target;
	}
	
	@Override
	public void add(HashMap<String, Object> attributeTable) {
		String name = (String)attributeTable.get(nameAttribute);
		String lastName = (String)attributeTable.get(lastNameAttribute);
		Calendar birthDate = (Calendar)attributeTable.get(birthDateAttribute);
		
		if (name == null || lastName == null || birthDate == null) 
			throw new IllegalArgumentException(); 
		Client target = new Client(name, lastName, birthDate);
		
		clients.add(target);
	}
	
}
