package contractnet.src.behaviours;

import contractnet.src.Output;
import contractnet.src.agents.ManagerAgent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class WaitForStartupBehaviour extends SimpleBehaviour {

	private static final long serialVersionUID = 7777538405586761017L;
	private boolean done = false;
	private ManagerAgent agent;
	@Override
	public void action() {
		// TODO Auto-generated method stub
		agent = (ManagerAgent) myAgent;
		ACLMessage msg = myAgent.receive();
		Output.AgentMessage(myAgent, "Trying to recieve startup message.");
		if (msg != null)
		{
			if ( msg.getContent().equals("Start execution"))
			{
				done = true;
				agent.setAnnounceReady(true);
				Output.AgentMessage(myAgent, "Received startup message");
			}
		}
		else
		{
			Output.AgentMessage(myAgent, "Message is null.");
			block(100);
		}

	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return done;
	}

}
