package auction;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.FSMBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class BidderAgent extends Agent {
	private int _currentPrice;
	private AID _auctioneerAID;
	@Override
	protected void setup()
	{
		_currentPrice = 0;
		registerService();
		addBehaviours();
	}
	
	private void addBehaviours()
	{
		addBehaviour(new BidBehaviour());
	}
	
	private void registerService()
	{
		Output.AgentMessage(this, "Registering with DF");
	    DFAgentDescription dfd = new DFAgentDescription(); 
	    dfd.setName(getAID()); 
	    
	    ServiceDescription sd = new ServiceDescription(); 
	    sd.setType("bidder"); 
	    sd.setName("bidder"); 
	    dfd.addServices(sd); 
	    
	    try 
	    { 
	    	DFService.register(this, dfd); 
	    }
	    catch (FIPAException fe) 
	    { 
	    	fe.printStackTrace(); 
	    }		
	}

	public void setHighestBid(int price) 
	{
		_currentPrice = price;
	}
	
	public int getHighestBid()
	{
		return _currentPrice;
	}

	public void setAuctioneerAID(AID aid) {
		_auctioneerAID = aid;
	}
	
	public AID getAuctioneerAID()
	{
		return _auctioneerAID;
	}

	public int getBiddingLimit() {
		return 200;
	}
}
