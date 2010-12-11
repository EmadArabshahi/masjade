package exerc1.behaviours;

import java.awt.Point;
import java.util.Iterator;

import exerc1.MasterDisposerAgent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

public class ProcessMasterTargetBombRequestAction extends OneShotBehaviour {

	public static final int PROCESSED_TARGET_BOMB_REQUEST = 1;
	
	private MasterDisposerAgent _owner;

	public ProcessMasterTargetBombRequestAction(MasterDisposerAgent owner) {
		_owner = owner;
	}

	@Override
	public void action() {
		System.out.println("PROCESSING REQUEST!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		if (_owner.currentMessage != null)
		{
			ACLMessage request = _owner.currentMessage;
			
			System.out.println("PROCESSING REQUEST!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			
			if (_owner.disposerTargets.containsKey(request.getSender()))
			{
				_owner.disposerTargets.remove(request.getSender());
			}
			
			// find a new target
			Point newTarget = _owner.getNewBombTarget();
			
			if (newTarget != null)
			{
				new SendTargetBombReplyAction(_owner, request, newTarget).action();
			}
			else
			{
				_owner.unrepliedTargetBombRequests.add(request);
			}
			
			_owner.currentMessage = null;
		}
	}
	
	public int onEnd()
	{
		return PROCESSED_TARGET_BOMB_REQUEST;
	}
}
