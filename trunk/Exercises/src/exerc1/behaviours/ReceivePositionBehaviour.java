package exerc1.behaviours;

import java.awt.Point;

import exerc1.DisposingAgent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

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
		MessageTemplate templateBomb = MessageTemplate.MatchOntology("bomb-inform-onthology");
		MessageTemplate templateTrap = MessageTemplate.MatchOntology("trap-inform-onthology");
		MessageTemplate template = MessageTemplate.or(templateBomb, templateTrap);
		ACLMessage msg = _owner.receive(template);
		if (msg != null)
		{
			String[] splitMsg = msg.getContent().split(",");
			Point location = new Point();
			location.x = Integer.parseInt(splitMsg[0]);
			location.y = Integer.parseInt(splitMsg[1]);
			
			if (msg.getOntology() == "bomb-inform-onthology")
			{
				_owner.addKnownBomb(location);
			}
			else if (msg.getOntology() == "trap-inform-onthology")
			{
				_owner.addKnownTrap(location);
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
