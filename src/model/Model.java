package model;

import java.util.*;

import model.data.*;
import model.repository.*;

/**
 * MVC model layer class, used to store and manage repositories.
 * Instead of passing parameters between methods directly, "attributeTable"
 * is used. This should allow a high level of abstraction inside of this class.
 * 
 * @version a.2
 * @author	Boris Gordeev
 * @since 22-06-2015
 */

public class Model {
	
	private static String dataType = "dataType";
	
	private List<IRepository> repository;
	
	/*
	 * Should you need to add another data class to model, you will need to create
	 * a repository class for it (see IRepository) and then add it inside of
	 * this class' constructor. You may even give it links to another
	 * repositories if it is necessary.
	 */
	public Model() {
		repository = new ArrayList<IRepository>();
		repository.add(new ClientRepository());
		repository.add(new OrderRepository(repository.get(0)));
		repository.add(new ProductRepository());
	}
	
	/**
	 * Performs search operation.
	 * Attribute "dataType" will be used to find which repository must
	 * handle the request.
	 * Returns sorted list of requested objects.
	 */
	public List<Object> select(HashMap<String, Object> attributeTable) {
		List<Object> returnValue = new ArrayList<Object>();
		for(int i = 0; i < repository.size(); i++) {
			if (repository.get(i).getDataType()
					.equals((String)attributeTable.get(dataType))) {
				returnValue = repository.get(i).select(attributeTable);
				break;
			}
		}
		return returnValue;
	}
	
	/**
	 * Performs add operation.
	 * Attribute "dataType" will be used to find which repository must
	 * handle the request.
	 */
	public void add(HashMap<String, Object> attributeTable) {
		for(int i = 0; i < repository.size(); i++) {
			if (repository.get(i).getDataType()
					.equals((String)attributeTable.get(dataType))) {
				repository.get(i).add(attributeTable);
				break;
			}
		}
	}
	
	/**
	 * Attribute "dataType" will be used to find which repository must
	 * handle the request.
	 * Returns list of attributes.
	 */
	public List<String> getAttributeList(String dataTypeInput) {
		List<String> returnValue = new ArrayList<String>();
		for(int i = 0; i < repository.size(); i++) {
			if (repository.get(i).getDataType().equals(dataTypeInput)) {
				returnValue = repository.get(i).getAttributeList();
				break;
			}
		}
		return returnValue;
	}
}
