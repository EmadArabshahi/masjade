package exerc1a;

import java.awt.Point;
import java.util.*; 
import gridworld.Environment;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.Agent;

public class MemoryRandomWalkBehaviour extends CyclicBehaviour
{

	private BombRemovalAgent _owner;
	
	public MemoryRandomWalkBehaviour(BombRemovalAgent owner) 
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
