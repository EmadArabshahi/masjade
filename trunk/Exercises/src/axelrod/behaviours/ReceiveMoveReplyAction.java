package axelrod.behaviours;

import axelrod.Output;
import axelrod.Round;
import axelrod.messages.MoveReply;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ReceiveMoveReplyAction extends SimpleBehaviour {
	private int _numberMessagesReceived;
	private Round _round;
	
	public ReceiveMoveReplyAction(Round round)
	{
		_round = round;
		_numberMessagesReceived = 0;
	}
	
	@Override
	public void action() 
	{
		//matching converstationID is not enough, since it can be a reply from the system informing the message could not be delivered.
		//ACLMessage msg = myAgent.receive(MessageTemplate.MatchConversationId(_round.getConversationId()));
		ACLMessage msg = myAgent.receive(MoveReply.getMessageTemplate(_round.getConversationId()));
		
		if (msg != null)
		{
			
			MoveReply moveReply = new MoveReply(msg);
			
			_numberMessagesReceived++;
			
			Output.AgentMessage(myAgent, String.format("Move reply received (%s), %s", moveReply.getConversationId(), moveReply.getMessage().getContent()));
			
			if (msg.getSender().equals(_round.getContestant1()))
			{
				_round.setActionContestant1(moveReply.getAction());
			}
			else if (msg.getSender().equals(_round.getContestant2()))
			{
				_round.setActionContestant2(moveReply.getAction());
			}
		}
	}

	@Override
	public boolean done() 
	{
		return _numberMessagesReceived == 2;
	}
}