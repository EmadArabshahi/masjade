package auction;

import jade.lang.acl.ACLMessage;

public class DoDutchAuctionBehaviour extends DoAuctionBehaviour {
	public DoDutchAuctionBehaviour(MultipleItem item)
	{
		_item = item;
	}

	@Override
	public boolean done() {
		return _done;
	}

	@Override
	public void doStartingPhase() {
		Output.AgentMessage(_agent, "Starting Multi Unit Dutch Auction");
		Output.AgentMessage(_agent, String.format("Starting auction for item: %s/%s/%s Setting starting price at %s per unit", _item.getType(), _item.getName(), getMultipleItem().getAmount(), _item.getStartingPrice()));

		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		msg.setOntology("starting-price-dutch");
		msg.setContent(_item.getStartingPrice() + "|" + getMultipleItem().getAmount());
		
		_agent.sendStartingPrice(msg);
		
		Output.AgentMessage(_agent, "Starting price is sent");
		Output.AgentMessage(_agent, "Waiting for bids");
		
		_currentPhase = BIDDING_PHASE;
	}

	@Override
	public void doBiddingPhase() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doClosingPhase() {
		// TODO Auto-generated method stub
	}
	
	private MultipleItem getMultipleItem()
	{
		MultipleItem item = (MultipleItem) _item;
		return item;
	}
}
