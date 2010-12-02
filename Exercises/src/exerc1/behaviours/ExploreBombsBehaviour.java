package exerc1.behaviours;

import exerc1.BombRemovalAgent;
import gridworld.Environment;
import jade.core.behaviours.SimpleBehaviour;
import jade.core.Agent;

public class ExploreBombsBehaviour extends SimpleBehaviour
{

	private BombRemovalAgent _owner;
	
	private boolean _firstTimeUse;
	
	public ExploreBombsBehaviour(BombRemovalAgent owner)
	{
		this._owner = owner;
		this._firstTimeUse = true;
	}
	
	@Override
	public void action() 
	{
		System.out.println("In ExploreBombsBehaviour action");
		
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
		return _owner.knowsBombs();
	}

}
