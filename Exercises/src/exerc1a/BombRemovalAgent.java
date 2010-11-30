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
	private Random _randomGenerator;
	
	/** The last position of the agent.
	 * 
	 */
	private Point _lastPosition;
	
	protected void setup()
	{
		_knownBombs = new HashSet<Point>();
		
		long seed = this.getName().hashCode();
		this._randomGenerator = new Random(seed);
		
		_lastPosition = null;
		
		// todo: get starting and trap locations from arguments.
		knownTraps = new ArrayList<Point>();
		knownTraps.add(new Point(0, 0));

		
		//Enter the environment
		Environment.enter(getLocalName(), new Point(5, 5), "blue");
		
		
		//addBehaviour(new BombRemovalBehaviour(this));
		

		addBehaviour(new ExploreBombsBehaviour(this));
		
		
		System.out.println(getLocalName() + " is ready.");
	}

	
	
	public boolean knowsBombs()
	{
		return _knownBombs.size() > 0;
	}
	
	/**
	 * Gets a random position to where the agent can move too. 
	 * The last position of the agent is excluded from the posible moves, except if its the only option.
	 * @return
	 */
	public Point getRandomMoveablePosition()
	{
		//Gets the positions where the agent can move to.
		Set<Point> moveablePositions = getMoveablePositions();
		
		//Removes the last positions from the list, if there are other options.
		//So the agent doesnt move back to its last position.
		if(moveablePositions.size() > 1)
			moveablePositions.remove(getLastPosition());
		
		//Generates a random number between 0 and the number of movable positions.
		int randomNumber = _randomGenerator.nextInt(moveablePositions.size());
		
		Point randomPosition = null;
		
		int i=0;
		for(Point position : moveablePositions)
		{
			if(i==randomNumber)
			{
				randomPosition = position;
				break;
			}
			i++;
		}
		
		return randomPosition;
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
	 * Gets the last position of the agent. This can be null, if the agent just entered.!
	 * @return A point indicating the last position of the agent.
	 */
	public Point getLastPosition()
	{
		return this._lastPosition;
	}
	
	/**
	 * Returns a set of positions where the agent can move to.
	 * TODO: add avoid bombs/walls mechanism.
	 * @return
	 */
	public Set<Point> getMoveablePositions()
	{
		Point current = Environment.getPosition(getLocalName());
		Set<Point> moveablePositions = new HashSet<Point>();
		
		moveablePositions.add(new Point(current.x-1, current.y));
		moveablePositions.add(new Point(current.x, current.y-1));
		moveablePositions.add(new Point(current.x+1, current.y));
		moveablePositions.add(new Point(current.x, current.y+1));
		
		return moveablePositions;
	}
	
	/**
	 * Moves the agent to the position specified, if its adjacent.
	 * @param newPosition The new position to move too.
	 * @return A boolean indicating wether the move was succesfull.
	 */
	public boolean move(Point newPosition)
	{
		Point current = Environment.getPosition(getLocalName());
		
		if(current.x == newPosition.x)
		{
			if(current.y - newPosition.y == 1)
			{
				return moveNorth();
			}
			else if(current.y - newPosition.y == -1)
			{
				return moveSouth();
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
				return moveWest();
			}
			else if(current.x - newPosition.x == -1)
			{
				return moveEast();
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
	public boolean move(int i)
	{
		Point oldPosition = Environment.getPosition(getLocalName());
		boolean succesfullyMoved = false;
		
		switch(i)
		{
		case 0: succesfullyMoved = Environment.north(getLocalName()); break;
		case 1: succesfullyMoved = Environment.east(getLocalName()); break;
		case 2: succesfullyMoved = Environment.south(getLocalName()); break;
		case 3: succesfullyMoved = Environment.west(getLocalName()); break;
		}
	
		if(succesfullyMoved)
			this._lastPosition = oldPosition;
		
		return succesfullyMoved;
	}
	
	/**
	 * Moves the agent north.
	 * @return A boolean indicating wether the move was succesfull.
	 */
	public boolean moveNorth()
	{
		return move(0);
	}
	
	/**
	 * Moves the agent east.
	 * @return A boolean indicating wether the move was succesfull.
	 */
	public boolean moveEast()
	{
		return move(1);
	}
	
	/** Moves the agent south.
	 * @return A boolean indicating wether the move was succesfull.
	 */
	public boolean moveSouth()
	{
		return move(2);
	}
	
	/** Moves the agent west.
	 * @return A boolean indicating wether the move was succesfull.
	 */
	public boolean moveWest()
	{
		return move(3);
	}
	
}
