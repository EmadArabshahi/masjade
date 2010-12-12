package logistics.behaviours.walking;

import gridworld.Environment;

import java.awt.Point;
import java.util.*; 
import jade.core.behaviours.OneShotBehaviour;
import logistics.GridWorldAgent;

public class StepToPositionAction extends OneShotBehaviour
{

	private GridWorldAgent _owner;
	
	private Point _targetPosition;
	
	public StepToPositionAction(GridWorldAgent owner, Point targetPosition) 
	{
		this._owner = owner;
		this._targetPosition = targetPosition;
	}
	
	@Override
	public void action() 
	{
		System.out.println("StepToPosition in action");
		
		List<Point> moveablePositions = _owner.getRandomMoveablePositions();
				
		//If there are still no positions left, agent is trapped and cant move..
		if(moveablePositions.size() == 0)
		{
			System.out.println("unable to move.!!");
			return;
		}
			
		Point minimalDistancePoint = moveablePositions.get(0);
		double minimalDistance = _targetPosition.distance(minimalDistancePoint);
		
		for(int i=1; i<moveablePositions.size(); i++)
		{
			Point position = moveablePositions.get(i);
			double distance = _targetPosition.distance(position);
			
			if(distance < minimalDistance)
			{
				minimalDistance = distance;
				minimalDistancePoint = position;
			}
		}
		
		
		if(!_owner.step(minimalDistancePoint))
		{	
			Point current = Environment.getPosition(_owner.getLocalName());
			System.out.println("Agent tried to move from" + current + " to" + minimalDistancePoint + " thinking it was at" + _owner.getCurrentPosition() + "but was unable too.!!!!");
		}
	}
	
	
}
