package simpleExample.behaviors;

import gridworld.Environment;
import jade.core.behaviours.Behaviour;

public class AlwaysGoLeft extends Behaviour {
	String agentName;
	public AlwaysGoLeft(String agentName) {
		this.agentName=agentName;
	}
	@Override
	public void action() {
		
		Environment.west(agentName);
	}

	@Override
	public boolean done() {
		return false;
	}

}
