package auction;

public class MultipleItem extends Item {
	private int _amount;
	
	public MultipleItem(String name, String type, int startingPrice, int amount) {
		super(name, type, startingPrice);
		_amount = amount;
	}
	
	public void setAmount(int amount)
	{
		_amount = amount;
	}
	
	public int getAmount()
	{
		return _amount;
	}
}
