package simulation.environment;

/*
 3APL Blockworld program by Jelle Herold, copyright 2003.
 Written for the Intelligent Systems group at Utrecht University.
 This is LGPL software. Extended by Frank van Meeuwen

 $Id: Agent.java,v 1.6 2004/09/14 10:58:06 cvs-3apl Exp $
 */

import simulation.environment.lib.Signal;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;


// / Agent representation in Env. This doubles as a plugin instance.
public class Agent 
{
	public static final int GREEDY = 0;
	public static final int COMMUNIST = 1;
	public static final int LIBERAL = 2;
	public static final int FREE_MIND = 3;
	
	public static final int startingEnergyLevel = 100;
	public static final int startingMoney = 100;
	
	protected String _name = null;
	
	Point _position = null;

	int _apples = 0;
	
	int _energyLevel = startingEnergyLevel;
	
	int _moneyInEuroCents = startingMoney;
	
	int _colorID = 0;
	
	int _type;

	public Agent( String name, int type ) 
	{
		_name = name;
		_type = type;
		init();
	}
	
	public int getType()
	{
		return _type;
	}
	
	public void init()
	{
		_position = null;
		_apples = 0;
		_energyLevel = startingEnergyLevel;
		_moneyInEuroCents = startingMoney;
	}
	
	public String getName() 
	{
		return _name;
	}
	
	/**
	 * Get current agent position. \returns This will return null if the agent
	 * is not entered in the world.
	 */
	public Point getPosition() 
	{
		return _position;
	}
	
	public int getEnergylevel()
	{
		return _energyLevel;
	}
	
	public int getApples()
	{
		return _apples;
	}
	
	public int getMoney()
	{
		return _moneyInEuroCents;
	}
	
	public void pickupApple(TypeObject apple)
	{
		_apples++;
	}
	
	
	/**
	 * Check if agent is "entered" in the environment. That is, it has a
	 * position in the world. \returns true if agent is entered in the
	 * environment
	 */
	public boolean isEntered() 
	{
		return (_position != null);
	}

	/**
	 * Called by the interpreter when the agent this instance refers to is
	 * reset. \todo signalMove show become special signal enter/exit
	 */
	public void reset() {
		System.out.println( "Agent reset!" );
		init();
		
		signalMove.emit();
	}

	/**
	 * returns the unique name of the agent this instance refers to.
	 */
	public String toString() {
		return getName();
	}

	// / removes all listeners
	public void deleteObservers() {
		signalMove.deleteObservers();
		signalPickupApple.deleteObservers();
		signalTrade.deleteObservers();
		signalEat.deleteObservers();
				
		signalMoveSucces.deleteObservers();
		signalPickupAppleSucces.deleteObservers();
		signalTradeSucces.deleteObservers();
		signalEat.deleteObservers();
	}

	// / emitted if agent attemps movement (succesful or not)
	public transient Signal signalMove = new Signal( "agent attempts move" );

	// / emitted if agent attemps to pickup a bomb (succesful or not)
	public transient Signal signalPickupApple = new Signal("agent attempts pickup" );

	// / emitted if agent attemps to drop a bomb (succesful or not)
	public transient Signal signalTrade = new Signal( "agent attempts to trade" );

	
	public transient Signal signalEat = new Signal("agents attempts to eat");
	
	// / emitted if agent succesfully moves
	public transient Signal signalMoveSucces = new Signal(
			"agent succesful move" );

	// / emitted if agent (succesfully) picks up a bomb
	public transient Signal signalPickupAppleSucces = new Signal(
			"agent succesful pickup" );

	// / emitted if agent (succesfully) drops a bomb
	public transient Signal signalTradeSucces = new Signal(
			"agent sucessful trade" );
	
	public transient Signal signalEatSucces = new Signal("agent succesful eat");
}
