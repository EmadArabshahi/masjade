package simulation.behaviours;

import simulation.agents.GridWorldAgent;


public class RandomWalkBehaviour extends WalkBehaviour
{

	public RandomWalkBehaviour(GridWorldAgent owner)
	{
		super(owner);
	}
	
	public RandomWalkBehaviour(GridWorldAgent owner, boolean senseLocation, boolean senseStones, boolean senseAgents, boolean senseApples)
	{
		super(owner, senseLocation, senseStones, senseAgents, senseApples);
	}
	
	public void walk()
	{
		new MemoryRandomStepAction(_owner).action();
	}
	
	@Override
	public boolean done() 
	{
		return false;
	}
	
	@Override
	public int onEnd()
	{
		return 1;
	}
	}
