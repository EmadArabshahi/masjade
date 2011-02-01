package contractnet.src.behaviours;

import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import contractnet.src.Bid;
import contractnet.src.Output;
import contractnet.src.agents.ManagerAgent;

public class ReceiveBidBehaviour extends SimpleBehaviour {

	private static final long serialVersionUID = -7352639800262926141L;
	private ManagerAgent agent;
	
	@Override
	public void action() {
		// TODO Auto-generated method stub
	agent = (ManagerAgent) myAgent;
		
		ACLMessage msg = agent.receive( MessageTemplate.MatchPerformative( ACLMessage.PROPOSE));
		
		if ( msg != null)
		{
			if ( !agent.isDeadlinePassed())
			{
				Bid bid = null;
				try {
					bid = (Bid) msg.getContentObject();
				} catch (UnreadableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Output.AgentMessage( agent, "Cannot read bid from agent" + msg.getSender());
				}
				if ( bid != null)
				{
					Output.AgentMessage( agent, "Bid recieved from agent " + msg.getSender());
					agent.getBids().add(bid);
				}
			}
			else
			{
				ACLMessage rejectMsg = new ACLMessage( ACLMessage.REJECT_PROPOSAL);
				rejectMsg.setConversationId( msg.getConversationId());
				rejectMsg.setProtocol( msg.getProtocol());
				rejectMsg.setOntology( "bid-reject");
				rejectMsg.setContent( "Deadline has passed.");
				rejectMsg.addReceiver( msg.getSender());
				agent.send( rejectMsg);
				
				Output.AgentMessage(agent, "Deadline has passed. Sent reject proposal to agent " + msg.getSender());
			}
			
		}
		else
			block(100);

	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return false;
	}

}