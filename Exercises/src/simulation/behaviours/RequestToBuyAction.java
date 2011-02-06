package simulation.behaviours;

import java.awt.Point;

import simulation.agents.GridWorldAgent;
import simulation.environment.Environment;
import jade.core.behaviours.OneShotBehaviour;


public class RequestToBuyAction extends OneShotBehaviour {

		
	private GridWorldAgent _owner;
	private int _endState;
	
	public static final int SUCCESS = 1;
	public RequestToBuyAction(GridWorldAgent owner) 
	{
		_owner = owner;
		_endState = SUCCESS;
	}

	@Override
	public void action() 
	{
		if(!_owner.hasOutstandingRequest())
		{
			int price = _owner.getRequestToBuyPrice();
			System.out.println("NO LAST REQUEST FOUND : REQUESTING TO BUY APPLE FOR " + price + "           agent: " + _owner.getLocalName());
			
			if(price > 0)
				_owner.appleRequested(Environment.requestToBuy(_owner.getLocalName(), price ));
		}
		else
		{
			int price = _owner.getRequestToBuyPrice();
			Environment.removeRequest(_owner.getLocalName(), _owner.getOutstandingRequest());
			System.out.println("REQUESTING TO BUY APPLE FOR " + price + "           agent: " + _owner.getLocalName());
			if(price > 0)
				_owner.appleRequested(Environment.requestToBuy(_owner.getLocalName(), price ));
		}
	}
	
	
	
	public int onEnd()
	{
		return _endState;
	}
}