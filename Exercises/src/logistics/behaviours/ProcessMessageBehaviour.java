package logistics.behaviours;

import jade.core.behaviours.SimpleBehaviour;
import logistics.BombCarrierAgent;
import logistics.GridWorldAgent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.MessageTemplate.MatchExpression;

public class ProcessMessageBehaviour extends SimpleBehaviour
{
	public static final int MESSAGE_RECEIVED = 1;
	public static final int NO_MESSAGE_RECEIVED = 2;
	
	GridWorldAgent _owner;
	
	private boolean _done = false;
	private boolean _blocking;
	private int _messagePerformative;
	private int _state = NO_MESSAGE_RECEIVED;
	private MatchExpression _matchExpression;
	
	public ProcessMessageBehaviour(GridWorldAgent carrier, boolean blocking, int ACLMessagePerformative)
	{
		this._owner = carrier;
		this._blocking = blocking;
		this._messagePerformative = ACLMessagePerformative;
		this._matchExpression = new MatchExpression() {
			
			@Override
			public boolean match(ACLMessage arg0) {
				return arg0.getPerformative() == _messagePerformative;
			}
		};
		
	}
	
	public void action()
	{
		System.out.println("In MESSAGE PROCESSING ACTION.");
		
		_done = false;
		
		MessageTemplate mt = new MessageTemplate(_matchExpression);
		ACLMessage msg = _owner.receive(mt);
		if (msg != null) 
		{
			_state = MESSAGE_RECEIVED;
			_owner.messageReceived(msg);
			
				if(_blocking)
				{
					_done = true;
					
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
