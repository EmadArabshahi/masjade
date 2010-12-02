package exerc1.behaviours;

import java.awt.Point;
import exerc1.BombRemovalAgent;
import gridworld.Environment;
import jade.core.behaviours.SimpleBehaviour;


public class WalkToPositionBehaviour extends SimpleBehaviour
{
	
	
	private BombRemovalAgent _owner;
	
	private boolean _firstTimeUse;
	
	private Point _targetPosition;
	
	public WalkToPositionBehaviour(BombRemovalAgent owner, Point targetPosition)
	{
		this._owner = owner;
		this._targetPosition = targetPosition;
		this._firstTimeUse = true;
	}
	
	@Override
	public void action() 
	{
		System.out.println("WalkToPosition in action");
		
		///If its the first time this behaviour is used, then the step is not performed.
		///In that case only the Sensing is performed, so always the area is first sensed and then walked.
		if(!_firstTimeUse)
			new StepToPositionAction(_owner, _targetPosition).action();
		else
			_firstTimeUse = false;
		
		new SensingAction(_owner).action();
		
		
	}

	
	@Override
	public boolean done() 
	{
		System.out.println("position " + _owner.getCurrentPosition() + "  target " + _targetPosition);
		return (_owner.getCurrentPosition().equals(_targetPosition));

	}
	
	
	
	
	
}
