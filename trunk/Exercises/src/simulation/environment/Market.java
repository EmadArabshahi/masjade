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
	private volatile int _proposalKeyCount;
	private volatile int _requestKeyCount;
	private volatile Map<Integer, TradeUnit> _sellProposals;
	private volatile Map<Integer, TradeUnit> _buyRequests;
	private volatile List<Tuple<Integer,Integer>> _trades;
	private volatile Map<String, Boolean> _tradeOutcomes;
	
	private volatile Map<String, List<Integer>> _succesfullBuys;
	private volatile Map<String, List<Integer>> _succesfullSells;
	
	private static Market _instance;
	
	private Market()
	{
		_sellProposals = new HashMap<Integer,TradeUnit>();
		_buyRequests = new HashMap<Integer,TradeUnit>();
		_trades = new ArrayList<Tuple<Integer,Integer>>();
		_tradeOutcomes = new HashMap<String, Boolean>();
		_succesfullBuys = new HashMap<String, List<Integer>>();
		_succesfullSells = new HashMap<String, List<Integer>>();
		
		_proposalKeyCount = 0;
		_requestKeyCount = 0;
	}
	
	public synchronized static Market getInstance()
	{
		if(_instance == null)
		{
			_instance = new Market();
			return _instance;
		}
		return _instance;
	}
	
	public synchronized List<Integer> getSuccesfullBuys(String sAgent)
	{
		return _succesfullBuys.get(sAgent);
	}
	
	public synchronized List<Integer> getSuccesfullSells(String sAgent)
	{
		return _succesfullSells.get(sAgent);
	}
	
	public synchronized void clearTradedActions()
	{
		
		for(Tuple<Integer,Integer> tuple : _trades)
		{
			TradeUnit buy = _buyRequests.get(tuple.getFirst());
			TradeUnit sell = _sellProposals.get(tuple.getSecond());
			
			if(buy != null && sell != null)
			{
				//The buy action is performed
				if( _tradeOutcomes.get(buy.getAgentName()) != null && _tradeOutcomes.get(buy.getAgentName()))
				{
					//the sell action is performed
					if(_tradeOutcomes.get(sell.getAgentName()) != null && _tradeOutcomes.get(sell.getAgentName()))
					{
						_buyRequests.remove(tuple.getFirst());
						_sellProposals.remove(tuple.getSecond());
						
						
						if(_succesfullBuys.containsKey(buy.getAgentName()))
						{
							_succesfullBuys.get(buy.getAgentName()).add(sell.getPrice());
						}
						else
						{
							List<Integer> l = new ArrayList<Integer>();
							l.add(sell.getPrice());
							_succesfullBuys.put(buy.getAgentName(), l);
						}
						if(_succesfullSells.containsKey(sell.getAgentName()))
						{
							_succesfullSells.get(sell.getAgentName()).add(sell.getPrice());
							
						}
						else
						{
							List<Integer> l = new ArrayList<Integer>();
							l.add(sell.getPrice());
							_succesfullSells.put(sell.getAgentName(), l);
						}
						
					}
				}
				
			}
			
			
		}
	}
	
	
	public synchronized void registerTradeOutcome(String sAgent, boolean outcome)
	{
		_tradeOutcomes.put(sAgent, outcome);
	}
	
	public synchronized  boolean registeredForTrade(String sAgent)
	{
		if(_tradeOutcomes.containsKey(sAgent))
		{
			Boolean canTrade = _tradeOutcomes.get(sAgent);
			return canTrade;
		}
		return false;
	}
	
	
	public synchronized int getRequestPrice(String sAgent, int requestId)
	{
		TradeUnit t = _buyRequests.get(requestId);
		if(t == null)
			return 0;
		else
			return t.getPrice();
	}
	
	
	public synchronized int getProposalPrice(String sAgent, int proposalId)
	{
		TradeUnit t = _sellProposals.get(proposalId);
		if(t==null)
			return 0;
		else
			return t.getPrice();
	}
	
	//Puts a sell proposal in the market, returns the proposal id
	public synchronized int proposeToSell(String sAgent, int price)
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
	
	public synchronized void removeAllProposals(String sAgent)
	{
		List<Integer> keysToRemove = new ArrayList<Integer>();
		
		for(int key : _sellProposals.keySet())
		{
			if(_sellProposals.get(key).getAgentName().equals(sAgent))
				keysToRemove.add(key);
		}
		
		for(int i=0; i<keysToRemove.size(); i++)
		{
			removeProposal(sAgent, keysToRemove.get(i));
		}
	}
	
	public synchronized int requestToBuy(String sAgent, int price)
	{
		int key = _requestKeyCount;
		_buyRequests.put(key, new TradeUnit(sAgent, price));
		_requestKeyCount++;
		
		return key;
	}
	
	public synchronized boolean removeRequest(String sAgent, int requestId)
	{
		TradeUnit tradeUnit = _buyRequests.get(requestId);
		if(tradeUnit != null && tradeUnit.getAgentName().equals(sAgent))
		{
			_buyRequests.remove(requestId);
			return true;
		}
		return false;
	}
	
	public synchronized void removeAllRequest(String sAgent)
	{
		List<Integer> keysToRemove = new ArrayList<Integer>();
		for(int key : _buyRequests.keySet())
		{
			if(_buyRequests.get(key).getAgentName().equals(sAgent))
				keysToRemove.add(key);
		}
		
		for(int i=0; i<keysToRemove.size(); i++)
		{
			removeRequest(sAgent, keysToRemove.get(i));
		}
		
		
	}
	
	public synchronized boolean removeAgent(String sAgent)
	{
		removeAllProposals(sAgent);
		removeAllRequest(sAgent);
		_trades.clear();
		return false;
	}
	
	public synchronized void clear()
	{
		_proposalKeyCount = 0;
		_requestKeyCount = 0;
		_buyRequests.clear();
		_sellProposals.clear();
		_trades.clear();
		_tradeOutcomes.clear();
	}
	
	//Is called at the end of each round and find matches between proposals and requeest.
	public synchronized void findMatches()
	{
		//clear any remaining marked for trades (should actually not occur, but just to be sure)
		_trades.clear();
		_tradeOutcomes.clear();
		
		System.out.println("************** BEGIN FIND MATCHES **************");
		
		for(TradeUnit buyRequest : _buyRequests.values())
		{
			System.out.println("BUYREQUEST: " + buyRequest.getAgentName() + " : " + buyRequest.getPrice());
			buyRequest.setMarkedForTrade(false);
		}
		for(TradeUnit sellProposal : _sellProposals.values())
		{
			System.out.println("SELLPROPOSAL: " + sellProposal.getAgentName() + " : " + sellProposal.getPrice());
			sellProposal.setMarkedForTrade(false);
		}
		//The map is so.
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
				
				System.out.println("FOUND MATCH:   buyRequest: " + buyRequest.getAgentName() + " : " + buyRequest.getPrice() 
						+ " sellProposal" + sellProposal.getAgentName() + " : " + sellProposal.getPrice());
				
				
				_trades.add(new Tuple<Integer, Integer>(requestId, sellProposalId));
			}
		}
		
		for(Tuple<Integer,Integer> t : _trades)
		{
			System.out.println("In _trades: " + t.getFirst() + " - " + t.getSecond());
		}
		
		System.out.println("************** END FIND MATCHES **************");
		
	}
	
	private synchronized List<Integer> getCheapestMatchingProposals(int buyPrice, String sAgent)
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

	public synchronized boolean mustTrade(String sAgent)
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
	
	public synchronized List<TradeDescription> getTradeAction(String sAgent)
	{
		List<TradeDescription> list = new ArrayList<TradeDescription>();
		
		for(Tuple<Integer,Integer> tuple : _trades)
		{
			TradeUnit buyRequest = _buyRequests.get(tuple.getFirst());
			TradeUnit sellProposal = _sellProposals.get(tuple.getSecond());
			
			//The price of the seller is maintained. So the buyer may get lucky.
			if(sellProposal != null && buyRequest != null && buyRequest.getAgentName().equals(sAgent))
			{
				list.add(new TradeDescription(TradeDescription.BUY, sellProposal.getPrice(), tuple.getFirst(), sAgent, sellProposal.getAgentName()));
			}
			else if(sellProposal != null && buyRequest != null && sellProposal.getAgentName().equals(sAgent))
			{
				list.add(new TradeDescription(TradeDescription.SELL, sellProposal.getPrice(), tuple.getSecond(), sAgent, buyRequest.getAgentName()));
			}
		}
		
		return list;
	}
	
	
	public synchronized int[] getPricesPaidForApples()
	{
		List<Integer> pricesPaid = new ArrayList<Integer>();
		
		for(Tuple<Integer,Integer> tuple : _trades)
		{
			TradeUnit buyRequest = _buyRequests.get(tuple.getFirst());
			TradeUnit sellProposal = _sellProposals.get(tuple.getSecond());
			
			//if the agent who wants to buy is registered as succesfull trade outcome in the _tradeoucomes list.
			if(buyRequest != null && _tradeOutcomes.get(buyRequest.getAgentName()) != null && _tradeOutcomes.get(buyRequest.getAgentName()))
			{
				//And the agent who wants to sell is also registered as succesfull trade outcome in the _tradeoutcomes list.
				if(sellProposal != null && _tradeOutcomes.get(sellProposal.getAgentName()) != null && _tradeOutcomes.get(sellProposal.getAgentName()))
				{
					pricesPaid.add(sellProposal.getPrice());
				}
			}
		}
		
		
		//convert list<Integer> to int[].;
		int[] intArray = new int[pricesPaid.size()];
		for(int i=0; i<intArray.length; i++)
		{
			intArray[i] = pricesPaid.get(i);
		}
		return intArray;
	}
	
	public synchronized int[] getPricesInMarket()
	{
		List<Integer> pricesInMarket = new ArrayList<Integer>();
		
		for(TradeUnit u : _sellProposals.values())
		{
			pricesInMarket.add( u.getPrice() ) ;
		}
		
		//convert list<Integer> to int[].;
		int[] intArray = new int[pricesInMarket.size()];
		for(int i=0; i<intArray.length; i++)
		{
			intArray[i] = pricesInMarket.get(i);
		}
		return intArray;
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

