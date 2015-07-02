package model.storing;

import java.util.*;

import controller.operations.*;
import model.data.datatypes.*;
import exceptions.*;

/**
 * Class for storing information about currently used by data storage
 * classes. Be aware that objects on both controller and model layer might
 * reference info stored there.
 * 
 * @version b.3
 * @author	Boris Gordeev
 * @since 02-07-2015
 */

public final class DataStorageInfo {
	
	/**
	 * If you need to add your data type to this model, hard code it here.
	 * Model will automatically use it.
	 */
	public static final Map<DataTypeEnum, DataType> dataTypesUsed =
			Collections.unmodifiableMap(new EnumMap<DataTypeEnum, DataType> (
					DataTypeEnum.class) {{
						put(DataTypeEnum.Client, 
								Client.getEmptyInstance());
						
						put(DataTypeEnum.Order, 
								Order.getEmptyInstance());
						
						put(DataTypeEnum.Product,
								Product.getEmptyInstance());
					}});
	
	/**
	 * If you need to add your model-layer operation to this model,
	 * hard code it here.
	 * Controller will automatically use it.
	 */
	public final static Map<OperationEnum, IOperation> operationsUsed =
			Collections.unmodifiableMap(new EnumMap<OperationEnum, IOperation> (
					OperationEnum.class) {{
				put(OperationEnum.Select, new Select());
				put(OperationEnum.Add,  new Add());
				put(OperationEnum.GetByID, new GetByID());
			}});

}
