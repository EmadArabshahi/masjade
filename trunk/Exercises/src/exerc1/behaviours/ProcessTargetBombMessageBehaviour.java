package exerc1.behaviours;

import java.awt.Point;

import exerc1.SlaveDisposerAgent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;

public class ProcessTargetBombMessageBehaviour extends SimpleBehaviour {
	public static final int PROCESSED_TARGET_BOMB = 1;
	private SlaveDisposerAgent _owner;
	private boolean _done;

	public ProcessTargetBombMessageBehaviour(SlaveDisposerAgent owner) {
		_owner = owner;
		_done = false;
	}

	@Override
	public void action() {
		ACLMessage msg = _owner.receive();
		if (msg != null && msg.getOntology().equals("bomb-target-position"))
		{
			String[] splitMsg = msg.getContent().split(",");
			Point location = new Point();
			location.x = Integer.parseInt(splitMsg[0]);
			location.y = Integer.parseInt(splitMsg[1]);
			
			_owner.targetBombLocation = location;
		}
		else
		{
			block();
		}
	}

	@Override
	public boolean done() {
		return _done;
	}
	
	public int onEnd()
	{
		return PROCESSED_TARGET_BOMB;	
	}
}
