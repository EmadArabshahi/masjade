package contractnet.src.behaviours;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import contractnet.src.Output;
import contractnet.src.agents.ManagerAgent;
import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;

public class AnnounceTaskBehaviour extends SimpleBehaviour {

	private static final long serialVersionUID = 7840622197992536302L;
	
	private ManagerAgent agent;
	private boolean done = false;
	
	@Override
	public void action() {
		// TODO Auto-generated method stub
		agent = (ManagerAgent) myAgent;
		
		if ( agent.isAnnounceReady())
		{			
			Output.AgentMessage( agent, "Starting task announcement");
			
			ArrayList<AID> contractorAgents = agent.getContractorAIDs();
			Iterator<AID> i = contractorAgents.iterator();
			
			ACLMessage cfpMsg;
			AID contractor;
			int counter = 1;
			while ( i.hasNext())
			{
				cfpMsg = new ACLMessage( ACLMessage.CFP);
				cfpMsg.setProtocol( "fipa-contract-net");
				cfpMsg.setOntology( "task");
				cfpMsg.setConversationId( Integer.toString(counter));
				cfpMsg.setReplyByDate( new Date( Calendar.getInstance().getTimeInMillis() + agent.getDeadlineTime()));
				contractor = i.next();
				cfpMsg.addReceiver( contractor);
				try {
					cfpMsg.setContentObject( agent.getTask());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				agent.send( cfpMsg);
				agent.getConversationIDs().put(contractor, cfpMsg.getConversationId());
				counter++;
			}	
			
			agent.initializeDeadlineTimer();
			
			Output.AgentMessage( agent, "Task Announcement is sent.");
			Output.AgentMessage( agent, "Waiting contractors' offers.");
			done = true;
		}
		else
			block(300);
	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return done;
	}	
	

}
