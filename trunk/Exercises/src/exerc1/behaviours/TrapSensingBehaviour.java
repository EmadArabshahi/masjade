package exerc1.behaviours;

import exerc1.BombRemovalAgent;
import gridworld.Environment;
import jade.core.behaviours.OneShotBehaviour;

public class TrapSensingBehaviour extends OneShotBehaviour
{
	
	private BombRemovalAgent _owner;
	
	public TrapSensingBehaviour(BombRemovalAgent owner)
	{
		this._owner = owner;
	}
	
	@Override
	public void action() 
	{
		_owner.stonesSensed(Environment.senseTraps(_owner.getLocalName()));	
	}

}
