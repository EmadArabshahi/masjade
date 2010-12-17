package axelrod;
import axelrod.behaviours.ReceiveMoveReplyAction;
import axelrod.behaviours.SendMoveRequestAction;
import axelrod.Rules;
import jade.core.AID;

public class Round {
	private AID _contestant1;
	private AID _contestant2;
	
	private int _actionContestant1;
	private int _actionContestant2;
	private boolean _played;
	private TournamentAgent _tournamentAgent;
	private int _roundNumber;
	private int _gameNumber;
	
	public Round(TournamentAgent tournamentAgent, int roundNumber, int gameNumber, AID contestant1, AID contestant2)
	{
		_tournamentAgent = tournamentAgent;
		_contestant1 = contestant1;
		_contestant2 = contestant2;
		_roundNumber = roundNumber;
		_gameNumber = gameNumber;
	}

	public void play() 
	{
		SendMoveRequestAction sendMoveRequest = new SendMoveRequestAction(_contestant1, _contestant2, _roundNumber, _gameNumber);
		
		_tournamentAgent.behaviours.addSubBehaviour(sendMoveRequest);
		_tournamentAgent.behaviours.addSubBehaviour(new ReceiveMoveReplyAction(sendMoveRequest.getConversationId()));
	}
	
	public int getUtilityContestant1() throws Exception
	{
		int utility = -1;
		if (_played)
		{
			utility = Rules.getUtilityForPlayer1(_actionContestant1, _actionContestant2);
		}
		else
		{
			throw new Exception("Round has not yet been played.");
		}
		return utility;
	}
	
	public int getUtilityContestant2() throws Exception
	{
		int utility = -1;
		if (_played)
		{
			utility = Rules.getUtilityForPlayer2(_actionContestant2, _actionContestant1);
		}
		else
		{
			throw new Exception("Round has not yet been played.");
		}
		return utility;
	}
}