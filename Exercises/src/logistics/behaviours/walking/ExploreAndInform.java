package logistics.behaviours.walking;

import gridworld.Environment;
import jade.core.behaviours.SimpleBehaviour;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import logistics.BombExplorerAgent;
import logistics.GridWorldAgent;
import logistics.behaviours.ProcessMessageBehaviour;
import logistics.behaviours.RequestRemoveBombsAction;
import logistics.behaviours.InformTrapsAction;

public class ExploreAndInform extends WalkBehaviour
{
	
	public ExploreAndInform(BombExplorerAgent owner)
	{
		super(owner);
	}
	
	public ExploreAndInform(BombExplorerAgent owner, boolean senseLocation, boolean senseStones, boolean senseAgents, boolean senseBombs, boolean senseTraps)
	{
		super(owner, senseLocation, senseStones, senseAgents, senseBombs, senseTraps);
	}
	
	public void walk()
	{
		new MemoryRandomStepAction(_owner).action();
	}
	
	public void afterWalk()
	{
		new RequestRemoveBombsAction((BombExplorerAgent)_owner).action();
		new InformTrapsAction((BombExplorerAgent)_owner).action();
		new ProcessMessageBehaviour((BombExplorerAgent)_owner, false, ACLMessage.INFORM).action();
	}
	
	@Override
	public boolean done() 
	{
		return false;
		//return _owner.knowsBombs() && _owner.knowsTraps();
	}
	
	@Override
	public int onEnd()
	{
		return 1;
	}
}
