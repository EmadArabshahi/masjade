package logistics.behaviours.walking;

import gridworld.Environment;
import jade.core.behaviours.SimpleBehaviour;
import jade.core.Agent;
import logistics.GridWorldAgent;
import logistics.behaviours.SensingAction;

public abstract class WalkBehaviour extends SimpleBehaviour
{
	protected GridWorldAgent _owner;
	
	protected boolean _senseLocation, _senseStones, _senseAgents, _senseBombs, _senseTraps;
	
	public WalkBehaviour(GridWorldAgent owner)
	{
		this._owner = owner;
		_senseLocation = true;
		_senseStones = true;
		_senseAgents = true;
		_senseBombs = true;
		_senseTraps = true;
	}
	
	public WalkBehaviour(GridWorldAgent owner, boolean senseLocation, boolean senseStones, boolean senseAgents, boolean senseBombs, boolean senseTraps)
	{
		_owner = owner;
		_senseLocation = senseLocation;
		_senseStones = senseStones;
		_senseAgents = senseAgents;
		_senseBombs = senseBombs;
		_senseTraps = senseTraps;
	}
	
	
	public final void action()
	{
		new SensingAction(_owner, _senseLocation, _senseStones, _senseAgents, _senseBombs, _senseTraps).action();
		walk();
		new SensingAction(_owner, _senseLocation, _senseStones, _senseAgents, _senseBombs, _senseTraps).action();
		afterWalk();
	}
	
	
	public abstract void walk();
	
	public void afterWalk()
	{};
	
	public abstract boolean done();
	
	public abstract int onEnd();
}
