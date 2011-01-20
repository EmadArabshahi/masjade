package auction;

public class CheapBidBehaviour extends BidBehaviour {
	@Override
	protected boolean makeADutchBid(int currentPricePerUnit, int numberOfUnits, int amountToBuy, BidderAgent agent)
	{
		// only bid, when you could afford the whole batch for this price.
		return currentPricePerUnit * numberOfUnits <= agent.getRemainingBudget() && amountToBuy > 0;
	}
}
