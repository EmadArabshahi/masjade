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
			System.out.println("unable to move.!!");
			return;
		}
			
		
		if(!_owner.step(moveablePositions.get(0)))
		{	
			Point current = Environment.getPosition(_owner.getLocalName());
			System.out.println("Agent tried to move from" + current + " to" + moveablePositions.get(0) + " thinking it was at" + _owner.getCurrentPosition() + "but was unable too.!!!!");
		}
	}
	
	
}
