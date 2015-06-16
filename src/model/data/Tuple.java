package model.data;

public class Tuple {
	
	private IService service;
	private int amount;
	
	public Tuple(IService service, int amount ) {
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
