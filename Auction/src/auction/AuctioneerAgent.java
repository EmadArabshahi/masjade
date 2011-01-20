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
	private ArrayList<Item> _englishItems;
	private ArrayList<MultipleItem> _dutchItems;
	private AuctioneerWindow _window;

	@Override
	protected void setup()
	{
		_window = new AuctioneerWindow();
		_window.setSize(600, 700);
		
		registerService();
		addEnglishItems();
		addDutchItems();
		addBehaviours();
		
		_window.setVisible(true);
	}
	
	public void addItem(Item item)
	{
		addItem(item.getName(), item.getType(), item.getPrice(), 1);
	}
	
	public void addItem(MultipleItem item)
	{
		addItem(item.getName(), item.getType(), item.getPrice(), item.getAmount());
	}
	
	public void addItem(String itemName, String type, int unitPrice, int amount)
	{
		_window.addItem(itemName, type, unitPrice, amount);
	}
	
	public void addLogEntry(String entry)
	{
		_window.addLogEntry(entry);
	}
	
	public void addEnglishItems()
	{
		_englishItems = new ArrayList<Item>();
		_englishItems.add(new Item("Fellowship of the ring", "book", 50));
		_englishItems.add(new Item("Two Towers", "book", 70));
	}
	
	public void addDutchItems()
	{
		_dutchItems = new ArrayList<MultipleItem>();
		_dutchItems.add(new MultipleItem("tulip", "flower", 5, 50));
		_dutchItems.add(new MultipleItem("rose", "flower", 10, 50));
	}
	
	public void addBehaviours()
	{
		SequentialBehaviour behaviours = new SequentialBehaviour();
		
		behaviours.addSubBehaviour(new WaitForBiddersBehaviour(this, 1000));
		
		Iterator<Item> i = _englishItems.iterator();
		
		while (i.hasNext())
		{
			Item item = i.next();
			behaviours.addSubBehaviour(new DoEnglishAuctionBehaviour(item));
			addItem(item);
		}
		
		Iterator<MultipleItem> di = _dutchItems.iterator();
		
		while (di.hasNext())
		{
			MultipleItem mi = di.next();
			behaviours.addSubBehaviour(new DoDutchAuctionBehaviour(mi));
			addItem(mi);
		}
		
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

	public void sendMessageToBidders(ACLMessage msg) {		
		ArrayList<AID> bidderAgents = getBidderAIDs();
		Iterator<AID> i = bidderAgents.iterator();
		while (i.hasNext())
		{
			AID bidder = i.next();
			msg.addReceiver(bidder);
		}
		send(msg);
	}

	public ArrayList<AID> getBidderAIDs() {
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
