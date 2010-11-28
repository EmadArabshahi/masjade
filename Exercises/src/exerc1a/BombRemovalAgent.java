package exerc1a;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import gridworld.Environment;
import jade.core.Agent;

public class BombRemovalAgent extends Agent {
	public boolean hasBomb;
	public Point targetBombLocation;
	public Point targetTrapLocation;
	public ArrayList<Point> knownTraps;
	
	protected void setup()
	{
		addBehaviour(new BombRemovalBehaviour(this));
		// todo: get starting and trap locations from arguments.
		knownTraps = new ArrayList<Point>();
		knownTraps.add(new Point(0, 0));

		Environment.enter(getLocalName(), new Point(5, 5), "blue");
		
		System.out.println(getLocalName() + " is ready.");
	}
}
