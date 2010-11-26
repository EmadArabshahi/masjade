package simpleExample;
import gridworld.Environment;
import jade.core.Agent;
import java.util.Iterator;
import java.lang.Thread;
import java.awt.Point;
import java.util.LinkedList;

import simpleExample.behaviors.AlwaysGoLeft;
import simpleExample.behaviors.RespondToHelloBehavior;
import simpleExample.behaviors.SayHelloBehavior;

public class HelloWorldAgent extends Agent
{
	protected void setup() 
	{
		//you may need to take some arguments from input
		getArguments();
		/**
		 * In this function, you should set up primary things about
		 * your agent (like entering the environment...)
		 * and setting behaviors...
		 */
		if (getAID().getLocalName().equals("agent1"))		
			Environment.enter(getAID().getLocalName(),new Point(1,3),"red");
		else if (getAID().getLocalName().equals("agent2"))
			Environment.enter(getAID().getLocalName(),new Point(5,5),"blue");
		else
			Environment.enter(getAID().getLocalName(),new Point(8,5),"blue");
		
		this.addBehaviour(new AlwaysGoLeft(this.getLocalName()));
		/*
		if(getLocalName().equals("agent1"))
			this.addBehaviour(new SayHelloBehavior(this));
		else
			this.addBehaviour(new RespondToHelloBehavior(this));*/

	}
		
		
}