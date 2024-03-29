package axelrod.behaviours;
import axelrod.Output;
import axelrod.agents.TournamentAgent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;


public class PlayTournamentAction extends OneShotBehaviour {
	@Override
	public void action() 
	{
		TournamentAgent agent = (TournamentAgent) myAgent;
		Output.AgentMessage(agent, "Games started.");
		agent.play();
		
		
		
	}
}
