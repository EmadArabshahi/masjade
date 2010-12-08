package exerc1.behaviours;

import java.awt.Point;

import exerc1.DisposingAgent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;

public class ReceivePositionBehaviour extends SimpleBehaviour 
{
	public static int BOMB_AND_TRAP_KNOWN = 1;
	private DisposingAgent _owner;
	
	public ReceivePositionBehaviour(DisposingAgent owner)
	{
		_owner = owner;
	}
	@Override
	public void action() {
		ACLMessage msg = _owner.receive();
		if (msg != null)
		{
			String[] splitMsg = msg.getContent().split(",");
			Point location = new Point();
			location.x = Integer.parseInt(splitMsg[0]);
			location.y = Integer.parseInt(splitMsg[1]);
			
			if (msg.getOntology() == "bomb-inform-onthology")
			{
				_owner.bombSensed(location);
			}
			else if (msg.getOntology() == "trap-inform-onthology")
			{
				_owner.trapSensed(location);
			}
		}
		else
		{
			block();
		}
	}

	@Override
	public boolean done() {
		return _owner.knowsBombs() && _owner.knowsTraps();
	}
	
	public int onEnd()
	{
		return BOMB_AND_TRAP_KNOWN;
	}
}
