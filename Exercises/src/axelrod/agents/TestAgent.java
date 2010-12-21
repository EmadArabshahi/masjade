package axelrod.agents;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class TestAgent extends Agent
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5135933990486995587L;
	
	
	public void setup()
	{
		//The agent description
	    DFAgentDescription dfd = new DFAgentDescription(); 
	    dfd.setName(getAID()); 
	    
	    //The bomb-positions service.
	    ServiceDescription sd = new ServiceDescription(); 
	    sd.setType("contestant"); 
	    sd.setName("contestant"); 
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
	
	public void takedown()
	{
		try 
		{ 
			DFService.deregister(this); 
		}  
		catch (FIPAException fe) 
		{ 
			fe.printStackTrace(); 
		}
	}
}
