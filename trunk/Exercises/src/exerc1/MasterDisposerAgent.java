package exerc1;

import jade.core.AID;
import jade.core.behaviours.FSMBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exerc1.behaviours.BroadcastClearedBombLocationsAction;
import exerc1.behaviours.BroadcastTargetBombAction;
import exerc1.behaviours.DropBombAction;
import exerc1.behaviours.PickTargetBombAction;
import exerc1.behaviours.PickTargetTrapAction;
import exerc1.behaviours.PickUpBombAction;
import exerc1.behaviours.ProcessBombPositionAction;
import exerc1.behaviours.ProcessDisposerCurrentPositionAction;
import exerc1.behaviours.ProcessDisposerActivitiesBehaviour;
import exerc1.behaviours.ProcessMasterDisposerMessagesAction;
import exerc1.behaviours.ProcessMasterTargetBombRequestAction;
import exerc1.behaviours.ProcessTrapPositionAction;
import exerc1.behaviours.ReceivePositionBehaviour;
import exerc1.behaviours.ReceiveTargetBombAction;
import exerc1.behaviours.SendTargetBombPositionAction;
import exerc1.behaviours.WalkToClosestBombBehaviour;
import exerc1.behaviours.WalkToClosestTrapBehaviour;

public class MasterDisposerAgent extends GridWorldAgent
{
	public HashMap<AID, Point> disposerTargets;
	public HashMap<AID, Point> lastKnownDisposerPositions;
	public ArrayList<ACLMessage> unrepliedTargetBombRequests;
	
	public AID currentDisposer;
	
