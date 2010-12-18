package axelrod.behaviours;

import axelrod.ContestantAgent;
import axelrod.Output;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ReceiveRoundResultBehaviour extends SimpleBehaviour {

public static final int RECEIVED_ROUND_RESULT = 1;
	
	private boolean _messageReceived;
	
	@Override
	public void action() {
		_messageReceived = false;
		ContestantAgent agent = (ContestantAgent) myAgent;
		ACLMessage msg = agent.receive(MessageTemplate.MatchOntology("result"));
		
		if (msg != null)
		{
			String conversationId = msg.getConversationId();
			
			Output.AgentMessage(agent, String.format("Received round result (%s)", conversationId));
			
			// check whether this the first round. reset the agents move history lists if this is the case.
			// when the round is zero, then this is the first round of this game. this is gotten from
			// the conversationId.
			String[] unsplitKeyValuePairsConversationId = conversationId.split("\\|");
			
			for (String unsplitKeyValuePair : unsplitKeyValuePairsConversationId)
			{
				String[] keyValuePair = unsplitKeyValuePair.split(";");
				if (keyValuePair[0].equals("round") && keyValuePair[1].equals("0"))
				{
					agent.clearRoundHistory();
					Output.AgentMessage(agent, "Round history cleared");
				}
			}
			
			// store the moves in the contents
			String[] unsplitKeyValuePairsContent = msg.getContent().split("\\|");
			
			for (String unsplitKeyValuePair : unsplitKeyValuePairsContent)
			{
				String[] keyValuePair = unsplitKeyValuePair.split(";");
				// if the AID matches that of the agent it's his own move, otherwise
				// it's its opponents.
				if (keyValuePair[0].equals(agent.getAID()))
				{
					agent.addMoveToMyRoundHistory(Integer.parseInt(keyValuePair[1]));
				}
				else
				{
					agent.addMoveToOpponentRoundHistory(Integer.parseInt(keyValuePair[1]));
				}
			}
			
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
		return RECEIVED_ROUND_RESULT;
	}
}
