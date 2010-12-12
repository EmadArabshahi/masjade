package exerc1.behaviours;

import java.awt.Point;

import exerc1.MasterDisposerAgent;

import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import logistics.GridWorldAgent;

public class SendTargetBombReplyAction extends OneShotBehaviour {
	private MasterDisposerAgent _owner;
	private ACLMessage _request;
	private Point _target;

	public SendTargetBombReplyAction(MasterDisposerAgent owner, ACLMessage request, Point target) {
		_owner = owner;
		_request = request;
		_target = target;
	}

	@Override
	public void action() {
		_owner.removeKnownBomb(_target);
		System.out.println("NEW TARGET SENT!!!!!!!!!!!!!!!!");
		DFAgentDescription dfd = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("receive-target-bomb-position");
		dfd.addServices(sd);
		
		ACLMessage reply = _request.createReply();
		reply.setOntology("target-bomb-reply");
		reply.setContent(String.format("%s,%s", _target.x, _target.y));
		_owner.send(reply);	
	}
}
