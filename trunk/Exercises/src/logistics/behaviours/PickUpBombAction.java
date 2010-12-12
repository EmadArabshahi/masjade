package logistics.behaviours;

import java.awt.Point;

import logistics.GridWorldAgent;
import gridworld.Environment;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

public class PickUpBombAction extends OneShotBehaviour {

	public static final int PICKED_UP_BOMB = 1;
	public static final int FOUND_NO_BOMB = 2;
	
	private GridWorldAgent _owner;
	private int _endState;

	public PickUpBombAction(GridWorldAgent owner) 
	{
		_owner = owner;
		_endState = -1;
	}

	@Override
	public void action() 
	{
		if (_owner.isOnBomb())
		{
			_owner.bombPickedUp(Environment.takeBomb(_owner.getLocalName()));
			sendMessage(_owner.getCurrentPosition());
			_endState = PICKED_UP_BOMB;
		}
		else
		{
			_owner.bombPickedUp(false);
			_endState = FOUND_NO_BOMB;
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
	private void sendMessage(Point location)
	{
		//Setup agent description.
		DFAgentDescription dfdBomb = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription(); 
	    sd.setType("bomb-pickups"); 
	    sd.setName("bomb-pickups"); 
	    dfdBomb.addServices(sd); 
		
	    //Send messages.
	    try
		{
			DFAgentDescription[] resultBomb = DFService.search(_owner, dfdBomb);
			System.out.println("Receivers for pickUUUUUPPPPP: " + resultBomb.length + "   !!!!!!!!!!!!!!!!!!");
			ACLMessage pickupInform = new ACLMessage(ACLMessage.INFORM);
				
			for (int i = 0; i < resultBomb.length; i++)
			{
				pickupInform.addReceiver(resultBomb[i].getName());
			}
					
			pickupInform.setOntology("bomb-pickups");
			pickupInform.setContent(String.format("%s,%s", location.x, location.y));
			_owner.send(pickupInform);
			
		}
	    catch(Exception e)
	    {
	    	System.out.println(e);
	    }
	    
	}
	
	
	public int onEnd()
	{
		return _endState;
	}
}