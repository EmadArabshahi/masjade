package axelrod;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class SendMoveReplyAction extends OneShotBehaviour {
	public static final int SENT_MOVE_REPLY = 1;
	
	public SendMoveReplyAction()
	{
		
	}
	
	@Override
	public void action() {
		ContestantAgent agent = (ContestantAgent) myAgent;
		
		ACLMessage request = agent.getCurrentMoveRequest();
		ACLMessage reply = request.createReply();
		agent.clearCurrentMoveRequest();
		
		reply.setContent(Integer.toString(agent.getMove()));
		
		agent.send(reply);
		Output.AgentMessage(agent, String.format("Move reply sent (%s): %s", reply.getConversationId(), reply.getContent()));
	}
	
	@Override
	public int onEnd()
	{
		return SENT_MOVE_REPLY;
	}
}
