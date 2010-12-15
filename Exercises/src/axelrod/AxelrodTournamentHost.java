package axelrod;

import java.util.HashSet;
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
	
	private AvailableAgentsList _availableAgentsList;
	
	protected void setup()
	{
		_availableAgentsList = new AvailableAgentsList();
		_window = new Window(_availableAgentsList);
		_window.setVisible(true);	
		
		addBehaviour(new RefreshAgentListBehaviour(this,1000));
	}
	
	public void setAvailableAgents(DFAgentDescription[] participants)
	{
		this._availableAgentsList.set
		for(DFAgentDescription participant : participants)
		{
			this._availableAgentsList.addAgent(participant.getName().getLocalName());
		}
	}
	
	protected void takedown()
	{
		_window.dispose();
	}
}
