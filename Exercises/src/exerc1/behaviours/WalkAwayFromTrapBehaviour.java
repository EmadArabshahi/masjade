package exerc1.behaviours;

import exerc1.GridWorldAgent;
import gridworld.Environment;
import jade.core.behaviours.SimpleBehaviour;
import jade.core.Agent;

public class WalkAwayFromTrapBehaviour extends SimpleBehaviour
{
	private GridWorldAgent _owner;
	
	private boolean _firstTimeUse;
	
	public static int NOT_ON_TRAP =1;
	
	public WalkAwayFromTrapBehaviour(GridWorldAgent owner)
	{
		this._owner = owner;
		this._firstTimeUse = true;
	}
	
	@Override
	public void action() 
	{
		System.out.println("In WalkAwayFromTrapBehaviour action");
		
		///If its the first time this behaviour is used, then the random step is not performed.
		///In that case only the Sensing is performed, so always the area is first sensed and then explored.
		if(!_firstTimeUse)
			new MemoryRandomStepAction(_owner).action();
		else
			_firstTimeUse = false;
		
		new SensingAction(_owner).action();			
	}

	@Override
	public boolean done() 
	{
		return !_owner.isOnTrap();
	}
	
	@Override
	public int onEnd()
	{
		return NOT_ON_TRAP;
	}
}
