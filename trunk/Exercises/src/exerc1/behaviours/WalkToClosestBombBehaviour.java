package exerc1.behaviours;

import java.awt.Point;
import java.util.Set;

import exerc1.BombRemovalAgent;
import gridworld.Environment;
import jade.core.behaviours.SimpleBehaviour;


public class WalkToClosestBombBehaviour extends SimpleBehaviour
{
	
	private BombRemovalAgent _owner;
	
	private boolean _firstTimeUse;
	
	public WalkToClosestBombBehaviour(BombRemovalAgent owner)
	{
		this._owner = owner;
		this._firstTimeUse = true;
	}
	
	@Override
	public void action()
	{
		System.out.println("In WalkToClosestBombBehaviour action");
		
		if(!_owner.knowsBombs())
			return;
		
		///If its the first time this behaviour is used, then the step is not performed.
		///In that case only the Sensing is performed, so always the area is first sensed and then walke
		if(!_firstTimeUse)
		{
			
			Point currentPosition = _owner.getCurrentPosition();
			Set<Point> knownBombs = _owner.getKnownBombs();
			
			Point minimalDistanceBomb = null;
			double minimalDistance = Double.MAX_VALUE;
				
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
		if(!_owner.knowsBombs())
		{
			System.out.println("Agent doenst known bomb quiting!");
			return true;
		}
		else
		{	
			for(Point bombPosition : _owner.getKnownBombs())
			{
				if (bombPosition.equals(_owner.getCurrentPosition()))
				{
					System.out.println("Agent is on bomb.. quiting!");
					return true;
				}
			}
			
			System.out.println("Agent not on bomb returning false");
			return false;
		}
	}
	
	
}
