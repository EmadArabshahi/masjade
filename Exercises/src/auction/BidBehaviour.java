package auction;

import org.omg.stub.java.rmi._Remote_Stub;

import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class BidBehaviour extends SimpleBehaviour {
	private boolean _done = false;
	
	@Override
	public void action() {
		BidderAgent agent = (BidderAgent) myAgent;
		ACLMessage msg = myAgent.receive();
		boolean bidSent = false;
		
		if (msg != null)
		{
			boolean ownsHighestBid = false;
			if (msg.getOntology() == "starting-price-english")
			{
				agent.setAuctioneerAID(msg.getSender());
				agent.setHighestBid(Integer.parseInt(msg.getContent()));
			}
			else if (msg.getOntology() == "starting-price-dutch")
			{
				agent.setAuctioneerAID(msg.getSender());
				doDutchBid(msg);
				bidSent = true;
			}
			else if (msg.getOntology() == "price-dutch")
			{
				doDutchBid(msg);
				bidSent = true;
			}
			else if (msg.getOntology() == "confirm-bid-dutch")
			{
				String[] content = msg.getContent().split("\\|");
				int currentPricePerUnit = Integer.parseInt(content[0]);
				int numberOfUnits = Integer.parseInt(content[1]);
				
				agent.setRemainingBudget(agent.getRemainingBudget() - currentPricePerUnit * numberOfUnits);
				
				Output.AgentMessage(agent, String.format("Bid confirmation received: %s items bought", numberOfUnits));
				bidSent = true;
			}
			else if (msg.getOntology() == "new-highest-bid")
			{				
				String contentString = msg.getContent();
				String[] splitContentStrings = contentString.split("\\|");
				int newHighestBid = Integer.parseInt(splitContentStrings[0]);
				String highestBidder = splitContentStrings[1];
				agent.setHighestBid(newHighestBid);
				
				if (agent.getName().equals(highestBidder))
				{
					ownsHighestBid = true;
				}
			}
			else if (msg.getOntology() == "auction-won")
			{
				agent.setRemainingBudget(agent.getRemainingBudget() - agent.getHighestBid());
				Output.AgentMessage(agent, String.format("Auction won confirmed, bid: %s, budget remaining: %s", agent.getHighestBid(), agent.getRemainingBudget()));
			}
			
			if (!bidSent)
			{
				BidderAgent bidder = (BidderAgent) myAgent;
				int bid = bidder.getHighestBid() + 1;
				
				if (!ownsHighestBid && bidder.getBidLimit() >= bid && bidder.getRemainingBudget() >= bid && msg.getOntology() != "auction-won")
				{
					ACLMessage msgBid = new ACLMessage(ACLMessage.INFORM);
					msgBid.setOntology("bid");
					msgBid.setContent(bid + "");
					msgBid.addReceiver(bidder.getAuctioneerAID());
					bidder.send(msgBid);
					Output.AgentMessage(myAgent, "Bid sent: " + bid);
				}
			}
			
			agent.updateRemainingBudget();
		}
		else
		{
			block();
		}
	}
	
	private void doDutchBid(ACLMessage msg)
	{
		BidderAgent agent = (BidderAgent) myAgent;
		
		String[] content = msg.getContent().split("\\|");
		int currentPricePerUnit = Integer.parseInt(content[0]);
		int numberOfUnits = Integer.parseInt(content[1]);
		
		int amountToBuy = agent.getRemainingBudget() / currentPricePerUnit;
		
		if (amountToBuy > numberOfUnits)
		{
			amountToBuy = numberOfUnits;
		}
		
		if (currentPricePerUnit * amountToBuy <= agent.getRemainingBudget() && amountToBuy > 0)
		{
			ACLMessage msgBid = new ACLMessage(ACLMessage.INFORM);
			msgBid.setOntology("bid");
			msgBid.setContent(amountToBuy + "");
			msgBid.addReceiver(agent.getAuctioneerAID());
			agent.send(msgBid);
			
			Output.AgentMessage(myAgent, "Bid sent: " + amountToBuy);
		}
	}

	@Override
	public boolean done() {
		return _done;
	}
}
