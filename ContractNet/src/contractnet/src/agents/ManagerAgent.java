package contractnet.src.agents;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Timer;

import contractnet.src.Bid;
import contractnet.src.ManagerWindow;
import contractnet.src.Output;
import contractnet.src.Task;
import contractnet.src.behaviours.AnnounceTaskBehaviour;
import contractnet.src.behaviours.EvaluateBidsBehaviour;
import contractnet.src.behaviours.ReceiveBidBehaviour;


public class ManagerAgent extends Agent {

	private static final long serialVersionUID = -5987409020127752127L;
	
	private Task task;
	private ArrayList<Bid> bids;
	
	private Timer deadlineTimer;
	private boolean deadlinePassed;	
	private final int deadlineTime = 3000;
	
	private boolean announceReady;
	private Map<AID, String> conversationIDs;
	private ManagerWindow managerWindow;

	@Override
	protected void setup() {
		
		setAnnounceReady(false);
		deadlinePassed = false;
		deadlineTimer = null;
		conversationIDs = new HashMap<AID, String>();
		setTask(new Task());
		bids = new ArrayList<Bid>();
		managerWindow = new ManagerWindow( this);
		
		
		registerService();
		addBehaviours();
		managerWindow.setVisible(true);
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
		int counter = 1;
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
		//FSMBehaviour behaviours = new FSMBehaviour();
		//behaviours.registerFirstState(new WaitForStartupBehaviour(), "startUp");
		//behaviours.registerLastState(new AnnounceTaskBehaviour(), "announceTask");
		//behaviours.registerState(new ReceiveBidBehaviour(), "receiveBid");
		//behaviours.registerState(new EvaluateBidsBehaviour(), "evaluateBids");
		
		//behaviours.registerDefaultTransition("startUp", "announceTask");
		//behaviours.registerDefaultTransition("announceTask", "receiveBid");
		//behaviours.registerDefaultTransition("receiveBid", "evaluateBids");
		
		
		//addBehaviour(behaviours);
		//addBehaviour( new WaitForStartupBehaviour());
		addBehaviour( new AnnounceTaskBehaviour());
		addBehaviour( new ReceiveBidBehaviour());
		addBehaviour( new EvaluateBidsBehaviour());
		
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
	
	public void initializeDeadlineTimer()
	{
		Action stopReceive = new AbstractAction() {
			
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

	public int getDeadlineTime() {
		return deadlineTime;
	}

	public void setConversationIDs(Map<AID, String> conversationIDs) {
		this.conversationIDs = conversationIDs;
	}

	public Map<AID, String> getConversationIDs() {
		return conversationIDs;
	}

	public ManagerWindow getManagerWindow() {
		return managerWindow;
	}

	public void setAnnounceReady(boolean announceReady) {
		this.announceReady = announceReady;
	}

	public boolean isAnnounceReady() {
		return announceReady;
	}
	
	public void setResponse(String agentName, String response)
	{
		managerWindow.responses.put(agentName, response);
	}
	
	public void updateInfo()
	{
		managerWindow.updateInfoTextArea();
	}
	
	public void setTaskStatus(String taskStatus)
	{
		managerWindow.taskStatus = taskStatus;
	}
}


	


