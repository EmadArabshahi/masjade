package auction;

import axelrod.messages.MoveReply;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class WaitForBiddingToStartBehaviour extends SimpleBehaviour {
	private boolean _done = false;
	@Override
	public void action() {
		BidderAgent agent = (BidderAgent) myAgent;
		ACLMessage msg = myAgent.receive(MessageTemplate.MatchOntology("starting-price"));
		
		if (msg != null)
		{
			agent.setCurrentPrice(Integer.parseInt(msg.getContent()));
			agent.setAuctioneerAID(msg.getSender());
			_done = true;
		}
		else
		{
			block();
		}
	}

	@Override
	public boolean done() {
		return _done;
	}
}