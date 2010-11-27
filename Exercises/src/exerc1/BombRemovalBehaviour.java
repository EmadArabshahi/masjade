package exerc1;

import java.awt.Point;
import java.util.Iterator;
import java.util.Set;

import gridworld.Environment;
import gridworld.LogicalEnv;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;

public class BombRemovalBehaviour extends Behaviour{
	private boolean _hasBomb;
	private Point _targetBombLocation;
	private BombRemovalAgent _myAgent;
	
	public BombRemovalBehaviour(BombRemovalAgent owner)
	{
		_myAgent = owner;
	}
	
	@Override
	public void action() {
		if (_targetBombLocation == null)
		{
			// get new target bomb.
			Set<Point> bombsFound = LogicalEnv.getEnv().senseBombs(_myAgent.getLocalName());
	
			if (bombsFound.size() > 0)
			{
				Iterator<Point> i = bombsFound.iterator();
				// find nearest bomb.
				while(i.hasNext())
				{
					_targetBombLocation = i.next();
				}
				System.out.println(String.format("%s: New target at (%s, %s)", _myAgent.getLocalName(), _targetBombLocation.x, _targetBombLocation.y));
			}
			else
			{
				// Move around the environment to find bombs.
			}
		}
		else
		{
			// Move towards target bomb
			if (_myAgent.currentLocation.x > _targetBombLocation.x)
			{
				Environment.west(myAgent.getLocalName());
				_myAgent.currentLocation.x--;
			}
			else if (_myAgent.currentLocation.x < _targetBombLocation.x)
			{
				Environment.east(myAgent.getLocalName());
				_myAgent.currentLocation.x++;
			}
			else if (_myAgent.currentLocation.y > _targetBombLocation.y)
			{
				Environment.north(myAgent.getLocalName());
				_myAgent.currentLocation.y--;
			}
			else if (_myAgent.currentLocation.y < _targetBombLocation.y)
			{
				Environment.south(myAgent.getLocalName());
				_myAgent.currentLocation.y++;
			}
		}
		
		//System.out.println(_myAgent.getLocalName() + ": " + bombsFound.size() + " bombs found.");
	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return _hasBomb;
	}
}
