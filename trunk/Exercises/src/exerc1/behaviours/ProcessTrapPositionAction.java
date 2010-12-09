package exerc1.behaviours;

import java.awt.Point;

import exerc1.GridWorldAgent;
import exerc1.MasterDisposerAgent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class ProcessTrapPositionAction extends OneShotBehaviour {

	public static final int PROCESSED_TRAP_POSITION = 1;
	private GridWorldAgent _owner;

	public ProcessTrapPositionAction(GridWorldAgent owner) {
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
			
			_owner.addKnownTrap(location);
			
			_owner.currentMessage = null;
		}
	}
	
	public int onEnd()
	{
		return PROCESSED_TRAP_POSITION;
	}
}
