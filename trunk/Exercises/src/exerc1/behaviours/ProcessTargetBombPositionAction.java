package exerc1.behaviours;

import java.awt.Point;

import exerc1.SlaveDisposerAgent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class ProcessTargetBombPositionAction extends OneShotBehaviour {

	public static final int PROCESSED_TARGET_BOMB_POSITION = 1;
	private SlaveDisposerAgent _owner;

	public ProcessTargetBombPositionAction(SlaveDisposerAgent owner) {
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
			
			_owner.targetBombLocation = location;
			
			_owner.currentMessage = null;
		}
	}
	
	public int onEnd()
	{
		return PROCESSED_TARGET_BOMB_POSITION;
	}
}
