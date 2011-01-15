package auction;

import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class BidBehaviour extends SimpleBehaviour {
	private boolean _done = false;
	
	@Override
	public void action() {
		BidderAgent agent = (BidderAgent) myAgent;
		ACLMessage msg = myAgent.receive(MessageTemplate.or(MessageTemplate.MatchOntology("starting-price"), MessageTemplate.MatchOntology("new-highest-bid")));
		
		if (msg != null)
		{
			boolean ownsHighestBid = false;
			if (msg.getOntology() == "starting-price")
			{
				agent.setAuctioneerAID(msg.getSender());
				agent.setHighestBid(Integer.parseInt(msg.getContent()));
			}
			else if (msg.getOntology() == "new-highest-bid")
			{				
				String contentString = msg.getContent();
				String[] splitContentStrings = contentString.split("\\|");
				int newHighestBid = Integer.parseInt(splitContentStrings[0]);
				String highestBidder = splitContentStrings[1];
				agent.setHighestBid(newHighestBid);
				
				if (myAgent.getName().equals(highestBidder))
				{
					ownsHighestBid = true;
				}
			}
			
			BidderAgent bidder = (BidderAgent) myAgent;
			int bid = bidder.getHighestBid() + 1;
			
			if (!ownsHighestBid && bidder.getBiddingLimit() >= bid)
			{
				ACLMessage msgBid = new ACLMessage(ACLMessage.INFORM);
				msgBid.setOntology("bid");
				msgBid.setContent(bid + "");
				msgBid.addReceiver(bidder.getAuctioneerAID());
				bidder.send(msgBid);
				Output.AgentMessage(myAgent, "Bid sent: " + bid);
			}
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
