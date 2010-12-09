package exerc1.behaviours;

import exerc1.GridWorldAgent;
import exerc1.MasterDisposerAgent;
import gridworld.Environment;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;

public class PickUpBombAction extends OneShotBehaviour {

	public static final int PICKED_UP_BOMB = 1;
	public static final int FOUND_NO_BOMB = 2;
	private GridWorldAgent _owner;
	private int _endState;

	public PickUpBombAction(GridWorldAgent owner) {
		_owner = owner;
		_endState = -1;
	}

	@Override
	public void action() {
		if (_owner.isOnBomb())
		{
			_owner.bombPickedUp(Environment.takeBomb(_owner.getLocalName()));
			_owner.targetBombLocation = null;
			_owner.hasBomb = true;
			_endState = PICKED_UP_BOMB;
		}
		else
		{
			_owner.bombPickedUp(false);
			_endState = FOUND_NO_BOMB;
		}
	}
	
	public int onEnd()
	{
		return _endState;
	}
}
