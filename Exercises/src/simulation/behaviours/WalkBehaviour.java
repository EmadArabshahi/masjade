package simulation.behaviours;

import simulation.environment.Environment;

import jade.core.behaviours.SimpleBehaviour;
import jade.core.Agent;

import simulation.agents.GridWorldAgent;


public abstract class WalkBehaviour extends SimpleBehaviour
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4464036361865707912L;

	protected GridWorldAgent _owner;
	
	protected boolean _senseLocation, _senseStones, _senseAgents, _senseApples;
	
	public WalkBehaviour(GridWorldAgent owner)
	{
		this._owner = owner;
		_senseLocation = true;
		_senseStones = true;
		_senseAgents = true;
		_senseApples = true;
	}
	
	public WalkBehaviour(GridWorldAgent owner, boolean senseLocation, boolean senseStones, boolean senseAgents, boolean senseApples)
	{
		_owner = owner;
		_senseLocation = senseLocation;
		_senseStones = senseStones;
		_senseAgents = senseAgents;
		_senseApples = senseApples;
	}
	
	
	public final void action()
	{
		new SensingAction(_owner, _senseLocation, _senseStones, _senseAgents, _senseApples);
		new SensingAction(_owner, _senseLocation, _senseStones, _senseAgents, _senseApples).action();
		walk();
		new SensingAction(_owner, _senseLocation, _senseStones, _senseAgents, _senseApples).action();
		afterWalk();
	}
	
	
	public abstract void walk();
	
	public void afterWalk()
	{};
	
	public abstract boolean done();
	
	public abstract int onEnd();
}
