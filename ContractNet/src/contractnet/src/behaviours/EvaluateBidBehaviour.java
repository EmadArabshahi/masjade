package contractnet.src.behaviours;

import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import contractnet.src.Bid;
import contractnet.src.Output;
import contractnet.src.agents.ManagerAgent;

public class EvaluateBidBehaviour extends SimpleBehaviour {

	private static final long serialVersionUID = -7352639800262926141L;
	private ManagerAgent agent;
	@Override
	public void action() {
		// TODO Auto-generated method stub
	agent = (ManagerAgent) myAgent;
		
		ACLMessage msg = agent.receive();
		
		if ( msg != null)
		{
			if ( msg.getOntology().equals( "Bid-Propose"))
			{
				Bid bid = null;
				try {
					bid = (Bid) msg.getContentObject();
				} catch (UnreadableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if ( bid != null)
				{
					Output.AgentMessage( agent, "Bid recieved");
					
					//Evaluate bid
					
					
					
					//send inform to bidder
					
				}
				
			}			
			
		}

	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return false;
	}

}
