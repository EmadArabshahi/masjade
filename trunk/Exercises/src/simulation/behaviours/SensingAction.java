package simulation.behaviours;

import simulation.environment.Environment;
import jade.core.behaviours.OneShotBehaviour;
import simulation.agents.GridWorldAgent;;

public class SensingAction extends OneShotBehaviour
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2966479739896115955L;

	/**
	 * The agent that performs the behaviour.
	 */
	private GridWorldAgent _owner;
	
	/**
	 * Flags to turn sensing of a specific kind of object on or off.
	 */
	private boolean _senseLocation, _senseStones, _senseApples, _senseAgents, _sensePropperties;
	
	/**
	 * Constructs a new SensingBehaviour that will senses all objects in the environment.
	 * @param owner The agent that performs the behaviour.
	 */
	public SensingAction(GridWorldAgent owner)
	{
		this(owner, true, true, true, true);
	}
	
	public SensingAction(GridWorldAgent owner, boolean senseLocation, boolean senseStones, boolean senseAgents, boolean senseApples)
	{
		this._owner = owner;
		this._senseLocation = senseLocation;
		this._senseStones = senseStones;
		this._senseAgents = senseAgents;
		this._senseApples = senseApples;
	}

	public void action() 
	{
		if(_senseLocation)
			_owner.locationSensed(Environment.getPosition(_owner.getLocalName()));
		if(_senseStones)
			_owner.stonesSensed(Environment.senseStones(_owner.getLocalName()));
		if(_senseAgents)
			_owner.agentsSensed(Environment.senseAgents(_owner.getLocalName()));
		if(_senseApples)
			_owner.applesSensed(Environment.senseApples(_owner.getLocalName()));
	}

}
