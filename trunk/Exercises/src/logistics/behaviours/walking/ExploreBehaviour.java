package logistics.behaviours.walking;

import gridworld.Environment;
import jade.core.behaviours.SimpleBehaviour;
import jade.core.Agent;
import logistics.GridWorldAgent;

public class ExploreBehaviour extends WalkBehaviour
{
	public static int BOMB_AND_TRAP_FOUND = 1;
	
	public ExploreBehaviour(GridWorldAgent owner)
	{
		super(owner);
	}
	
	public ExploreBehaviour(GridWorldAgent owner, boolean senseLocation, boolean senseStones, boolean senseAgents, boolean senseBombs, boolean senseTraps)
	{
		super(owner, senseLocation, senseStones, senseAgents, senseBombs, senseTraps);
	}
	
	public void walk()
	{
		new MemoryRandomStepAction(_owner).action();
	}
	
	@Override
	public boolean done() 
	{
		return _owner.knowsBombs() && _owner.knowsTraps();
	}
	
	@Override
	public int onEnd()
	{
		return BOMB_AND_TRAP_FOUND;
	}
}
