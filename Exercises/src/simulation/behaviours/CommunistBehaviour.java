package simulation.behaviours;

import simulation.agents.GridWorldAgent;
import jade.core.behaviours.CyclicBehaviour;

public class CommunistBehaviour extends CyclicBehaviour 
{
	
	private GridWorldAgent _owner;
	
	public CommunistBehaviour(GridWorldAgent owner)
	{
		_owner = owner;
	}
	public void action()
	{
		_owner.handleRequests();
		_owner.handleRequests();
		
		if(_owner.mustTrade())
			     trade();
		else if(_owner.isHungry() && !_owner.hasApples())
			     exploreOrCollect();
		else if(_owner.hasLackOfEnergy() && _owner.hasApples())
				eatApple();
		else
			exploreOrCollect();
	}
	
	private void trade()
	{
		new TradeAction(_owner).action();
	}
	
	private void eatApple()
	{
		new EatAppleAction(_owner).action();
	}
	
	private void exploreOrCollect()
	{
		if(!_owner.hasApples() && _owner.knowsApples())
			collect();
		else
			explore();
	}
	
	private void collect()
	{
		
	}
	
	private void explore()
	{
		
	}
	
	
}
