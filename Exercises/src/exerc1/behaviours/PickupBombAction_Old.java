package exerc1.behaviours;

import exerc1.GridWorldAgent;
import gridworld.Environment;
import jade.core.behaviours.*;

public class PickupBombAction_Old extends OneShotBehaviour
{

	private GridWorldAgent _owner;
	
	
	public static int AGENT_HAS_BOMB = 1;
	public static int AGENT_HAS_NO_BOMB = 2;
	
	public PickupBombAction_Old(GridWorldAgent owner)
	{
		this._owner = owner;
	}
	
	@Override
	public void action()
	{
		if(_owner.isOnBomb() && !_owner.hasBomb())
		{
			_owner.bombPickedUp(Environment.takeBomb(_owner.getLocalName()));
		}
		else
			_owner.bombPickedUp(false);
	}
	
	@Override
	public int onEnd()
	{
		if(_owner.hasBomb())
			return AGENT_HAS_BOMB;
		else
			return AGENT_HAS_NO_BOMB;
			
	}
}
