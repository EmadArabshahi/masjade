package simulation.behaviours;

import java.awt.Point;

import simulation.agents.GridWorldAgent;
import simulation.environment.Environment;
import jade.core.behaviours.OneShotBehaviour;


public class PickUpAppleAction extends OneShotBehaviour {

	public static final int PICKED_UP_BOMB = 1;
	public static final int FOUND_NO_BOMB = 2;
	
	private GridWorldAgent _owner;
	private int _endState;

	public PickUpAppleAction(GridWorldAgent owner) 
	{
		_owner = owner;
		_endState = -1;
	}

	@Override
	public void action() 
	{
		if (_owner.isOnApple())
		{
			_owner.applePickedUp(Environment.pickupApple(_owner.getLocalName()));
			_endState = PICKED_UP_BOMB;
		}
		else
		{
			_owner.applePickedUp(false);
			_endState = FOUND_NO_BOMB;
		}
		

	}
	
	
	
	public int onEnd()
	{
		return _endState;
	}
}