package axelrod;

import axelrod.Rules;
import axelrod.agents.TournamentAgent;
import jade.core.AID;
import jade.core.Agent;

import java.util.ArrayList;
import java.util.List;

public class Game {	
	private Contestant _contestant1;
	private Contestant _contestant2;
	
	private int _currentRound;
	
	private ArrayList<Round> _rounds;
	private TournamentAgent _tournamentAgent;
	private int _gameNumber;
	
	public Game(TournamentAgent tournamentAgent, int game, Contestant contestant1, Contestant contestant2)
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
			Round round = new Round(roundNumber, this);
			_rounds.add(round);
		}
	}
	
	public void play()
	{
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
	
	public List<Round> getRounds()
	{
		return _rounds;
	}
	
	public Contestant getContestant1()
	{
		return _contestant1;
	}
	
	public Contestant getContestant2()
	{
		return _contestant2;
	}

	public int getUtilityContestant1() {
		int totalUtility = 0;
		for (Round round : _rounds)
		{
			totalUtility += round.getUtilityContestant1();
		}
		return totalUtility;
	}
	
	public int getUtilityContestant2() {
		int totalUtility = 0;
		for (Round round : _rounds)
		{
			totalUtility += round.getUtilityContestant2();
		}
		return totalUtility;
	}

	public int getGameNumber() {
		return _gameNumber;
	}

	public TournamentAgent getTournamentAgent() {
		return _tournamentAgent;
	}
}