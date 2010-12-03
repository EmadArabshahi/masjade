package exerc1;

import jade.core.behaviours.*;
import java.awt.Point;

import javax.print.attribute.standard.Finishings;

import exerc1.behaviours.*;

public class BombRemovalAgent extends GridWorldAgent
{
	
	
	protected void addBehaviours()
	{
		FSMBehaviour fsm =new FSMBehaviour();
		fsm.registerFirstState(new ExploreBombsBehaviour(this), "explore");
		fsm.registerState(new WalkToClosestBombBehaviour(this), "walkToBomb");
		fsm.registerState(new PickupBombAction(this), "pickupBomb");
		fsm.registerState(new WalkToClosestTrapBehaviour(this),"walkToTrap");
		fsm.registerState(new DropBombAction(this), "dropBomb");
		fsm.registerTransition("explore", "walkToBomb", ExploreBombsBehaviour.BOMB_AND_TRAP_FOUND);
		fsm.registerTransition("walkToBomb", "pickupBomb", WalkToClosestBombBehaviour.ON_BOMB);
		fsm.registerTransition("walkToBomb", "explore", WalkToClosestBombBehaviour.NO_BOMBS_FOUND);
		fsm.registerTransition("pickupBomb", "walkToTrap", PickupBombAction.AGENT_HAS_BOMB);
		fsm.registerTransition("pickupBomb", "walkToBomb", PickupBombAction.AGENT_HAS_NO_BOMB);
		fsm.registerTransition("walkToTrap", "dropBomb", WalkToClosestTrapBehaviour.ON_TRAP);
		fsm.registerTransition("walkToTrap", "explore", WalkToClosestTrapBehaviour.NO_TRAPS_FOUND);
		fsm.registerTransition("dropBomb", "walkToBomb", DropBombAction.AGENT_HAS_NO_BOMB);
		fsm.registerTransition("dropBomb", "walkToTrap", DropBombAction.AGENT_HAS_BOMB);
		
		addBehaviour(fsm);
		
		/*
		SequentialBehaviour seqBehaviour = new SequentialBehaviour(this);
		seqBehaviour.addSubBehaviour(new ExploreBombsBehaviour(this));
		seqBehaviour.addSubBehaviour(new WalkToClosestBombBehaviour(this));
		seqBehaviour.addSubBehaviour(new PickupBombAction(this));
		seqBehaviour.addSubBehaviour(new WalkToClosestTrapBehaviour(this));
		seqBehaviour.addSubBehaviour(new DropBombAction(this));
		//seqBehaviour.addSubBehaviour(new ResetBehaviour(seqBehaviour));
		CyclicBehaviour cb=new CyclicBehaviour() {
			
			@Override
			public void action() {
				// TODO Auto-generated method stub
				
			}
		};
		*/
		
	}
}