	@Override
	protected void setupAgent() {
		disposerTargets = new HashMap<AID, Point>();
		lastKnownDisposerPositions = new HashMap<AID, Point>();
		
		unrepliedTargetBombRequests = new ArrayList<ACLMessage>();
		
		registerServices();
		
		enter(startingPoint, "green");
		
		FSMBehaviour fsm = new FSMBehaviour();
		
		fsm.registerFirstState(new ProcessDisposerActivitiesBehaviour(this), "processActivities");
		fsm.registerState(new ProcessMasterDisposerMessagesAction(this), "processMessage");
		fsm.registerState(new ProcessBombPositionAction(this), "processBombPosition");
		fsm.registerState(new ProcessTrapPositionAction(this), "processTrapPosition");
		fsm.registerState(new ProcessDisposerCurrentPositionAction(this), "processDisposerCurrentPosition");
		fsm.registerState(new ProcessMasterTargetBombRequestAction(this), "processTargetBombRequest");
		fsm.registerState(new PickUpBombAction(this), "pickupBomb");
		fsm.registerState(new DropBombAction(this), "dropBomb");
		fsm.registerState(new PickTargetBombAction(this), "pickTargetBomb");
		fsm.registerState(new PickTargetTrapAction(this), "pickTargetTrap");
		
		// Processing activities means that the disposer will do it's own disposing work (walk bomb, 
		// pick it up, walk to trap, drop bomb) while it keeps listening for incoming messages.
		
		// If the agent is trying to get a bomb, but has no target it picks one.
		fsm.registerTransition("processActivities", "pickTargetBomb", ProcessDisposerActivitiesBehaviour.HAS_NO_TARGET_BOMB);
		// If the agent is trying to get to a trap, but has no target it picks one.
		fsm.registerTransition("processActivities", "pickTargetTrap", ProcessDisposerActivitiesBehaviour.HAS_NO_TARGET_TRAP);
		// The agent is on a bomb and needs to pick it up.
		fsm.registerTransition("processActivities", "pickupBomb", ProcessDisposerActivitiesBehaviour.IS_ON_BOMB);
		// The agent is on a trap and needs to drop the bomb. (the behaviour makes sure that 
		// the agent has a bomb, otherwise it will not return ON_TRAP.
		fsm.registerTransition("processActivities", "dropBomb", ProcessDisposerActivitiesBehaviour.IS_ON_TRAP);
		// The agent has received a message. It stops with its current activities and processes the message.
		fsm.registerTransition("processActivities", "processMessage", ProcessDisposerActivitiesBehaviour.RECEIVED_MESSAGE);
		
		// If the agent receives a messages it determines what kind of message it is and moves to the
		// appropriate state to process its contents.
		fsm.registerTransition("processMessage", "processBombPosition", ProcessMasterDisposerMessagesAction.RECEIVED_BOMB_POSITION);
		fsm.registerTransition("processMessage", "processTrapPosition", ProcessMasterDisposerMessagesAction.RECEIVED_TRAP_POSITION);
		fsm.registerTransition("processMessage", "processDisposerCurrentPosition", ProcessMasterDisposerMessagesAction.RECEIVED_DISPOSER_CURRENT_POSITION);
		fsm.registerTransition("processMessage", "processTargetBombRequest", ProcessMasterDisposerMessagesAction.RECEIVED_TARGET_BOMB_REQUEST);
		
		// Once the new bomb position is stored, the agent will return to its disposing activities.
		fsm.registerTransition("processBombPosition", "processActivities", ProcessBombPositionAction.PROCESSED_BOMB_POSITION);
		// Once the new trap position is stored, the agent will return to its disposing activities.
		fsm.registerTransition("processTrapPosition", "processActivities", ProcessTrapPositionAction.PROCESSED_TRAP_POSITION);
		// Once the new position of the sending disposer is stored, the agent will return to its disposing activities.		
		fsm.registerTransition("processDisposerCurrentPosition", "processActivities", ProcessDisposerCurrentPositionAction.PROCESSED_DISPOSER_CURRENT_POSITION);
		// The agent receives a message telling that the sender has picked up his target. The agent will now
		// distribute a new target to the sender.
		fsm.registerTransition("processTargetBombRequest", "processActivities", ProcessMasterTargetBombRequestAction.PROCESSED_TARGET_BOMB_REQUEST);
		
		// If the agent has successfully picked up a bomb, it returns to its activities.
		fsm.registerTransition("pickupBomb", "processActivities", PickUpBombAction.PICKED_UP_BOMB);
		// If the agent is unable to pick up a bomb here, it will choose a new target.
		fsm.registerTransition("pickupBomb", "pickTargetBomb", PickUpBombAction.FOUND_NO_BOMB);
		
		// If the agent has successfully dropped a bomb into a trap.
		fsm.registerTransition("dropBomb", "processActivities", DropBombAction.DROPPED_BOMB);
		// If the agent tries to drop the bomb, but there's no trap it picks a new target trap.
		fsm.registerTransition("dropBomb", "pickTargetTrap", DropBombAction.FOUND_NO_TRAP);
		
		// The agent chooses a new target bomb and resumes its activities. It could be so that there's no target bomb.
		// Which means the agent will just continue to listen for incoming messages.
		fsm.registerTransition("pickTargetBomb", "processActivities", PickTargetBombAction.PICKED_TARGET_BOMB);
		// The agent chooses a new target trap and resumes its activities. It could be so that there's no target trap.
		// Which means the agent will just continue to listen for incoming messages.
		fsm.registerTransition("pickTargetTrap", "processActivities", PickTargetTrapAction.PICKED_TARGET_TRAP);
		
		addBehaviour(fsm);
	}
	
	private void registerServices()
	{
		// describe agent
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		// describe receive-bomb-position service
		ServiceDescription sdReceiveBombPosition = new ServiceDescription();
		sdReceiveBombPosition.setType("receive-bomb-position");
		sdReceiveBombPosition.setName("receive-bomb-position");
		
		// describe receive-trap-position service
		ServiceDescription sdReceiveTrapPosition = new ServiceDescription();
		sdReceiveTrapPosition.setType("receive-trap-position");
		sdReceiveTrapPosition.setName("receive-trap-position");
		
		// describe receive-bomb-picked-up service
		ServiceDescription sdReceiveBombPickedUp = new ServiceDescription();
		sdReceiveBombPickedUp.setType("receive-target-bomb-request");
		sdReceiveBombPickedUp.setName("receive-target-bomb-request");
		
		// register services with the agent
		dfd.addServices(sdReceiveBombPosition);
		dfd.addServices(sdReceiveTrapPosition);
		dfd.addServices(sdReceiveBombPickedUp);
		
		// register the agent with the DF
		try
		{
			DFService.register(this, dfd);
		}
		catch (FIPAException fe)
		{
			fe.printStackTrace();
		}
	}

	public Point getNewBombTarget() {
		System.out.println("checking for new target!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		Point newTarget = null;
		for (Point bomb : getKnownBombs()){
			if ((targetBombLocation == null || !targetBombLocation.equals(bomb)) &&
				!disposerTargets.containsValue(bomb))
			{
				newTarget = bomb;
			}
		}
		return newTarget;
	}
}
