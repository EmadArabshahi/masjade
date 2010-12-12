package logistics.behaviours.walking;

import java.awt.Point;
import java.util.Set;


import gridworld.Environment;
import jade.core.behaviours.SimpleBehaviour;
import logistics.GridWorldAgent;
import logistics.behaviours.SensingAction;


public class WalkToClosestBombBehaviour extends WalkBehaviour
{
	private int _endState = -1;
	
	public static final int ON_BOMB = 1;
	public static final int NO_BOMBS_FOUND = 2;
	
	public WalkToClosestBombBehaviour(GridWorldAgent owner)
	{
		super(owner);
	}
	
	public WalkToClosestBombBehaviour(GridWorldAgent owner, boolean senseLocation, boolean senseStones, boolean senseAgents, boolean senseBombs, boolean senseTraps)
	{
		super(owner, senseLocation, senseStones, senseAgents, senseBombs, senseTraps);
	}
	
	
	@Override
	public void walk()
	{
		System.out.println("In WalkToClosestBombBehaviour action");
		
		if(!_owner.knowsBombs())
		{
			System.out.println("Agent doenst know bombs. returning.");
			return;
		}
		
		Point currentPosition = _owner.getCurrentPosition();
		Set<Point> knownBombs = _owner.getKnownBombs();
		
		System.out.println("2nd time use.. positions retrived");
		Point minimalDistanceBomb = null;
		double minimalDistance = Double.MAX_VALUE;
		
		System.out.println("Going to loop..");
		
		for (Point bombPosition : knownBombs)
		{
			double distance = bombPosition.distance(currentPosition);
			if(distance < minimalDistance)
			{
				minimalDistanceBomb = bombPosition;
				minimalDistance = distance;
			}
		}
		//step in position of the closest bomb
		new StepToPositionAction(_owner, minimalDistanceBomb).action();
			
		
	}

	@Override
	public boolean done() 
	{
		System.out.println("in WalkToClosestBombBehaviour DONE");
		
		
		if(!_owner.knowsBombs())
		{
			System.out.println("Agent doenst known bomb quiting!");
			this._endState  = NO_BOMBS_FOUND; // No bombs.
			return true;
		}
		if(_owner.isOnBomb())
		{
			System.out.println("AGent is on bomb, quiting!");
			this._endState = ON_BOMB; // On Bomb.
			return true;
		}
		
		
		System.out.println("Agent not on bomb returning false");
		return false;
		
	}
	
	@Override
	public int onEnd()
	{
		return _endState;
	}
	
}
