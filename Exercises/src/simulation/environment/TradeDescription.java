package simulation.environment;

public class TradeDescription 
{
	private int _action;
	private int _price;
	private String _thisAgentName;
	private String _otherAgentName;
	
	public static final int SELL = 1;
	public static final int BUY = 0;
	
	public TradeDescription(int action, int price, String thisAgentName, String otherAgentName)
	{
		this._action = action;
		this._price = price;
		this._thisAgentName = thisAgentName;
		this._otherAgentName = otherAgentName;
	}
	
	public int getAction()
	{
		return this._action;
	}
	
	public int getPrice()
	{
		return this._price;
	}
	
	public String getThisAgentName()
	{
		return this._thisAgentName;
	}
	
	public String getOtherAgentName()
	{
		return this._otherAgentName;
	}
	
}
