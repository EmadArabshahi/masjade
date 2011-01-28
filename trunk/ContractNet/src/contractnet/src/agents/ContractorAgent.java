package contractnet.src.agents;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

import java.util.ArrayList;

import contractnet.src.Component;
import contractnet.src.Output;
import contractnet.src.Component.ComponentType;
import contractnet.src.behaviours.ContractBidBehaviour;


public class ContractorAgent extends Agent {

	private static final long serialVersionUID = -2896092398183390841L;
	
	private ArrayList<Component> components;

	@Override
	protected void setup() {
		
		components = new ArrayList<Component>();
		components.add( new Component( ComponentType.CPU, "hp", 2000, 1000));
		components.add( new Component( ComponentType.GPU, "hp", 2000, 100));
		components.add( new Component( ComponentType.Motherboard, "hp", 2000, 100));
		
		registerService();
		addBehaviours();

	}
	
	private void registerService()
	{
		Output.AgentMessage(this, "Registering with DF");
	    DFAgentDescription dfd = new DFAgentDescription(); 
	    dfd.setName(getAID()); 
	    
	    ServiceDescription sd = new ServiceDescription(); 
	    sd.setType("contractor"); 
	    sd.setName("contractor"); 
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
	
	public void addBehaviours()
	{
		addBehaviour( new ContractBidBehaviour());
	}
}
