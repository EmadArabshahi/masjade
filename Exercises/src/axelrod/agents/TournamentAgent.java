package axelrod.agents;
import axelrod.Contestant;
import axelrod.Game;
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

import axelrod.behaviours.EndTournamentAction;
import axelrod.behaviours.PlayTournamentAction;
import axelrod.behaviours.RefreshAgentListBehaviour;
import axelrod.behaviours.SetupTournamentAction;
import axelrod.gui.Window;


public class TournamentAgent extends Agent 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8202958476515095561L;
	private List<Game> _games;
	private List<Contestant> _contestants;
	private int _currentGame;
	public SequentialBehaviour behaviours;
	
	private Window _window;
	
	public TournamentAgent()
	{
		_games = new ArrayList<Game>();
	}
	
	public void setup()
	{
		registerServices();
		
		_window = new Window(this);
		_window.setVisible(true);	
		
		addBehaviour(new RefreshAgentListBehaviour(this,1000));
	}
	
	/*
	public void setAvailableAgents(DFAgentDescription[] participants)
	{
		List<AID> newAgents = new ArrayList<AID>();
		for(int i=0; i<participants.length; i++)
		{
			newAgents.add(participants[i].getName());
		}
		_window.updateAgentList(newAgents);
	}
	*/
	
	public void registerContestants()
	{
		this._contestants = _window.getSelectedContestants();
	}
	
	protected void takedown()
	{
		_window.dispose();
		deregisterServices();
	}
	
	private void registerServices()
	{
		//The agent description
	    DFAgentDescription dfd = new DFAgentDescription(); 
	    dfd.setName(getAID()); 
	    
	    ServiceDescription sd = new ServiceDescription(); 
	    sd.setType("axelrod-tournament-host"); 
	    sd.setName("axelrod-tournament-host"); 
	    dfd.addServices(sd); 
	    
	    try 
	    { 
	    	DFService.register(this, dfd); 
	    }
	    catch (FIPAException fe) 
	    { 
	    	fe.printStackTrace(); 
	    }		
	}
	
	private void deregisterServices()
	{
		try 
		{ 
			DFService.deregister(this); 
		}  
		catch (FIPAException fe) 
		{ 
			fe.printStackTrace(); 
		}
	}
	
	public void addContestant(Contestant contestant)
	{
		this._window.addContestant(contestant);
	}
	
	public void startBehaviours()
	{
		addBehaviour(behaviours);
	}
	
	public List<Game> getGames()
	{
		return _games;
	}
	
	public List<Contestant> getContestants()
	{
		return this._contestants;
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
