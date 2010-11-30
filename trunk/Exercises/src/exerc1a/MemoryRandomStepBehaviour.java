package exerc1a;

import java.awt.Point;
import java.util.*; 
import gridworld.Environment;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.Agent;

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
		Point randomPosition = _owner.getRandomMoveablePosition();
		
		if(randomPosition == null)
			System.out.println("NextRandomPosition is null wtf.");
		
		if(_owner.move(randomPosition));
	}
	
	
}
