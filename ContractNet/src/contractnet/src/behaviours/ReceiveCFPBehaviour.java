package contractnet.src.behaviours;

import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import contractnet.src.Component;
import contractnet.src.Computer;
import contractnet.src.Output;
import contractnet.src.SubTask;
import contractnet.src.SubTaskBid;
import contractnet.src.Task;
import contractnet.src.agents.ContractorAgent;

public class ReceiveCFPBehaviour extends SimpleBehaviour {

	private static final long serialVersionUID = -5266395297517167327L;
	private ContractorAgent agent;

	@Override
	public void action() {
		agent = (ContractorAgent) myAgent;
		
		ACLMessage msg = agent.receive( MessageTemplate.MatchPerformative( ACLMessage.CFP));
		
		if ( msg != null)
		{
			if ( msg.getOntology().equals( "task"))
			{
				Task task = null;
				try {
					task = (Task) msg.getContentObject();
				} catch (UnreadableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if ( task != null)
				{
					Output.AgentMessage( agent, "Task recieved from agent" + msg.getSender().getLocalName());
					agent.addInfo(String.format("Task recieved from %s\n", msg.getSender().getLocalName()));
					
					agent.setReceivedTask(task);
					agent.setManagerAID( msg.getSender());
					agent.getConversationIDs().put( agent.getManagerAID(), msg.getConversationId());
					
					//check if sub contracting is needed
					if ( ( agent.hasComponent("CPU") && agent.hasComponent("GPU") && agent.hasComponent("M.Board")) != true)
					{
						//makeSubtask and send CFP
						Output.AgentMessage(agent, "Lack of resource, sub-contracting");
						agent.addInfo("Lack of resource, sub-contracting.\n");
						
						SubTask subtask = createSubTask(task);
						agent.setSubTask(subtask);
						ArrayList<AID> contractorAgents = agent.getContractorAIDs();
						Iterator<AID> i = contractorAgents.iterator();
						
						ACLMessage cfpMsg;
						AID contractor;
						int counter = 1;
						while ( i.hasNext())
						{
							cfpMsg = new ACLMessage( ACLMessage.CFP);
							cfpMsg.setProtocol( "fipa-contract-net");
							cfpMsg.setOntology( "sub-task");
							cfpMsg.setConversationId( "sub." + Integer.toString(counter));
							cfpMsg.setReplyByDate( new Date( Calendar.getInstance().getTimeInMillis() + agent.getDeadlineTime()));
							contractor = i.next();
							if ( !contractor.equals(agent.getAID()))
							{
								cfpMsg.addReceiver( contractor);
								try {
									cfpMsg.setContentObject( subtask);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								agent.send( cfpMsg);
								agent.getConversationIDs().put(contractor, cfpMsg.getConversationId());
								counter++;
							}
						}	
						
						agent.initializeDeadlineTimer();
					}
					else
						agent.setReadyToPropose(true); //we are ready to propose
			
				}
				else
				{
					Output.AgentMessage( agent, "Cannot read task.");
				}
			}
			if ( msg.getOntology().equals( "sub-task")) // for sub contracted tasks
			{
				SubTask subtask = null;
				try {
					subtask = (SubTask) msg.getContentObject();
				} catch (UnreadableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if ( subtask != null)
				{
					Output.AgentMessage( agent, "Sub-task recieved from agent" + msg.getSender().getLocalName());
					agent.addInfo(String.format("Sub-task recieved from %s\n", msg.getSender().getLocalName()));
					
					//Evaluate the task??
					SubTaskBid bid = evaluate( subtask);
					//Send either proposal or reject proposal
					ACLMessage bidMsg;
					if ( bid != null)
					{
						bidMsg = new ACLMessage( ACLMessage.PROPOSE);
						Output.AgentMessage( agent, "Offering a tender to " + msg.getSender().getLocalName());
						agent.addInfo(String.format("Offering a tender to %s\n", msg.getSender().getLocalName()));
					}
					else
					{
						bidMsg = new ACLMessage( ACLMessage.REFUSE);
						Output.AgentMessage( agent, "Refusing to make a tender to " + msg.getSender().getLocalName());
						agent.addInfo(String.format("Refusing to make a tender to %s\n", msg.getSender().getLocalName()));
					}
					bidMsg.setOntology("Bid");
					bidMsg.setConversationId( msg.getConversationId());
					bidMsg.setProtocol( msg.getProtocol());
					bidMsg.addReceiver( msg.getSender());
					try {
						bidMsg.setContentObject( bid);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					agent.send(bidMsg);
					//Output.AgentMessage( agent, "Offered a tender to " + msg.getSender());
				}
				else
					Output.AgentMessage( agent, "Cannot read sub-task.");	
			}			
		}
		else
			block(100);

	}
	
	private SubTaskBid evaluate( SubTask subtask) {
		SubTaskBid newBid = new SubTaskBid( agent.getAID());
		for ( Component c : subtask.components)
		{
			Component comp = evaluate(c);
			if ( comp == null)
				return null;
			else
				newBid.componentList.add(comp);
		}
		newBid.bidderID = agent.getAID();
		return newBid;
	}
	
	private Component evaluate( Component task)
	{				
		Component bestChoice = null;
		double bestRatio = -1;
		for ( Component c : agent.getComponents())
		{
			if ( c.getType().equals(task.getType()))
			{
				double qt = task.getQuality();
				double pt = task.getPrice();
				double qc = c.getQuality();
				double pc = c.getPrice();
				double mCoef = 5;
				
				//If quality or price is not specified in the task				
				if ( qt == 1)
					qc = 1;
				if ( pt == 1)
					pc = 1;
				if ( task.getManufacturer().equals("") || ( task.getManufacturer().equals( c.getManufacturer())))
					mCoef = 10;
				
				double taskQPRatio = qt / pt;
				double compQPRatio = qc / pc;
				
				if ( compQPRatio * mCoef >= taskQPRatio)
				{
					if ( compQPRatio * mCoef > bestRatio)						
					{
						bestChoice = c;
						bestRatio = compQPRatio * mCoef;
					}
				}				
			}
		}		
		return bestChoice;
	}

	private SubTask createSubTask( Task task)
	{
		SubTask subtask = new SubTask();
		boolean cpuMissing = false;
		boolean gpuMissing = false;
		boolean mbMissing = false;
		if ( !agent.hasComponent("CPU"))
			cpuMissing = true;
		if ( !agent.hasComponent("GPU"))
			gpuMissing = true;
		if ( !agent.hasComponent("M.Board"))
			mbMissing = true;
		for (Computer c: task.computerList)
		{
			if ( cpuMissing)
				subtask.components.add( c.getCpu());
			if ( gpuMissing)
				subtask.components.add( c.getGpu());
			if ( mbMissing)
				subtask.components.add( c.getMotherboard());
		}
		
		return subtask;
	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return false;
	}

}
