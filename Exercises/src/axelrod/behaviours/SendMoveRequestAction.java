package axelrod.behaviours;
import axelrod.Output;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class SendMoveRequestAction extends OneShotBehaviour {
	private AID _contestant1;
	private AID _contestant2;
	private int _roundNumber;
	private int _gameNumber;
	private boolean _done;
	private String _conversationId;
	
	public SendMoveRequestAction(AID contestant1, AID contestant2, int roundNumber, int gameNumber) {
		_contestant1 = contestant1;
		_contestant2 = contestant2;
		_roundNumber = roundNumber;
		_gameNumber = gameNumber;
		_done = false;
		_conversationId = String.format("move-game:%s-round:%s", gameNumber, roundNumber); 
	}

	@Override
	public void action() {
		ACLMessage moveRequest = new ACLMessage(ACLMessage.REQUEST);
			
		moveRequest.setConversationId(_conversationId);
		moveRequest.addReceiver(_contestant1);
		moveRequest.addReceiver(_contestant2);
		moveRequest.setOntology("move");
		
		myAgent.send(moveRequest);

		Output.AgentMessage(myAgent, String.format("Move request sent (%s).", _conversationId));
	}

	public String getConversationId() {
		return _conversationId;
	}
}