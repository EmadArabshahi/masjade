package exerc1;

import java.awt.Point;
import java.util.ArrayList;

import exerc1.behaviours.BroadcastNewPositionsAction;
import exerc1.behaviours.DropBombAction;
import exerc1.behaviours.ExploreBehaviour;
import exerc1.behaviours.PickupBombAction_Old;
import exerc1.behaviours.ReceiveRemovedBombsAction;
import exerc1.behaviours.WalkToClosestBombBehaviour;
import exerc1.behaviours.WalkToClosestTrapBehaviour;
import jade.core.Agent;
import jade.core.behaviours.FSMBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class SensingAgent extends GridWorldAgent 
{
	// contain the bombs and traps that have already been broadcasted
	// to the disposer agents.
	public ArrayList<Point> broadcastedTrapPositions;
	public ArrayList<Point> broadcastedBombPositions;
	
	@Override
	protected void setupAgent() {
		enter(startingPoint, "red");
		
		registerService();
		
		broadcastedTrapPositions = new ArrayList<Point>();
		broadcastedBombPositions = new ArrayList<Point>();
		
		FSMBehaviour fsm = new FSMBehaviour();
		
		fsm.registerFirstState(new ExploreBehaviour(this), "explore");
		fsm.registerState(new BroadcastNewPositionsAction(this), "informDisposers");
		fsm.registerState(new ReceiveRemovedBombsAction(this), "receiveClearedBombs");
		
		fsm.registerTransition("explore", "receiveClearedBombs", ExploreBehaviour.BOMB_AND_TRAP_FOUND);
		fsm.registerTransition("receiveClearedBombs", "informDisposers", ReceiveRemovedBombsAction.RECEIVE_DONE);
		fsm.registerTransition("informDisposers", "explore", BroadcastNewPositionsAction.BROADCAST_DONE);
		
		addBehaviour(fsm);
	}
	
	/**
	 * setup the service for receiving information
 	 * about with bombs are cleared by other agents.
	 */
	private void registerService()
	{ 
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("bomb-managing");
		sd.setName("bomb-managing");
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
