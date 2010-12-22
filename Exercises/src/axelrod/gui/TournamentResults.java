package axelrod.gui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class TournamentResults extends JPanel
{
	
	private JPanel _resultsTable;
	
	public TournamentResults()
	{
		init();
	}
	
	private void init()
	{
		this.setLayout(new BorderLayout());
		
		JLabel header = new JLabel("Tournament Results:");
		this.add(header, BorderLayout.NORTH);
		
		_resultsTable = new JPanel();
		//Just to get it visible...
		_resultsTable.setBackground(Color.BLACK);
		this.add(_resultsTable, BorderLayout.CENTER);
	}
	
}
