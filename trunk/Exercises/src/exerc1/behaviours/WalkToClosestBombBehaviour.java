package exerc1.behaviours;

import java.awt.Point;
import java.util.Set;

import exerc1.GridWorldAgent;
import gridworld.Environment;
import jade.core.behaviours.SimpleBehaviour;


public class WalkToClosestBombBehaviour extends SimpleBehaviour
{
	
	private GridWorldAgent _owner;
	
	private boolean _firstTimeUse;
	
	private int _endState = -1;
	
	public static final int ON_BOMB = 1;
	public static final int NO_BOMBS_FOUND = 2;
	
	public WalkToClosestBombBehaviour(GridWorldAgent owner)
	{
		this._owner = owner;
		this._firstTimeUse = true;
	}
	
	@Override
	public void action()
	{
		System.out.println("In WalkToClosestBombBehaviour action");
		
		if(!_owner.knowsBombs())
		{
			System.out.println("Agent doenst know bombs. returning.");
			return;
		}
		
		///If its the first time this behaviour is used, then the step is not performed.
		///In that case only the Sensing is performed, so always the area is first sensed and then walke
		if(!_firstTimeUse)
		{
			System.out.println("2nd time use..");
			
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
			
			
			
			new StepToPositionAction(_owner, minimalDistanceBomb).action();
			
		}
		else
			_firstTimeUse = false;
		
		new SensingAction(_owner).action();
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
