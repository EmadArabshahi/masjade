package exerc1a;

import gridworld.Environment;
import jade.core.behaviours.OneShotBehaviour;

public class StoneSensingBehaviour extends OneShotBehaviour
{
	
	private BombRemovalAgent _owner;
	
	public StoneSensingBehaviour(BombRemovalAgent owner)
	{
		this._owner = owner;
	}
	
	@Override
	public void action() 
	{
		_owner.stonesSensed(Environment.senseStones(_owner.getLocalName()));	
	}

}
