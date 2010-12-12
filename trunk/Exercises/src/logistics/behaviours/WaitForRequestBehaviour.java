package logistics.behaviours;

import jade.core.behaviours.SimpleBehaviour;
import logistics.BombCarrierAgent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class WaitForRequestBehaviour extends SimpleBehaviour
{
	public static final int REQUEST_RECEIVED_AND_KNOWS_TRAPS = 1;
	public static final int REQUEST_RECEIVED_AND_NO_TRAPS = 2;
	
	BombCarrierAgent _owner;
	
	private boolean _done = false;
	
	public WaitForRequestBehaviour(BombCarrierAgent carrier)
	{
		this._owner = carrier;
		
	}
	
	public void action()
	{
		_done = false;
		System.out.println("In WAITING ACTION.");
		_owner.StartListeningForBombRequest();
		
		
		ACLMessage msg = _owner.receive();
		if (msg != null) 
		{
			if(msg.getPerformative() == ACLMessage.REQUEST)
			{	
				System.out.println("wiehoe REQUEST received.");
				_owner.requestReceived(msg);
				_done = true;
			}
			else
			{
				System.out.println("wiehoe INFORM received.");
				_owner.informReceived(msg);
			}
		}
		else
		{
			block();
		}
	}
	
	public boolean done()
	{
		return _done;
	}
	
	
	
	public int onEnd()
	{
		if(_owner.knowsTraps())
			return REQUEST_RECEIVED_AND_KNOWS_TRAPS;
		else
			return REQUEST_RECEIVED_AND_NO_TRAPS;
	}
}
