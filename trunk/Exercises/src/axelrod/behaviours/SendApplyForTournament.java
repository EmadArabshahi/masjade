package axelrod.behaviours;

import axelrod.Output;
import axelrod.agents.AbstractContestantAgent;
import axelrod.agents.TournamentAgent;
import axelrod.messages.ApplyForTournament;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

public class SendApplyForTournament extends OneShotBehaviour {

	public SendApplyForTournament() 
	{
	}

	@Override
	public void action() 
	{
		AbstractContestantAgent owner = (AbstractContestantAgent) myAgent;
		String strategy = owner.getStrategy();
		AID host = getHost();
		
		ApplyForTournament apply = new ApplyForTournament(strategy, host);
		myAgent.send(apply.getMessage());
		
		Output.AgentMessage(myAgent, String.format("Inform sent: %s", apply.getMessage().getContent()));
	}
	
	private AID getHost()
	{
		
		AID host = null;
		
		//Setup agent description.
		DFAgentDescription tournamentParticipantsDescription = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription(); 
	    sd.setType("axelrod-tournament-host"); 
	    sd.setName("axelrod-tournament-host"); 
	    tournamentParticipantsDescription.addServices(sd); 
		
	    try
		{
	    	TournamentAgent agent = (TournamentAgent) myAgent;
			DFAgentDescription[] hosts = DFService.search(agent, tournamentParticipantsDescription);
		
			host = hosts[0].getName();
		}
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	    return host;
	}
}

