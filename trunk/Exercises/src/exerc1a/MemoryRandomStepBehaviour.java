package exerc1a;

import java.awt.Point;
import java.util.*; 
import jade.core.behaviours.OneShotBehaviour;

public class MemoryRandomStepBehaviour extends OneShotBehaviour
{

	private BombRemovalAgent _owner;
	
	public MemoryRandomStepBehaviour(BombRemovalAgent owner) 
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
		}
			
		
		if(!_owner.step(moveablePositions.get(0)))
			System.out.println("Agent tried to move but was unable too.!!!!");
	}
	
	
}
