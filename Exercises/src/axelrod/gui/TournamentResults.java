package axelrod.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneLayout;
import javax.swing.table.DefaultTableModel;

import axelrod.Contestant;
import axelrod.Game;
import axelrod.Round;
import axelrod.Tournament;

public class TournamentResults extends JPanel
{
	
	private JTable _rankings;
	
	private JPanel _innerPanel;
	private JScrollPane _scrollPane;
	
	public TournamentResults()
	{
		init();
	}
	
	private void init()
	{
		_scrollPane = new JScrollPane();
		_innerPanel = new JPanel();
		
		_rankings = new JTable();
		_scrollPane = new JScrollPane(_rankings);
		
		_scrollPane.setPreferredSize(new Dimension(750, 250));
		
		this.add(_scrollPane, BorderLayout.CENTER);
	}
	
	public void startNewTournament(Tournament tournament)
	{	
		for (Contestant contestant : tournament.getContestants())
		{
			contestant.reset();
		}
		Object[][] data = {	};
		
		Object[] columnNames = new Object[2];
		columnNames[0] = "Contestant";
		columnNames[1] = "Utility";
		
		DefaultTableModel model = (DefaultTableModel)_rankings.getModel();
		model.setDataVector(data, columnNames);
	}
	
	public void updateRankings(List<Contestant> contestants)
	{
		DefaultTableModel model = (DefaultTableModel)_rankings.getModel();
		
		model.setRowCount(0);
		
		for (Contestant contestant : contestants)
		{
			Object[] rowData = new Object[2];
			rowData[0] = contestant.toString();
			rowData[1] = contestant.getTotalUtility();
			
			model.addRow(rowData);
		}
	}
}
