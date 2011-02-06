package simulation.behaviours;

import java.awt.Point;

import simulation.agents.GridWorldAgent;
import simulation.environment.Environment;
import jade.core.behaviours.OneShotBehaviour;


public class EatAppleAction extends OneShotBehaviour {

		
	private GridWorldAgent _owner;
	private int _endState;

	public static int EAT_APPLE_SUCCES;
	public static int EAT_APPLE_FAILURE;
	
	public EatAppleAction(GridWorldAgent owner) 
	{
		_owner = owner;
		_endState = -1;
	}

	@Override
	public void action() 
	{
		if (_owner.hasApples())
		{
			_owner.appleEaten(Environment.eatApple(_owner.getLocalName()));
			_endState = EAT_APPLE_SUCCES;
		}
		else
		{
			_owner.applePickedUp(false);
			_endState = EAT_APPLE_FAILURE;
		}
		

	}
	
	
	
	public int onEnd()
	{
		return _endState;
	}
}