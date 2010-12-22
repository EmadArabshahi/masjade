package axelrod.agents;
import axelrod.Contestant;
import axelrod.Game;
import axelrod.Rules;
import axelrod.Tournament;
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
	private Tournament _currentTournament;
	public SequentialBehaviour _behaviours;
	
	private Window _window;
	
	
	public void setup()
	{
		registerServices();
		
		_currentTournament = null;
		
		_window = new Window(this);
		_window.setVisible(true);	
		
		addBehaviour(new RefreshAgentListBehaviour(this,1000));
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
		addBehaviour(_behaviours);
	}
	
	public void setupTournament()
	{
		Tournament tournament = new Tournament(this, _window.getSelectedContestants());
		this._currentTournament = tournament;
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
		_behaviours = new SequentialBehaviour();
		
		if(_currentTournament != null)
			_currentTournament.play();
		
		_behaviours.addSubBehaviour(new EndTournamentAction());
		startBehaviours();
	}

	public void stop() {
		_window.reset();
	}
}
