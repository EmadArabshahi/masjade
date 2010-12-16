import jade.core.AID;
import jade.core.Agent;

import java.util.ArrayList;

public class Game {	
	private AID _contestant1;
	private AID _contestant2;
	
	private int _currentRound;
	
	private ArrayList<Round> _rounds;
	private TournamentAgent _tournamentAgent;
	private int _gameNumber;
	
	public Game(TournamentAgent tournamentAgent, int game, AID contestant1, AID contestant2)
	{
		_tournamentAgent = tournamentAgent;
		_rounds = new ArrayList<Round>();
		_contestant1 = contestant1;
		_contestant2 = contestant2;
		_currentRound = 0;
		_gameNumber = game;
		
		createRounds();
	}
	
	private void createRounds()
	{
		// create the rounds that should be played. each game consists of 200 rounds.
		for (int roundNumber = 0; roundNumber < Rules.getNumberOfRoundsPerGame(); roundNumber++)
		{
			Round round = new Round(_tournamentAgent, roundNumber, _gameNumber, _contestant1, _contestant2);
			_rounds.add(round);
		}
	}
	
	public void play()
	{
		Output.AgentMessage(_tournamentAgent, String.format("Starting game (%s).", _gameNumber));
		// play all the rounds.
		for (Round round : _rounds)
		{
			round.play();
			_currentRound++;
		}
	}
	
	public int getCurrentRoundNumber()
	{
		return _currentRound;
	}
}