package auction;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Timer;

import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class DoEnglishAuctionBehaviour extends DoAuctionBehaviour {	
	public DoEnglishAuctionBehaviour(Item item)
	{
		_item = item;
	}
	
	@Override
	protected void initializeTimer()
	{
		Action endBidding = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_currentPhase = CLOSING_PHASE;
			}
		};
		
		_biddingTimer = new Timer(1000, endBidding);
	}
	
	@Override
	protected void doStartingPhase()
	{
		Output.AgentMessage(_agent, "Starting auction");
		Output.AgentMessage(_agent, String.format("Starting auction for item: %s/%s Setting starting price at: %s", _item.getType(), _item.getName(), _item.getPrice()));
		_agent.addLogEntry(String.format("Started english auction for item %s (%s) with a starting price of %s", _item.getName(), _item.getType(), _item.getPrice())); 
		
		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		msg.setOntology("starting-price-english");
		msg.setContent(_item.getPrice() + "");
		
		_agent.sendMessageToBidders(msg);
		Output.AgentMessage(_agent, "Starting price is sent");
		Output.AgentMessage(_agent, "Waiting for bids");
		
		_currentPhase = BIDDING_PHASE;
	}
	
	@Override
	protected void doBiddingPhase()
	{
		ACLMessage msg = _agent.receive(MessageTemplate.MatchOntology("bid"));
		
		if (msg != null)
		{
			_biddingTimer.restart();
			AID bidder = msg.getSender();
			int newBid = Integer.parseInt(msg.getContent());
			
			Output.AgentMessage(_agent, String.format("Bid received from %s: %s", bidder.getLocalName(), newBid));
			_agent.addLogEntry(String.format("Bid received from %s: %s", bidder.getLocalName(), newBid));
			
			if (newBid > _item.getHighestBid())
			{
				_item.setHighestBid(newBid);
				_item.setHighestBidder(bidder);
				Output.AgentMessage(_agent, String.format("New highest price set: %s, done by %s", newBid, bidder.getLocalName()));
				_agent.addLogEntry(String.format("New highest bid (%s) accepted from %s", newBid, bidder.getLocalName())); 
				
				ACLMessage msgNewBid = new ACLMessage(ACLMessage.INFORM);
				msgNewBid.setOntology("new-highest-bid");
				msgNewBid.setContent(String.format("%s|%s", newBid, bidder.getName()));
				
				ArrayList<AID> bidders = _agent.getBidderAIDs();
				
				Iterator<AID> i = bidders.iterator();
				
				while (i.hasNext())
				{
					AID aid = i.next();
					msgNewBid.addReceiver(aid);
				}
				
				_agent.send(msgNewBid);
			}
		}
		else
		{
			block(1000);
		}
	}
	
	@Override
	protected void doClosingPhase()
	{
		Output.AgentMessage(_agent, String.format("Bidding Closed! Auction won by %s with a bid of %s", _item.getHighestBidder().getLocalName(), _item.getHighestBid()));
		_agent.addLogEntry(String.format("Auction was won by %s for the price of %s", _item.getHighestBidder().getLocalName(), _item.getHighestBid()));
		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		msg.addReceiver(_item.getHighestBidder());
		msg.setOntology("auction-won");
		msg.setContent(String.format("%s|%s|%s", _item.getName(), _item.getType(), _item.getHighestBid()));
		
		_agent.send(msg);
		
		_done = true;
	}
}
