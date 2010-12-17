package axelrod;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import axelrod.behaviours.RefreshAgentListBehaviour;
import axelrod.gui.Window;

import jade.core.Agent;
import jade.domain.FIPAAgentManagement.DFAgentDescription;

public class AxelrodTournamentHost extends Agent
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6479860514173823491L;

	private Window _window;
	
	//private Rules _rules;
	
	protected void setup()
	{
		_window = new Window(this);
		_window.setVisible(true);	
		
		addBehaviour(new RefreshAgentListBehaviour(this,1000));
	}
	
	public void setAvailableAgents(DFAgentDescription[] participants)
	{
		List<String> newAgents = new ArrayList<String>();
		for(int i=0; i<participants.length; i++)
		{
			newAgents.add(participants[i].getName().getName());
		}
		_window.updateAgentList(newAgents);
	}
	
	protected void takedown()
	{
		_window.dispose();
	}

}
