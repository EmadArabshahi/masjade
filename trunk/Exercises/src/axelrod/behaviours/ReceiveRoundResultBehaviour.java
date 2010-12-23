package axelrod.behaviours;

import axelrod.Output;
import axelrod.agents.AbstractContestantAgent;
import axelrod.messages.RoundResult;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ReceiveRoundResultBehaviour extends SimpleBehaviour {

public static final int RECEIVED_ROUND_RESULT = 1;
	
	private boolean _messageReceived;
	
	@Override
	public void action() {
		_messageReceived = false;
		AbstractContestantAgent agent = (AbstractContestantAgent) myAgent;
		
		ACLMessage msg = agent.receive(RoundResult.getMessageTemplate());
		
		if (msg != null)
		{
			
			RoundResult roundResult = new RoundResult(msg);
			
			if(roundResult.getRoundNr() == 0)
				agent.clearRoundHistory();
			
			if(agent.getAID() == roundResult.getPlayer1())
			{
				agent.addMoveToMyRoundHistory(roundResult.getActionPlayer1());
				agent.addMoveToOpponentRoundHistory(roundResult.getActionPlayer2());
			}
			else
			{
				agent.addMoveToMyRoundHistory(roundResult.getActionPlayer2());
				agent.addMoveToOpponentRoundHistory(roundResult.getActionPlayer2());
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
