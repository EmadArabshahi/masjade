package exerc1.behaviours;

import java.awt.Point;

import exerc1.GridWorldAgent;
import exerc1.MasterDisposerAgent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ProcessDisposerActivitiesBehaviour extends SimpleBehaviour {

	public static final int HAS_NO_TARGET_BOMB = 1;
	public static final int HAS_NO_TARGET_TRAP = 2;
	public static final int IS_ON_BOMB = 3;
	public static final int IS_ON_TRAP = 4;
	public static final int RECEIVED_MESSAGE = 5;
	
	private boolean _done;
	private int _endState;
	private GridWorldAgent _owner;
	
	public ProcessDisposerActivitiesBehaviour(GridWorldAgent owner)
	{
		_done = false;
		_endState = -1;
		_owner = owner;
	}
	
	@Override
	public void action() {
		ACLMessage msg = _owner.receive();
		if (msg != null)
		{
			System.out.println("has no bomb.");
			_owner.currentMessage = msg;
			_endState = RECEIVED_MESSAGE;
			_done = true;
		}
		else if (_owner.targetBombLocation == null && !_owner.hasBomb)
		{
			_endState = HAS_NO_TARGET_BOMB;
			_done = true;
		}
		else if (_owner.targetTrapLocation == null && _owner.hasBomb)
		{
			_endState = HAS_NO_TARGET_TRAP;
			_done = true;
		}
		else if (_owner.isOnBomb() && _owner.getCurrentPosition().equals(_owner.targetBombLocation))
		{
			_endState = IS_ON_BOMB;
			_done = true;
		}
		else if (_owner.hasBomb && _owner.isOnTrap())
		{
			_endState = IS_ON_TRAP;
			_done = true;
		}
		else if (!_owner.hasBomb)
		{
			// If the agent does not have a bomb, walk towards the target bomb location to pick one up.
			new SensingAction(_owner).action();
			new StepToPositionAction(_owner, _owner.targetBombLocation).action();
			new SensingAction(_owner).action();
		}
		else if (_owner.hasBomb)
		{
			// If the agent has a bomb in its possession, move towards the trap.
			new SensingAction(_owner).action();
			new StepToPositionAction(_owner, _owner.targetTrapLocation).action();
			new SensingAction(_owner).action();
		}
	}

	@Override
	public boolean done() {
		return _done;
	}
	
	public int onEnd()
	{
		return _endState;
	}
}
