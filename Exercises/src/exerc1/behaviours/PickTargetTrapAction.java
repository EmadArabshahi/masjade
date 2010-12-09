package exerc1.behaviours;

import java.awt.Point;
import java.util.Set;

import exerc1.GridWorldAgent;
import exerc1.MasterDisposerAgent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;

public class PickTargetTrapAction extends OneShotBehaviour {

	public static final int PICKED_TARGET_TRAP = 1;
	private GridWorldAgent _owner;

	public PickTargetTrapAction(GridWorldAgent owner) {
		_owner = owner;
	}

	@Override
	public void action() {
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
		
		_owner.targetTrapLocation = minimalDistanceTrap;
	}
	
	public int onEnd()
	{
		return PICKED_TARGET_TRAP;
	}
}
