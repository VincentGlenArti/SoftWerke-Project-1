package model;

import java.util.*;

import model.data.*;
import model.repository.*;

public class Model {
	
	private static String dataType = "dataType";
	
	private List<IRepository> repository;
	
	public Model() {
		repository = new ArrayList<IRepository>();
		repository.add(new ClientRepository());
		repository.add(new OrderRepository(repository.get(0)));
		repository.add(new ProductRepository());
	}
	
	public List<Object> select(HashMap<String, Object> attributeTable) {
		List<Object> target = null;
		for(int i = 0; i < repository.size(); i++) {
			if (repository.get(i).getDataType()
					.equals((String)attributeTable.get(dataType))) {
				target = repository.get(i).select(attributeTable);
				break;
			}
		}
		return target;
	}
	
	public void add(HashMap<String, Object> attributeTable) {
		for(int i = 0; i < repository.size(); i++) {
			if (repository.get(i).getDataType()
					.equals((String)attributeTable.get(dataType))) {
				repository.get(i).add(attributeTable);
				break;
			}
		}
	}
}
