package exerc1;

import java.awt.Point;
import java.util.Iterator;
import java.util.Set;

import gridworld.Environment;
import gridworld.LogicalEnv;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;

public class BombRemovalBehaviour extends CyclicBehaviour{
	private BombRemovalAgent _myAgent;
	
	public BombRemovalBehaviour(BombRemovalAgent owner)
	{
		_myAgent = owner;
	}
	
	private void findNearestTrap()
	{
		// todo: find nearest
		Iterator<Point> i = _myAgent.knownTraps.iterator();
		while (i.hasNext())
		{
			_myAgent.targetTrapLocation = i.next();
		}
	}
	
	private void findNearestBomb(Set<Point> bombsFound)
	{
		Iterator<Point> i = bombsFound.iterator();
		// todo: find nearest.
		while(i.hasNext())
		{
			_myAgent.targetBombLocation = i.next();
		}
		System.out.println(String.format("%s: New target at (%s, %s)", _myAgent.getLocalName(), _myAgent.targetBombLocation.x, _myAgent.targetBombLocation.y));
	}
	
	private void moveTowardsBombAndPickItUp()
	{
		// If target is on the target bomb pick it up.
		if (_myAgent.currentLocation.x == _myAgent.targetBombLocation.x &&
			_myAgent.currentLocation.y == _myAgent.targetBombLocation.y)
		{
			Environment.takeBomb(_myAgent.getLocalName());
			// Set the target bomb location to null to indicate that there's
			// no longer a current target.
			_myAgent.targetBombLocation = null;
			_myAgent.hasBomb = true;
		}
		// Otherwise move towards target bomb
		else if (_myAgent.currentLocation.x > _myAgent.targetBombLocation.x)
		{
			Environment.west(myAgent.getLocalName());
			_myAgent.currentLocation.x--;
		}
		else if (_myAgent.currentLocation.x < _myAgent.targetBombLocation.x)
		{
			Environment.east(myAgent.getLocalName());
			_myAgent.currentLocation.x++;
		}
		else if (_myAgent.currentLocation.y > _myAgent.targetBombLocation.y)
		{
			Environment.north(myAgent.getLocalName());
			_myAgent.currentLocation.y--;
		}
		else if (_myAgent.currentLocation.y < _myAgent.targetBombLocation.y)
		{
			Environment.south(myAgent.getLocalName());
			_myAgent.currentLocation.y++;
		}
	}
	
	private void moveTowardsBinAndDropBomb()
	{
		// move towards trap to and drop the bomb into it.
		// If target is on the target bomb pick it up.
		if (_myAgent.currentLocation.x == _myAgent.targetTrapLocation.x &&
			_myAgent.currentLocation.y == _myAgent.targetTrapLocation.y)
		{
			Environment.dropBomb(_myAgent.getLocalName());
			// Set the target bomb location to null to indicate that there's
			// no longer a current target.
			_myAgent.targetTrapLocation = null;
			_myAgent.hasBomb = false;
		}
		// Otherwise move towards target bomb
		else if (_myAgent.currentLocation.x > _myAgent.targetTrapLocation.x)
		{
			Environment.west(myAgent.getLocalName());
			_myAgent.currentLocation.x--;
		}
		else if (_myAgent.currentLocation.x < _myAgent.targetTrapLocation.x)
		{
			Environment.east(myAgent.getLocalName());
			_myAgent.currentLocation.x++;
		}
		else if (_myAgent.currentLocation.y > _myAgent.targetTrapLocation.y)
		{
			Environment.north(myAgent.getLocalName());
			_myAgent.currentLocation.y--;
		}
		else if (_myAgent.currentLocation.y < _myAgent.targetTrapLocation.y)
		{
			Environment.south(myAgent.getLocalName());
			_myAgent.currentLocation.y++;
		}
	}
	
	@Override
	public void action() {
		if (_myAgent.targetBombLocation == null)
		{
			if (_myAgent.hasBomb)
			{
				if (_myAgent.targetTrapLocation == null)
				{
					// if there's no trap location find one
					findNearestTrap();
				}
				else
				{
					moveTowardsBinAndDropBomb();
				}
			}
			else
			{
				// get a list of possible target bombs.
				// todo: make this a ticker thingie, so it won't be spammed if he can't find
				// bombs.
				Set<Point> bombsFound = Environment.senseBombs(_myAgent.getLocalName());
		
				if (bombsFound.size() > 0)
				{
					findNearestBomb(bombsFound);
				}
				else
				{
					// todo: Move around the environment to find bombs.
				}
			}
		}
		else
		{
			moveTowardsBombAndPickItUp();
		}
	}
}
