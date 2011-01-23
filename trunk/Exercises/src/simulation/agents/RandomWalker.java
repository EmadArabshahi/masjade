package simulation.agents;

import jade.core.behaviours.FSMBehaviour;
import jade.lang.acl.ACLMessage;
import simulation.behaviours.RandomWalkBehaviour;

public class RandomWalker extends GridWorldAgent
{
	
	public void messageReceived(ACLMessage message)
	{};
	
	protected void setupAgent()
	{
		
		FSMBehaviour fsm = new FSMBehaviour();
		
		fsm.registerFirstState(new RandomWalkBehaviour(this),"randomWalk");

		addBehaviour(fsm);
	}
	

}
