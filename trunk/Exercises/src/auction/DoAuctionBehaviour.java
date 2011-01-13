package auction;

import jade.core.behaviours.SimpleBehaviour;

public class DoAuctionBehaviour extends SimpleBehaviour {
	private static final int STARTING_PHASE = 0;
	private static final int BIDDING_PHASE = 1;
	
	private boolean _done = false;
	private int _currentPhase = 0;
	
	@Override
	public void action() {
		switch (_currentPhase)
		{
			case STARTING_PHASE:
			{
				AuctioneerAgent agent = (AuctioneerAgent) myAgent;
				Output.AgentMessage(agent, "Starting auction");
				Output.AgentMessage(agent, "Setting starting price at: " + agent.getStartingPrice());
				agent.sendStartingPrice();
				Output.AgentMessage(agent, "Starting price is sent");
				Output.AgentMessage(agent, "Waiting for bids");
				
				_currentPhase = BIDDING_PHASE;
				break;
			}
		}
	}

	@Override
	public boolean done() {
		return _done;
	}

}
