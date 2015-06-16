package model.repository;

import java.util.*;

public interface IRepository {
	
	public String getDataType();
	
	public List<Object> select(HashMap<String,Object> attributeTable);
	
	public void add(HashMap<String,Object> attributeTable);
}
