package exerc1.behaviours;

import java.awt.Point;
import java.util.ArrayList;

import exerc1.MasterDisposerAgent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class ProcessBombPositionAction extends OneShotBehaviour {

	public static final int PROCESSED_BOMB_POSITION = 1;
	private MasterDisposerAgent _owner;

	public ProcessBombPositionAction(MasterDisposerAgent owner) {
		_owner = owner;
	}

	@Override
	public void action() {
		if (_owner.currentMessage != null)
		{
			ACLMessage msg = _owner.currentMessage;
			
			String[] splitMsg = msg.getContent().split(",");
			Point location = new Point();
			location.x = Integer.parseInt(splitMsg[0]);
			location.y = Integer.parseInt(splitMsg[1]);
			
			_owner.addKnownBomb(location);
			
			ArrayList<ACLMessage> processedRequests = new ArrayList<ACLMessage>();
			for (ACLMessage request : _owner.unrepliedTargetBombRequests)
			{
				Point newTarget = _owner.getNewBombTarget();
				if (newTarget != null)
				{
					new SendTargetBombReply(_owner, request, newTarget).action();
					processedRequests.add(request);
				}
			}
			_owner.unrepliedTargetBombRequests.removeAll(processedRequests);
			
			_owner.currentMessage = null;
		}
	}
	
	public int onEnd()
	{
		return PROCESSED_BOMB_POSITION;
	}
}
