package auction;

import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;

public class BidBehaviour extends SimpleBehaviour {
	private boolean _done = false;
	
	@Override
	public void action() {
		BidderAgent bidder = (BidderAgent) myAgent;
		int bid = bidder.getCurrentPrice() + 1;
		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		msg.setOntology("bid");
		msg.addReceiver(bidder.getAuctioneerAID());
		bidder.send(msg);
		Output.AgentMessage(myAgent, "Bid sent: " + bid);
		_done = true;
	}

	@Override
	public boolean done() {
		return _done;
	}

}
