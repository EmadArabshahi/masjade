package exerc1.behaviours;

import java.awt.Point;

import exerc1.GridWorldAgent;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

public class BroadcastTargetBombAction extends OneShotBehaviour {

	public static int BROADCAST_DONE = 1;
	
	private GridWorldAgent _owner;

	public BroadcastTargetBombAction(GridWorldAgent owner)
	{
		_owner = owner;
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
			
			ACLMessage targetInform = new ACLMessage(ACLMessage.INFORM);
					
			for (int i = 0; i < result.length; i++)
			{
				AID name = result[i].getName();
				if (!name.equals(_owner.getAID()))
				{
					targetInform.addReceiver(name);
				}
			}
					
			targetInform.setOntology("target-inform-onthology");
			targetInform.setContent(String.format("%s,%s", _owner.targetBombLocation.x, _owner.targetBombLocation.y));
			_owner.send(targetInform);
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
