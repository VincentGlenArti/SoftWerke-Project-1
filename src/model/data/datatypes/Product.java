package model.data.datatypes;

import java.util.*;

import model.data.auxiliary.*;
import model.data.comparators.*;
import model.data.datatypes.factory.*;

/**
 * Data class for describing product.
 * 
 * @version b.2
 * @author	Boris Gordeev
 * @since 29-06-2015
 */

public final class Product extends DataType implements IService {
	
	private static final List<String> propertyNamesAsList =
			Collections.unmodifiableList(new ArrayList<String>() {{
				add("manufacturerName"); 
				add("modelName");
				add("releaseDate"); 
				add("color"); 
				add("productType");
			}});
	private static final Map<String, Comparator<Object>> comparatorsMap =
			Collections.unmodifiableMap(new HashMap<String, 
					Comparator<Object>>() {{
				put("ID", new IDComparator());
				put("manufacturerName", new ProductManufacturerComparator());
				put("releaseDate", new ProductReleaseDateComparator());
				put("productType", new ProductTypeComparator());
				put("modelName", new ProductNameComparator());
			}});
	
	private String manufacturerName;
	private ProductType productType;
	private String modelName;
	private ColorDiscrete color;
	private Date releaseDate;
	
	private Product(boolean generateID, Number manualID,
			String manufacturerName, ProductType productType,
			String modelName, ColorDiscrete color, Date releaseDate) {
		super(generateID, manualID , setPropertiesAsMap(manufacturerName, productType,
				modelName, color, releaseDate));
		this.manufacturerName = manufacturerName;
		this.productType = productType;
		this.modelName = modelName;
		this.color = color;
		this.releaseDate = releaseDate;
	}
	
	/**
	 * Constructs a new instance of this class.
	 * If generateID is true, manualID will be ignored.
	 * if generateID is true but any input parameters besides manualID
	 * are null, then an exception will be thrown.
	 * 
	 * @throws IllegalArgumentException
	 */
	public static Product getInstance(boolean generateID, 
			Number manualID, String manufacturerName,
			ProductType productType, String modelName, ColorDiscrete color,
			Date releaseDate) throws IllegalArgumentException {
		if (generateID) {
			if (manufacturerName == null ||
					productType == null ||
					modelName == null ||
					color == null ||
					releaseDate == null) {
				throw new IllegalArgumentException("ID will be " +
					"generated only if all required fields are not null");
			}
		}
		
		
		return new Product(generateID, manualID, manufacturerName, productType,
				modelName, color, releaseDate);
	}
	
	/**
	 * Returns a map<key, value> object, where "key" is
	 * supplied property name and "value" is supplied property value.
	 */
	private final static Map<String, Object> setPropertiesAsMap(
			final String manufacturerName, final ProductType productType,
			final String modelName, final ColorDiscrete color, 
			final Date releaseDate) {
		return Collections.unmodifiableMap(
			new HashMap<String, Object>(){{ 
				put("manufacturerName", manufacturerName);
				put("productType", productType);
				put("modelName", modelName);
				put("color", color);
				put("releaseDate", releaseDate);
		}});
	}
	
	public Date getReleaseDate() {
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
	
	@Override
	public final Map<String, Comparator<Object>> getComparatorsMap() {
		return comparatorsMap;
	}
	
	@Override
	public final IFactory getFactory() {
		return new ProductFactory();
	}
	
	@Override
	public final List<String> getPropertyNamesAsList() {
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
