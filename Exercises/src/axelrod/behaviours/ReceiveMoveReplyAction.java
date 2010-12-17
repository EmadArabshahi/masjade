package axelrod.behaviours;

import axelrod.Output;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ReceiveMoveReplyAction extends SimpleBehaviour {
	private String _conversationId;
	private int _counter;
	
	public ReceiveMoveReplyAction(String conversationId)
	{
		_conversationId = conversationId;
		_counter = 0;
	}
	
	@Override
	public void action() 
	{
		ACLMessage msg = myAgent.receive(MessageTemplate.MatchConversationId(_conversationId));
		
		if (msg != null)
		{
			_counter++;
			Output.AgentMessage(myAgent, String.format("Move reply received (%s)", _conversationId));
		}
	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return _counter == 2;
	}
	
}