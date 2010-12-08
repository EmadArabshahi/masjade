package exerc1.behaviours;

import java.awt.Point;

import exerc1.GridWorldAgent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

public class BroadcastClearedBombLocationsAction extends OneShotBehaviour {
	public static int BROADCAST_DONE = 1;
	
	private GridWorldAgent _owner;

	public BroadcastClearedBombLocationsAction(GridWorldAgent owner)
	{
		_owner = owner;
	}
	
	@Override
	public void action() {
		DFAgentDescription dfd = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("bomb-managing");
		dfd.addServices(sd);
		
		try
		{
			DFAgentDescription[] result = DFService.search(_owner, dfd);
			for (Point location : _owner.getRemovedBombLocations())
			{
				ACLMessage bombClearedInform = new ACLMessage(ACLMessage.INFORM);
				
				for (int i = 0; i < result.length; i++)
				{
					bombClearedInform.addReceiver(result[i].getName());
				}
				
				bombClearedInform.setOntology("bomb-cleared-inform-onthology");
				bombClearedInform.setContent(String.format("%s,%s", location.x, location.y));
				_owner.send(bombClearedInform);
			}
			_owner.clearRemovedBombLocations();
		}
		catch (FIPAException fe)
		{
			fe.printStackTrace();
		}
	}
	
	public int onEnd()
	{
		return BROADCAST_DONE;
	}
}
