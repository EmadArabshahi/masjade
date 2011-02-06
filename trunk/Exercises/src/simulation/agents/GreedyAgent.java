package simulation.agents;

import simulation.behaviours.*;
import jade.core.behaviours.FSMBehaviour;
import jade.lang.acl.ACLMessage;

public class GreedyAgent extends GridWorldAgent 
{
	
	

	@Override
	protected void setupAgent() 
	{
		// TODO Auto-generated method stub
		
		FSMBehaviour fsm = new FSMBehaviour();
		
		fsm.registerFirstState(new SimpleExploreBehaviour(this),"explore");
		fsm.registerState(new SimpleCollectBehaviour(this), "collect");
		fsm.registerState(new EatAppleAction(this), "eatAppleExplore");
		fsm.registerState(new EatAppleAction(this), "eatAppleCollect");
		
		fsm.registerTransition("explore", "eatAppleExplore", SimpleExploreBehaviour.LACK_OF_ENERGY_AND_HAS_APPLE);
		fsm.registerTransition("explore", "explore", SimpleExploreBehaviour.MUST_TRADE_NEXT_ROUND);
		fsm.registerTransition("explore", "collect", SimpleExploreBehaviour.KNOWS_APPLES);
		
		fsm.registerTransition("collect", "eatAppleCollect", SimpleCollectBehaviour.LACK_OF_ENERGY_AND_HAS_APPLE);
		fsm.registerTransition("collect", "explore", SimpleCollectBehaviour.DOESNT_KNOW_APPLES);
		fsm.registerTransition("collect", "collect", SimpleCollectBehaviour.MUST_TRADE_NEXT_ROUND);
		
		fsm.registerTransition("eatAppleExplore", "explore", EatAppleAction.EAT_APPLE_SUCCES);
		fsm.registerTransition("eatAppleExplore", "explore", EatAppleAction.EAT_APPLE_FAILURE);
		
		fsm.registerTransition("eatAppleCollect", "collect", EatAppleAction.EAT_APPLE_SUCCES);
		fsm.registerTransition("eatAppleCollect", "collect", EatAppleAction.EAT_APPLE_FAILURE);
		
		
		addBehaviour(fsm);
	}

	@Override
	public int getRequestToBuyPrice() 
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getProposeToSellPrice() {
		// TODO Auto-generated method stub
		return 0;
	}


}
