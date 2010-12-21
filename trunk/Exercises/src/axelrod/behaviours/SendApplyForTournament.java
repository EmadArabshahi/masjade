package axelrod.behaviours;

import axelrod.Output;
import axelrod.agents.AbstractContestantAgent;
import axelrod.agents.TournamentAgent;
import axelrod.messages.ApplyForTournament;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

public class SendApplyForTournament extends SimpleBehaviour 
{
	public static final int MESSAGE_SEND = 1;
	private boolean _done = false;

	public void action()
	{
		AbstractContestantAgent owner = (AbstractContestantAgent) myAgent;
		String strategy = owner.getStrategy();
		AID host = getHost();
		
		if(host != null)
		{
			ApplyForTournament apply = new ApplyForTournament(strategy, host);
			myAgent.send(apply.getMessage());
		
			Output.AgentMessage(myAgent, String.format("Inform sent: %s", apply.getMessage().getContent()));
			_done = true;
		}
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
			DFAgentDescription[] hosts = DFService.search(myAgent, tournamentParticipantsDescription);
			if(hosts.length > 0)
				host = hosts[0].getName();
		}
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	    return host;
	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return _done;
	}
	
	@Override
	public int onEnd() 
	{
		return MESSAGE_SEND;
	}

}

