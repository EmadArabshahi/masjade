package logistics.behaviours.walking;

import gridworld.Environment;
import jade.core.behaviours.SimpleBehaviour;
import jade.core.Agent;
import logistics.GridWorldAgent;
import logistics.behaviours.SensingAction;

public class WalkAwayFromTrapBehaviour extends WalkBehaviour
{	
	public static int NOT_ON_TRAP =1;
	
	public WalkAwayFromTrapBehaviour(GridWorldAgent owner)
	{
		super(owner);
	}
	
	public WalkAwayFromTrapBehaviour(GridWorldAgent owner, boolean senseLocation, boolean senseStones, boolean senseAgents, boolean senseBombs, boolean senseTraps)
	{
		super(owner, senseLocation, senseStones, senseAgents, senseBombs, senseTraps);
	}
	
	@Override
	public void walk() 
	{
		new MemoryRandomStepAction(_owner).action();		
	}

	@Override
	public boolean done() 
	{	
		boolean done = !_owner.isOnTrap();
		
		if(done)
			System.out.println("Owner is away from trap!! quiting");
		
		return done;
	}
	
	@Override
	public int onEnd()
	{
		return NOT_ON_TRAP;
	}
}
