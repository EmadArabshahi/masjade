package contractnet.src.behaviours;

import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import contractnet.src.Component;
import contractnet.src.SubTaskBid;
import contractnet.src.agents.ContractorAgent;

public class EvaluateSubTaskBidsBehaviour extends SimpleBehaviour {

	private static final long serialVersionUID = -3325566016557092505L;
	private ContractorAgent agent;
	private boolean done = false;

	@Override
	public void action() {
		// TODO Auto-generated method stub
		agent = (ContractorAgent) myAgent;
		
		if ( agent.isDeadlinePassed())
		{
			if (  ( agent.getSubBids().size() > 0))
			{	
				//evaluate bids and select best one
				AID bestContractorID = evaluateBids();
				
				//send accept/reject proposal
				ACLMessage resultMsg = null;
				for ( SubTaskBid bid : agent.getSubBids())
				{
					if ( bid.bidderID.equals( bestContractorID))
					{
						resultMsg = new ACLMessage( ACLMessage.ACCEPT_PROPOSAL);
						resultMsg.setProtocol( "fipa-contract-net");
						resultMsg.setOntology( "bid-accept");
						resultMsg.setConversationId( agent.getConversationIDs().get(bid.bidderID));
						resultMsg.setContent( "Tender proposal accepted.");
						//add it to the agent components
						
						for ( Component c : bid.componentList)
						{
							agent.getComponents().add(c);
						}
						
						agent.addInfo(String.format("Sub-contract accepted from %s\n", bid.bidderID.getLocalName()));
					}
					else
					{
						resultMsg = new ACLMessage( ACLMessage.REJECT_PROPOSAL);
						resultMsg.setProtocol( "fipa-contract-net");
						resultMsg.setOntology( "bid-reject");
						resultMsg.setConversationId( agent.getConversationIDs().get(bid.bidderID));
						resultMsg.setContent( "Tender proposal rejected.");				
					}
					resultMsg.addReceiver( bid.bidderID);
					agent.send( resultMsg);			
				}
			}
			agent.setReadyToPropose(true);
			done = true;
		}

	}

	private AID evaluateBids() {
		
		AID bestBidderID = null;
		double bestBid = -1;
		for ( SubTaskBid bid : agent.getSubBids())
		{
			double result = 0;
			for ( int i = 0; i < bid.componentList.size(); i++)
			{
				result += evaluateComponent(i, bid.componentList.get(i));
			}
			
			if ( result > bestBid)
			{
				bestBid = result;
				bestBidderID = bid.bidderID;
			}			
		}
		return bestBidderID;
	}

	private double evaluateComponent( int i, Component comp)
	{
		double mCoef = 1;
		String taskMan = agent.getSubTask().components.get(i).getManufacturer();
		if ( taskMan.equals("") || taskMan.equals( comp.getManufacturer()))
			mCoef = 2;
		
		return mCoef * comp.getQuality() / comp.getPrice();
	}
	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return done;
	}

}
