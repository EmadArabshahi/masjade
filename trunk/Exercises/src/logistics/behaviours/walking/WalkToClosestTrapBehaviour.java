package logistics.behaviours.walking;

import java.awt.Point;
import java.util.Set;


import gridworld.Environment;
import jade.core.behaviours.SimpleBehaviour;
import logistics.BombRemovalAgent;
import logistics.GridWorldAgent;
import logistics.behaviours.SensingAction;


public class WalkToClosestTrapBehaviour extends WalkBehaviour
{
	
	private int _endState = -1;
	
	public static final int ON_TRAP = 1;
	public static final int NO_TRAPS_FOUND = 2;
	
	public WalkToClosestTrapBehaviour(GridWorldAgent owner)
	{
		super(owner);
	}
	
	public WalkToClosestTrapBehaviour(GridWorldAgent owner, boolean senseLocation, boolean senseStones, boolean senseAgents, boolean senseBombs, boolean senseTraps)
	{
		super(owner, senseLocation, senseStones, senseAgents, senseBombs, senseTraps);
	}
	
	
	
	@Override
	public void walk()
	{
		System.out.println("In WalkToClosestTrapBehaviour action");
		
		if(!_owner.knowsTraps())
		{
			System.out.println("Agent doenst know traps. returning.");
			return;
		}
		
		Point currentPosition = _owner.getCurrentPosition();
		Set<Point> knownTraps = _owner.getKnownTraps();
		
		Point minimalDistanceTrap = null;
		double minimalDistance = Double.MAX_VALUE;
		
		for (Point trapPosition : knownTraps)
		{
			double distance = trapPosition.distance(currentPosition);
			if(distance < minimalDistance)
			{
				minimalDistanceTrap = trapPosition;
				minimalDistance = distance;
			}
		}
		
		
		
		new StepToPositionAction(_owner, minimalDistanceTrap).action();
			
	}

	@Override
	public boolean done() 
	{
		System.out.println("in WalkToClosestTrapBehaviour DONE");
		
		
		if(!_owner.knowsTraps())
		{
			System.out.println("Agent doenst known trap quiting!");
			this._endState = NO_TRAPS_FOUND;
			return true;
		}
		
		if(_owner.isOnTrap())
		{
			System.out.println("Agent is on trap, quiting!");
			this._endState = ON_TRAP;
			return true;
		}
		
		
		System.out.println("Agent not on trap returning false");
		return false;
		
	}
	
	@Override
	public int onEnd()
	{
		return _endState;
	}
	
}

