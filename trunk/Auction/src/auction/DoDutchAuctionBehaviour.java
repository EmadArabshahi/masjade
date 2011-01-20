package auction;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Timer;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class DoDutchAuctionBehaviour extends DoAuctionBehaviour {
	private boolean _lowerPrice;
	
	public DoDutchAuctionBehaviour(MultipleItem item)
	{
		_lowerPrice = false;
		_item = item;
	}

	@Override
	public boolean done() {
		return _done;
	}
	
	@Override
	protected void initializeTimer()
	{
		Action endBidding = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_lowerPrice = true;
			}
		};
		
		_biddingTimer = new Timer(1000, endBidding);
	}

	@Override
	public void doStartingPhase() {
		Output.AgentMessage(_agent, "Starting Multi Unit Dutch Auction");
		Output.AgentMessage(_agent, String.format("Starting auction for item: %s/%s/%s Setting starting price at %s per unit", _item.getType(), _item.getName(), getMultipleItem().getAmount(), _item.getPrice()));
		_agent.addLogEntry(String.format("Started dutch auction for %s of item %s (%s) with a starting price of %s", getMultipleItem().getAmount(), _item.getName(), _item.getType(), _item.getPrice()));

		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		msg.setOntology("starting-price-dutch");
		msg.setContent(_item.getPrice() + "|" + getMultipleItem().getAmount());
		
		_agent.sendMessageToBidders(msg);
		
		Output.AgentMessage(_agent, "Starting price is sent");
		Output.AgentMessage(_agent, "Waiting for bids");
		
		_currentPhase = BIDDING_PHASE;
	}

	@Override
	public void doBiddingPhase() {
		ACLMessage msg = _agent.receive(MessageTemplate.MatchOntology("bid"));
		
		if (msg != null)
		{
			_biddingTimer.restart();
			
			int bid = Integer.parseInt(msg.getContent());
			Output.AgentMessage(_agent, String.format("Bid received from %s for %s items", msg.getSender().getLocalName(), bid));
			_agent.addLogEntry(String.format("Bid received from %s for %s items at the price of %s", msg.getSender().getLocalName(), bid, _item.getPrice()));
			MultipleItem mi = getMultipleItem();
			
			int newAmount = mi.getAmount() - bid;
			int confirmedAmount = bid;
			if (newAmount < 0)
			{
				newAmount = 0;
				confirmedAmount = mi.getAmount();
			}
			
			mi.setAmount(newAmount);
			
			ACLMessage confirmMsg = new ACLMessage(ACLMessage.INFORM);
			confirmMsg.addReceiver(msg.getSender());
			confirmMsg.setOntology("confirm-bid-dutch");
			confirmMsg.setContent(String.format("%s|%s|%s|%s", _item.getName(), _item.getType(), _item.getPrice(), confirmedAmount));
			_agent.addLogEntry(String.format("Sold %s of %s (%s) to %s for %s per unit", confirmedAmount, _item.getName(), _item.getType(), msg.getSender().getLocalName(), _item.getPrice() ));
			_agent.send(confirmMsg);
			
			if (mi.getAmount() <= 0)
			{
				_currentPhase = CLOSING_PHASE;
			}			
		}
		else if (_lowerPrice)
		{
			int newPrice = _item.getPrice() - 1;
			if (newPrice <= 0)
			{
				_currentPhase = CLOSING_PHASE;
			}
			else
			{
				_item.setPrice(newPrice);
				Output.AgentMessage(_agent, String.format("New price set: %s", newPrice));
				_agent.addLogEntry(String.format("Lowering the price for %s (%s) to %s per unit", _item.getName(), _item.getType(), newPrice));
				ACLMessage priceMsg = new ACLMessage(ACLMessage.INFORM);
				priceMsg.setOntology("price-dutch");
				priceMsg.setContent(_item.getPrice() + "|" + getMultipleItem().getAmount());
				_agent.sendMessageToBidders(priceMsg);
				_lowerPrice = false;
				_biddingTimer.restart();
			}
		}
		else
		{
			block(1000);
		}
	}

	@Override
	public void doClosingPhase() {
		Output.AgentMessage(_agent, String.format("Auction closed! %s items remaining", getMultipleItem().getAmount()));
		_agent.addLogEntry(String.format("Auction closed. %s units of %s (%s) remain unsold", getMultipleItem().getAmount(), _item.getName(), _item.getType()));
		_done = true;
	}
	
	private MultipleItem getMultipleItem()
	{
		MultipleItem item = (MultipleItem) _item;
		return item;
	}
}
