package auction;

import jade.core.AID;

public class Item {
	private String _name;
	private String _type;
	private int _startingPrice;
	private int _highestBid = 0;
	private AID _highestBidder;
	
	public Item(String name, String type, int startingPrice) 
	{
		_name = name;
		_type = type;
		_startingPrice = startingPrice;
	}
	
	public void setName(String name)
	{
		_name = name;
	}
	
	public void setType(String type)
	{
		_type = type;
	}
	
	public void setPrice(int startingPrice)
	{
		_startingPrice = startingPrice;
	}
	
	public String getName()
	{
		return _name;
	}
	
	public String getType()
	{
		return _type;
	}
	
	public int getPrice()
	{
		return _startingPrice;
	}
	
	public int getHighestBid() {
		return _highestBid;
	}

	public void setHighestBid(int newBid) {
		_highestBid = newBid;
	}
	
	public AID getHighestBidder()
	{
		return _highestBidder;
	}

	public void setHighestBidder(AID bidder) {
		_highestBidder = bidder;
	}
}
