package axelrod;
import axelrod.behaviours.ReceiveMoveReplyAction;
import axelrod.behaviours.SendMoveRequestAction;
import axelrod.behaviours.SendRoundResultAction;
import axelrod.Rules;
import jade.core.AID;

public class Round {
	private AID _contestant1;
	private AID _contestant2;
	private int _actionContestant1;
	private int _actionContestant2;
	private boolean _played;
	private TournamentAgent _tournamentAgent;
	private String _conversationId;
	
	public Round(TournamentAgent tournamentAgent, int roundNumber, int gameNumber, AID contestant1, AID contestant2)
	{
		_tournamentAgent = tournamentAgent;
		_contestant1 = contestant1;
		_contestant2 = contestant2;
		_conversationId = String.format("game;%s|round;%s", gameNumber, roundNumber);
	}
	
	public void setActionContestant1(int action)
	{
		_actionContestant1 = action;
	}
	
	public void setActionContestant2(int action)
	{
		_actionContestant2 = action;
	}
	
	public String getConversationId() {
		return _conversationId;
	}

	public AID getContestant1() {
		return _contestant1;
	}
	
	public AID getContestant2() {
		return _contestant2;
	}
	
	public int getActionContestant1() {
		return _actionContestant1;
	}
	
	public int getActionContestant2() {
		return _actionContestant2;
	}
	
	public void play() 
	{
		_tournamentAgent.behaviours.addSubBehaviour(new SendMoveRequestAction(this));
		_tournamentAgent.behaviours.addSubBehaviour(new ReceiveMoveReplyAction(this));
		_tournamentAgent.behaviours.addSubBehaviour(new SendRoundResultAction(this));
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