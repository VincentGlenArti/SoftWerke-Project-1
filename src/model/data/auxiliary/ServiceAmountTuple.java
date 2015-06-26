package model.data.auxiliary;

import model.data.datatypes.IService;

/**
 * Class used to pair service and service's amount within client's order.
 * 
 * @version a.2
 * @author	Boris Gordeev
 * @since 22-06-2015
 */

public class ServiceAmountTuple {
	
	private IService service;
	private int amount;
	
	public ServiceAmountTuple(IService service, int amount ) {
		this.service = service;
		this.amount = amount;
	}
	
	public IService getService() {
		return service;
	}

	public int getAmount() {
		return amount;
	}

}
