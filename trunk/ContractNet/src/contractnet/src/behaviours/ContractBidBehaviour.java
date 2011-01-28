package contractnet.src.behaviours;

import java.io.IOException;

import contractnet.src.Bid;
import contractnet.src.Output;
import contractnet.src.Task;
import contractnet.src.agents.ContractorAgent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

public class ContractBidBehaviour extends SimpleBehaviour {

	private static final long serialVersionUID = 4001679695379004945L;
	
	private ContractorAgent agent;
	private final boolean done = false;
	
	@Override
	public void action() {
		// TODO Auto-generated method stub
		agent = (ContractorAgent) myAgent;
		
		ACLMessage msg = agent.receive();
		
		if ( msg != null)
		{
			if ( msg.getOntology().equals( "Announcement"))
			{
				Task task = null;
				try {
					task = (Task) msg.getContentObject();
				} catch (UnreadableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if ( task != null)
				{
					Output.AgentMessage( agent, "Task recieved");
					Bid bid = evaluate( task);
					if ( bid != null)
					{
						ACLMessage bidMsg = new ACLMessage( ACLMessage.INFORM);
						bidMsg.setOntology("Bid-Propose");
						bidMsg.addReceiver( msg.getSender());
						try {
							bidMsg.setContentObject( bid);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						agent.send(bidMsg);
					}
					else
					{
						
					}
					
				}
				else
					Output.AgentMessage( agent, "Cannot read task.");
			}
			else if ( msg.getOntology().equals( "Inform"))
			{
				
			}
			else if ( msg.getOntology().equals("Bid")) //for sub-contractors?
			{
				
			}
			
		}
		

	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return done;
	}
	
	private Bid evaluate( Task task)
	{
		Bid newBid = null;
		
		
		return newBid; 
	}

}
