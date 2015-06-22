package model.data;

import java.util.*;

/**
 * Data class for describing product.
 * 
 * @version a.2
 * @author	Boris Gordeev
 * @since 22-06-2015
 */

public class Product implements IService {
	
	private static int globalID;
	
	private int id;
	private String manufacturerName;
	private ProductType productType;
	private String modelName;
	private ColorDiscrete color;
	private Calendar releaseDate;
	
	public Product(String manufacturerName, ProductType productType,
			String modelName, ColorDiscrete color, Calendar releaseDate) {
		this.manufacturerName = manufacturerName;
		this.productType = productType;
		this.modelName = modelName;
		this.color = color;
		this.releaseDate = releaseDate;
		this.id = globalID;
		globalID++;
	}
	
	public int getID() {
		return id;
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
	
	@Override
	public final ServiceType getType() { 
		return ServiceType.Product; 
	}
	
	@Override
	public String toString() {
		return productType.toString() + " " + manufacturerName + " " +
				modelName + " " +  releaseDate.toString() + " " + color.toString();
	}
}
