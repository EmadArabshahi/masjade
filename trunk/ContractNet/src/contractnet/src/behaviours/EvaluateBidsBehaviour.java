package contractnet.src.behaviours;

import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import contractnet.src.Bid;
import contractnet.src.Component;
import contractnet.src.Computer;
import contractnet.src.agents.ManagerAgent;

public class EvaluateBidsBehaviour extends SimpleBehaviour {

	private static final long serialVersionUID = -6529454122358460677L;

	private ManagerAgent agent;
	private boolean done = false;
	@Override
	public void action() {
		agent = (ManagerAgent) myAgent;
		
		if ( agent.isDeadlinePassed())
		{
			agent.setTaskStatus("Bidding closed");
			agent.updateInfo();
			//evaluate bids and select best one
			AID bestContractorID = evaluateBids();
			
			//send accept/reject proposal
			ACLMessage resultMsg = null;
			for ( Bid bid : agent.getBids())
			{
				if ( bid.bidderID.equals( bestContractorID))
				{
					resultMsg = new ACLMessage( ACLMessage.ACCEPT_PROPOSAL);
					resultMsg.setProtocol( "fipa-contract-net");
					resultMsg.setOntology( "bid-accept");
					resultMsg.setConversationId( agent.getConversationIDs().get(bid.bidderID));
					resultMsg.setContent( "Tender proposal accepted.");
					agent.setResponse(bestContractorID.getLocalName(), "Proposal accepted!");
					agent.updateInfo();
				}
				else
				{
					resultMsg = new ACLMessage( ACLMessage.REJECT_PROPOSAL);
					resultMsg.setProtocol( "fipa-contract-net");
					resultMsg.setOntology( "bid-reject");
					resultMsg.setConversationId( agent.getConversationIDs().get(bid.bidderID));
					resultMsg.setContent( "Tender proposal rejected.");	
					agent.setResponse(bid.bidderID.getLocalName(), "Proposal rejected");
					agent.updateInfo();
				}
				resultMsg.addReceiver( bid.bidderID);
				agent.send( resultMsg);			
			}
			done = true;
		}
	}
	
	private AID evaluateBids()
	{
		AID bestBidderID = null;
		double bestBid = -1;
		for ( Bid bid : agent.getBids())
		{
			double result = 0;
			for ( int i = 0; i < bid.computerList.size(); i++)
			{
				result += evaluateComputer(i, bid.computerList.get(i));
			}
			System.out.println( bid.bidderID.getLocalName() + ":" + result);
			if ( result > bestBid)
			{
				bestBid = result;
				bestBidderID = bid.bidderID;
			}			
		}
		return bestBidderID;
	}
	
	private double evaluateComputer( int i, Computer comp)
	{
		return evaluateComponent(i, comp.getCpu()) + evaluateComponent(i, comp.getGpu()) + evaluateComponent(i, comp.getMotherboard());
	}
	
	private double evaluateComponent( int i, Component comp)
	{
		double mCoef = 1;
		String taskMan = agent.getTask().computerList.get(i).getComponentByType( comp.getType()).getManufacturer();
		if ( taskMan.equals("") || taskMan.equals( comp.getManufacturer()))
			mCoef = 2;
		
		return mCoef * comp.getQuality() / comp.getPrice();
	}

	@Override
	public boolean done() {
		return done;
	}
}
