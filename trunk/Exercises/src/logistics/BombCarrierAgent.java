package logistics;

import java.awt.Point;

import jade.core.behaviours.FSMBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import logistics.behaviours.*;
import logistics.behaviours.PickUpBombAction;
import logistics.behaviours.WaitForRequestBehaviour;
import logistics.behaviours.walking.ExploreBehaviour;
import logistics.behaviours.walking.WalkAwayFromTrapBehaviour;
import logistics.behaviours.walking.WalkToClosestBombBehaviour;
import logistics.behaviours.walking.*;

public class BombCarrierAgent extends GridWorldAgent
{

	protected Point _targetBombPosition;
	protected boolean _listeningForBombs = true;
	
	protected void setupAgent()
	{
		
		FSMBehaviour fsm = new FSMBehaviour();
		fsm.registerFirstState(new WalkAwayFromTrapBehaviour(this), "walkAway");
		fsm.registerState(new WaitForRequestBehaviour(this), "waitForRequest");	
		fsm.registerState(new WalkToTargetBombBehaviour(this), "walkToBomb");
		fsm.registerState(new PickUpBombAction(this), "pickupBomb");
		fsm.registerState(new WalkToClosestTrapBehaviour(this),"walkToTrap");
		fsm.registerState(new DropBombAction(this), "dropBomb");
		fsm.registerState(new ProcessMessageBehaviour(this, true, ACLMessage.INFORM), "waitforINFORM");
		fsm.registerState(new ProcessMessageBehaviour(this, false, ACLMessage.INFORM), "procesINFORM");
	
	
		fsm.registerTransition("walkAway", "waitForRequest", WalkAwayFromTrapBehaviour.NOT_ON_TRAP);
		fsm.registerTransition("waitForRequest", "waitforINFORM", WaitForRequestBehaviour.REQUEST_RECEIVED_AND_NO_TRAPS);
		fsm.registerTransition("waitForRequest", "walkToBomb", WaitForRequestBehaviour.REQUEST_RECEIVED_AND_KNOWS_TRAPS);
		fsm.registerTransition("waitforINFORM", "walkToBomb", ProcessMessageBehaviour.MESSAGE_RECEIVED);
		
		fsm.registerTransition("walkToBomb", "pickupBomb", WalkToTargetBombBehaviour.POSITION_REACHED);
		
		fsm.registerTransition("pickupBomb", "walkAway", PickUpBombAction.FOUND_NO_BOMB);
		fsm.registerTransition("pickupBomb", "procesINFORM", PickUpBombAction.PICKED_UP_BOMB);
		
		fsm.registerTransition("procesINFORM", "walkToTrap", ProcessMessageBehaviour.MESSAGE_RECEIVED);
		fsm.registerTransition("procesINFORM", "walkToTrap", ProcessMessageBehaviour.NO_MESSAGE_RECEIVED);
		fsm.registerTransition("walkToTrap", "dropBomb", WalkToClosestTrapBehaviour.ON_TRAP);
		
		//Dit moet iets anders worden, geen traps betekend wachten op trap informatie.
		//fsm.registerTransition("walkToTrap", "WaitforINFORM", WalkToClosestTrapBehaviour.NO_TRAPS_FOUND);
		//fsm.registerTransition("WaitforINFORM", "walkToTrap", ProcessMessageBehaviour.MESSAGE_RECEIVED);
		
		fsm.registerTransition("dropBomb", "walkAway", DropBombAction.DROPPED_BOMB);
		//fsm.registerTransition("dropBomb", "walkToTrap", DropBombAction.FOUND_NO_TRAP);
		
		addBehaviour(fsm);
		registerService();
		
	}
	
	
	private void registerService()
	{
		//The agent description
	    DFAgentDescription dfd = new DFAgentDescription(); 
	    dfd.setName(getAID()); 
	    
	    //The bomb-positions service.
	    ServiceDescription sd = new ServiceDescription(); 
	    sd.setType("bomb-positions"); 
	    sd.setName("bomb-positions"); 
	    dfd.addServices(sd); 
	   
	    sd = new ServiceDescription(); 
	    sd.setType("trap-positions"); 
	    sd.setName("trap-positions"); 
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
	
	public void StartListeningForBombRequest()
	{
		if(_listeningForBombs)
			return;
		
		_listeningForBombs = true;
		//The agent description
	    DFAgentDescription dfd = new DFAgentDescription(); 
	    dfd.setName(getAID()); 
	    
	    //The bomb-positions service.
	    ServiceDescription sd = new ServiceDescription(); 
	    sd.setType("bomb-positions"); 
	    sd.setName("bomb-positions"); 
	    dfd.addServices(sd); 
	   
	    sd = new ServiceDescription(); 
	    sd.setType("trap-positions"); 
	    sd.setName("trap-positions"); 
	    dfd.addServices(sd); 
	    
	    try
	    {
	    	DFService.modify(this, dfd);
	    }
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	}
	
	public void StopListeningForBombRequests()
	{
		if(!_listeningForBombs)
			return;
		
		_listeningForBombs = false;
		
		//The agent description
	    DFAgentDescription dfd = new DFAgentDescription(); 
	    dfd.setName(getAID()); 
	    
	    ServiceDescription sd = new ServiceDescription(); 
	    sd.setType("trap-positions"); 
	    sd.setName("trap-positions"); 
	    dfd.addServices(sd); 
	    
	    try
	   {
	    	DFService.modify(this, dfd);
	    }
	    catch(FIPAException e)
	    {
	    	e.printStackTrace();
	    }
	}

	protected void takeDown()
	{
		deregisterService();
	}
	
	public void messageReceived(ACLMessage message)
	{
		if(message.getPerformative() == ACLMessage.INFORM)
		{
			System.out.println("wiehoee inform received?");
			informReceived(message);
		}
		else if(message.getPerformative() == ACLMessage.REQUEST)
		{
			System.out.println("wiehoee request received?");
			requestReceived(message);
		}
	}
	
	public void informReceived(ACLMessage message)
	{
		String[] splitMsg = message.getContent().split(",");
		Point location = new Point();
		location.x = Integer.parseInt(splitMsg[0]);
		location.y = Integer.parseInt(splitMsg[1]);
		this._knownTraps.add(location);
	}
	
	public void requestReceived(ACLMessage message)
	{
		String[] splitMsg = message.getContent().split(",");
		Point location = new Point();
		location.x = Integer.parseInt(splitMsg[0]);
		location.y = Integer.parseInt(splitMsg[1]);
		
		this._targetBombPosition = location;
		StopListeningForBombRequests();
	}
	
	public Point getTargetBombPosition()
	{
		return this._targetBombPosition;
	}
	
}