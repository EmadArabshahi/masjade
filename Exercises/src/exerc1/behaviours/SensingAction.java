package exerc1.behaviours;

import exerc1.GridWorldAgent;
import gridworld.Environment;
import jade.core.behaviours.OneShotBehaviour;

public class SensingAction extends OneShotBehaviour
{
	
	/**
	 * The agent that performs the behaviour.
	 */
	private GridWorldAgent _owner;
	
	/**
	 * Flags to turn sensing of a specific kind of object on or off.
	 */
	private boolean _senseLocation, _senseStones, _senseBombs, _senseAgents, _senseTraps;
	
	/**
	 * Constructs a new SensingBehaviour that will senses all objects in the environment.
	 * @param owner The agent that performs the behaviour.
	 */
	public SensingAction(GridWorldAgent owner)
	{
		this(owner, true, true, true, true, true);
	}
	
	public SensingAction(GridWorldAgent owner, boolean senseLocation, boolean senseStones, boolean senseAgents, boolean senseBombs, boolean senseTraps)
	{
		this._owner = owner;
		this._senseLocation = senseLocation;
		this._senseStones = senseStones;
		this._senseAgents = senseAgents;
		this._senseBombs = senseBombs;
		this._senseTraps = senseTraps;
	}

	public void action() 
	{
		if(_senseLocation)
			_owner.locationSensed(Environment.getPosition(_owner.getLocalName()));
		if(_senseStones)
			_owner.stonesSensed(Environment.senseStones(_owner.getLocalName()));
		if(_senseAgents)
			_owner.agentsSensed(Environment.senseAgents(_owner.getLocalName()));
		if(_senseBombs)
			_owner.bombsSensed(Environment.senseBombs(_owner.getLocalName()));
		if(_senseTraps)
			_owner.trapsSensed(Environment.senseTraps(_owner.getLocalName()));
			
	}

}
