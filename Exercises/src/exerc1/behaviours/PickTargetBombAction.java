package exerc1.behaviours;

import java.awt.Point;
import java.util.Set;

import exerc1.GridWorldAgent;
import jade.core.behaviours.OneShotBehaviour;

public class PickTargetBombAction extends OneShotBehaviour {
	public static final int PICKED_TARGET_BOMB = 1;
	//public static int AGENT_HAS_TARGET = 1;
	private GridWorldAgent _owner;
	public PickTargetBombAction(GridWorldAgent owner)
	{
		_owner = owner;
	}
	@Override
	public void action() {
		//System.out.println("2nd time use.. " + _owner.getKnownBombs().size());
		if(!_owner.knowsBombs())
		{
			//System.out.println("Agent doenst know bombs. returning.");
			return;
		}
		
		Point currentPosition = _owner.getCurrentPosition();
		Set<Point> knownBombs = _owner.getKnownBombs();
		
		//System.out.println("2nd time use.. positions retrived");
		Point minimalDistanceBomb = null;
		double minimalDistance = Double.MAX_VALUE;
		
		//System.out.println("Going to loop..");
		
		for (Point bombPosition : knownBombs)
		{
			double distance = bombPosition.distance(currentPosition);
			if(distance < minimalDistance)
			{
				minimalDistanceBomb = bombPosition;
				minimalDistance = distance;
			}
		}
		
		_owner.targetBombLocation = minimalDistanceBomb;	
	}
	
	@Override
	public int onEnd()
	{
		return PICKED_TARGET_BOMB;
	}
}
