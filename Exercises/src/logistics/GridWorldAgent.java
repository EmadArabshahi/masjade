package logistics;
import java.awt.Point;

import java.util.*;

import gridworld.Environment;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

public abstract class GridWorldAgent extends Agent 
{
	/**
	 * A boolean indicating the agent is holding a bomb.
	 */
	private boolean _hasBomb;
	
	/** A set to hold the known bomb locations.
	 * 
	 */
	protected Set<Point> _knownBombs;
	
		
	/**
	 * A set to hold the known stone locations.
	 */
	private Set<Point> _knownStones;
	
	/**
	 * A set to hold the current agent locations.
	 */
	private Set<Point> _knownAgents;
	
	
	/**
	 * A set to hold the know traps locations.
	 */
	protected Set<Point> _knownTraps;
	
	/** A random number generator.
	 * 
	 */
	private Random _randomGenerator;
	
	/** The last positions of the agent.
	 * 
	 */
	private List<Point> _positionHistory;
	
	/**
	 * The number of positions to remember in the positionhistory.
	 */
	private int _positionHistorySize;
	
	/**
	 * The starting point of the Agent.
	 */
	private Point _startingPoint;
	
	
	protected void setup()
	{
		Object[] args = getArguments();
		if(args == null || args.length < 3)
			throw new IllegalArgumentException("Please provide the starting location and the colour of the agent.");
		int x = Integer.parseInt(args[0].toString());
		int y = Integer.parseInt(args[1].toString());
		String color = args[2].toString();
		
		
		_startingPoint = new Point(x, y);
		
		_knownBombs = new HashSet<Point>();
		_knownStones = new HashSet<Point>();
		_knownTraps = new HashSet<Point>();
		_knownAgents = new HashSet<Point>();
		
		long seed = hashCode() + System.currentTimeMillis();
		this._randomGenerator = new Random(seed);
		
		//set the history size, should be read from arguments.
		_positionHistorySize = 100;
		//construct empty list, with _positionHistorySize capacity.
		_positionHistory = new ArrayList<Point>(_positionHistorySize);
		
		setupAgent();
		
		enter(_startingPoint, color);
		
		System.out.println(getLocalName() + " is ready.");
	}
	
	protected void enter(Point startingPoint, String colour)
	{
		Environment.enter(getLocalName(), startingPoint, colour);
	}
	
	protected abstract void setupAgent();
	
	public boolean knowsBombs()
	{
		return _knownBombs.size() > 0;
	}
	
	public boolean knowsTraps()
	{
		return _knownTraps.size() > 0;
	}
	
	/**
	 * This method is called by a behaviour to pass the bombs that are sensed.
	 * @param bombPositions A set with the locations of the bombs that are sensed.
	 */
	public void bombsSensed(Set<Point> bombPositions) 
	{
		_knownBombs.addAll(bombPositions);
	}
	
	public void addKnownBomb(Point bombPosition) 
	{
		_knownBombs.add(bombPosition);
	}
	
	/**
	 * This method is called by a behaviour to pass the traps that are sensed.
	 * @param trapsPositions A set with the locations of the traps that are sensed.
	 */
	public void trapsSensed(Set<Point> trapsPositions)
	{
		_knownTraps.addAll(trapsPositions);
	}
	
	public void addKnownTrap(Point trapPosition)
	{
		_knownTraps.add(trapPosition);
	}
	
	/**
	 * This method is called by a behaviour to pass the stones that are sensed.
	 * @param stonesPositions A set with the locations of the stones that are sensed.
	 */
	public void stonesSensed(Set<Point> stonesPositions)
	{
		_knownStones.addAll(stonesPositions);
	}
	
	/**
	 * This method is called by a behaviour to pass the stones that are sensed.
	 * @param stonesPositions A set with the locations of the stones that are sensed.
	 */
	public void agentsSensed(Set<Point> agentsPositions)
	{
		_knownAgents.clear();
		_knownAgents.addAll(agentsPositions);
	}
	
