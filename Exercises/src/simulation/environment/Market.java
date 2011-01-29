package simulation.environment;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.ArrayList;

public class Market 
{
	//A map with the agentID as key, and the value is also a Map.
	//The value has as key an proposal id, and as value the minimum price of the proposal.
	private int _proposalKeyCount;
	private int _requestKeyCount;
	private Map<Integer, TradeUnit> _sellProposals;
	private Map<Integer, TradeUnit> _buyRequests;
	private List<Tuple<Integer,Integer>> _trades;
	
	private static Market _instance;
	
	private Market()
	{
		_sellProposals = new HashMap<Integer,TradeUnit>();
		_buyRequests = new HashMap<Integer,TradeUnit>();
		_trades = new ArrayList<Tuple<Integer,Integer>>();
		_proposalKeyCount = 0;
		_requestKeyCount = 0;
	}
	
	public static Market getInstance()
	{
		if(_instance == null)
		{
			_instance = new Market();
			return _instance;
		}
		return _instance;
	}
	
	//Puts a sell proposal in the market, returns the proposal id
	public int proposeToSell(String sAgent, int price)
	{
		int key = _proposalKeyCount;
		_sellProposals.put(key, new TradeUnit(sAgent, price));
		_proposalKeyCount++;
		
		return key;
	}
	
	//removes the proposal with given proposalid.
	//Returns true if entry exists, false othwerise
	public boolean removeProposal(String sAgent, int proposalId)
	{
		TradeUnit tradeUnit = _sellProposals.get(proposalId);
		if(tradeUnit != null && tradeUnit.getAgentName().equals(sAgent))
		{
			_sellProposals.remove(proposalId);
			return true;
		}
		return false;
	}
	
	public void removeAllProposals(String sAgent)
	{
		for(int key : _sellProposals.keySet())
		{
			removeProposal(sAgent, key);
		}
	}
	
	public int requestToBuy(String sAgent, int price)
	{
		int key = _requestKeyCount;
		_buyRequests.put(key, new TradeUnit(sAgent, price));
		_requestKeyCount++;
		
		return key;
	}
	
	public boolean removeRequest(String sAgent, int requestId)
	{
		TradeUnit tradeUnit = _buyRequests.get(requestId);
		if(tradeUnit != null && tradeUnit.getAgentName().equals(sAgent))
		{
			_buyRequests.remove(requestId);
			return true;
		}
		return false;
	}
	
	public void removeAllRequest(String sAgent)
	{
		for(int key : _buyRequests.keySet())
		{
			removeRequest(sAgent, key);
		}
	}
	
	public boolean removeAgent(String sAgent)
	{
		removeAllProposals(sAgent);
		removeAllRequest(sAgent);
		_trades.clear();
		return false;
	}
	
	public void clear()
	{
		_proposalKeyCount = 0;
		_requestKeyCount = 0;
		_buyRequests.clear();
		_sellProposals.clear();
		_trades.clear();
	}
	
	//Is called at the end of each round and find matches between proposals and requeest.
	public void findMatches()
	{
		//clear any remaining marked for trades (should actually not occur, but just to be sure)
		_trades.clear();
		for(TradeUnit buyRequest : _buyRequests.values())
			buyRequest.setMarkedForTrade(false);
		for(TradeUnit sellProposal : _sellProposals.values())
			sellProposal.setMarkedForTrade(false);
		
		//The map is sorted by the keys, so this way older requests get handled first.
		Set<Integer> keys = _buyRequests.keySet();
		for(int requestId : keys)
		{
			TradeUnit buyRequest = _buyRequests.get(requestId);
			List<Integer> sellProposals = getCheapestMatchingProposals(buyRequest.getPrice(), buyRequest.getAgentName());
			
			//shuffle the list.
			Collections.shuffle(sellProposals);
			if(sellProposals.size() > 0)
			{
				int sellProposalId = sellProposals.get(0);
				TradeUnit sellProposal = _sellProposals.get(sellProposalId);
				sellProposal.setMarkedForTrade(true);
				buyRequest.setMarkedForTrade(true);
				
				_trades.add(new Tuple<Integer, Integer>(requestId, sellProposalId));
			}
		}
		
		
	}
	
	private List<Integer> getCheapestMatchingProposals(int buyPrice, String sAgent)
	{
		Set<Integer> keys = _sellProposals.keySet();
		int cheapest = Integer.MAX_VALUE;
		List<Integer> resultSet = new ArrayList<Integer>();
		
		for(int proposalId : keys)
		{
			TradeUnit sellProposal = _sellProposals.get(proposalId);
			if(!sellProposal.isMarkedForTrade() && !sellProposal.getAgentName().equals(sAgent))
			{
				int sellPrice = sellProposal.getPrice();
					
				if(sellPrice <= buyPrice)
				{
					if(sellPrice < cheapest)
					{
						resultSet.clear();
						resultSet.add(proposalId);
						cheapest = sellProposal.getPrice();
					}
					else if(sellPrice == cheapest)
					{
						resultSet.add(proposalId);
					}
				}		}
		}
		
		
		return resultSet;
	}

	
	
	public boolean mustTrade(String sAgent)
	{
		for(Tuple<Integer,Integer> tuple: _trades)
		{
			int requestId = tuple.getFirst();
			int proposalId = tuple.getSecond();
						
			if(_buyRequests.get(requestId) != null && _buyRequests.get(requestId).getAgentName().equals(sAgent))
				return true;
			if(_sellProposals.get(proposalId) != null && _sellProposals.get(proposalId).getAgentName().equals(sAgent))
				return true;
		}
		return false;
	}
	
	public List<TradeDescription> getTradeAction(String sAgent)
	{
		List<TradeDescription> list = new ArrayList<TradeDescription>();
		
		for(Tuple<Integer,Integer> tuple : _trades)
		{
			TradeUnit buyRequest = _buyRequests.get(tuple.getFirst());
			TradeUnit sellProposal = _sellProposals.get(tuple.getSecond());
			
			//The price of the seller is maintained. So the buyer may get lucky.
			if(buyRequest != null && buyRequest.getAgentName().equals(sAgent))
			{
				list.add(new TradeDescription(TradeDescription.BUY, sellProposal.getPrice(), sAgent, sellProposal.getAgentName()));
			}
			else if(sellProposal != null && sellProposal.getAgentName().equals(sAgent))
			{
				list.add(new TradeDescription(TradeDescription.SELL, sellProposal.getPrice(), sAgent, buyRequest.getAgentName()));
			}
		}
		
		return list;
	}
	
	
	
}

class Tuple<T,U>
{
	private T _first;
	private U _second;
	
	public Tuple(T first,U second)
	{
		setFirst(first);
		setSecond(second);
	}
	
	public void setFirst(T first)
	{
		this._first = first;
	}
	
	public void setSecond(U second)
	{
		this._second = second;
	}
	
	public T getFirst()
	{
		return _first;
	}
	
	public U getSecond()
	{
		return _second;
	}
}

class TradeUnit
{
	private String _sAgent;
	int _price;
	boolean _markedForTrade;
	
	
	public TradeUnit(String sAgent, int price)
	{
		this._sAgent = sAgent;
		this._price = price;
		this._markedForTrade = false;
	}
	
	public String getAgentName()
	{
		return _sAgent;
	}
	
	public int getPrice()
	{
		return _price;
	}
	
	
	public boolean isMarkedForTrade()
	{
		return _markedForTrade;
	}
	
	public void setMarkedForTrade(boolean mark)
	{
		this._markedForTrade = mark;
	}
	
	
}

