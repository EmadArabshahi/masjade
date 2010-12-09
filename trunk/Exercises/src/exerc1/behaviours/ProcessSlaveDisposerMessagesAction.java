package exerc1.behaviours;

import exerc1.SlaveDisposerAgent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class ProcessSlaveDisposerMessagesAction extends OneShotBehaviour {

	public static final int RECEIVED_TARGET_BOMB_POSITION = 1;
	public static final int RECEIVED_TRAP_POSITION = 2;
	private SlaveDisposerAgent _owner;
	private int _endState;

	public ProcessSlaveDisposerMessagesAction(SlaveDisposerAgent owner) {
		_owner = owner;
		_endState = -1;
	}

	@Override
	public void action() {
		if (_owner.currentMessage != null)
		{
			ACLMessage msg = _owner.currentMessage;
			
			if (msg.getOntology().equals("target-bomb-position"))
			{
				_endState = RECEIVED_TARGET_BOMB_POSITION;
			}
			else if (msg.getOntology().equals("trap-position"))
			{
				_endState = RECEIVED_TRAP_POSITION;
			}
		}
	}
	
	public int onEnd()
	{
		return _endState;
	}
}
