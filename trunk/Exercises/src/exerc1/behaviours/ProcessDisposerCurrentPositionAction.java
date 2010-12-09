package exerc1.behaviours;

import java.awt.Point;
import java.security.acl.LastOwnerException;

import exerc1.MasterDisposerAgent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class ProcessDisposerCurrentPositionAction extends OneShotBehaviour {

	public static final int PROCESSED_DISPOSER_CURRENT_POSITION = 1;
	private MasterDisposerAgent _owner;

	public ProcessDisposerCurrentPositionAction(MasterDisposerAgent owner) {
		_owner = owner;
	}

	@Override
	public void action() {
		if (_owner.currentMessage != null)
		{
			ACLMessage msg = _owner.currentMessage;
			
			String[] splitMsg = msg.getContent().split(",");
			Point location = new Point();
			location.x = Integer.parseInt(splitMsg[0]);
			location.y = Integer.parseInt(splitMsg[1]);
			
			_owner.lastKnownDisposerPositions.put(msg.getSender(), location);
			
			_owner.currentMessage = null;
		}
	}
	
	public int onEnd()
	{
		return PROCESSED_DISPOSER_CURRENT_POSITION;
	}
}
