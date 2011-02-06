package simulation.agents;

import simulation.behaviours.*;
import jade.core.behaviours.FSMBehaviour;
import jade.lang.acl.ACLMessage;

public class CommunistAgent extends GridWorldAgent 
{
	
	private int _lastPriceRequested = -1;

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
		// TODO Auto-generated method stub
		if(_lastPriceRequested == -1)
		{
			_lastPriceRequested = 1;
		}
		else
		{
			_lastPriceRequested++;
		}
		
		return _lastPriceRequested;
	}

	public void applesTraded(boolean success)
	{
		super.applesTraded(success);
		_lastPriceRequested = -1;
	}
	@Override
	public int getProposeToSellPrice() {
		// TODO Auto-generated method stub
		return 1;
	}


}
