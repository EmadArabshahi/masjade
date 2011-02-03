package contractnet.src.behaviours;

import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import contractnet.src.Output;
import contractnet.src.SubTaskBid;
import contractnet.src.agents.ContractorAgent;

public class ReceiveSubBidBehaviour extends SimpleBehaviour {

	private static final long serialVersionUID = -1762857042540098616L;
	private ContractorAgent agent;
	
	@Override
	public void action() {
		// TODO Auto-generated method stub
		agent = (ContractorAgent) myAgent;
		
		ACLMessage msg = agent.receive( MessageTemplate.MatchPerformative( ACLMessage.PROPOSE));
		
		if ( msg != null)
		{
			Output.AgentMessage(agent, "Received a tender from agent " + msg.getSender().getLocalName());
			agent.addInfo(String.format("Received a tender from %s\n", msg.getSender().getLocalName()));
			if ( !agent.isDeadlinePassed())
			{
				SubTaskBid bid = null;
				try {
					bid = (SubTaskBid) msg.getContentObject();
				} catch (UnreadableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Output.AgentMessage( agent, "Cannot read bid from agent" + msg.getSender().getLocalName());
				}
				if ( bid != null)
				{
					agent.getSubBids().add(bid);
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
				agent.addInfo(String.format("Deadline has passed. Sent reject proposal to %s\n", msg.getSender().getLocalName()));
				agent.send( rejectMsg);
				
				//Output.AgentMessage(agent, "Deadline has passed. Sent reject proposal to agent " + msg.getSender());
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
