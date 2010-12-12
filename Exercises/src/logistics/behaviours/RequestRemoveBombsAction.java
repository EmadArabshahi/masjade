package logistics.behaviours;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

import gridworld.Environment;
import jade.core.behaviours.*;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import logistics.BombExplorerAgent;
import logistics.GridWorldAgent;


public class RequestRemoveBombsAction extends OneShotBehaviour
{
	private BombExplorerAgent _owner;
	
	public RequestRemoveBombsAction(BombExplorerAgent owner)
	{
		this._owner = owner;
	}

	@Override
	public void action() 
	{
		
		//Setup agent description.
		DFAgentDescription dfdBomb = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription(); 
	    sd.setType("bomb-positions"); 
	    sd.setName("bomb-positions"); 
	    dfdBomb.addServices(sd); 
		
	    //Sens messages.
	    try
		{
			
			Set<Point> bombs = _owner.getBombsToBroadcast();
			Set<Point> bombsToRemove = new HashSet<Point>();
			for (Point location : bombs)
			{
				DFAgentDescription[] carriers = DFService.search(_owner, dfdBomb);
			
				
				if(carriers.length == 0)
				{
					System.out.println("no carriers");
					break;
				}
				else
				{
					System.out.println("sending request, carriers: " + carriers.length);
					ACLMessage bombsRequest = new ACLMessage(ACLMessage.REQUEST);	
					bombsRequest.addReceiver(carriers[_owner.getRandomInt(carriers.length)].getName());
					
					bombsRequest.setOntology("bomb-position");
					bombsRequest.setContent(String.format("%s,%s", location.x, location.y));
					_owner.send(bombsRequest);
					bombsToRemove.add(location);
					System.out.println("bombs to broadcast size before." + _owner.getBombsToBroadcast().size());
				}
			}
			
			bombs.removeAll(bombsToRemove);
		}
	    catch(Exception e)
	    {
	    	System.out.println(e);
	    }
	    
	    System.out.println("bombs to broadcast size after." + _owner.getBombsToBroadcast().size());
	}
		
	
}
