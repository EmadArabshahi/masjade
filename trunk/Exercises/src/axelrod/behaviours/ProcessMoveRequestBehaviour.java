package axelrod.behaviours;
import axelrod.Output;
import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ProcessMoveRequestBehaviour extends SimpleBehaviour {
	private AID _contestant;
	private int _roundNumber;
	private int _gameNumber;
	private boolean _done;
	private boolean _msgSend;
	private String _conversationId;
	
	public ProcessMoveRequestBehaviour(AID contestant, int roundNumber, int gameNumber) {
		_contestant = contestant;
		_roundNumber = roundNumber;
		_gameNumber = gameNumber;
		_done = false;
		_msgSend = false;
		_conversationId = String.format("move-game:%s-round:%s-contestent:%s", gameNumber, roundNumber, contestant.getName()); 
	}

	@Override
	public void action() {
	    //Send messages.
		if (!_msgSend)
		{
			Output.AgentMessage(myAgent, String.format("Sending move request (%s)...", _conversationId));
			ACLMessage moveRequest = new ACLMessage(ACLMessage.REQUEST);
				
			moveRequest.setConversationId(_conversationId);
			moveRequest.addReceiver(_contestant);
			moveRequest.setOntology("move");
			// send the round number in the content.
			moveRequest.setContent(Integer.toString(_roundNumber));
			
			myAgent.send(moveRequest);
			_msgSend = true;
			Output.AgentMessage(myAgent, String.format("Move request sent (%s)...", _conversationId));
		}
		else
		{
			ACLMessage msg = myAgent.receive(MessageTemplate.MatchConversationId(_conversationId));
			
			if (msg != null)
			{
				Output.AgentMessage(myAgent, String.format("Move reply received (%s)", _conversationId));
			}
		}
	}

	@Override
	public boolean done() {
		return _done;
	}
}