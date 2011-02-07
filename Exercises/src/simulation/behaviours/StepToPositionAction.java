package simulation.behaviours;

import simulation.environment.Environment;

import java.awt.Point;
import java.util.*; 
import jade.core.behaviours.OneShotBehaviour;
import simulation.agents.GridWorldAgent;

public class StepToPositionAction extends OneShotBehaviour
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2051319736964732628L;

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
		List<Point> moveablePositions = _owner.getRandomMoveablePositions();
				
		//If there are still no positions left, agent is trapped and cant move..
		if(moveablePositions.size() == 0)
		{
			_owner.unableToMove();
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
			
		}
	}
	
	
}
