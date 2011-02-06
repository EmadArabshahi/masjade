package simulation.behaviours;

import gridworld.Environment;
import jade.core.behaviours.SimpleBehaviour;
import jade.core.Agent;
import simulation.agents.GridWorldAgent;

public class SimpleExploreBehaviour extends WalkBehaviour
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1654409861694726237L;
	
	//in order of which they are checked. so must trade has highest priority.
	public static int MUST_TRADE_NEXT_ROUND = 0;
	public static int LACK_OF_ENERGY_AND_HAS_APPLE = 1;
	public static int KNOWS_APPLES = 2;
	
	private int  _endResult = -1;
	
	public SimpleExploreBehaviour(GridWorldAgent owner)
	{
		super(owner);
	}
	
	public SimpleExploreBehaviour
	(GridWorldAgent owner, boolean senseLocation, boolean senseStones, boolean senseAgents, boolean senseApples)
	{
		super(owner, senseLocation, senseStones, senseAgents, senseApples);
	}
	
	public void walk()
	{
		if(_owner.mustTrade())
			return;
		
		new MemoryRandomStepAction(_owner).action();
	}
	
	@Override
	public boolean done() 
	{
		if(_owner.mustTrade())
		{
			_endResult = MUST_TRADE_NEXT_ROUND;
			return true;
		}
		else if(_owner.hasLackOfEnergy() && _owner.hasApples())
		{
			_endResult = LACK_OF_ENERGY_AND_HAS_APPLE;
			return true;
		}
		else if(_owner.knowsApples())
		{
			_endResult = KNOWS_APPLES;
			return true;
		}
		else
		{
			return false;
		}

	}
	
	@Override
	public int onEnd()
	{
		return _endResult;
	}
}
