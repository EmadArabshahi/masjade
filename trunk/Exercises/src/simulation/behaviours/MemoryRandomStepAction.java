package simulation.behaviours;

import java.awt.Point;
import java.util.*; 
import jade.core.behaviours.OneShotBehaviour;

import simulation.agents.GridWorldAgent;
import simulation.environment.Environment;

public class MemoryRandomStepAction extends OneShotBehaviour
{

	private GridWorldAgent _owner;
	
	public MemoryRandomStepAction(GridWorldAgent owner) 
	{
		this._owner = owner;
	}
	
	@Override
	public void action() 
	{
		
		List<Point> moveablePositions = _owner.getRandomMoveablePositions();
		List<Point> visitedPositions = _owner.getPositionHistory();
		
		moveablePositions.removeAll(visitedPositions);
		
		//If there are no moveable positions left, then get new random moveable positions.
		if(moveablePositions.size() == 0)
		{
			moveablePositions = _owner.getRandomMoveablePositions();
		}
		
		//If there are still no positions left, agent is trapped and cant move..
		if(moveablePositions.size() == 0)
		{
			_owner.unableToMove();
			return;
		}
			
		
		if(!_owner.step(moveablePositions.get(0)))
		{	
			Point current = Environment.getPosition(_owner.getLocalName());
		}
	}
	
	
}
