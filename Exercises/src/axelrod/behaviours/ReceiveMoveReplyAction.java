package axelrod.behaviours;

import axelrod.Contestant;
import axelrod.Output;
import axelrod.Round;
import axelrod.Rules;
import axelrod.agents.TournamentAgent;
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
		ACLMessage msg = myAgent.receive(MoveReply.getMessageTemplate(_round.getConversationId()));
		
		if (msg != null)
		{
			
			MoveReply moveReply = new MoveReply(msg);
			
			_numberMessagesReceived++;
			
			Output.AgentMessage(myAgent, String.format("Move reply received (%s), %s", moveReply.getConversationId(), moveReply.getMessage().getContent()));
			
			if (msg.getSender().equals(_round.getContestant1().getAID()))
			{	
				_round.setActionContestant1(moveReply.getAction());
			}
			else if (msg.getSender().equals(_round.getContestant2().getAID()))
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
	
	@Override
	public int onEnd()
	{
		// update utilities for c1 and c2
		Contestant c1 = _round.getContestant1();
		Contestant c2 = _round.getContestant2();
		
		c1.addUtility(Rules.getUtilityForPlayer1(_round.getActionContestant1(), _round.getActionContestant2()));
		c2.addUtility(Rules.getUtilityForPlayer2(_round.getActionContestant1(), _round.getActionContestant2()));
		
		// update the current game display
		TournamentAgent agent = (TournamentAgent) myAgent;
		
		agent.updateCurrentGame(_round.getGame());
		agent.addPlayedRound(_round);
		
		if (_round.getRoundNr() == Rules.getNumberOfRoundsPerGame() - 1)
		{
			// if the game is finished, update the rankings
			agent.updateRankings();
			agent.addPlayedGame(_round.getGame());
		}
		
		_round.getGame().gotoNextRound();
		return 0;
	}
}