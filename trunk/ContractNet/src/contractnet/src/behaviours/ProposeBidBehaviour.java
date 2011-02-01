package contractnet.src.behaviours;

import java.io.IOException;

import contractnet.src.Bid;
import contractnet.src.Component;
import contractnet.src.Computer;
import contractnet.src.Output;
import contractnet.src.Task;
import contractnet.src.agents.ContractorAgent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class ProposeBidBehaviour extends SimpleBehaviour {

	private static final long serialVersionUID = 4001679695379004945L;
	
	private ContractorAgent agent;
	private final boolean done = false;
	
	@Override
	public void action() {
		// TODO Auto-generated method stub
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
					Output.AgentMessage( agent, "Task recieved from manager " + msg.getSender());
					//Evaluate the task??
					Bid bid = evaluate( task);
					//Send either proposal or reject proposal
					ACLMessage bidMsg;
					if ( bid != null)
					{
						bidMsg = new ACLMessage( ACLMessage.PROPOSE);
						Output.AgentMessage( agent, "Offering a tender to " + msg.getSender());
					}
					else
					{
						bidMsg = new ACLMessage( ACLMessage.REFUSE);
						Output.AgentMessage( agent, "Refusing to make a tender to " + msg.getSender());
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
				}
				else
				{
					Output.AgentMessage( agent, "Cannot read task.");
				}
			}
			else if ( msg.getOntology().equals( "sub-task")) // for sub contracted tasks?
			{
				
			}			
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
		Bid newBid = new Bid();
		for ( Computer c : task.computerList)
		{
			Computer comp = evaluate(c);
			if ( comp == null)
				return null;
			else
				newBid.computerList.add(comp);
		}		
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
			if ( c.getType() == task.getType())
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
