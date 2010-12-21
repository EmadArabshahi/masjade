package axelrod.behaviours;

import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import axelrod.Contestant;
import axelrod.agents.TournamentAgent;
import axelrod.messages.ApplyForTournament;
import axelrod.messages.RoundResult;

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
		TournamentAgent agent = (TournamentAgent) myAgent;
		
		ACLMessage msg = agent.receive(ApplyForTournament.getMessageTemplate());
		
		if(msg != null)
		{
			ApplyForTournament aply = new ApplyForTournament(msg);
			Contestant contestant = new Contestant(aply.getPlayer(), aply.getStrategy());
			agent.addContestant(contestant);
		}
		else
		{
			block();
		}
		
	}	
}
