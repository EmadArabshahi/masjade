package contractnet.src.behaviours;

import java.io.IOException;

import contractnet.src.Output;
import contractnet.src.agents.ManagerAgent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class AnnounceTaskBehaviour extends OneShotBehaviour {

	private static final long serialVersionUID = 7840622197992536302L;
	
	private ManagerAgent agent;
	
	@Override
	public void action() {
		// TODO Auto-generated method stub
		agent = (ManagerAgent) myAgent;
		
		Output.AgentMessage( agent, "Starting task announcement");
		ACLMessage announceMsg = new ACLMessage( ACLMessage.INFORM);
		announceMsg.setOntology("Announcement");
		try {
			announceMsg.setContentObject( agent.getTask());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		agent.sendMessageToContractors( announceMsg);
		
		Output.AgentMessage( agent, "Task Announcement is sent.");
		Output.AgentMessage( agent, "Waiting contractors' offers.");
	}

}
