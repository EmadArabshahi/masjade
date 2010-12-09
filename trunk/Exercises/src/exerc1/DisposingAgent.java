package exerc1;

import java.awt.Point;

import exerc1.behaviours.BroadcastClearedBombLocationsAction;
import exerc1.behaviours.BroadcastNewPositionsAction;
import exerc1.behaviours.BroadcastTargetBombAction;
import exerc1.behaviours.DropBombAction;
import exerc1.behaviours.ExploreBehaviour;
import exerc1.behaviours.PickTargetBombAction;
import exerc1.behaviours.PickupBombAction_Old;
import exerc1.behaviours.ReceivePositionBehaviour;
import exerc1.behaviours.ReceiveTargetBombAction;
import exerc1.behaviours.WalkToClosestBombBehaviour;
import exerc1.behaviours.WalkToClosestTrapBehaviour;

import jade.core.behaviours.FSMBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class DisposingAgent extends GridWorldAgent
{
	@Override
	protected void setupAgent() {
		enter(startingPoint, "blue");
		
		registerService();
		
		FSMBehaviour fsm = new FSMBehaviour();
		
		fsm.registerFirstState(new ReceivePositionBehaviour(this), "receivePositions");
		fsm.registerState(new WalkToClosestBombBehaviour(this), "walkToBomb");
		fsm.registerState(new PickupBombAction_Old(this), "pickupBomb");
		fsm.registerState(new BroadcastClearedBombLocationsAction(this), "sendClearedPositions");
		fsm.registerState(new WalkToClosestTrapBehaviour(this),"walkToTrap");
		fsm.registerState(new DropBombAction(this), "dropBomb");
		fsm.registerState(new BroadcastTargetBombAction(this), "informTarget");
		fsm.registerState(new PickTargetBombAction(this), "targetBomb");
		fsm.registerState(new ReceiveTargetBombAction(this), "receiveTarget");
		
		fsm.registerTransition("receivePositions", "receiveTarget", ReceivePositionBehaviour.BOMB_AND_TRAP_KNOWN);
		fsm.registerTransition("receiveTarget", "targetBomb", ReceiveTargetBombAction.RECEIVE_DONE);
		//fsm.registerTransition("targetBomb", "informTarget", PickTargetBombAction.AGENT_HAS_TARGET);
		fsm.registerTransition("informTarget", "walkToBomb", BroadcastTargetBombAction.BROADCAST_DONE);
		fsm.registerTransition("walkToBomb", "sendClearedPositions", WalkToClosestBombBehaviour.ON_BOMB);
		fsm.registerTransition("sendClearedPositions", "pickupBomb", BroadcastClearedBombLocationsAction.BROADCAST_DONE);
		fsm.registerTransition("walkToBomb", "receivePositions", WalkToClosestBombBehaviour.NO_BOMBS_FOUND);
		fsm.registerTransition("pickupBomb", "walkToTrap", PickupBombAction_Old.AGENT_HAS_BOMB);
		fsm.registerTransition("pickupBomb", "walkToBomb", PickupBombAction_Old.AGENT_HAS_NO_BOMB);
		fsm.registerTransition("walkToTrap", "dropBomb", WalkToClosestTrapBehaviour.ON_TRAP);
		fsm.registerTransition("walkToTrap", "receivePositions", WalkToClosestTrapBehaviour.NO_TRAPS_FOUND);
		//fsm.registerTransition("dropBomb", "walkToBomb", DropBombAction.AGENT_HAS_NO_BOMB);
		//fsm.registerTransition("dropBomb", "walkToTrap", DropBombAction.AGENT_HAS_BOMB);
		
		addBehaviour(fsm);
	}
	
	/**
	 * Register the agent with the DF, so it
	 * other agents can sent it info about bomb
	 * and trap locations.
	 */
	private void registerService()
	{
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("bomb-disposing");
		sd.setName("bomb-disposing");
		dfd.addServices(sd);
		try
		{
			DFService.register(this, dfd);
		}
		catch (FIPAException fe)
		{
			fe.printStackTrace();
		}
	}
}