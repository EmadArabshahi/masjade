package exerc1a;

import gridworld.Environment;
import jade.core.behaviours.SimpleBehaviour;
import jade.core.Agent;

public class ExploreBombsBehaviour extends SimpleBehaviour
{

	private BombRemovalAgent _owner;
	
	public ExploreBombsBehaviour(BombRemovalAgent owner)
	{
		this._owner = owner;
	}
	
	@Override
	public void action() 
	{
		_owner.addBehaviour(new BombSensingBehaviour(_owner));
		_owner.addBehaviour(new MemoryRandomStepBehaviour(_owner));
	}

	@Override
	public boolean done() 
	{
		return _owner.knowsBombs();
	}

}
