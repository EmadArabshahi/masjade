package axelrod;

import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ReceiveMoveRequestBehaviour extends SimpleBehaviour {

	public static final int RECEIVED_MOVE_REQUEST = 1;
	
	private boolean _messageReceived;

	public ReceiveMoveRequestBehaviour()
	{
		
	}
	
	@Override
	public void action() {
		_messageReceived = false;
		ContestantAgent agent = (ContestantAgent) myAgent;
		ACLMessage msg = agent.receive(MessageTemplate.MatchOntology("move"));
		
		if (msg != null)
		{
			Output.AgentMessage(agent, String.format("Received move request (%s)", msg.getConversationId()));
			agent.setCurrentMoveRequest(msg);
			_messageReceived = true;
		}
		else
		{
			block();
		}
	}

	@Override
	public boolean done() {
		return _messageReceived;
	}
	
	@Override
	public int onEnd()
	{
		return RECEIVED_MOVE_REQUEST;
	}
}
