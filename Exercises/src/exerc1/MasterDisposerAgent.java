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

import logistics.GridWorldAgent;
import logistics.behaviours.DropBombAction;
import logistics.behaviours.WalkAwayFromTrapBehaviour;

import exerc1.behaviours.PickTargetBombAction;
import exerc1.behaviours.PickTargetTrapAction;
import exerc1.behaviours.PickUpBombAction;
import exerc1.behaviours.ProcessBombPositionAction;
import exerc1.behaviours.ProcessDisposerCurrentPositionAction;
import exerc1.behaviours.ProcessDisposerActivitiesBehaviour;
import exerc1.behaviours.ProcessMasterBombPickedUpAction;
import exerc1.behaviours.ProcessMasterDisposerMessagesAction;
import exerc1.behaviours.ProcessMasterTargetBombRequestAction;
import exerc1.behaviours.ProcessTrapPositionAction;

/**
 * This agent is responsible for disposing bombs as well as informing slave disposers
 * about the bombs they have to dispose of.
 *
 */
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
		fsm.registerState(new ProcessMasterBombPickedUpAction(this), "processBombPickedUp");
		fsm.registerState(new WalkAwayFromTrapBehaviour(this), "walkAwayFromTrap");
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
		fsm.registerTransition("processMessage", "processBombPickedUp", ProcessMasterDisposerMessagesAction.RECEIVED_BOMB_PICKED_UP);
		
		// Once the new bomb position is stored, the agent will return to its disposing activities.
		fsm.registerTransition("processBombPosition", "processActivities", ProcessBombPositionAction.PROCESSED_BOMB_POSITION);
		// Once the new trap position is stored, the agent will return to its disposing activities.
		fsm.registerTransition("processTrapPosition", "processActivities", ProcessTrapPositionAction.PROCESSED_TRAP_POSITION);
		// Once the new position of the sending disposer is stored, the agent will return to its disposing activities.		
		fsm.registerTransition("processDisposerCurrentPosition", "processActivities", ProcessDisposerCurrentPositionAction.PROCESSED_DISPOSER_CURRENT_POSITION);
		// The agent receives a message telling that the sender requires a new target bomb. It responds with a new target.
		fsm.registerTransition("processTargetBombRequest", "processActivities", ProcessMasterTargetBombRequestAction.PROCESSED_TARGET_BOMB_REQUEST);
		fsm.registerTransition("processBombPickedUp", "processActivities", ProcessMasterBombPickedUpAction.PROCESSED_BOMB_PICKED_UP);
		
		// If the agent has successfully picked up a bomb, it returns to its activities.
		fsm.registerTransition("pickupBomb", "processActivities", PickUpBombAction.PICKED_UP_BOMB);
		// If the agent is unable to pick up a bomb here, it will choose a new target.
		fsm.registerTransition("pickupBomb", "pickTargetBomb", PickUpBombAction.FOUND_NO_BOMB);
		
		
		//If the agent has successfully dropped a bomb into a trap, it walks away from the trap.
		fsm.registerTransition("dropBomb", "walkAwayFromTrap", DropBombAction.DROPPED_BOMB);
		// If the agent tries to drop the bomb, but there's no trap it picks a new target trap.
		fsm.registerTransition("dropBomb", "pickTargetTrap", DropBombAction.FOUND_NO_TRAP);
		
		//If the agent walked away from trap, start over.
		fsm.registerTransition("walkAwayFromTrap", "processActivities", WalkAwayFromTrapBehaviour.NOT_ON_TRAP);
		
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
		
		// describe receive-target-bomb-request service
		ServiceDescription sdReceiveTargetBombRequest = new ServiceDescription();
		sdReceiveTargetBombRequest.setType("receive-target-bomb-request");
		sdReceiveTargetBombRequest.setName("receive-target-bomb-request");
		
		// describe receive-target-bomb-request service
		ServiceDescription sdReceiveBombPickedUp = new ServiceDescription();
		sdReceiveBombPickedUp.setType("receive-bomb-picked-up");
		sdReceiveBombPickedUp.setName("receive-bomb-picked-up");
		
		// register services with the agent
		dfd.addServices(sdReceiveBombPosition);
		dfd.addServices(sdReceiveTrapPosition);
		dfd.addServices(sdReceiveTargetBombRequest);
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
