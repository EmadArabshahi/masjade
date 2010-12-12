package exerc1;

import java.awt.Point;
import java.util.ArrayList;

import exerc1.behaviours.ProcessSenserBombPickedUpAction;
import exerc1.behaviours.SendNewPositionsAction;
import jade.core.behaviours.FSMBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import logistics.GridWorldAgent;
import logistics.behaviours.ExploreBehaviour;

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
		fsm.registerState(new SendNewPositionsAction(this), "informDisposers");
		fsm.registerState(new ProcessSenserBombPickedUpAction(this), "receiveBombPickedUp");
		
		fsm.registerTransition("explore", "receiveBombPickedUp", ExploreBehaviour.BOMB_AND_TRAP_FOUND);
		fsm.registerTransition("receiveBombPickedUp", "informDisposers", ProcessSenserBombPickedUpAction.RECEIVE_DONE);
		fsm.registerTransition("informDisposers", "explore", SendNewPositionsAction.BROADCAST_DONE);
		
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
		sd.setType("receive-bomb-picked-up");
		sd.setName("receive-bomb-picked-up");
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
