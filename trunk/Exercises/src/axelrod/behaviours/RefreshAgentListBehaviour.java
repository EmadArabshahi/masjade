package axelrod.behaviours;

import java.awt.Point;

import java.util.Arrays;
import java.util.Set;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import axelrod.AxelrodTournamentHost;

public class RefreshAgentListBehaviour extends TickerBehaviour
{
	
	private AxelrodTournamentHost _owner;
	
	public RefreshAgentListBehaviour(AxelrodTournamentHost owner, int refreshInMs)
	{
		super(owner, refreshInMs);
		_owner = owner;
	}

	@Override
	protected void onTick() 
	{
		//Setup agent description.
		DFAgentDescription tournamentParticipantsDescription = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription(); 
	    sd.setType("axelrod-tournament-participant"); 
	    sd.setName("axelrod-tournament-participant"); 
	    tournamentParticipantsDescription.addServices(sd); 
		
	    try
		{
			DFAgentDescription[] participants = DFService.search(_owner, tournamentParticipantsDescription);
			_owner.setAvailableAgents(participants);
		
		}
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	    
		
		
	}
	
}
