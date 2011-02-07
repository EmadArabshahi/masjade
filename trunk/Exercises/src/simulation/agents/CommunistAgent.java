package simulation.agents;

import simulation.behaviours.*;
import simulation.environment.Environment;
import simulation.environment.Market;
import jade.core.behaviours.FSMBehaviour;
import jade.lang.acl.ACLMessage;

public class CommunistAgent extends GridWorldAgent 
{
	int oustandingRequest = -1;
	int outstandingProposal;

	
	@Override
	protected void setupAgent() 
	{
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

	@Override
	public boolean hasLackOfEnergy()
	{
		boolean maximumEnergyGain = (_energy <= (_maxEnergy - _energyGain + _energyCost));
		boolean onlyFewStepsLeft = getRoundsLeft() < 3;
		return (maximumEnergyGain || onlyFewStepsLeft);
	}
	
	@Override
	public void handleRequests() 
	{
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
	
	@Override
	public void handleProposals() 
	{
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
			if(outstandingProposal == -1)
			{
				int price = getProposeToSellPrice();
				outstandingProposal = Environment.proposeToSell(getLocalName(), price);
			}
		}
	}
	
	public int getRequestToBuyPrice() 
	{
		//The price is always an emergency bid.
		//it should depend on how much energy left and money.
		
		//If energyleft for 1 round only then bid 100% of money.
		//if erngyleft for 2 rounds pit 50% of money.
		//If energyLeft for 3 rounds bid 25% of money,, etc..
		int roundsLeft = getRoundsLeft();
		
		if(roundsLeft <= 0)
			return _money;
		else
		{
			double fraction = (1.0d / Math.pow(roundsLeft, 2));
			int priceToPay = ((int) Math.ceil(fraction * _money));
			
			if(priceToPay == 0)
				return 1;
			else
				return priceToPay;
		}		
		
	}

	public int getProposeToSellPrice()
	{
		return 1;
	}


}
