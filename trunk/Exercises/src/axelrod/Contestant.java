package axelrod;

import jade.core.AID;

public class Contestant 
{
	private AID _agentId;
	private String _strategy;
	
	public Contestant(AID agentId, String strategy)
	{
		this._agentId = agentId;
		this._strategy = strategy;
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
}
