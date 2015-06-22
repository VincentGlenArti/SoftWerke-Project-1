package model.data;

/**
 * Interface used to describe services that can be accounted in order
 * such as a "Product" or a "BankOperation".
 * 
 * @version a.2
 * @author	Boris Gordeev
 * @since 22-06-2015
 */

public interface IService {
	
	/**
	 * Show which IService derived class this object responds to.
	 */
	ServiceType getType();
	
}
