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
	private JScrollPane _scrollPaneRankings;
	private JTable _playedGames;
	private JScrollPane _scrollPanePlayedGames;
	
	public TournamentResults()
	{
		init();
	}
	
	private void init()
	{
		_scrollPaneRankings = new JScrollPane();
		
		_rankings = new JTable();
		_scrollPaneRankings = new JScrollPane(_rankings);
		
		_playedGames = new JTable();
		_scrollPanePlayedGames = new JScrollPane(_playedGames);
		
		_scrollPaneRankings.setPreferredSize(new Dimension(300, 250));
		_scrollPanePlayedGames.setPreferredSize(new Dimension(300, 250));
		
		this.add(_scrollPaneRankings, BorderLayout.WEST);
		this.add(_scrollPanePlayedGames, BorderLayout.CENTER);
	}
	
	public void startNewTournament(Tournament tournament)
	{	
		for (Contestant contestant : tournament.getContestants())
		{
			contestant.reset();
		}
		Object[][] data = {	};
		
		Object[] columnNamesRankings = new Object[2];
		columnNamesRankings[0] = "Contestant";
		columnNamesRankings[1] = "Utility";
		
		DefaultTableModel modelRankings = (DefaultTableModel)_rankings.getModel();
		modelRankings.setDataVector(data, columnNamesRankings);
		
		Object[] columnNamesPlayedGames = new Object[4];
		columnNamesPlayedGames[0] = "C1";
		columnNamesPlayedGames[1] = "U1";
		columnNamesPlayedGames[2] = "C2";
		columnNamesPlayedGames[3] = "U2";
		
		DefaultTableModel modelPlayedGames = (DefaultTableModel)_playedGames.getModel();
		modelPlayedGames.setDataVector(data, columnNamesPlayedGames);
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
	
	public void addPlayedGame(Game game)
	{
		DefaultTableModel model = (DefaultTableModel)_playedGames.getModel();
		
		Object[] rowData = new Object[4];
		
		rowData[0] = game.getContestant1();
		rowData[1] = game.getUtilityContestant1();
		rowData[2] = game.getContestant2();
		rowData[3] = game.getUtilityContestant2();
		
		model.addRow(rowData);
	}
}
