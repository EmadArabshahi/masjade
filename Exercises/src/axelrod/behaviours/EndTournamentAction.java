package axelrod.behaviours;

import axelrod.Output;
import axelrod.agents.TournamentAgent;
import jade.core.behaviours.OneShotBehaviour;

public class EndTournamentAction extends OneShotBehaviour {

	@Override
	public void action() {
		TournamentAgent agent = (TournamentAgent) myAgent;
		Output.AgentMessage(agent, "Tournament Finished!");
		agent.stop();		
	}
}
