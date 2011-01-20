package auction;

public class CheapBidderAgent extends BidderAgent {
	@Override
	protected void addBehaviours()
	{
		addBehaviour(new CheapBidBehaviour());
	}
}
