package exerc1.behaviours;

import java.awt.Point;

import exerc1.SensingAgent;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ProcessBombPickedUpAction extends OneShotBehaviour{

	public static final int RECEIVE_DONE = 1;
	
	private SensingAgent _owner;

	public ProcessBombPickedUpAction(SensingAgent owner)
	{
		_owner = owner;
	}
	@Override
	public void action() {
		ACLMessage msg = _owner.receive(MessageTemplate.MatchOntology("receive-bomb-picked-up"));
		if (msg != null)
		{
			String[] splitMsg = msg.getContent().split(",");
			Point location = new Point();
			location.x = Integer.parseInt(splitMsg[0]);
			location.y = Integer.parseInt(splitMsg[1]);
			
			_owner.broadcastedBombPositions.remove(location);
			
			System.out.println("pickeduprecieved!!!!!!!!!!!!!!!!");
		}
	}
	
	public int onEnd()
	{
		return RECEIVE_DONE;
	}

}
