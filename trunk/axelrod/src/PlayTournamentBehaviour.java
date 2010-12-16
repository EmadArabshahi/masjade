import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;


public class PlayTournamentBehaviour extends OneShotBehaviour {
	@Override
	public void action() {
		TournamentAgent agent = (TournamentAgent) myAgent;
		Output.AgentMessage(agent, "Games started.");
		agent.play();
	}
}
