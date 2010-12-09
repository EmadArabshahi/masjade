package exerc1;

import exerc1.behaviours.DropBombAction;
import exerc1.behaviours.PickTargetBombAction;
import exerc1.behaviours.PickTargetTrapAction;
import exerc1.behaviours.PickUpBombAction;
import exerc1.behaviours.ProcessBombPositionAction;
import exerc1.behaviours.ProcessDisposerCurrentPositionAction;
import exerc1.behaviours.ProcessDisposerActivitiesBehaviour;
import exerc1.behaviours.ProcessMasterDisposerMessagesAction;
import exerc1.behaviours.ProcessMasterTargetBombRequestAction;
import exerc1.behaviours.ProcessSlaveDisposerMessagesAction;
import exerc1.behaviours.ProcessTargetBombMessageBehaviour;
import exerc1.behaviours.ProcessTargetBombPositionAction;
import exerc1.behaviours.ProcessSlaveTargetBombRequestBehaviour;
import exerc1.behaviours.ProcessTrapPositionAction;
import exerc1.behaviours.SendTargetBombPositionAction;
import jade.core.behaviours.FSMBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class SlaveDisposerAgent extends GridWorldAgent {
	@Override
	protected void setupAgent() {
		registerServices();		
		
		enter(startingPoint, "blue");
		
		FSMBehaviour fsm = new FSMBehaviour();
		
		
		fsm.registerFirstState(new ProcessDisposerActivitiesBehaviour(this), "processActivities");
		fsm.registerState(new ProcessSlaveDisposerMessagesAction(this), "processMessage");
		fsm.registerState(new PickUpBombAction(this), "pickupBomb");
		fsm.registerState(new DropBombAction(this), "dropBomb");
		fsm.registerState(new ProcessTrapPositionAction(this), "processTrapPosition");
		fsm.registerState(new ProcessTargetBombPositionAction(this), "processTargetBombPosition");
		fsm.registerState(new PickTargetTrapAction(this), "pickTargetTrap");
		fsm.registerState(new ProcessSlaveTargetBombRequestBehaviour(this), "processTargetBombRequest");
		
		fsm.registerTransition("processActivities", "processTargetBombRequest", ProcessDisposerActivitiesBehaviour.HAS_NO_TARGET_BOMB);
		fsm.registerTransition("processActivities", "pickTargetTrap", ProcessDisposerActivitiesBehaviour.HAS_NO_TARGET_TRAP);
		fsm.registerTransition("processActivities", "pickupBomb", ProcessDisposerActivitiesBehaviour.IS_ON_BOMB);
		fsm.registerTransition("processActivities", "dropBomb", ProcessDisposerActivitiesBehaviour.IS_ON_TRAP);
		fsm.registerTransition("processActivities", "processMessage", ProcessDisposerActivitiesBehaviour.RECEIVED_MESSAGE);
		
		fsm.registerTransition("processTargetBombRequest", "processActivities", ProcessSlaveTargetBombRequestBehaviour.PROCESSED_TARGET_BOMB_REQUEST);
		
		fsm.registerTransition("processMessage", "processTargetBombPosition", ProcessSlaveDisposerMessagesAction.RECEIVED_TARGET_BOMB_POSITION);
		fsm.registerTransition("processMessage", "processTrapPosition", ProcessSlaveDisposerMessagesAction.RECEIVED_TRAP_POSITION);
		
		fsm.registerTransition("processTrapPosition", "processActivities", ProcessTrapPositionAction.PROCESSED_TRAP_POSITION);
		
		fsm.registerTransition("pickTargetTrap", "processActivities", PickTargetTrapAction.PICKED_TARGET_TRAP);
		
		fsm.registerTransition("pickupBomb", "processActivities", PickUpBombAction.PICKED_UP_BOMB);
		fsm.registerTransition("pickupBomb", "processActivities", PickUpBombAction.FOUND_NO_BOMB);
		
		fsm.registerTransition("dropBomb", "processActivities", DropBombAction.DROPPED_BOMB);
		fsm.registerTransition("dropBomb", "processActivities", DropBombAction.FOUND_NO_TRAP);
		
		addBehaviour(fsm);
	}
	
	private void registerServices()
	{
		// describe agent
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		// describe receive-target-bomb-position service
		ServiceDescription sdReceiveBombPosition = new ServiceDescription();
		sdReceiveBombPosition.setType("receive-target-bomb-position");
		sdReceiveBombPosition.setName("receive-target-bomb-position");
		
		// describe receive-trap-position service
		ServiceDescription sdReceiveTrapPosition = new ServiceDescription();
		sdReceiveTrapPosition.setType("receive-trap-position");
		sdReceiveTrapPosition.setName("receive-trap-position");
		
		// register services with the agent
		dfd.addServices(sdReceiveBombPosition);
		dfd.addServices(sdReceiveTrapPosition);
		
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
}
