package exerc1.behaviours;

import java.awt.Point;
import java.util.Iterator;
import java.util.Set;

import exerc1.MasterDisposerAgent;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

public class SendTargetBombPositionAction extends OneShotBehaviour {

	public static final int SENT_TARGET_BOMB_POSITION = 1;
	private MasterDisposerAgent _owner;

	public SendTargetBombPositionAction(MasterDisposerAgent owner) {
		_owner = owner;
	}

	@Override
	public void action() {
			DFAgentDescription dfd = new DFAgentDescription();
			ServiceDescription sd = new ServiceDescription();
			sd.setType("receive-target-bomb-position");
			dfd.addServices(sd);
			
			try
			{	
				ACLMessage targetInform = new ACLMessage(ACLMessage.INFORM);
				
				/*
				if (_owner.currentDisposer != null)
				{
					targetInform.addReceiver(_owner.currentDisposer);
				}
				else
				{*/
					DFAgentDescription[] result = DFService.search(_owner, dfd);
					for (int i = 0; i < result.length; i++)
					{
						targetInform.addReceiver(result[i].getName());
					}
				//}
				
				targetInform.setOntology("target-bomb-position");
				
				// todo: find the closest bomb from the known bomb list instead of its own target
				if (_owner.knowsBombs())
				{
					Iterator<Point> knownBombs = _owner.getKnownBombs().iterator();
					Point bomb = knownBombs.next();
					targetInform.setContent(String.format("%s,%s", bomb.x, bomb.y));
				
					_owner.send(targetInform);
				}
				_owner.currentDisposer = null;
			}
			catch (FIPAException fe)
			{
				fe.printStackTrace();
			}
	}
	
	public int onEnd()
	{
		return SENT_TARGET_BOMB_POSITION;
	}
}
