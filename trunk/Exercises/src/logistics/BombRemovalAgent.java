package logistics;

import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import logistics.behaviours.*;
import logistics.behaviours.walking.ExploreBehaviour;
import logistics.behaviours.walking.WalkToClosestBombBehaviour;
import logistics.behaviours.walking.WalkToClosestTrapBehaviour;

public class BombRemovalAgent extends GridWorldAgent
{
	public void messageReceived(ACLMessage message)
	{};
	
	protected void setupAgent()
	{
	 
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
	}
}
