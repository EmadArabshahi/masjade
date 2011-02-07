package simulation.behaviours;

import java.awt.Point;

import simulation.agents.GridWorldAgent;
import simulation.environment.Environment;
import jade.core.behaviours.OneShotBehaviour;


public class TradeAction extends OneShotBehaviour {

		
	private GridWorldAgent _owner;
	private int _endState;
	
	public static final int SUCCESS = 1;
	public TradeAction(GridWorldAgent owner) 
	{
		_owner = owner;
		_endState = SUCCESS;
	}

	@Override
	public void action() 
	{
		_owner.applesTraded(Environment.tradeApple(_owner.getLocalName()));
		//int price = _owner.getRequestToBuyPrice();
		//Environment.requestToBuy(_owner.getLocalName(), price );
		
	}
	
	
	
	public int onEnd()
	{
		return _endState;
	}
}