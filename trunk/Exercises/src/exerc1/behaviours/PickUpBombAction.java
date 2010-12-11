package exerc1.behaviours;

import java.awt.Point;
import java.util.Iterator;

import exerc1.GridWorldAgent;
import exerc1.MasterDisposerAgent;
import gridworld.Environment;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

public class PickUpBombAction extends OneShotBehaviour {

	public static final int PICKED_UP_BOMB = 1;
	public static final int FOUND_NO_BOMB = 2;
	private GridWorldAgent _owner;
	private int _endState;

	public PickUpBombAction(GridWorldAgent owner) {
		_owner = owner;
		_endState = -1;
	}

	@Override
	public void action() {
		if (_owner.isOnBomb() && _owner.getCurrentPosition().equals(_owner.targetBombLocation))
		{			
			DFAgentDescription dfd = new DFAgentDescription();
			ServiceDescription sd = new ServiceDescription();
			sd.setType("receive-bomb-picked-up");
			dfd.addServices(sd);
			
			try
			{	
				ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
				
				msg.setContent(String.format("%s,%s", _owner.targetBombLocation.x, _owner.targetBombLocation.y));
				msg.setOntology("receive-bomb-picked-up");
				
				DFAgentDescription[] result = DFService.search(_owner, dfd);
				for (int i = 0; i < result.length; i++)
				{
					msg.addReceiver(result[i].getName());
				}
				_owner.send(msg);
			}
			catch (FIPAException fe)
			{
				fe.printStackTrace();
			}
			_owner.bombPickedUp(Environment.takeBomb(_owner.getLocalName()));
			_owner.hasBomb = true;
			_owner.targetBombLocation = null;
			
			_endState = PICKED_UP_BOMB;
		}
		else
		{
			_owner.bombPickedUp(false);
			_endState = FOUND_NO_BOMB;
		}
	}
	
	public int onEnd()
	{
		return _endState;
	}
}
