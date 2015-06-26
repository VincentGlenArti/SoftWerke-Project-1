package model.data.datatypes;

import java.util.*;

import model.data.auxiliary.ColorDiscrete;
import model.data.auxiliary.ProductType;
import model.data.auxiliary.ServiceType;

/**
 * Data class for describing product.
 * 
 * @version b.1
 * @author	Boris Gordeev
 * @since 25-06-2015
 */

public class Product extends DataType implements IService {
	
	private static int globalID;
	private static final List<String> propertyNamesAsList =
			Collections.unmodifiableList(new ArrayList<String>() {{
				add("manufacturerName"); 
				add("modelName");
				add("releaseDate"); 
				add("color"); 
				add("productType");
			}});
	
	private String manufacturerName;
	private ProductType productType;
	private String modelName;
	private ColorDiscrete color;
	private Calendar releaseDate;
	
	/**
	 * Default constructor.
	 * Does not allow null input.
	 * Sets primary key.
	 * 
	 * @throws IllegalArgumentException
	 */
	public Product(String manufacturerName, ProductType productType,
			String modelName, ColorDiscrete color, Calendar releaseDate)
			throws IllegalArgumentException {
		super(globalID, setPropertiesAsMap(manufacturerName, productType,
				modelName, color, releaseDate));
		if (
				manufacturerName == null ||
				productType == null ||
				modelName == null ||
				color == null ||
				releaseDate == null)
			throw new IllegalArgumentException();
		this.manufacturerName = manufacturerName;
		this.productType = productType;
		this.modelName = modelName;
		this.color = color;
		this.releaseDate = releaseDate;
		globalID++;
	}
	
	/**
	 * Nullable constructor. Use to build object that you are going to use
	 * to compare with.
	 * Allows null input.
	 * Manual primary key.
	 */
	public Product(Integer id, String manufacturerName, ProductType productType,
			String modelName, ColorDiscrete color, Calendar releaseDate){
		super(id, setPropertiesAsMap(manufacturerName, productType,
				modelName, color, releaseDate));
		this.manufacturerName = manufacturerName;
		this.productType = productType;
		this.modelName = modelName;
		this.color = color;
		this.releaseDate = releaseDate;
	}
	
	/**
	 * Copy constructor.
	 * Throws an exception if "addItem" has any null fields, which is checked
	 * using "hasNullProperties". Throws an exception if so.
	 * Sets primary key.
	 * 
	 * @throws IllegalArgumentException
	 */
	public Product(Product addItem) throws IllegalArgumentException {
		super(globalID, setPropertiesAsMap(
				addItem.getManufacturerName(),
				addItem.getProductType(),
				addItem.getModelName(),
				addItem.getColor(),
				addItem.getReleaseDate()));
		if (addItem.hasNullProperties()) throw new IllegalArgumentException();
		this.manufacturerName = addItem.getManufacturerName();
		this.productType = addItem.getProductType();
		this.modelName = addItem.getModelName();
		this.color = addItem.getColor();
		this.releaseDate = addItem.getReleaseDate();
		globalID++;
	}
	
	
	/**
	 * Returns a map<key, value> object, where "key" is
	 * supplied property name and "value" is supplied property value.
	 */
	private static Map<String, Object> setPropertiesAsMap(
			final String manufacturerName, final ProductType productType,
			final String modelName, final ColorDiscrete color, 
			final Calendar releaseDate) {
		return Collections.unmodifiableMap(
			new HashMap<String, Object>(){{ 
				put("manufacturerName", manufacturerName);
				put("productType", productType);
				put("modelName", modelName);
				put("color", color);
				put("releaseDate", releaseDate);
		}});
	}
	
	public Calendar getReleaseDate() {
		return releaseDate;
	}
	
	public String getManufacturerName() {
		return manufacturerName;
	}
	
	public String getModelName() {
		return modelName;
	}
	
	public ColorDiscrete getColor() {
		return color;
	}
	
	public ProductType getProductType(){
		return productType;
	}
	
	public static final List<String> getPropertyNamesAsList() {
		return propertyNamesAsList;
	}
	
	@Override
	public final DataTypeEnum getType() {
		return DataTypeEnum.Product;
	}
	
	@Override
	public final ServiceType getServiceType() { 
		return ServiceType.Product; 
	}
	
	@Override
	public String toString() {
		StringBuilder returnValue = new StringBuilder("");
		returnValue.append(productType.toString())
			.append(" ")
			.append(manufacturerName)
			.append(" ")
			.append(modelName)
			.append(" ")
			.append(releaseDate.toString())
			.append(" ")
			.append(color.toString());
		return returnValue.toString();
	}
}
