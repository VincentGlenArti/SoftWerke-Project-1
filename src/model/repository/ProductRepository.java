package model.repository;

import java.util.*;

import model.data.*;
import model.data.comparators.*;

public class ProductRepository implements IRepository {
	
	private static String productsType = "products";
	private static String idAttribute = "id";
	private static String manufacturerNameAttribute = "manufacturerName";
	private static String modelNameAttribute = "modelName";
	private static String colorAttribute = "color";
	private static String productTypeAttribute = "productType";
	private static String releaseDateAttribute = "releaseDate";
	private static String sortAttribute = "sort";

	private List<Product> products;
	
	public ProductRepository() {
		products = new ArrayList<Product>();
	}
	
	@Override
	public String getDataType() {
		return productsType;
	}
	
	@Override
	public List<Object> select(HashMap<String, Object> attributeTable) {
		List<Object> target = new ArrayList<Object>();
		String manufacturerName = (String)attributeTable.get(manufacturerNameAttribute);
		String modelName = (String)attributeTable.get(modelNameAttribute);
		ColorDiscrete color = (ColorDiscrete)attributeTable.get(colorAttribute);
		ProductType productType = (ProductType)attributeTable.get(productTypeAttribute);
		Calendar releaseDate = (Calendar)attributeTable.get(releaseDateAttribute);
		String sort = (String)attributeTable.get(sortAttribute);
		Integer id = (Integer)attributeTable.get(idAttribute);
		Product product;
		
		for(int i = 0; i < products.size(); i++) {
			product = products.get(i);
			if ( ((manufacturerName == null) ? 
							true : product.getManufacturerName().equals(manufacturerName)) &&
					((modelName == null) ?
							true : product.getModelName().equals(modelName)) &&
					((color == null) ? 
							true : product.getColor() == color) &&
					((productType == null) ? 
							true : product.getProductType() == productType) &&
					((releaseDate == null) ? 
							true : product.getReleaseDate().equals(releaseDate)) &&
					((id == null) ? 
							true : id.equals(product.getID()))) {
				target.add(product);
			}
		}
		
		if (sort.equals(manufacturerNameAttribute)) {
			Collections.sort(target, new ProductManufacturerComparator());
		} else if (sort.equals(productTypeAttribute)) {
			Collections.sort(target, new ProductTypeComparator());
		} else if (sort.equals(releaseDateAttribute)) {
			Collections.sort(target, new ProductReleaseDateComparator());
		} else if (sort.equals(idAttribute)) {
			Collections.sort(target, new ProductIDComparator());
		}
		
		return target;
	}
	
	@Override
	public void add(HashMap<String, Object> attributeTable) {
		String manufacturerName = (String)attributeTable.get(manufacturerNameAttribute);
		String modelName = (String)attributeTable.get(modelNameAttribute);
		ColorDiscrete color = (ColorDiscrete)attributeTable.get(colorAttribute);
		ProductType productType = (ProductType)attributeTable.get(productTypeAttribute);
		Calendar releaseDate = (Calendar)attributeTable.get(releaseDateAttribute);
		
		if (manufacturerName == null || modelName == null 
				|| color == null || productType == null || releaseDate == null) 
			throw new IllegalArgumentException(); 
		Product product = new Product(manufacturerName, productType,
				modelName, color, releaseDate);
		products.add(product);
	}
	
}