	/**
	 * This method is called by a behaviour to pass the current location to the agent.
	 * This location is then set as current, and added to the position history.
	 * @param currentLocation The current location of the agent.
	 */
	public void locationSensed(Point currentLocation)
	{
		this.addPositionToHistory(currentLocation);
	}
	
	public boolean hasBomb()
	{
		return this._hasBomb;
	}
	
	public void bombPickedUp(boolean success)
	{
		if(!_hasBomb)
		if(success)
		{
			_hasBomb = true;
			_knownBombs.remove(getCurrentPosition());
		}
		else
			_hasBomb = false;
	}
	
	public void bombDropped(boolean success)
	{
		if(_hasBomb)
			_hasBomb = !success;
	}
	
	/**
	 * Gets the position history of the agent.
	 * The first item in the list is the current position, the second item its previous position etc.
	 * @return A list with positions.
	 */
	public List<Point> getPositionHistory()
	{
		//Make the list readonly, so an exception occurs if there is messed with.
		List<Point> readOnlyHistory  = Collections.unmodifiableList(_positionHistory);
		return readOnlyHistory;
	}
	
	public int getRandomInt(int maxValue)
	{
		return this._randomGenerator.nextInt(maxValue);
	}
	
	
	/**
	 * Gets a set with the known bomb locations.
	 * @return A set with Positions of known bomb locations.
	 */
	public Set<Point> getKnownBombs()
	{
		//Make a deep/shallow? copy of the bombs!!
		Set<Point> copyOfKnownBombs = new HashSet<Point>();
		
		for(Point bombPosition : _knownBombs)
		{
			copyOfKnownBombs.add(bombPosition);
		}
		
		return copyOfKnownBombs;
	}
	
	/**
	 * Gets a set with the known trap locations.
	 * @return A set with Positions of known trap locations.
	 */
	public Set<Point> getKnownTraps()
	{
		//Make a deep/shallow? copy of the traps!!
		Set<Point> copyOfKnownTraps = new HashSet<Point>();
		
		for(Point trapPosition : _knownTraps)
		{
			copyOfKnownTraps.add(trapPosition);
		}
		
		return copyOfKnownTraps;
	}
	
	/***
	 * Checks if the agents current position is on a bomb.
	 * @return A boolean indicating whether the agent is on a bomb.
	 */
	public boolean isOnBomb()
	{
		for(Point bombPosition : getKnownBombs())
		{
			if(getCurrentPosition() != null)
			{
				if (bombPosition.equals(getCurrentPosition()))
				{
					return true;
				}
			}
		}
		return false;
	}
	
	/***
	 * Checks if the agents current position is on a bomb.
	 * @return A boolean indicating whether the agent is on a bomb.
	 */
	public boolean isOnTrap()
	{
		for(Point trapPosition : getKnownTraps())
		{
			if(getCurrentPosition() != null)
			{
				if (trapPosition.equals(getCurrentPosition()))
				{
					return true;
				}
			}
		}
		
		System.out.println("CurrentPosition " + getCurrentPosition() + " Traps" + getKnownTraps());
		
		return false;
	}
	
	
	/**
	 * Gets the previous position of the agent. This can be null, if the agent just entered.!
	 * @return A point indicating the previous position of the agent.
	 */
	public Point getPreviousPosition()
	{
		if(_positionHistory.size() > 1)
			return _positionHistory.get(1);
		else
			return null;
	}
	
	/**
	 * Gets the current position of the agent. 
	 * @return A point indicating the current position of the agent.
	 */
	public Point getCurrentPosition()
	{
		if(_positionHistory.size() > 0)
			return _positionHistory.get(0);
		else
			return null;
	}
	
	
	public abstract void messageReceived(ACLMessage message);
	
