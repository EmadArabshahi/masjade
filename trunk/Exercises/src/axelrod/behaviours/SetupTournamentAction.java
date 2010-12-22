package axelrod.behaviours;

import axelrod.Output;
import axelrod.Rules;
import axelrod.agents.TournamentAgent;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.WakerBehaviour;

public class SetupTournamentAction extends OneShotBehaviour {
	@Override
	public void action() {
		TournamentAgent agent = (TournamentAgent) myAgent;
		
		Output.AgentMessage(agent, "Tournament started.");
		Output.AgentMessage(agent, "Registering contestants...");
		
		//TODO
		//agent.registerContestants();
		
		
		agent.setupTournament();
		
		//Output.AgentMessage(agent, String.format("%s contestants registered.", agent.getContestants().size()));
		Output.AgentMessage(agent, String.format("Creating games (%s for each pair of contestants)...", Rules.getNumberOfGamesPerUniqueOpponents()));
		
		//TODO 
		//agent.createGames();
		
		
		//Output.AgentMessage(agent, String.format("%s games created.", agent.getGames().size()));
    } 
}
