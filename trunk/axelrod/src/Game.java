import java.util.ArrayList;

public class Game {	
	private Contestant _contestant1;
	private Contestant _contestant2;
	
	private int _currentRound;
	
	private ArrayList<Round> _rounds;
	
	public Game(Contestant contestant1, Contestant contestant2)
	{
		_contestant1 = contestant1;
		_contestant2 = contestant2;
		_currentRound = 0;
		
		createRounds();
	}
	
	private void createRounds()
	{
		// create the rounds that should be played. each game consists of 200 rounds.
		for (int i = 0; i < Rules.getNumberOfRoundsPerGame(); i++)
		{
			Round round = new Round(_contestant1, _contestant2);
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
}
