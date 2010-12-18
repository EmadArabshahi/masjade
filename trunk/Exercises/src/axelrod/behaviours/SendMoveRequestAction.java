package axelrod.behaviours;
import axelrod.Output;
import axelrod.Round;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class SendMoveRequestAction extends OneShotBehaviour {
	private Round _round;
	
	public SendMoveRequestAction(Round round) {
		_round = round;
	}

	@Override
	public void action() {
		ACLMessage moveRequest = new ACLMessage(ACLMessage.REQUEST);
			
		moveRequest.setConversationId(_round.getConversationId());
		moveRequest.addReceiver(_round.getContestant1());
		moveRequest.addReceiver(_round.getContestant2());
		moveRequest.setOntology("move");
		
		myAgent.send(moveRequest);

		Output.AgentMessage(myAgent, String.format("Move request sent (%s)", _round.getConversationId()));
	}
}