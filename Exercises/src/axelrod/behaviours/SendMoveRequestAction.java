package axelrod.behaviours;
import axelrod.Output;
import axelrod.Round;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import axelrod.messages.*;


public class SendMoveRequestAction extends OneShotBehaviour {
	private Round _round;
	
	public SendMoveRequestAction(Round round) {
		_round = round;
	}

	@Override
	public void action() {
		
		
		MoveRequest moveRequest = new MoveRequest(_round);
		
		// moveRequest.setConversationId(_round.getConversationId());
		// moveRequest.addReceiver(_round.getContestant1());
		// moveRequest.addReceiver(_round.getContestant2());
		// moveRequest.setOntology("move");
		
		myAgent.send(moveRequest.getMessage());

		Output.AgentMessage(myAgent, String.format("Move request sent (%s), %s, %s, %s, %s, %s", moveRequest.getConversationId(), moveRequest.getPlayer1(), moveRequest.getPlayer2(), moveRequest.getMessage().getContent(), moveRequest.getLanguage(), moveRequest.getOntology()));
	}
}