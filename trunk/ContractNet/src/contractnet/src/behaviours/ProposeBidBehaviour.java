package contractnet.src.behaviours;

import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;

import java.io.IOException;

import contractnet.src.Bid;
import contractnet.src.Component;
import contractnet.src.Computer;
import contractnet.src.Output;
import contractnet.src.Task;
import contractnet.src.agents.ContractorAgent;

public class ProposeBidBehaviour extends SimpleBehaviour {

	private static final long serialVersionUID = 4001679695379004945L;
	
	private ContractorAgent agent;
	private boolean done = false;
	
	@Override
	public void action() {
		// TODO Auto-generated method stub
		agent = (ContractorAgent) myAgent;
		
		if ( agent.isReadyToPropose())
		{
		
			//Evaluate the task
			Bid bid = evaluate( agent.getReceivedTask());
			//Send either proposal or reject proposal
			ACLMessage bidMsg;
			if ( bid != null)
			{
				bidMsg = new ACLMessage( ACLMessage.PROPOSE);
				Output.AgentMessage( agent, "Offering a tender to " + agent.getManagerAID().getLocalName());
			}
			else
			{
				bidMsg = new ACLMessage( ACLMessage.REFUSE);
				Output.AgentMessage( agent, "Refusing to make a tender to " + agent.getManagerAID().getLocalName());
			}
			bidMsg.setOntology("Bid");
			bidMsg.setConversationId( agent.getConversationIDs().get( agent.getManagerAID()));
			bidMsg.setProtocol( "fipa-contract-net");
			bidMsg.addReceiver( agent.getManagerAID());
			try {
				bidMsg.setContentObject( bid);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			agent.send( bidMsg);
			done = true;

		}					
		
		else
			block(100);
	}

	

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return done;
	}
	
	private Bid evaluate( Task task)
	{
		Bid newBid = new Bid( agent.getAID());
		for ( Computer c : task.computerList)
		{
			Computer comp = evaluate(c);
			if ( comp == null)
				return null;
			else
				newBid.computerList.add(comp);
		}
		newBid.bidderID = agent.getAID();
		return newBid; 
	}
	
	private Computer evaluate( Computer task)
	{
		Computer bidComp = null;
		Component cpu = evaluate( task.getCpu());
		Component gpu = evaluate( task.getGpu());
		Component mb = evaluate( task.getMotherboard());
		
		if ( ( cpu != null) && ( gpu != null) && ( mb != null) )
			bidComp = new Computer(cpu, gpu, mb);
		return bidComp;
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
				if ( task.getManufacturer() == null || ( task.getManufacturer().equals( c.getManufacturer())))
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

}
