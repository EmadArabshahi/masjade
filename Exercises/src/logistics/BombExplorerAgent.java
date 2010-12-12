package logistics;

import java.awt.Point;
import java.util.*;

import jade.core.behaviours.FSMBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import logistics.behaviours.DropBombAction;
import logistics.behaviours.PickUpBombAction;
import logistics.behaviours.walking.ExploreAndInform;
import logistics.behaviours.walking.ExploreBehaviour;
import logistics.behaviours.walking.WalkToClosestBombBehaviour;
import logistics.behaviours.walking.WalkToClosestTrapBehaviour;

public class BombExplorerAgent extends GridWorldAgent 
{
	
	public Set<Point> _bombsToBroadCast;
	public Set<Point> _trapsToBroadCast;
	
	protected void setupAgent()
	{
		_bombsToBroadCast = new HashSet<Point>();
		_trapsToBroadCast = new HashSet<Point>();
		
		/*
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
		*/
		
		registerService();
		
		addBehaviour(new ExploreAndInform(this));
	}
	
	private void registerService()
	{
		//The agent description
	    DFAgentDescription dfd = new DFAgentDescription(); 
	    dfd.setName(getAID()); 
	    
	    //The bomb-positions service.
	    ServiceDescription sd = new ServiceDescription(); 
	    sd.setType("bomb-pickups"); 
	    sd.setName("bomb-pickups"); 
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
	/**
	 * Deregister the service of this agent.
	 */
	private void deregisterService()
	{
		try 
		{ 
			DFService.deregister(this); 
		}  
		catch (FIPAException fe) 
		{ 
			fe.printStackTrace(); 
		}
	}
	
	protected void takeDown()
	{
		deregisterService();
	}
	
	@Override
	public void bombsSensed(Set<Point> bombPositions) 
	{	
		bombPositions.removeAll(_knownBombs);
		_bombsToBroadCast.addAll(bombPositions);
		_knownBombs.addAll(bombPositions);	
	}
	
	@Override
	public void trapsSensed(Set<Point> trapPositions)
	{
		trapPositions.removeAll(_knownTraps);
		_trapsToBroadCast.addAll(trapPositions);
		_knownTraps.addAll(trapPositions);
	}
	
	public Set<Point> getBombsToBroadcast()
	{
		return _bombsToBroadCast;
	}
	
	public void messageReceived(ACLMessage message)
	{
		if(message.getPerformative() == ACLMessage.INFORM)
		{
			String[] splitMsg = message.getContent().split(",");
			Point location = new Point();
			location.x = Integer.parseInt(splitMsg[0]);
			location.y = Integer.parseInt(splitMsg[1]);
			
			this._knownBombs.remove(location);
		}
	}
	
	
	public Set<Point> getTrapsToBroadcast()
	{
		return _trapsToBroadCast;
	}
	
	public void emptyTrapsToBroadCast()
	{
		_trapsToBroadCast.clear();
	}
}
