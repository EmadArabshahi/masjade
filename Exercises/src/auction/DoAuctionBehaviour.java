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

public class DoAuctionBehaviour extends SimpleBehaviour {
	private static final int STARTING_PHASE = 0;
	private static final int BIDDING_PHASE = 1;
	private static final int CLOSING_PHASE = 2;
	
	private boolean _done = false;
	private int _currentPhase = 0;
	private Timer _biddingTimer;
	private AuctioneerAgent _agent;
	private Item _item;
	
	public DoAuctionBehaviour(Item item)
	{
		initializeTimer();
		_item = item;
	}

	@Override
	public void action() {
		_agent = (AuctioneerAgent) myAgent;
		
		switch (_currentPhase)
		{
			case STARTING_PHASE:
			{
				doStartingPhase();
				break;
			}
			case BIDDING_PHASE:
			{
				doBiddingPhase();
				break;
			}
			case CLOSING_PHASE:
			{
				doClosingPhase();
				break;
			}
		}
	}
	
	@Override
	public boolean done() {
		return _done;
	}
	
	private void initializeTimer()
	{
		Action endBidding = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_currentPhase = CLOSING_PHASE;
			}
		};
		
		_biddingTimer = new Timer(1000, endBidding);
	}
	
	private void doStartingPhase()
	{
		Output.AgentMessage(_agent, "Starting auction");
		Output.AgentMessage(_agent, String.format("Starting auction for item: %s/%s Setting starting price at: %s", _item.getType(), _item.getName(), _item.getStartingPrice()));
		_agent.sendStartingPrice(_item.getStartingPrice());
		Output.AgentMessage(_agent, "Starting price is sent");
		Output.AgentMessage(_agent, "Waiting for bids");
		
		_currentPhase = BIDDING_PHASE;
	}
	
	private void doBiddingPhase()
	{
		_biddingTimer.start();
		ACLMessage msg = _agent.receive(MessageTemplate.MatchOntology("bid"));
		
		if (msg != null)
		{
			_biddingTimer.restart();
			AID bidder = msg.getSender();
			int newBid = Integer.parseInt(msg.getContent());
			
			Output.AgentMessage(_agent, String.format("Bid received from %s: %s", bidder.getLocalName(), newBid));
			
			if (newBid > _item.getHighestBid())
			{
				_item.setHighestBid(newBid);
				_item.setHighestBidder(bidder);
				Output.AgentMessage(_agent, String.format("New highest price set: %s, done by %s", newBid, bidder.getLocalName()));
				
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
	
	private void doClosingPhase()
	{
		_biddingTimer.stop();
		Output.AgentMessage(_agent, String.format("Bidding Closed! Auction won by %s with a bid of %s", _item.getHighestBidder().getLocalName(), _item.getHighestBid()));
		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		msg.addReceiver(_item.getHighestBidder());
		msg.setOntology("auction-won");
		msg.setContent(_item.getHighestBid() + "");
		
		_agent.send(msg);
		
		_done = true;
	}
}
