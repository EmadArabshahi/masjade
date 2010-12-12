package logistics.behaviours;

import jade.core.behaviours.SimpleBehaviour;
import logistics.BombCarrierAgent;
import logistics.GridWorldAgent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ProcessMessageBehaviour extends SimpleBehaviour
{
	public static final int MESSAGE_RECEIVED = 1;
	public static final int NO_MESSAGE_RECEIVED = 2;
	
	GridWorldAgent _owner;
	
	private boolean _done = false;
	private boolean _blocking;
	private int _messagePerformative;
	private int _state = NO_MESSAGE_RECEIVED;

	
	public ProcessMessageBehaviour(GridWorldAgent carrier, boolean blocking, int ACLMessagePerformative)
	{
		this._owner = carrier;
		this._blocking = blocking;
		this._messagePerformative = ACLMessagePerformative;
	}
	
	public void action()
	{
		System.out.println("In MESSAGE PROCESSING ACTION.");
		
		_done = false;
		
		ACLMessage msg = _owner.receive();
		if (msg != null) 
		{
			_state = MESSAGE_RECEIVED;
			_owner.messageReceived(msg);
			
			if(msg.getPerformative() == this._messagePerformative)
			{
				if(_blocking)
				{
					_done = true;
					
				}
			}
			
		}
		else
		{
			if(_blocking)
				block();
			else
			{	
				_done = true;
				_state = NO_MESSAGE_RECEIVED;
			}
		}
	}
	
	public boolean done()
	{
		return _done;
	}
	
	public int onEnd()
	{
		return _state;
	}
}
