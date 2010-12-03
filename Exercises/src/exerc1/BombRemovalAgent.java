package exerc1;

import jade.core.behaviours.SequentialBehaviour;

import java.awt.Point;

import exerc1.behaviours.*;
import exerc1.behaviours.ExploreBombsBehaviour;
import exerc1.behaviours.WalkToClosestBombBehaviour;
import exerc1.behaviours.WalkToPositionBehaviour;

public class BombRemovalAgent extends GridWorldAgent
{
	protected void addBehaviours()
	{
		//addBehaviour(new BombRemovalBehaviour(this));
		SequentialBehaviour mainBehaviour = new SequentialBehaviour(this);
		mainBehaviour.addSubBehaviour(new ExploreBombsBehaviour(this));
		mainBehaviour.addSubBehaviour(new WalkToClosestBombBehaviour(this));
		mainBehaviour.addSubBehaviour(new PickupBombAction(this));
		mainBehaviour.addSubBehaviour(new WalkToClosestTrapBehaviour(this));
		
		addBehaviour(mainBehaviour);
	}
}
