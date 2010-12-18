package axelrod.gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;
import java.util.Set;
import java.awt.*;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import axelrod.AxelrodTournamentHost;
import axelrod.Rules;


public class Window extends JFrame
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2165175645115692616L;
	
	private SelectionList<String> _availableAgentsList;
	
	private AxelrodTournamentHost _hostAgent;
	
	public Window(AxelrodTournamentHost hostAgent)
	{
		super("Axelrod Tournament");
		_hostAgent = hostAgent;
		_availableAgentsList = new SelectionList<String>("selected agents:", "available agents:");
		init();
	}
	
	private void init()
	{
		this.setSize(800, 600);	
		//divide into toprow and bottomrow.
		this.setLayout(new GridLayout(2,0));
		
		JPanel agentList = _availableAgentsList;
		agentList.setBorder(BorderFactory.createLineBorder(Color.black));
		
		JPanel topRow = new JPanel();
		
		JPanel bottomRow = new JPanel();
		bottomRow.setLayout(new GridLayout(0,2));
		
		
		bottomRow.add(getRulesPanel());
		bottomRow.add(agentList);
		
		getContentPane().add(topRow);
		getContentPane().add(bottomRow);
	}
	
	
	private JPanel getRulesPanel()
	{
		
		
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createLineBorder(Color.black));
		panel.setLayout(new BorderLayout());
		JLabel header = new JLabel("Utilities:", JLabel.CENTER);
		header.setFont(new Font("sansserif", Font.BOLD, 26));
		
		panel.add(header, BorderLayout.NORTH);
		
		JPanel subpanel = new JPanel();
		subpanel.setLayout(new GridLayout(3,3));
		subpanel.add(new JLabel(""));
		subpanel.add(UtilityLabel("Cooperate"));
		subpanel.add(UtilityLabel("Defect"));
		subpanel.add(UtilityLabel("Cooperate"));
		subpanel.add(UtilityLabel("3,3"));
		subpanel.add(UtilityLabel("0,4"));
		subpanel.add(UtilityLabel("Defect"));
		subpanel.add(UtilityLabel("4,0"));
		subpanel.add(UtilityLabel("1,1"));
		
		panel.add(subpanel, BorderLayout.CENTER);
		return panel;
	}
	
	private JLabel UtilityLabel(String text)
	{
		JLabel label = new JLabel(text);
		label.setFont(new Font("sansserif", Font.BOLD, 18));
		return label;
	}
	
	public void updateAgentList(List<String> newAgents)
	{
		this._availableAgentsList.setItems(newAgents);
	}
	
}