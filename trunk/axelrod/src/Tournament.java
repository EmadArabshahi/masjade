import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

import java.awt.Point;
import java.util.ArrayList;


public class Tournament extends Agent {
	private ArrayList<AID> _games;
	private ArrayList<AID> _contestants;
	private int _currentGame;
	
	public Tournament()
	{
		_games = new ArrayList<AID>();
		_contestants = new ArrayList<AID>();
	}
	
	public void setup()
	{
		addBehaviour(new SetupTournamentBehaviour(this, 1000));
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
	
	private void createGames()
	{
		// each contestant plays 5 other games against each other contestant.
		for (AID contestant1 : _contestants)
		{
			for (AID contestant2 : _contestants)
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
		for (AID game : _games)
		{
			//game.play();
			_currentGame++;
		}
	}
}
