package exerc1.behaviours;

import java.awt.Point;
import java.util.Set;

import exerc1.BombRemovalAgent;
import gridworld.Environment;
import jade.core.behaviours.SimpleBehaviour;


public class WalkToClosestTrapBehaviour extends SimpleBehaviour
{
	
	private BombRemovalAgent _owner;
	
	private boolean _firstTimeUse;
	
	public WalkToClosestTrapBehaviour(BombRemovalAgent owner)
	{
		this._owner = owner;
		this._firstTimeUse = true;
	}
	
	@Override
	public void action()
	{
		System.out.println("In WalkToClosestTrapBehaviour action");
		
		if(!_owner.knowsTraps())
		{
			System.out.println("Agent doenst know traps. returning.");
			return;
		}
		
		///If its the first time this behaviour is used, then the step is not performed.
		///In that case only the Sensing is performed, so always the area is first sensed and then walke
		if(!_firstTimeUse)
		{
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
		else
			_firstTimeUse = false;
		
		new SensingAction(_owner).action();
	}

	@Override
	public boolean done() 
	{
		System.out.println("in WalkToClosestTrapBehaviour DONE");
		
		
		if(!_owner.knowsTraps())
		{
			System.out.println("Agent doenst known trap quiting!");
			return true;
		}
		System.out.println("Agent does know at leat 1 trap!!");		
		System.out.println("Going to loop through known traps");
		
		for(Point trapPosition : _owner.getKnownTraps())
		{
			if(_owner.getCurrentPosition() != null)
			{ 
				if (trapPosition.equals(_owner.getCurrentPosition()))
				{
					System.out.println("Agent is on bomb.. quiting!");
					return true;
				}
				else
				{
					System.out.println("Agent is not on bomb ... continuing.");
				}
			}
			else
			{
				System.out.println("Agent current position is null so not on bomb.");
				return false;
			}	
			
		}
		
		
		System.out.println("Agent not on bomb returning false");
		return false;
		
	}
	
	
}

