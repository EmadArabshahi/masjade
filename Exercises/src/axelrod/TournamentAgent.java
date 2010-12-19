package axelrod;
import axelrod.Rules;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.SequentialBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

import java.util.ArrayList;
import java.util.List;

import axelrod.behaviours.PlayTournamentAction;
import axelrod.behaviours.RefreshAgentListBehaviour;
import axelrod.behaviours.SetupTournamentAction;
import axelrod.gui.Window;


public class TournamentAgent extends Agent 
{
	private List<Game> _games;
	private List<AID> _contestants;
	private int _currentGame;
	public SequentialBehaviour behaviours;
	
	private Window _window;
	
	public TournamentAgent()
	{
		_games = new ArrayList<Game>();
		_contestants = new ArrayList<AID>();
	}
	
	public void setAvailableAgents(DFAgentDescription[] participants)
	{
		List<AID> newAgents = new ArrayList<AID>();
		for(int i=0; i<participants.length; i++)
		{
			newAgents.add(participants[i].getName());
		}
		_window.updateAgentList(newAgents);
	}
	
	protected void takedown()
	{
		_window.dispose();
	}
	
	public void setup()
	{
		_window = new Window(this);
		_window.setVisible(true);	
		
		addBehaviour(new RefreshAgentListBehaviour(this,1000));
	}
	
	public void startBehaviours()
	{
		addBehaviour(behaviours);
	}
	
	public List<Game> getGames()
	{
		return _games;
	}
	
	public List<AID> getContestants()
	{
		return _contestants;
	}
	
	public void registerContestants()
	{
		_contestants = _window.getSelectedContestants();
	}
	
	public void createGames()
	{
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
					Game game = new Game(this, gameNumber, _contestants.get(i), _contestants.get(j));
					_games.add(game);
					gameNumber++;
				}
			}
		}
	}
	
	public void start()
	{
		SequentialBehaviour setupBehaviours = new SequentialBehaviour();
		setupBehaviours.addSubBehaviour(new SetupTournamentAction());
		setupBehaviours.addSubBehaviour(new PlayTournamentAction());
		addBehaviour(setupBehaviours);
	}
	
	public void play()
	{
		behaviours = new SequentialBehaviour();
		
		for (Game game : _games)
		{
			game.play();
			_currentGame++;
		}
		behaviours.addSubBehaviour(new EndTournamentAction());
		startBehaviours();
	}

	public void stop() {
		_window.reset();
	}
}
