package simulation.agents;

import jade.core.behaviours.FSMBehaviour;
import simulation.behaviours.ExtendedCollectBehaviour;
import simulation.behaviours.ExtendedExploreBehaviour;
import simulation.environment.Environment;

public class FreemindAgent extends GridWorldAgent 
{

	protected int outstandingProposal = -1;
	protected int oustandingRequest = -1;
	
	@Override
	protected void setupAgent() 
	{
		// TODO Auto-generated method stub
		
		FSMBehaviour fsm = new FSMBehaviour();
		
		fsm.registerFirstState(new ExtendedExploreBehaviour(this),"explore");
		fsm.registerState(new ExtendedCollectBehaviour(this), "collect");
		

		fsm.registerTransition("explore", "collect", ExtendedExploreBehaviour.KNOWS_APPLE_AND_HAS_NO_APPLE);
		fsm.registerTransition("explore", "collect", ExtendedExploreBehaviour.KNOWS_APPLE_AND_HAS_APPLE);
		fsm.registerTransition("collect", "collect", ExtendedCollectBehaviour.HAS_APPLE);
		fsm.registerTransition("collect", "explore", ExtendedCollectBehaviour.DOESNT_KNOW_APPLES);
		
		addBehaviour(fsm);
		
	}

	@Override
	public void handleRequests() 
	{
		// TODO Auto-generated method stub
		if(mustTrade())
		{
				//do nothing leave proposals and requests.
			    //the traded proposals/requests are removed after trade.
		}
		else if(isHungry() && !hasApples())
		{
			if(oustandingRequest != -1)
				Environment.removeRequest(getLocalName(), oustandingRequest);
			
			int newPrice = getRequestToBuyPrice();
			
			oustandingRequest = Environment.requestToBuy(getLocalName(), newPrice);
		}
		else
		{
			if(oustandingRequest != -1)
			{
				Environment.removeRequest(getLocalName(), oustandingRequest);
				oustandingRequest = -1;
			}
		}
	}
	
	public int getRequestToBuyPrice()
	{
		return _startingMoney/8;
	}
	
	public int getProposeToSellPrice()
	{
		return _startingMoney/4;
	}
	
	
	//Will sell if it has greater apples
	@Override
	public void handleProposals() 
	{
		// TODO Auto-generated method stub
		if(mustTrade())
		{
			//leave
		}
		else if(hasLackOfEnergy())
		{
			if(outstandingProposal != -1)
			{
				Environment.removeProposal(getLocalName(), outstandingProposal);
				outstandingProposal = -1;
			}
		}
		else if(!hasApples())
		{
			if(outstandingProposal != -1)
			{
				Environment.removeProposal(getLocalName(), outstandingProposal);
				outstandingProposal = -1;
			}
		}
		// has apples
		else
		{
			if(_apples >= _maxAppleCapacity / 2)
			{
				if(outstandingProposal == -1)
				{
					int price = getProposeToSellPrice();
					outstandingProposal = Environment.proposeToSell(getLocalName(), price);
				}
			}
			
		}
		
	}

	@Override
	public boolean hasLackOfEnergy() 
	{
		boolean maximumEnergyGain = (_energy <= (_maxEnergy - _energyGain + _energyCost));
		boolean onlyFewStepsLeft = getRoundsLeft() < 3;
		return (maximumEnergyGain || onlyFewStepsLeft);
	}
	
}
