package axelrod.behaviours;

import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import axelrod.agents.TournamentAgent;

public class RefreshAgentListBehaviour extends TickerBehaviour
{	
	private static final long serialVersionUID = -6567203261955271409L;

	public RefreshAgentListBehaviour(TournamentAgent tournamentAgent, int refreshInMs)
	{
		super(tournamentAgent, refreshInMs);
	}

	@Override
	protected void onTick() 
	{
		//Setup agent description.
		DFAgentDescription tournamentParticipantsDescription = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription(); 
	    sd.setType("contestant"); 
	    sd.setName("contestant"); 
	    tournamentParticipantsDescription.addServices(sd); 
		
	    try
		{
	    	TournamentAgent agent = (TournamentAgent) myAgent;
			DFAgentDescription[] participants = DFService.search(agent, tournamentParticipantsDescription);
			agent.setAvailableAgents(participants);
		}
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	}	
}
