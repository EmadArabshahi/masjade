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

import axelrod.behaviours.PlayTournamentBehaviour;
import axelrod.behaviours.RefreshAgentListBehaviour;
import axelrod.behaviours.SetupTournamentBehaviour;
import axelrod.gui.Window;


public class TournamentAgent extends Agent {
	private ArrayList<Game> _games;
	private ArrayList<AID> _contestants;
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
		List<String> newAgents = new ArrayList<String>();
		for(int i=0; i<participants.length; i++)
		{
			newAgents.add(participants[i].getName().getName());
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
	
	public ArrayList<Game> getGames()
	{
		return _games;
	}
	
	public ArrayList<AID> getContestants()
	{
		return _contestants;
	}
	
	public void registerContestants()
	{
		_contestants = new ArrayList<AID>();
		//Setup agent description.
		DFAgentDescription dfdContestants = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription(); 
	    sd.setType("contestant"); 
	    sd.setName("contestant"); 
	    dfdContestants.addServices(sd); 
	    
		try
		{
			DFAgentDescription[] resultContestants = DFService.search(this, dfdContestants);
					
			for (int i = 0; i < resultContestants.length; i++)
			{
				_contestants.add(resultContestants[i].getName());
			}
		}
		catch (FIPAException e) {
			e.printStackTrace();
		}
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
	
	public void play()
	{
		SequentialBehaviour setupBehaviours = new SequentialBehaviour();
		setupBehaviours.addSubBehaviour(new SetupTournamentBehaviour(this, 1000));
		setupBehaviours.addSubBehaviour(new PlayTournamentBehaviour());
		addBehaviour(setupBehaviours);
		behaviours = new SequentialBehaviour();
		
		for (Game game : _games)
		{
			game.play();
			_currentGame++;
		}
		startBehaviours();
	}
}
