package exerc1;
import java.awt.Point;

import gridworld.Environment;
import jade.core.Agent;

public class BombRemovalAgent extends Agent {
	public Point currentLocation;
	
	protected void setup()
	{
		addBehaviour(new BombRemovalBehaviour(this));
		// todo: get starting location from arguments.
		currentLocation = new Point(5, 5);
		Environment.enter(getLocalName(), currentLocation, "blue");
		System.out.println(getLocalName() + " is ready.");
	}
}