	/**
	 * Adds a new point to the position history, meaning this point is the new current point.
	 * @param position The new current position of the agent.
	 */
	private void addPositionToHistory(Point position)
	{
		//If the position allready is the current position, it doenst need to be added.
		if(position.equals(getCurrentPosition()))
			return;
		
		//Make sure there is room for another item in the list.
		while(_positionHistory.size() >= _positionHistorySize)
		{
			//Remove the last item.
			_positionHistory.remove(_positionHistory.size()-1);
		}
		
		//adds the element to the end of the list.
		_positionHistory.add(position);
		
		//rotates the list with distance 1, which will move the latest element first.
		//and all other elements switch 1 to the right.
		Collections.rotate(_positionHistory, 1);
		
	}
	
	
	/**
	 * Returns a list of positions where the agent can move to in a random order.
	 * @return
	 */
	public List<Point> getRandomMoveablePositions()
	{
		List<Point> moveablePositions = getMoveablePositions();
		
		//shuffles the list.
		Collections.shuffle(moveablePositions, _randomGenerator);
		
	    
	    return moveablePositions;
	}
	
	/**
	 * Returns a set of positions where the agent can move to in a deterministic order.
	 * TODO: add avoid bombs/walls mechanism.
	 * @return
	 */
	public List<Point> getMoveablePositions()
	{
		Point current = getCurrentPosition();
		List<Point> moveablePositions = new ArrayList<Point>();
		
		//add adjacent cells to the list.
		moveablePositions.add(new Point(current.x-1, current.y));
		moveablePositions.add(new Point(current.x, current.y-1));
		moveablePositions.add(new Point(current.x+1, current.y));
		moveablePositions.add(new Point(current.x, current.y+1));
			
		moveablePositions.removeAll(_knownStones);
		moveablePositions.removeAll(_knownAgents);
		
		return moveablePositions;
	}
	
	/**
	 * Moves the agent to the position specified, if its adjacent.
	 * @param newPosition The new position to move too.
	 * @return A boolean indicating whether the move was successful.
	 * TODO: test function
	 */
	public boolean step(Point newPosition)
	{
		Point current = getCurrentPosition();//Environment.getPosition(getLocalName());
		
		if(current.x == newPosition.x)
		{
			if(current.y - newPosition.y == 1)
			{
				return stepNorth();
			}
			else if(current.y - newPosition.y == -1)
			{
				return stepSouth();
			}
			else
			{
				return false;
			}
		}
		else
		{
			if(current.x - newPosition.x == 1)
			{
				return stepWest();
			}
			else if(current.x - newPosition.x == -1)
			{
				return stepEast();
			}
			else
			{
				return false;
			}
		}
		
	}
	
	/**
	 * Moves the agent to a new position.
	 * @param i Indicates the direction (0 = North, 1 = East, 2 = South, 3 = West)
	 * @return Returns a boolean indicating wether the move was succesfull.
	 */
	public boolean step(int i)
	{
		boolean succesfullyMoved = false;
		
		switch(i)
		{
		case 0: succesfullyMoved = Environment.north(getLocalName()); break;
		case 1: succesfullyMoved = Environment.east(getLocalName()); break;
		case 2: succesfullyMoved = Environment.south(getLocalName()); break;
		case 3: succesfullyMoved = Environment.west(getLocalName()); break;
		}
	
		/* the new location is sensed.
		if(succesfullyMoved)
		{
			//retrieve new current location.(this can i.e. be different than expected)
			Point newPosition = Environment.getPosition(getLocalName());
			//Set the position as new current location.
			addPositionToHistory(newPosition);
		}
		*/
		
		return succesfullyMoved;
	}
	
	/**
	 * Moves the agent north.
	 * @return A boolean indicating wether the move was succesfull.
	 */
	public boolean stepNorth()
	{
		return step(0);
	}
	
	/**
	 * Moves the agent east.
	 * @return A boolean indicating whether the move was succesfull.
	 */
	public boolean stepEast()
	{
		return step(1);
	}
	
	/** Moves the agent south.
	 * @return A boolean indicating wether the move was succesfull.
	 */
	public boolean stepSouth()
	{
		return step(2);
	}
	
	/** Moves the agent west.
	 * @return A boolean indicating whether the move was succesfull.
	 */
	public boolean stepWest()
	{
		return step(3);
	}
	
}
