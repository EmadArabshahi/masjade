package exerc1.behaviours;

import exerc1.GridWorldAgent;
import gridworld.Environment;
import jade.core.behaviours.*;

public class PickupBombAction extends OneShotBehaviour
{

	private GridWorldAgent _owner;
	
	public PickupBombAction(GridWorldAgent owner)
	{
		this._owner = owner;
	}
	
	@Override
	public void action()
	{
		if(_owner.IsOnBomb())
		{
			_owner.bombPickedUp(Environment.takeBomb(_owner.getLocalName()));
		}
	}
}
