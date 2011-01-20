package auction;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Timer;

import jade.core.behaviours.SimpleBehaviour;

public abstract class DoAuctionBehaviour extends SimpleBehaviour {
	protected static final int STARTING_PHASE = 0;
	protected static final int BIDDING_PHASE = 1;
	protected static final int CLOSING_PHASE = 2;
	
	protected int _currentPhase = 0;

	protected boolean _done = false;
	protected Item _item;
	
	protected AuctioneerAgent _agent;
	
	protected Timer _biddingTimer;
	
	public DoAuctionBehaviour()
	{
		initializeTimer();
	}
	
	protected abstract void initializeTimer();
	
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
				_biddingTimer.start();
				doBiddingPhase();
				break;
			}
			case CLOSING_PHASE:
			{
				_biddingTimer.stop();
				doClosingPhase();
				// clear messages
				while (_agent.receive() != null) {}
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
