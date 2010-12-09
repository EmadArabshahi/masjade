package exerc1.behaviours;

import exerc1.GridWorldAgent;
import gridworld.Environment;
import jade.core.behaviours.*;

public class DropBombAction extends OneShotBehaviour
{
	public static final int DROPPED_BOMB = 1;
	public static final int FOUND_NO_TRAP = 2;

	private GridWorldAgent _owner;
	
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
			_owner.hasBomb = false;
			_owner.targetBombLocation = null;
			_owner.targetTrapLocation = null;
		}
		else
			_owner.bombDropped(false);
	}
	
	@Override
	public int onEnd()
	{
		if(_owner.hasBomb())
			return FOUND_NO_TRAP;
		else
			return DROPPED_BOMB;
	}
}
