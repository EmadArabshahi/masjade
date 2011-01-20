package auction;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class WaitForBiddersBehaviour extends WakerBehaviour {

	public WaitForBiddersBehaviour(Agent a, long timeout) {
		super(a, timeout);
		Output.AgentMessage(a, "Waiting for bidders");
	}
	
	@Override
	protected void onWake()
	{
		//
	}
}
