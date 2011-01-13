package auction;

import java.util.ArrayList;
import java.util.Iterator;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.SequentialBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

public class AuctioneerAgent extends Agent {
	private static final long serialVersionUID = 4944135154767164963L;

	@Override
	protected void setup()
	{
		registerService();
		addBehaviours();
	}
	
	public void addBehaviours()
	{
		SequentialBehaviour behaviours = new SequentialBehaviour();
		
		behaviours.addSubBehaviour(new WaitForBiddersBehaviour(this, 1000));
		behaviours.addSubBehaviour(new DoAuctionBehaviour());
		addBehaviour(behaviours);
	}
	
	private void registerService()
	{
		Output.AgentMessage(this, "Registering with DF");
	    DFAgentDescription dfd = new DFAgentDescription(); 
	    dfd.setName(getAID()); 
	    
	    ServiceDescription sd = new ServiceDescription(); 
	    sd.setType("auctioneer"); 
	    sd.setName("auctioneer"); 
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

	public int getStartingPrice() {
		return 100;
	}

	public void sendStartingPrice() {
		ArrayList<AID> bidderAgents = getBidderAIDs();
		
		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		msg.setOntology("starting-price");
		msg.setContent(getStartingPrice() + "");
		
		Iterator<AID> i = bidderAgents.iterator();
		while (i.hasNext())
		{
			AID bidder = i.next();
			msg.addReceiver(bidder);
		}
	}

	private ArrayList<AID> getBidderAIDs() {
		DFAgentDescription dfd = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription(); 
	    sd.setType("bidder"); 
	    sd.setName("bidder"); 
	    dfd.addServices(sd); 
		
	    ArrayList<AID> AIDs = new ArrayList<AID>();
	    try
		{
			DFAgentDescription[] hosts = DFService.search(this, dfd);
			for (DFAgentDescription host : hosts)
			{
				AIDs.add(host.getName());
			}
		}
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	    
	    return AIDs;
	}
}
