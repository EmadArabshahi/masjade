package axelrod;

import java.util.ArrayList;
import java.util.List;

import axelrod.agents.TournamentAgent;

public class Tournament 
{
	private TournamentAgent _owner;
	private List<Contestant> _contestants;
	private List<Game> _games;
	int _currentGame;
	
	public Tournament(TournamentAgent owner, List<Contestant> contestants)
	{
		this._owner = owner;
		this._contestants = contestants;
		this._games = createGames();
		this._currentGame = 0;
	}
	
	
	private List<Game> createGames()
	{
		List<Game> games = new ArrayList<Game>();
		// loop in such a way that each pair only comes up once,
		// and no pairs are made with the same object.
		int gameNumber = 0;
		
		for (int i = 0; i < _contestants.size(); i++)
		{
			for(int j = i + 1; j < _contestants.size(); j++)
			{
				// create for each pair the amount of games that is set (5).
				for (int k = 0; k < Rules.getNumberOfGamesPerUniqueOpponents(); k++)
				{
					Game game = new Game(_owner, gameNumber, _contestants.get(i), _contestants.get(j));
					games.add(game);
					gameNumber++;
				}
			}
		}
		
		return games;
	}

	public void play()
	{
		for (Game game : _games)
		{
			game.play();
			_currentGame++;
		}
	}

}
