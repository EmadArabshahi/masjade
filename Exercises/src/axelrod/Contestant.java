package axelrod;

import jade.core.AID;

public class Contestant 
{
	private AID _agentId;
	private String _strategy;
	private int _totalUtility;
	
	public Contestant(AID agentId, String strategy)
	{
		this._agentId = agentId;
		this._strategy = strategy;
		this._totalUtility = 0;
	}
	
	public AID getAID()
	{
		return this._agentId; 
	}
	
	public String getStrategy()
	{
		return this._strategy;
	}
	
	public String toString()
	{
		return getAID().getLocalName() + "(" + getStrategy() + ")";
	}

	public int getTotalUtility() {
		return _totalUtility;
	}
	
	public void addUtility(int utility)
	{
		_totalUtility += utility;
	}

	public void reset() {
		_totalUtility = 0;
	}
}
