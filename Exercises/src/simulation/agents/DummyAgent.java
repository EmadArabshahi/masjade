package simulation.agents;

import simulation.environment.Environment;
import simulation.environment.LogicalEnv;
import jade.lang.acl.ACLMessage;

public class DummyAgent extends jade.core.Agent
{

	@Override
	protected void setup() 
	{
		// TODO Auto-generated method stub
		//Environment.getMaxAppleCapacity();
		
		LogicalEnv.host(this);
	}

	
	
}
