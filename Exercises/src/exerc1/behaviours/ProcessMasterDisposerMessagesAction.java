package exerc1.behaviours;

import exerc1.MasterDisposerAgent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class ProcessMasterDisposerMessagesAction extends OneShotBehaviour {

	public static final int RECEIVED_BOMB_POSITION = 1;
	public static final int RECEIVED_TRAP_POSITION = 2;
	public static final int RECEIVED_DISPOSER_CURRENT_POSITION = 3;
	public static final int RECEIVED_TARGET_BOMB_REQUEST = 4;
	public static final int RECEIVED_BOMB_PICKED_UP = 5;
	
	private MasterDisposerAgent _owner;
	private int _endState;

	public ProcessMasterDisposerMessagesAction(MasterDisposerAgent owner) {
		_owner = owner;
		_endState = -1;
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		if (_owner.currentMessage != null)
		{
			ACLMessage msg = _owner.currentMessage;
			
			if (msg.getOntology().equals("bomb-position"))
			{
				_endState = RECEIVED_BOMB_POSITION;
			}
			else if (msg.getOntology().equals("trap-position"))
			{
				_endState = RECEIVED_TRAP_POSITION;
			}
			else if (msg.getOntology().equals("disposer-position"))
			{
				_endState = RECEIVED_DISPOSER_CURRENT_POSITION;
			}
			else if (msg.getOntology().equals("target-bomb-request"))
			{
				_endState = RECEIVED_TARGET_BOMB_REQUEST;
			}
			else if (msg.getOntology().equals("bomb-picked-up"))
			{
				_endState = RECEIVED_BOMB_PICKED_UP;
			}
		}
	}
	
	public int onEnd()
	{
		return _endState;
	}
}
