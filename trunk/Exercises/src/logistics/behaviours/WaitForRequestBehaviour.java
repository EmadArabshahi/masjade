package logistics.behaviours;

import jade.core.behaviours.SimpleBehaviour;
import logistics.BombCarrierAgent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.MessageTemplate.MatchExpression;

public class WaitForRequestBehaviour extends SimpleBehaviour
{
	public static final int REQUEST_RECEIVED_AND_KNOWS_TRAPS = 1;
	public static final int REQUEST_RECEIVED_AND_NO_TRAPS = 2;
	
	BombCarrierAgent _owner;
	
	private boolean _done = false;
	
	private MatchExpression _matchExpression;
	
	public WaitForRequestBehaviour(BombCarrierAgent carrier)
	{
		this._owner = carrier;
		this._matchExpression = new MatchExpression() {
			
			@Override
			public boolean match(ACLMessage arg0) {
				return arg0.getPerformative() == ACLMessage.REQUEST || arg0.getPerformative() == ACLMessage.INFORM;
			}
		};
	}
	
	public void action()
	{
		_done = false;
		System.out.println("In WAITING ACTION.");
		_owner.StartListeningForBombRequest();
		
		MessageTemplate mt = new MessageTemplate(_matchExpression);
		ACLMessage msg = _owner.receive(mt);
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
