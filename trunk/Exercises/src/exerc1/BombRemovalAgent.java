package exerc1;

import jade.core.behaviours.*;
import exerc1.behaviours.*;

public class BombRemovalAgent extends GridWorldAgent
{
	protected void setupAgent()
	{
		enter(startingPoint, "blue");
		
		FSMBehaviour fsm = new FSMBehaviour();
		fsm.registerFirstState(new ExploreBehaviour(this), "explore");
		fsm.registerState(new WalkToClosestBombBehaviour(this), "walkToBomb");
		fsm.registerState(new PickUpBombAction(this), "pickupBomb");
		fsm.registerState(new WalkToClosestTrapBehaviour(this),"walkToTrap");
		fsm.registerState(new DropBombAction(this), "dropBomb");
		fsm.registerTransition("explore", "walkToBomb", ExploreBehaviour.BOMB_AND_TRAP_FOUND);
		fsm.registerTransition("walkToBomb", "pickupBomb", WalkToClosestBombBehaviour.ON_BOMB);
		fsm.registerTransition("walkToBomb", "explore", WalkToClosestBombBehaviour.NO_BOMBS_FOUND);
		fsm.registerTransition("pickupBomb", "walkToTrap", PickUpBombAction.PICKED_UP_BOMB);
		fsm.registerTransition("pickupBomb", "walkToBomb", PickUpBombAction.FOUND_NO_BOMB);
		fsm.registerTransition("walkToTrap", "dropBomb", WalkToClosestTrapBehaviour.ON_TRAP);
		fsm.registerTransition("walkToTrap", "explore", WalkToClosestTrapBehaviour.NO_TRAPS_FOUND);
		fsm.registerTransition("dropBomb", "walkToBomb", DropBombAction.DROPPED_BOMB);
		fsm.registerTransition("dropBomb", "walkToTrap", DropBombAction.FOUND_NO_TRAP);
		
		addBehaviour(fsm);
		
		//SequentialBehaviour seqBehaviour = new SequentialBehaviour(this);
		//seqBehaviour.addSubBehaviour(new ExploreBehaviour(this));
		//seqBehaviour.addSubBehaviour(new WalkToClosestBombBehaviour(this));
		//seqBehaviour.addSubBehaviour(new PickUpBombAction(this));
		//seqBehaviour.addSubBehaviour(new WalkToClosestTrapBehaviour(this));
		//seqBehaviour.addSubBehaviour(new DropBombAction(this));
		//seqBehaviour.addSubBehaviour(new ResetBehaviour(seqBehaviour));
	}
}
