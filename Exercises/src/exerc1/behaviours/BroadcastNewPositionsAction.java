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
	public static int BROADCAST_DONE = 1;
	
	private SensingAgent _owner;
	
	public BroadcastNewPositionsAction(SensingAgent owner)
	{
		_owner = owner;
	}
	
	public int onEnd()
	{
		return BROADCAST_DONE;
	}

	@Override
	public void action() {
		DFAgentDescription dfdBomb = new DFAgentDescription();
		ServiceDescription sdBomb = new ServiceDescription();
		sdBomb.setType("receive-bomb-position");
		dfdBomb.addServices(sdBomb);
		
		DFAgentDescription dfdTrap = new DFAgentDescription();
		ServiceDescription sdTrap = new ServiceDescription();
		sdTrap.setType("receive-trap-position");
		dfdTrap.addServices(sdTrap);
		
		try
		{
			DFAgentDescription[] resultBomb = DFService.search(_owner, dfdBomb);
			for (Point location : _owner.getKnownBombs())
			{
				if (!_owner.broadcastedBombPositions.contains(location))
				{
					_owner.broadcastedBombPositions.add(location);
					ACLMessage bombsInform = new ACLMessage(ACLMessage.INFORM);
					
					for (int i = 0; i < resultBomb.length; i++)
					{
						bombsInform.addReceiver(resultBomb[i].getName());
					}
					
					bombsInform.setOntology("bomb-position");
					bombsInform.setContent(String.format("%s,%s", location.x, location.y));
					_owner.send(bombsInform);
				}
			}
			
			DFAgentDescription[] resultTrap = DFService.search(_owner, dfdTrap);
			for (Point location : _owner.getKnownTraps())
			{
				if (!_owner.broadcastedTrapPositions.contains(location))
				{
					_owner.broadcastedTrapPositions.add(location);
					ACLMessage trapInformMsg = new ACLMessage(ACLMessage.INFORM);
					
					for (int i = 0; i < resultTrap.length; i++)
					{
						trapInformMsg.addReceiver(resultTrap[i].getName());
					}
					
					trapInformMsg.setOntology("trap-position");
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
