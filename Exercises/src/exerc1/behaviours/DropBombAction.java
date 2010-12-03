package exerc1.behaviours;

import exerc1.GridWorldAgent;
import gridworld.Environment;
import jade.core.behaviours.*;

public class DropBombAction extends OneShotBehaviour
{

	private GridWorldAgent _owner;
	
	public static int AGENT_HAS_BOMB = 1;
	public static int AGENT_HAS_NO_BOMB = 2;
	
	
	public DropBombAction(GridWorldAgent owner)
	{
		this._owner = owner;
	}
	
	@Override
	public void action()
	{
		if(_owner.isOnTrap() && _owner.hasBomb())
		{
			_owner.bombDropped(Environment.dropBomb((_owner.getLocalName())));
		}
		else
			_owner.bombDropped(false);
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
