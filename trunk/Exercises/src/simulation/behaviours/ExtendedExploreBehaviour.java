package simulation.behaviours;

import gridworld.Environment;
import jade.core.behaviours.SimpleBehaviour;
import jade.core.Agent;
import simulation.agents.GridWorldAgent;

public class ExtendedExploreBehaviour extends WalkBehaviour
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1654409861694726237L;
	
	//in order of which they are checked. so must trade has highest priority.
	public static int MUST_TRADE_NEXT_ROUND = 0;
	public static int LACK_OF_ENERGY_AND_HAS_APPLE = 1;
	public static int IS_HUNGRY_AND_HAS_NO_APPLE = 2;
	public static int KNOWS_APPLE_AND_HAS_NO_APPLE = 3;
	public static int HAS_APPLE = 4;
	
	
	private int  _endResult = -1;
	
	public ExtendedExploreBehaviour(GridWorldAgent owner)
	{
		super(owner);
	}
	
	public ExtendedExploreBehaviour
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
		else if(_owner.isHungry() && !_owner.hasApples())
		{
			_endResult = IS_HUNGRY_AND_HAS_NO_APPLE;
			return true;
		}
		else if(_owner.knowsApples() && !_owner.hasApples())
		{
			_endResult = KNOWS_APPLE_AND_HAS_NO_APPLE;
			return true;
		}
		else if(_owner.hasApples())
		{
			_endResult = HAS_APPLE;
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
