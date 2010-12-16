package axelrod;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import axelrod.behaviours.RefreshAgentListBehaviour;

import jade.core.Agent;
import jade.domain.FIPAAgentManagement.DFAgentDescription;

public class AxelrodTournamentHost extends Agent
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6479860514173823491L;

	private Window _window;
	
	private List<String> _agentList;
	
	protected void setup()
	{
		_agentList = new ArrayList<String>();
		_window = new Window(_agentList);
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
		_agentList.clear();
		_agentList.addAll(newAgents);
		
		_window.updateAgentList();
	}
	
	protected void takedown()
	{
		_window.dispose();
	}
}
