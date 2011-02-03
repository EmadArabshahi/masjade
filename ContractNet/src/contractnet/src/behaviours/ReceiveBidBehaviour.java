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
		agent = (ManagerAgent) myAgent;
		
		MessageTemplate msgTemplate = MessageTemplate.or(MessageTemplate.MatchPerformative(ACLMessage.PROPOSE), MessageTemplate.MatchPerformative(ACLMessage.REFUSE));
		
		ACLMessage msg = agent.receive( msgTemplate );
		
		if ( msg != null)
		{
			if (msg.getPerformative() == ACLMessage.PROPOSE)
			{
				Output.AgentMessage(agent, "Received a tender from agent " + msg.getSender().getLocalName());
				if ( !agent.isDeadlinePassed())
				{
					Bid bid = null;
					try {
						bid = (Bid) msg.getContentObject();
					} catch (UnreadableException e) {
						e.printStackTrace();
						Output.AgentMessage( agent, "Cannot read bid from agent " + msg.getSender().getLocalName());
					}
					if ( bid != null)
					{
						Output.AgentMessage( agent, "Bid received from agent " + msg.getSender().getLocalName());
						agent.setResponse(msg.getSender().getLocalName(), "Bid received");
						agent.updateInfo();
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
					
					agent.setResponse(msg.getSender().getLocalName(), "Missed deadline");
					agent.updateInfo();
					Output.AgentMessage(agent, "Deadline has passed. Sent reject proposal to agent " + msg.getSender().getLocalName());
				}
			}
			else if (msg.getPerformative() == ACLMessage.REFUSE)
			{
				agent.setResponse(msg.getSender().getLocalName(), "Refusal received");
				agent.updateInfo();
				Output.AgentMessage( agent, "Refusal received from agent " + msg.getSender().getLocalName());
			}
		}
		else
			block(100);

	}

	@Override
	public boolean done() {
		return false;
	}

}
