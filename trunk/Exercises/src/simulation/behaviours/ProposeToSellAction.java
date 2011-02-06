package simulation.behaviours;

import java.awt.Point;

import simulation.agents.GridWorldAgent;
import simulation.environment.Environment;
import jade.core.behaviours.OneShotBehaviour;


public class ProposeToSellAction extends OneShotBehaviour {

		
	private GridWorldAgent _owner;
	private int _endState;
	
	public static final int SUCCESS = 1;
	public ProposeToSellAction(GridWorldAgent owner) 
	{
		_owner = owner;
		_endState = SUCCESS;
	}

	@Override
	public void action() 
	{
		if(!_owner.hasOutstandingProposal())
		{	
			int price = _owner.getProposeToSellPrice();
			System.out.println("NO LAST PROPOSAL FOUND: PROPOSING TO SELL APPLE FOR " + price + "           agent: " + _owner.getLocalName());
			_owner.appleProposed(Environment.proposeToSell(_owner.getLocalName(), price));
		}
		else
		{
			int price = _owner.getProposeToSellPrice();
			Environment.removeProposal(_owner.getLocalName(), _owner.getOutstandingProposal());
			System.out.println("PROPOSING TO SELL APPLE FOR " + price + "           agent: " + _owner.getLocalName());
			if(price > 0)
				_owner.appleRequested(Environment.requestToBuy(_owner.getLocalName(), price ));
		}
	}
	
	
	
	public int onEnd()
	{
		return _endState;
	}
}