package auction;

import jade.core.behaviours.SimpleBehaviour;

public abstract class DoAuctionBehaviour extends SimpleBehaviour {
	protected static final int STARTING_PHASE = 0;
	protected static final int BIDDING_PHASE = 1;
	protected static final int CLOSING_PHASE = 2;
	
	protected int _currentPhase = 0;

	protected boolean _done = false;
	protected Item _item;
	
	protected AuctioneerAgent _agent;
	
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
	
	protected abstract void doStartingPhase();
	protected abstract void doBiddingPhase();
	protected abstract void doClosingPhase();
}
