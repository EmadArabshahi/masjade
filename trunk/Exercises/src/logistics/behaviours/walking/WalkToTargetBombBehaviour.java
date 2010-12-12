package logistics.behaviours.walking;

import java.awt.Point;

import gridworld.Environment;
import jade.core.behaviours.SimpleBehaviour;
import logistics.BombCarrierAgent;
import logistics.GridWorldAgent;
import logistics.behaviours.SensingAction;


public class WalkToTargetBombBehaviour extends WalkBehaviour
{
	public static int POSITION_REACHED = 1;
	
	private Point _targetPosition;
	
	public WalkToTargetBombBehaviour(BombCarrierAgent owner)
	{
		super(owner);
		
	}
	
	public WalkToTargetBombBehaviour(BombCarrierAgent owner, boolean senseLocation, boolean senseStones, boolean senseAgents, boolean senseBombs, boolean senseTraps)
	{
		super(owner, senseLocation, senseStones, senseAgents, senseBombs, senseTraps);
	
	}
	
	
	@Override
	public void walk() 
	{
		
		this._targetPosition = ((BombCarrierAgent)_owner).getTargetBombPosition();
		
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
