package exerc1.behaviours;

import java.awt.Point;

import exerc1.GridWorldAgent;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ProcessMasterBombPickedUpAction extends OneShotBehaviour {
	public static final int PROCESSED_BOMB_PICKED_UP = 1;
	private GridWorldAgent _owner;
	
	public ProcessMasterBombPickedUpAction(GridWorldAgent owner)
	{
		_owner = owner;
	}

	@Override
	public void action() {
		ACLMessage msg = _owner.receive(MessageTemplate.MatchOntology("bomb-picked-up"));
		if (msg != null)
		{
			String[] splitMsg = msg.getContent().split(",");
			Point location = new Point();
			location.x = Integer.parseInt(splitMsg[0]);
			location.y = Integer.parseInt(splitMsg[1]);
			
			_owner.removeKnownBomb(location);
			
			System.out.println("MASTERpickeduprecieved!!!!!!!!!!!!!!!!");
		}
	}
	
	public int onEnd()
	{
		return PROCESSED_BOMB_PICKED_UP;		
	}

}
