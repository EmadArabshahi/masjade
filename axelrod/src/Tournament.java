import java.util.ArrayList;


public class Tournament {
	private ArrayList<Game> _games;
	private ArrayList<AxelrodAgent> _contestants;
	private int _currentGame;
	
	public Tournament(ArrayList<AxelrodAgent> contestants)
	{
		_contestants = contestants;
		
		createGames();
	}
	
	private void createGames()
	{
		// each contestant plays 5 other games against each other contestant.
		for (AxelrodAgent contestant1 : _contestants)
		{
			for (AxelrodAgent contestant2 : _contestants)
			{
				if (contestant1 != contestant2)
				{
					for (int i = 0; i < Rules.getNumberOfGamesPerUniqueOpponents(); i++)
					{
						
					}
				}
			}
		}
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
