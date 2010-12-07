package exerc1.behaviours;

import java.awt.Point;

import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import exerc1.GridWorldAgent;
import exerc1.SensingAgent;

public class BroadcastNewPositionsAction extends OneShotBehaviour
{
	public static int INFORM_DONE = 1;
	
	private SensingAgent _owner;
	
	public BroadcastNewPositionsAction(SensingAgent owner)
	{
		_owner = owner;
	}
	
	public int onEnd()
	{
		return INFORM_DONE;
	}

	@Override
	public void action() {
		DFAgentDescription dfd = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("bomb-disposing");
		dfd.addServices(sd);
		
		try
		{
			DFAgentDescription[] result = DFService.search(_owner, dfd);
			for (Point location : _owner.getKnownBombs())
			{
				if (!_owner.broadcastedBombPositions.contains(location))
				{
					_owner.broadcastedBombPositions.add(location);
					ACLMessage bombsInform = new ACLMessage(ACLMessage.INFORM);
					
					for (int i = 0; i < result.length; i++)
					{
						bombsInform.addReceiver(result[i].getName());
					}
					
					bombsInform.setOntology("bomb-inform-onthology");
					bombsInform.setContent(String.format("%s,%s", location.x, location.y));
					_owner.send(bombsInform);
				}
			}
			
			for (Point location : _owner.getKnownTraps())
			{
				if (!_owner.broadcastedTrapPositions.contains(location))
				{
					_owner.broadcastedTrapPositions.add(location);
					ACLMessage trapInformMsg = new ACLMessage(ACLMessage.INFORM);
					
					for (int i = 0; i < result.length; i++)
					{
						trapInformMsg.addReceiver(result[i].getName());
					}
					
					trapInformMsg.setOntology("trap-inform-onthology");
					trapInformMsg.setContent(String.format("%s,%s", location.x, location.y));
					_owner.send(trapInformMsg);
				}
			}
		}
		catch (FIPAException fe)
		{
			fe.printStackTrace();
		}
	}
}
