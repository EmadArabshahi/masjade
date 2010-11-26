package simpleExample.behaviors;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;


public class SayHelloBehavior extends Behaviour {
	Agent a;
	
	public SayHelloBehavior(Agent a)
	{
		this.a=a;
	}

	@Override
	public void action() {
		/*we suppose that the second agent is called "agent2" and we
		 * send him/her a message.
		 * 
		 * It is a bit restrictive to presuppose of the
		 * name of an agent. 
		 * Her name does not matter, only the work she offers !
		 * A bonus exercise is to get names it using "white pages".
		 * 
		 * Ask for DFAgentDescription for more details about this.
		 * 
		 */
		System.out.println(a.getLocalName()+" says hello to agent2");

		AID r=new AID("agent2",AID.ISLOCALNAME);
		ACLMessage aclMessage=new ACLMessage(ACLMessage.REQUEST);
		aclMessage.addReceiver(r);
		aclMessage.setContent("Hello, my name is "+a.getLocalName());
		a.send(aclMessage);
	}

	@Override
	public boolean done() {
		return true;
	}

}
