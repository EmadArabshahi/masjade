package logistics.behaviours;

import java.awt.Point;

import gridworld.Environment;
import jade.core.behaviours.*;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import logistics.BombExplorerAgent;
import logistics.GridWorldAgent;


public class InformPickupAction extends OneShotBehaviour
{
	private BombExplorerAgent _owner;
	
	public InformPickupAction(BombExplorerAgent owner, Point position)
	{
		this._owner = owner;
	}

	@Override
	public void action() 
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
			for (Point location : _owner.getTrapsToBroadcast())
			{
				System.out.println("Receivers: " + resultBomb.length + "   !!!!!!!!!!!!!!!!!!");
				ACLMessage bombsInform = new ACLMessage(ACLMessage.INFORM);
					
				for (int i = 0; i < resultBomb.length; i++)
				{
					bombsInform.addReceiver(resultBomb[i].getName());
				}
					
				bombsInform.setOntology("trap-position");
				bombsInform.setContent(String.format("%s,%s", location.x, location.y));
				_owner.send(bombsInform);
			}
		}
	    catch(Exception e)
	    {
	    	System.out.println(e);
	    }
	    
	    
	    _owner.emptyTrapsToBroadCast();
	}
		
	
}
