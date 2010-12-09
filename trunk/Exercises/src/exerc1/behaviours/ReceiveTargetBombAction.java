package exerc1.behaviours;

import java.awt.Point;

import exerc1.GridWorldAgent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ReceiveTargetBombAction extends OneShotBehaviour {
	public static int RECEIVE_DONE = 1;
	private GridWorldAgent _owner;

	public ReceiveTargetBombAction(GridWorldAgent owner)
	{
		_owner = owner;
	}
	
	@Override
	public void action() {
		ACLMessage msg = _owner.receive(MessageTemplate.MatchOntology("target-inform-onthology"));
		if (msg != null)
		{
			System.out.println(_owner.getLocalName() + " is receiving target bomb");
			String[] splitMsg = msg.getContent().split(",");
			Point location = new Point();
			location.x = Integer.parseInt(splitMsg[0]);
			location.y = Integer.parseInt(splitMsg[1]);
		
			_owner.removeKnownBomb(location);
			
			if (location.equals(_owner.targetBombLocation))
			{
				_owner.targetBombLocation = _owner.getCurrentPosition();
			}
		}
	}

	public int onEnd()
	{
		return RECEIVE_DONE;
	}
}
