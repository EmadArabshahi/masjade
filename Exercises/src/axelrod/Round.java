package axelrod;
import axelrod.agents.TournamentAgent;
import axelrod.behaviours.ReceiveMoveReplyAction;
import axelrod.behaviours.SendMoveRequestAction;
import axelrod.behaviours.SendRoundResultAction;
import axelrod.Rules;
import jade.core.AID;

public class Round {
	private int _actionContestant1;
	private int _actionContestant2;
	private boolean _played;
	private int _roundNr;
	private Game _game;
	
	public Round(int roundNumber, Game game)
	{
		_roundNr = roundNumber;
		_game = game;
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
		return String.format("game;%s|round;%s", _game.getGameNumber(), _roundNr);
	}

	public Contestant getContestant1() {
		return _game.getContestant1();
	}
	
	public Contestant getContestant2() {
		return _game.getContestant2();
	}
	
	public int getRoundNr()
	{
		return _roundNr;
	}
	
	public int getGameNr()
	{
		return _game.getGameNumber();
	}
	
	public int getActionContestant1() {
		return _actionContestant1;
	}
	
	public int getActionContestant2() {
		return _actionContestant2;
	}
	
	public void play() 
	{
		TournamentAgent agent = _game.getTournamentAgent();
		agent.behaviours.addSubBehaviour(new SendMoveRequestAction(this));
		agent.behaviours.addSubBehaviour(new ReceiveMoveReplyAction(this));
		agent.behaviours.addSubBehaviour(new SendRoundResultAction(this));
	}
	
	public int getUtilityContestant1()
	{
		return Rules.getUtilityForPlayer1(_actionContestant1, _actionContestant2);
	}
	
	public int getUtilityContestant2()
	{
		return Rules.getUtilityForPlayer2(_actionContestant1, _actionContestant2);
	}

	public Game getGame() {
		return _game;
	}
}