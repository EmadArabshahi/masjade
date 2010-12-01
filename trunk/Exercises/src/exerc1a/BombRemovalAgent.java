package exerc1a;
import java.awt.Point;

import java.util.*;



import gridworld.Environment;
import jade.core.Agent;

public class BombRemovalAgent extends Agent 
{
	public boolean hasBomb;
	public Point targetBombLocation;
	public Point targetTrapLocation;
	public ArrayList<Point> knownTraps;
	
	/** A set to hold the known bomb locations.
	 * 
	 */
	private Set<Point> _knownBombs;
	
	/** A random number generator.
	 * 
	 */
	private static Random _randomGenerator;
	
	/** The last positions of the agent.
	 * 
	 */
	private List<Point> _positionHistory;
	
	/**
	 * The number of positions to remember in the positionhistory.
	 */
	private int _positionHistorySize;
	
	
	protected void setup()
	{
		_knownBombs = new HashSet<Point>();
		
		long seed = this.getName().hashCode();
		this._randomGenerator = new Random(seed);
		
		//set the history size, should be read from arguments.
		_positionHistorySize = 100;
		//construct empty list, with _positionHistorySize capacity.
		_positionHistory = new ArrayList<Point>(_positionHistorySize);
		
		// todo: get starting and trap locations from arguments.
		knownTraps = new ArrayList<Point>();
		knownTraps.add(new Point(0, 0));

		
		//Enter the environment
		Environment.enter(getLocalName(), new Point(5, 5), "blue");
		
		//Sets the current location.
		addPositionToHistory(Environment.getPosition(getLocalName()));
		
		//addBehaviour(new BombRemovalBehaviour(this));
		

		addBehaviour(new ExploreBombsBehaviour(this));
		
		
		System.out.println(getLocalName() + " is ready.");
	}
	
	
	public boolean knowsBombs()
	{
		return _knownBombs.size() > 0;
	}
	
	
	/**
	 * This method is called by a behaviour to pass the bombs that are sensed.
	 * @param bombPositions A set with the locations of the bombs that are sensed.
	 */
	public void bombsSensed(Set<Point> bombPositions) 
	{
		_knownBombs.addAll(bombPositions);
	}
	
	/**
	 * This method is called by a behaviour to pass the stones that are sensed.
	 * @param stonesPositions A set with the locations of the stones that are sensed.
	 */
	public void stonesSensed(Set<Point> stonesPositions)
	{
		if(stonesPositions.size() > 0)
			System.out.println(stonesPositions + " is where stones were found!!!!");
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
	
	
	/**
	 * Adds a new point to the position history, meaning this point is the new current point.
	 * @param position The new current position of the agent.
	 */
	private void addPositionToHistory(Point position)
	{
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
		Point current = Environment.getPosition(getLocalName());
		
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
	
		if(succesfullyMoved)
		{
			//retrieve new current location.(this can i.e. be different than expected)
			Point newPosition = Environment.getPosition(getLocalName());
			//Set the position as new current location.
			addPositionToHistory(newPosition);
		}
		
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
