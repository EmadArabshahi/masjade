package simulation.behaviours;

import java.awt.Point;
import java.util.Set;

import simulation.environment.Environment;
import jade.core.behaviours.SimpleBehaviour;
import jade.core.Agent;
import simulation.agents.GridWorldAgent;

public class SimpleCollectBehaviour extends WalkBehaviour
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1654409861694726237L;
	
	//in order of which they are checked. so must trade has highest priority.
	public static int MUST_TRADE_NEXT_ROUND = 0;
	public static int LACK_OF_ENERGY_AND_HAS_APPLE = 1;
	public static int DOESNT_KNOW_APPLES = 2;
	
	
	private int  _endResult = -1;
	
	public SimpleCollectBehaviour(GridWorldAgent owner)
	{
		super(owner);
	}
	
	public SimpleCollectBehaviour
	(GridWorldAgent owner, boolean senseLocation, boolean senseStones, boolean senseAgents, boolean senseApples)
	{
		super(owner, senseLocation, senseStones, senseAgents, senseApples);
	}
	
	public void walk()
	{
		if(_owner.mustTrade())
			return;
		
		if(_owner.isOnApple())
		{
			new PickUpAppleAction(_owner).action();
			return;
		}
		
		if(!_owner.knowsApples())
		{
			return;
		}
		
		Point currentPosition = _owner.getCurrentPosition();
		Set<Point> knownApples = _owner.getKnownApples();
		
		Point minimalDistanceApple = null;
		double minimalDistance = Double.MAX_VALUE;
		
		for (Point applePosition : knownApples)
		{
			double distance = applePosition.distance(currentPosition);
			if(distance < minimalDistance)
			{
				minimalDistanceApple = applePosition;
				minimalDistance = distance;
			}
		}
		//step in position of the closest bomb
		new StepToPositionAction(_owner, minimalDistanceApple).action();
	}
	
	@Override
	public boolean done() 
	{
		if(_owner.mustTrade())
		{
			_endResult = MUST_TRADE_NEXT_ROUND;
			return true;
		}
		else if(_owner.hasLackOfEnergy() && _owner.hasApples())
		{
			_endResult = LACK_OF_ENERGY_AND_HAS_APPLE;
			return true;
		}
		else if(!_owner.knowsApples())
		{
			_endResult = DOESNT_KNOW_APPLES;
			return true;
		}
		else
		{
			return false;
		}

	}
	
	@Override
	public int onEnd()
	{
		return _endResult;
	}
}
