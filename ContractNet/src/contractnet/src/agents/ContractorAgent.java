package contractnet.src.agents;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Timer;

import contractnet.src.Component;
import contractnet.src.ContractorWindow;
import contractnet.src.Output;
import contractnet.src.SubTask;
import contractnet.src.SubTaskBid;
import contractnet.src.Task;
import contractnet.src.behaviours.EvaluateSubTaskBidsBehaviour;
import contractnet.src.behaviours.ProposeBidBehaviour;
import contractnet.src.behaviours.ReceiveBidResultBehaviour;
import contractnet.src.behaviours.ReceiveCFPBehaviour;
import contractnet.src.behaviours.ReceiveSubBidBehaviour;


public class ContractorAgent extends Agent {

	private static final long serialVersionUID = -2896092398183390841L;
	
	private ArrayList<Component> components;
	private ContractorWindow contractorWindow;
	private ArrayList<SubTaskBid> subBids;
	private boolean deadlinePassed;
	private Timer deadlineTimer;
	private final int deadlineTime = 2000;
	private Map<AID, String> conversationIDs;
	private boolean readyToPropose;
	private AID managerAID;
	private SubTask subTask;
	

	private Task receivedTask;

	@Override
	protected void setup() {
		
		contractorWindow = new ContractorWindow(this);
		components = new ArrayList<Component>();
		subBids = new ArrayList<SubTaskBid>();
		conversationIDs = new HashMap<AID, String>();
		deadlinePassed = false;
		setReadyToPropose(false);
		setManagerAID(null);
		subTask = null;
		setReceivedTask(null);

		registerService();
		addBehaviours();
		
		contractorWindow.setVisible(true);

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
		addBehaviour( new ReceiveCFPBehaviour());
		addBehaviour( new ReceiveSubBidBehaviour());
		addBehaviour( new EvaluateSubTaskBidsBehaviour());
		addBehaviour( new ProposeBidBehaviour());
		addBehaviour( new ReceiveBidResultBehaviour());
	}

	public ArrayList<Component> getComponents() {
		return components;
	}

	public void setComponents(ArrayList<Component> components) {
		this.components = components;
	}

	public ContractorWindow getContractorWindow() {
		return contractorWindow;
	}

	public void initializeDeadlineTimer()
	{
		Action stopReceive = new AbstractAction() {
			
			private static final long serialVersionUID = 8522353902923967614L;

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				deadlinePassed = true;
				deadlineTimer.stop();
			}
		};
		
		deadlineTimer = new Timer( deadlineTime, stopReceive);
		deadlineTimer.start();
	}

	public boolean isDeadlinePassed() {
		return deadlinePassed;
	}

	public ArrayList<SubTaskBid> getSubBids() {
		return subBids;
	}

	public SubTask getSubTask() {
		return subTask;
	}
	
	public boolean hasComponent( String cType)
	{
		for ( Component c: components)
		{
			if ( c.getType().equals( cType))
				return true;
		}
		return false;
	}

	public void setReceivedTask(Task receivedTask) {
		this.receivedTask = receivedTask;
	}

	public Task getReceivedTask() {
		return receivedTask;
	}
	
	public void setSubTask(SubTask subTask) {
		this.subTask = subTask;
	}
	

	public ArrayList<AID> getContractorAIDs() { // except himself
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
				if ( host.getName() != getAID())
					AIDs.add(host.getName());
			}
		}
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	    
	    return AIDs;
	}

	public int getDeadlineTime() {
		return deadlineTime;
	}
	
	public Map<AID, String> getConversationIDs() {
		return conversationIDs;
	}

	
	public void setReadyToPropose(boolean readyToPropose) {
		this.readyToPropose = readyToPropose;
	}

	public boolean isReadyToPropose() {
		return readyToPropose;
	}

	public void setManagerAID(AID managerAID) {
		this.managerAID = managerAID;
	}

	public AID getManagerAID() {
		return managerAID;
	}
	
	
	public void addInfo(String string)
	{
		contractorWindow.infoTextArea.append(string);
	}
	
}
