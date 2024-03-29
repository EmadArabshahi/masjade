package contractnet.src.behaviours;

import contractnet.src.Output;
import contractnet.src.agents.ContractorAgent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ReceiveBidResultBehaviour extends SimpleBehaviour {

	private static final long serialVersionUID = -7156157882448748388L;
	private ContractorAgent agent;
	
	@Override
	public void action() {
		// TODO Auto-generated method stub
		
		agent = ( ContractorAgent) myAgent;
		
		ACLMessage msg = agent.receive( MessageTemplate.or( MessageTemplate.MatchPerformative( 
				ACLMessage.ACCEPT_PROPOSAL), MessageTemplate.MatchPerformative(ACLMessage.REJECT_PROPOSAL)));
		
		if ( msg != null)
		{
			if ( msg.getPerformative() == ACLMessage.ACCEPT_PROPOSAL)
			{
				Output.AgentMessage(agent, "Received accept proposal message from " + msg.getSender().getLocalName());
				agent.addInfo(String.format("Received accept proposal message from %s\n", msg.getSender().getLocalName()));
				
				ACLMessage informDoneMsg = new ACLMessage( ACLMessage.INFORM);
				informDoneMsg.setProtocol( msg.getProtocol());
				informDoneMsg.setConversationId( msg.getConversationId());
				informDoneMsg.setOntology( "inform-done");
				informDoneMsg.setContent( "Task done.");
				informDoneMsg.addReceiver( msg.getSender());
				Output.AgentMessage(agent, "Sent inform-done message to agent " + msg.getSender().getLocalName());
				agent.addInfo(String.format("Sent inform-done message to %s\n", msg.getSender().getLocalName()));
				agent.send( informDoneMsg);
				
				
			}
			else if ( msg.getPerformative() == ACLMessage.REJECT_PROPOSAL)
			{
				Output.AgentMessage(agent, "Tender has been rejected by agent " + msg.getSender().getLocalName() + 
						"\n Reason: " + msg.getContent());
				agent.addInfo(String.format("Tender has been rejected by %s\n", msg.getSender().getLocalName()));
				agent.addInfo(String.format("--Reason: %s\n", msg.getContent()));
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
