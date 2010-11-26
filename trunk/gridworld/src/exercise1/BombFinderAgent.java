package exercise1;

import java.awt.Point;

import gridworld.Environment;
import jade.core.Agent;

public class BombFinderAgent extends Agent
{
	protected void setup()
	{
		addBehaviour(new BombFindingBehaviour(this));
		
		Environment.enter(getAID().getLocalName(), new Point(5,5), "blue");
		
		System.out.println(getAID().getLocalName() + " is ready.");
	}
}
