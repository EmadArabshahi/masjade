package exerc1.behaviours;

import java.awt.Point;
import java.util.Set;

import exerc1.DisposingAgent;
import exerc1.GridWorldAgent;
import exerc1.SensingAgent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

public class ReceiveRemovedBombsAction extends OneShotBehaviour {

	public static int RECEIVE_DONE = 1;
	private SensingAgent _owner;
	
	public ReceiveRemovedBombsAction(SensingAgent owner)
	{
		_owner = owner;
	}
	@Override
	public void action() {
		ACLMessage msg = _owner.receive();
		if (msg != null)
		{
			if (msg.getOntology() == "bomb-cleared-inform-onthology")
			{
				String[] splitMsg = msg.getContent().split(",");
				Point location = new Point();
				location.x = Integer.parseInt(splitMsg[0]);
				location.y = Integer.parseInt(splitMsg[1]);
			
				_owner.broadcastedBombPositions.remove(location);
				_owner.removeKnownBomb(location);
			}
		}
	}

	public int onEnd()
	{
		return RECEIVE_DONE;
	}
}
