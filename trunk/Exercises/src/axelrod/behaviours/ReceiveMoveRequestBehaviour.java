package axelrod.behaviours;

import axelrod.Output;
import axelrod.agents.AbstractContestantAgent;
import axelrod.messages.MoveRequest;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ReceiveMoveRequestBehaviour extends SimpleBehaviour {

	public static final int RECEIVED_MOVE_REQUEST = 1;
	
	private boolean _messageReceived;

	@Override
	public void action() {
		_messageReceived = false;
		
		System.out.println("Waiting for moveRequest!");
		
		AbstractContestantAgent agent = (AbstractContestantAgent) myAgent;
		ACLMessage msg = agent.receive();
		
		if (msg != null)
		{
			Output.AgentMessage(agent, String.format("Received move request (%s)", msg.getConversationId()));
			MoveRequest moveRequest = new MoveRequest(msg);
			agent.setCurrentMoveRequest(moveRequest);
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
