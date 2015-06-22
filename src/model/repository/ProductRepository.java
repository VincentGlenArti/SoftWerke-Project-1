package model.repository;

import java.util.*;

import model.data.*;
import model.data.comparators.*;

/**
 * IRepository implemented for "Product" class
 *
 * @version a.2
 * @author	Boris Gordeev
 * @since 22-06-2015
 */

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
	
	/**
	 * Does a conversion from String to ColorDiscrete that allows null values.
	 */
	private ColorDiscrete nullableColor(String input) {
		return (input == null) ? null : ColorDiscrete.valueOf(input);
	}
	
	/**
	 * Does a conversion from String to ProductType that allows null values.
	 */
	private ProductType nullableType(String input) {
		return (input == null) ? null : ProductType.valueOf(input);
	}
	
	/**
	 * Large "and" predicate that check if "comparable" matches search options.
	 * Ternary expressions are used for "ignoring" null (unspecified in
	 * attributeTable) search options.
	 */
	private boolean matchesRequest(Product comparable, String manufacturerName,
			String modelName, ColorDiscrete color, ProductType productType,
			Calendar releaseDate, Integer id) {
		return 	((manufacturerName == null) ? 
				true : comparable.getManufacturerName().equals(manufacturerName)) &&
		((modelName == null) ?
				true : comparable.getModelName().equals(modelName)) &&
		((color == null) ? 
				true : comparable.getColor() == color) &&
		((productType == null) ? 
				true : comparable.getProductType() == productType) &&
		((releaseDate == null) ? 
				true : comparable.getReleaseDate().equals(releaseDate)) &&
		((id == null) ? 
				true : id.equals(comparable.getID()));
	}
	
	/**
	 * Finds which comparator to use.
	 */
	private void sort(String sortMode, List<Object> sortable) {
		if (sortMode.equals(manufacturerNameAttribute)) {
			Collections.sort(sortable, new ProductManufacturerComparator());
		} else if (sortMode.equals(productTypeAttribute)) {
			Collections.sort(sortable, new ProductTypeComparator());
		} else if (sortMode.equals(releaseDateAttribute)) {
			Collections.sort(sortable, new ProductReleaseDateComparator());
		} else if (sortMode.equals(idAttribute)) {
			Collections.sort(sortable, new ProductIDComparator());
		}
	}
	
	@Override
	public String getDataType() {
		return productsType;
	}
	
	@Override
	public List<Object> select(HashMap<String, Object> attributeTable) {
		List<Object> returnValue = new ArrayList<Object>();
		String manufacturerName = (String)attributeTable.get(manufacturerNameAttribute);
		String modelName = (String)attributeTable.get(modelNameAttribute);
		ColorDiscrete color = nullableColor((String)attributeTable.get(colorAttribute));
		ProductType productType = nullableType((String)attributeTable.get(productTypeAttribute));
		Calendar releaseDate = (Calendar)attributeTable.get(releaseDateAttribute);
		String sortMode = (String)attributeTable.get(sortAttribute);
		Integer id = (Integer)attributeTable.get(idAttribute);
		
		for(int i = 0; i < products.size(); i++) {
			if (matchesRequest(products.get(i), manufacturerName, modelName,
					color, productType, releaseDate, id)) {
				returnValue.add(products.get(i));
			}
		}
		
		if (sortMode != null) {
			sort(sortMode, returnValue);
		}
		
		return returnValue;
	}
	
	@Override
	public void add(HashMap<String, Object> attributeTable) {
		String manufacturerName = (String)attributeTable.get(manufacturerNameAttribute);
		String modelName = (String)attributeTable.get(modelNameAttribute);
		ColorDiscrete color = nullableColor((String)attributeTable.get(colorAttribute));
		ProductType productType = nullableType((String)attributeTable.get(productTypeAttribute));
		Calendar releaseDate = (Calendar)attributeTable.get(releaseDateAttribute);
		
		if (manufacturerName == null || modelName == null 
				|| color == null || productType == null || releaseDate == null) 
			throw new IllegalArgumentException("Not enough attributes"); 
		Product product = new Product(manufacturerName, productType,
				modelName, color, releaseDate);
		products.add(product);
	}
	
	@Override
	public List<String> getAttributeList() {
		List<String> returnValue = new ArrayList<String>();
		returnValue.add(manufacturerNameAttribute);
		returnValue.add(modelNameAttribute);
		returnValue.add(idAttribute);
		returnValue.add(sortAttribute);
		returnValue.add(colorAttribute);
		returnValue.add(productTypeAttribute);
		returnValue.add(releaseDateAttribute);
		return returnValue;
	}
	
}
