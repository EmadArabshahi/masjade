package logistics.behaviours.walking;

import java.awt.Point;

import gridworld.Environment;
import jade.core.behaviours.SimpleBehaviour;
import logistics.GridWorldAgent;
import logistics.behaviours.SensingAction;


public class WalkToPositionBehaviour extends WalkBehaviour
{
	public static int POSITION_REACHED = 1;
	
	private Point _targetPosition;
	
	public WalkToPositionBehaviour(GridWorldAgent owner, Point targetPosition)
	{
		super(owner);
		this._targetPosition = targetPosition;
	}
	
	public WalkToPositionBehaviour(GridWorldAgent owner, boolean senseLocation, boolean senseStones, boolean senseAgents, boolean senseBombs, boolean senseTraps)
	{
		super(owner, senseLocation, senseStones, senseAgents, senseBombs, senseTraps);
	}
	
	@Override
	public void walk() 
	{
		new StepToPositionAction(_owner, _targetPosition).action();
	}

	
	@Override
	public boolean done() 
	{
		System.out.println("position " + _owner.getCurrentPosition() + "  target " + _targetPosition);
		return (_owner.getCurrentPosition().equals(_targetPosition));

	}
	
	@Override
	public int onEnd()
	{
		return POSITION_REACHED;
	}
	
	
	
	
}
