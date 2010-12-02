package exerc1.behaviours;

import exerc1.BombRemovalAgent;
import gridworld.Environment;
import jade.core.behaviours.OneShotBehaviour;

public class SensingBehaviour extends OneShotBehaviour
{
	
	/**
	 * The agent that performs the behaviour.
	 */
	private BombRemovalAgent _owner;
	
	/**
	 * Flags to turn sensing of a specific kind of object on or off.
	 */
	private boolean _senseLocation, _senseStones, _senseBombs, _senseTraps;
	
	/**
	 * Constructs a new SensingBehaviour that will senses all objects in the environment.
	 * @param owner The agent that performs the behaviour.
	 */
	public SensingBehaviour(BombRemovalAgent owner)
	{
		this(owner, true, true, true, true);
	}
	
	public SensingBehaviour(BombRemovalAgent owner, boolean senseLocation, boolean senseStones, boolean senseBombs, boolean senseTraps)
	{
		this._owner = owner;
		this._senseLocation = senseLocation;
		this._senseStones = senseStones;
		this._senseBombs = senseBombs;
		this._senseTraps = senseTraps;
	}
	@Override
	public void action() 
	{
		if(_senseLocation)
			_owner.locationSensed(Environment.getPosition(_owner.getLocalName()));
		if(_senseStones)
			_owner.stonesSensed(Environment.senseStones(_owner.getLocalName()));
		if(_senseBombs)
			_owner.bombsSensed(Environment.senseBombs(_owner.getLocalName()));
		if(_senseTraps)
			_owner.trapsSensed(Environment.senseTraps(_owner.getLocalName()));
			
	}

}
