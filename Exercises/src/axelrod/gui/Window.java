package axelrod.gui;

import jade.core.AID;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.awt.*;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import axelrod.Contestant;
import axelrod.Game;
import axelrod.Rules;
import axelrod.Tournament;
import axelrod.agents.TournamentAgent;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class Window extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2165175645115692616L;
	
	private SelectionList<Contestant> _availableAgentsList;
	
	private TournamentResults _tournamentResults;
	
	private TournamentAgent _hostAgent;

	private JButton _playButton;
	
	public Window(TournamentAgent hostAgent)
	{
		super("Axelrod Tournament");
		_hostAgent = hostAgent;
		_availableAgentsList = new SelectionList<Contestant>("selected agents:", "available agents:");
		_tournamentResults = new TournamentResults();
		init();
	}
	
	private void startTournament()
	{
		_hostAgent.start();
		_playButton.setText("Games in progress...");
		_playButton.setEnabled(false);
		_availableAgentsList.setEnabled(false);
		_tournamentResults.startNewTournament(_hostAgent.getCurrentTournament());
		_tournamentResults.setVisible(true);
		
	}
	
	private void init()
	{
		this.setSize(800, 600);	
		//divide into toprow and bottomrow.
		getContentPane().setLayout(new GridLayout(2,0));
		
		JPanel agentList = _availableAgentsList;
		agentList.setBorder(BorderFactory.createLineBorder(Color.black));
		
		JPanel topRow = new JPanel();
		topRow.setLayout(new BorderLayout());
		_playButton = new JButton("Play games");
		_playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				startTournament();
			}
		});
		topRow.add(_playButton, BorderLayout.NORTH);
		topRow.add(_tournamentResults, BorderLayout.CENTER);
		_tournamentResults.setVisible(false);
		
		
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
	
	public void addContestant(Contestant contestant)
	{
		this._availableAgentsList.addItem(contestant);
	}

	public List<Contestant> getSelectedContestants() 
	{
		return _availableAgentsList.getSelectedList();
	}

	public void reset() {
		_playButton.setEnabled(true);
		_playButton.setText("Play Games");
		_availableAgentsList.setEnabled(true);
	}

	public void updateRankings(List<Contestant> contestants)
	{
		_tournamentResults.updateRankings(contestants);
	}

	public void addPlayedGame(Game game) {
		_tournamentResults.addPlayedGame(game);
	}
}
