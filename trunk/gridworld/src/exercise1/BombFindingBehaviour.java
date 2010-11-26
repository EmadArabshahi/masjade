package exercise1;

import gridworld.Environment;
import jade.core.behaviours.Behaviour;
import jade.core.Agent;

public class BombFindingBehaviour extends Behaviour  {
	private Agent myAgent;
	
	public BombFindingBehaviour(Agent owner)
	{
		myAgent = owner;
	}
	@Override
	public void action() {
		Environment.senseBombs(myAgent.getName());
	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return false;
	}

}
