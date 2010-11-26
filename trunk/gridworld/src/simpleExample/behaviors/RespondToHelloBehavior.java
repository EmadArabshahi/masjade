package simpleExample.behaviors;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.tools.introspector.gui.MessageTableModel;

public class RespondToHelloBehavior 
/*extending OneShotBehaviour mean that this behavior is triggered
only once. This is equivalent to always return "true" with the done()
function*/
extends OneShotBehaviour {
	Agent a;
	
	/*
	 * A message template is a tool used to "check" if there is a message
	 * that matches with a given template (here, the performative is a request)
	 * in the queue of unread messages
	 * 
	 * There is more subtle way to use message templates
	 * (by combining them and matching with ontologies)
	 * 
	 * If you want to know more about ontologies, feel free to contact
	 * me.
	 */
	private static final MessageTemplate mt=
		MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
	public RespondToHelloBehavior(Agent a)
	{
		this.a=a;
	}

	@Override
	public void action() {
		/*
		 * In jade, incoming messages are stores in a queue.
		 * You do not have direct access to the second message of this queue.
		 * 
		 *  Nevertheless, with message templates, you can access to the first
		 *  message fitting with this template.
		 *  
		 *  Here, for instance, if you receive a INFORM message, this one
		 *  will not be handled by the receive, linked to this template
		 */
		
		/*
		 * This receive tests if there is a message in the queue.
		 * If there is a message, it is returned,
		 * otherwise, null is returned.
		 *
		 * ACLMessage aclmessage=a.receive(mt);
		 */
		/*
		 * This receive blocks until a message comes.
		 * Be VERY vigilant in manipulating these: deadlocks
		 * may arise quite easily without a clear conception.
		 */
		 ACLMessage aclmessage=a.blockingReceive(mt);
		 
		 System.out.println(a.getLocalName()+" received "+aclmessage.getContent()+" from "+aclmessage.getSender());
		 
	}

}
