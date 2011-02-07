package simulation.agents;

import java.util.HashMap;
import java.util.Map;

import simulation.behaviours.*;
import simulation.environment.Environment;
import simulation.environment.Market;
import jade.core.behaviours.FSMBehaviour;
import jade.lang.acl.ACLMessage;

public class LiberalAgent extends GridWorldAgent 
{

	private Map<Integer,Integer> _oustandingRequests = new HashMap<Integer,Integer>();
	private Map<Integer,Integer> _oustandingProposals = new HashMap<Integer,Integer>();
	

	@Override
	protected void setupAgent() 
	{
		// TODO Auto-generated method stub
		
	// TODO Auto-generated method stub
		
		FSMBehaviour fsm = new FSMBehaviour();
		
		fsm.registerFirstState(new ExtendedExploreBehaviour(this),"explore");
		fsm.registerState(new ExtendedCollectBehaviour(this), "collect");
		

		fsm.registerTransition("explore", "collect", ExtendedExploreBehaviour.KNOWS_APPLE_AND_HAS_NO_APPLE);
		fsm.registerTransition("explore", "explore", ExtendedExploreBehaviour.KNOWS_APPLE_AND_HAS_APPLE);
		fsm.registerTransition("collect", "explore", ExtendedCollectBehaviour.HAS_APPLE);
		fsm.registerTransition("collect", "explore", ExtendedCollectBehaviour.DOESNT_KNOW_APPLES);
		
		addBehaviour(fsm);
		
	}
	
	public boolean hasLackOfEnergy()
	{
		
		return (_energy <= (_maxEnergy - (_energyGain*2) + _energyCost));
	}
	
	@Override
	public void handleRequests() 
	{
		
		if(mustTrade())
		{
			//leave requests
		}
		else if(isHungry() && !hasApples())
		{
			//make an emergency bid.
			Environment.removeAllRequests(getLocalName());
			_oustandingRequests.clear();
			
			int price = getRequestToBuyPrice(true, 0);
			int id = Environment.requestToBuy(getLocalName(), price);
			_oustandingRequests.put(id, price);
		}
		//
		else
		{
			int apples = _apples;
			int maxCapacity = _maxAppleCapacity;
			
			//make profitable bids
			
			Environment.removeAllRequests(getLocalName());
			_oustandingRequests.clear();
			
			int moneySpend = 0;
			for(int i=0; i<maxCapacity-apples; i++)
			{
				int price = getRequestToBuyPrice(false, moneySpend);
				int id = Environment.requestToBuy(getLocalName(), price);
				_oustandingRequests.put(id, price);
				moneySpend += price;
			}
			
		}
	}


	@Override
	public void handleProposals() 
	{
		if(mustTrade())
		{
			//leave proposals
		}
		else if(hasLackOfEnergy())
		{
			//make sure there are x-1 proposals.
			int proposals = _apples - 1;
			int newPrice = getProposeToSellPrice();
			
			
			Environment.removeAllProposals(getLocalName());
			_oustandingProposals.clear();
			
			for(int i=0; i<proposals; i++)
			{
				int price = newPrice;
				int id = Environment.proposeToSell(getLocalName(), price);
				_oustandingProposals.put(id, price);
			}
			
		}
		else
		{
			int proposals = _apples;
			int newPrice = getProposeToSellPrice();
			
			Environment.removeAllProposals(getLocalName());
			_oustandingProposals.clear();
			
			
			for(int i=0; i<proposals; i++)
			{
				int price = newPrice;
				int id = Environment.proposeToSell(getLocalName(), price);
				_oustandingProposals.put(id, price);
			}
		}
			 
	}
	
	
	public int getRequestToBuyPrice(boolean emergency, int moneySpendSoFar) 
	{
		if(_money <= moneySpendSoFar)
			return 0;
		
		if(emergency)
		{
			int roundsLeft = getRoundsLeft();
			
			if(roundsLeft <= 0)
				return _money;
			else
			{
				double fraction = (1.0d / Math.pow(roundsLeft, 2));
				int priceToPay = ((int) Math.ceil(fraction * (_money - moneySpendSoFar)));
				
				if(priceToPay == 0)
					return 1;
				else
					return priceToPay;
			}		
		}
		else
		{
			return 1;
		}	
	}

	

	public int getProposeToSellPrice() 
	{
		
		int lastPrice = (getStartingMoney() / 10);

		//The bottom price is the minimum price an apple is bought for.
		int bottomPrice = Integer.MAX_VALUE;
		for(int i=0; i<_succesfullBuys.size(); i++)
		{
			if(_succesfullBuys.get(i) < bottomPrice)
				bottomPrice = _succesfullBuys.get(i);
		}
		if(bottomPrice == Integer.MAX_VALUE)
			bottomPrice = 1;
		
		//The highest succesfull sell price is a good starting point.
		int highestPrice = 0;
		for(int i=0; i<_succesfullSells.size(); i++)
		{
			if(_succesfullSells.get(i) > highestPrice )
				highestPrice = _succesfullSells.get(i);
		}
		
		int startingPrice = (getStartingMoney() / 10);
		if(highestPrice != 0)
			startingPrice = highestPrice + 2;
		
		
		if(!_oustandingProposals.isEmpty())
		{
			for(int price : _oustandingProposals.values())
			{
				lastPrice = price;
				break;
			}
		}
		
		if(lastPrice > bottomPrice + 1)
			return lastPrice-1;
		else
			return bottomPrice + 1;
	}





}
