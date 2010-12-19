package axelrod.behaviours;

import axelrod.ContestantAgent;
import axelrod.Output;
import axelrod.messages.MoveReply;
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
		
		
		MoveReply reply = new MoveReply(agent.getCurrentMoveRequest(), agent.getMove());
		
		/*
		ACLMessage request = agent.getCurrentMoveRequest().getMessage();
		ACLMessage reply = request.createReply();
		agent.clearCurrentMoveRequest();
		
		reply.setContent(Integer.toString(agent.getMove()));
		*/
		agent.send(reply.getMessage());
		Output.AgentMessage(agent, String.format("Move reply sent (%s): %s", reply.getConversationId(), reply.getMessage().getContent()));
	}
	
	@Override
	public int onEnd()
	{
		return SENT_MOVE_REPLY;
	}
}
