package simulation.agents;

import simulation.behaviours.*;
import simulation.environment.Market;
import jade.core.behaviours.FSMBehaviour;
import jade.lang.acl.ACLMessage;

public class LiberalAgent extends GridWorldAgent 
{
	

	@Override
	protected void setupAgent() 
	{
		// TODO Auto-generated method stub
		
		FSMBehaviour fsm = new FSMBehaviour();
		
		fsm.registerFirstState(new ExtendedExploreBehaviour(this),"explore");
		fsm.registerState(new ExtendedCollectBehaviour(this), "collect");
		fsm.registerState(new RequestToBuyAction(this), "requestToBuy");
		fsm.registerState(new ProposeToSellAction(this), "proposeToSell");
		fsm.registerState(new TradeAction(this), "trade");
		fsm.registerState(new EatAppleAction(this), "eatAppleExplore");
		fsm.registerState(new EatAppleAction(this), "eatAppleCollect");
		
		fsm.registerTransition("explore", "trade", ExtendedExploreBehaviour.MUST_TRADE_NEXT_ROUND);
		fsm.registerTransition("explore", "eatAppleExplore", ExtendedExploreBehaviour.LACK_OF_ENERGY_AND_HAS_APPLE);
		fsm.registerTransition("explore", "collect", ExtendedExploreBehaviour.KNOWS_APPLE_AND_HAS_NO_APPLE);
		fsm.registerTransition("explore", "requestToBuy",ExtendedExploreBehaviour.IS_HUNGRY_AND_HAS_NO_APPLE);
		fsm.registerTransition("explore", "proposeToSell", ExtendedExploreBehaviour.HAS_APPLE);
		
		fsm.registerTransition("collect", "trade", ExtendedCollectBehaviour.MUST_TRADE_NEXT_ROUND);
		fsm.registerTransition("collect", "eatAppleCollect", ExtendedCollectBehaviour.LACK_OF_ENERGY_AND_HAS_APPLE);
		fsm.registerTransition("collect", "requestToBuy", ExtendedCollectBehaviour.IS_HUNGRY_AND_HAS_NO_APPLE);
		fsm.registerTransition("collect", "proposeToSell", ExtendedCollectBehaviour.HAS_APPLE);
		fsm.registerTransition("collect", "explore", ExtendedCollectBehaviour.DOESNT_KNOW_APPLES);

		fsm.registerTransition("trade", "explore", TradeAction.SUCCESS);
		
		fsm.registerTransition("eatAppleExplore", "explore", EatAppleAction.EAT_APPLE_SUCCES);
		fsm.registerTransition("eatAppleExplore", "explore", EatAppleAction.EAT_APPLE_FAILURE);
		
		fsm.registerTransition("requestToBuy", "explore", RequestToBuyAction.SUCCESS);
		fsm.registerTransition("proposeToSell", "explore", ProposeToSellAction.SUCCESS);
		
		fsm.registerTransition("eatAppleCollect", "collect", EatAppleAction.EAT_APPLE_SUCCES);
		fsm.registerTransition("eatAppleCollect", "collect", EatAppleAction.EAT_APPLE_FAILURE);
		
		
		addBehaviour(fsm);
	}

	@Override
	public int getRequestToBuyPrice() 
	{
		
		int lastPrice = 0;
		if(hasOutstandingRequest())
		{
			lastPrice =  Market.getInstance().getRequestPrice(getLocalName(), getOutstandingRequest());
		}
		
		
		if( lastPrice == 0 && _money >= 1)
		{
			return 1;
		}
		else if(_money >= (lastPrice * 2 ))
		{
			return (lastPrice * 2);
		}
		else if(_money >= lastPrice + 1)
		{
			return lastPrice + 1;
		}
		else if(_money >= lastPrice) 
		{
			return lastPrice;
		}
		else
		{
			return _money;
		}		
	}

	
	@Override
	public int getProposeToSellPrice() 
	{
		
		int lastPrice = (getStartingMoney() / 10);
		if(hasOutstandingProposal())
			lastPrice = Market.getInstance().getProposalPrice(getLocalName(), getOutstandingProposal());
		
		if(lastPrice > 1)
			return lastPrice-1;
		else
			return 1;
	}


}
