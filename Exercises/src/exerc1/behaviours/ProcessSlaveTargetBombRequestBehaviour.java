package exerc1.behaviours;

import java.awt.Point;

import exerc1.SlaveDisposerAgent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ProcessSlaveTargetBombRequestBehaviour extends SimpleBehaviour {

	public static final int PROCESSED_TARGET_BOMB_REQUEST = 1;
	
	private SlaveDisposerAgent _owner;
	private boolean _requestSend; 

	private boolean _done;

	public ProcessSlaveTargetBombRequestBehaviour(SlaveDisposerAgent owner) {
		_owner = owner;
		_requestSend = false;
		_done = false;
	}

	@Override
	public void action() {
		if (!_requestSend)
		{
			DFAgentDescription dfd = new DFAgentDescription();
			ServiceDescription sd = new ServiceDescription();
			sd.setType("receive-target-bomb-request");
			dfd.addServices(sd);
			
			try
			{
				DFAgentDescription[] result = DFService.search(_owner, dfd);
				
				ACLMessage targetInform = new ACLMessage(ACLMessage.REQUEST);
				
				for (int i = 0; i < result.length; i++)
				{
					targetInform.addReceiver(result[i].getName());
				}
				
				targetInform.setOntology("target-bomb-request");
				
				_owner.send(targetInform);
				System.out.println("REQUEST SENT!!!!!!!!!!!!!");
				
				_requestSend = true;
			}
			catch (FIPAException fe)
			{
				fe.printStackTrace();
			}
		}
		else
		{
			// wait for reply
			ACLMessage msg = _owner.receive(MessageTemplate.MatchOntology("target-bomb-reply"));
			if (msg != null)
			{
				String[] splitMsg = msg.getContent().split(",");
				Point location = new Point();
				location.x = Integer.parseInt(splitMsg[0]);
				location.y = Integer.parseInt(splitMsg[1]);
			
				_owner.targetBombLocation = location;
				
				_done = true;
			}
			else
			{
				block();
			}
		}
	}
	
	@Override
	public boolean done() {
		return _done;
	}
	
	public int onEnd()
	{
		return PROCESSED_TARGET_BOMB_REQUEST;
	}
}
