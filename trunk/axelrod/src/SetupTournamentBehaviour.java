import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;

public class SetupTournamentBehaviour extends WakerBehaviour {

	public SetupTournamentBehaviour(Tournament tournament, long timeoutMilliSeconds) {
		super(tournament, timeoutMilliSeconds);
		Output.AgentMessage(myAgent, String.format("Waiting %s seconds for contestants.", timeoutMilliSeconds / 1000));
	}
	
	protected void handleElapsedTimeout() {
		Tournament agent = (Tournament) myAgent;
		Output.AgentMessage(agent, "Registering contestants.");
		agent.registerContestants();
		Output.AgentMessage(agent, String.format("%s contestants registered.", agent.getContestants().size()));
    } 
}
