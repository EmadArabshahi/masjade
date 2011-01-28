package contractnet.src.agents;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.SequentialBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;
import java.util.Iterator;

import contractnet.src.Bid;
import contractnet.src.Output;
import contractnet.src.Task;
import contractnet.src.behaviours.AnnounceTaskBehaviour;
import contractnet.src.behaviours.EvaluateBidBehaviour;


public class ManagerAgent extends Agent {

	private static final long serialVersionUID = -5987409020127752127L;
	
	private Task task;
	private ArrayList<Bid> bids;

	@Override
	protected void setup() {
		
		registerService();
		setTask(new Task());
		addBehaviours();		

	}
	
	private void registerService()
	{
		Output.AgentMessage(this, "Registering with DF");
	    DFAgentDescription dfd = new DFAgentDescription(); 
	    dfd.setName(getAID()); 
	    
	    ServiceDescription sd = new ServiceDescription(); 
	    sd.setType("manager"); 
	    sd.setName("manager"); 
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
	
	public void sendMessageToContractors( ACLMessage msg) {		
		ArrayList<AID> contractorAgents = getContractorAIDs();
		Iterator<AID> i = contractorAgents.iterator();
		while (i.hasNext())
		{
			AID contractor = i.next();
			msg.addReceiver( contractor);
		}
		send(msg);
	}

	public ArrayList<AID> getContractorAIDs() {
		DFAgentDescription dfd = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription(); 
	    sd.setType("contractor"); 
	    sd.setName("contractor"); 
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
	
	public void addBehaviours()
	{
		SequentialBehaviour behaviours = new SequentialBehaviour();
		
		behaviours.addSubBehaviour( new AnnounceTaskBehaviour());
		behaviours.addSubBehaviour( new EvaluateBidBehaviour());
		
		addBehaviour(behaviours);
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public Task getTask() {
		return task;
	}

	public void setBids(ArrayList<Bid> bids) {
		this.bids = bids;
	}

	public ArrayList<Bid> getBids() {
		return bids;
	}	
	
}


	


