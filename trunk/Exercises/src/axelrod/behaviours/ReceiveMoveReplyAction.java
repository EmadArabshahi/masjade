package axelrod.behaviours;

import axelrod.Output;
import axelrod.Round;
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
		ACLMessage msg = myAgent.receive(MessageTemplate.MatchConversationId(_round.getConversationId()));
		
		if (msg != null)
		{
			_numberMessagesReceived++;
			Output.AgentMessage(myAgent, String.format("Move reply received (%s)", _round.getConversationId()));
			if (msg.getSender().equals(_round.getContestant1()))
			{
				_round.setActionContestant1(Integer.parseInt(msg.getContent()));
			}
			else if (msg.getSender().equals(_round.getContestant2()))
			{
				_round.setActionContestant2(Integer.parseInt(msg.getContent()));
			}
		}
	}

	@Override
	public boolean done() 
	{
		return _numberMessagesReceived == 2;
	}
}