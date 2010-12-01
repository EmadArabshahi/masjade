package exerc1.behaviours;

import exerc1.BombRemovalAgent;
import gridworld.Environment;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.Agent;

public class BombSensingBehaviour extends OneShotBehaviour
{

	
	private BombRemovalAgent _owner;
	
	public BombSensingBehaviour(BombRemovalAgent owner)
	{
		this._owner = owner;
	}
	
	@Override
	public void action() 
	{
		_owner.bombsSensed(Environment.senseBombs(_owner.getLocalName()));	
	}

}
