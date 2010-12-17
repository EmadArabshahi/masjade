package axelrod;
import axelrod.behaviours.ProcessMoveRequestBehaviour;
import axelrod.Rules;
import jade.core.AID;

public class Round {
	private AID _contestant1;
	private AID _contestant2;
	
	private boolean _cooperationContestant1;
	private boolean _cooperationContestant2;
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
		_tournamentAgent.addBehaviour(new ProcessMoveRequestBehaviour(_contestant1, _roundNumber, _gameNumber));
		_tournamentAgent.addBehaviour(new ProcessMoveRequestBehaviour(_contestant2, _roundNumber, _gameNumber));
	}
	
	public int getUtilityContestant1() throws Exception
	{
		int utility = -1;
		if (_played)
		{
			utility = Rules.getUtilityForPlayer1(player1Action, player2Action)
			getUtility(_cooperationContestant1, _cooperationContestant2);
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
			utility = Rules.getUtility(_cooperationContestant2, _cooperationContestant1);
		}
		else
		{
			throw new Exception("Round has not yet been played.");
		}
		return utility;
	}
}