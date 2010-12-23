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
	private JTable _currentGame;
	private JScrollPane _scrollPaneCurrentGame;
	private JTable _playedRounds;
	private JScrollPane _scrollPanePlayedRounds;
	
	private JPanel _currentGamePanel;
	
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
		
		_currentGame = new JTable();
		_scrollPaneCurrentGame = new JScrollPane(_currentGame);
		
		_playedRounds = new JTable();
		_scrollPanePlayedRounds = new JScrollPane(_playedRounds);
		
		_scrollPaneRankings.setPreferredSize(new Dimension(150, 250));
		_scrollPanePlayedGames.setPreferredSize(new Dimension(300, 250));
		
		_scrollPaneCurrentGame.setPreferredSize(new Dimension(300, 55));
		_scrollPanePlayedRounds.setPreferredSize(new Dimension(300, 195));
		
		_currentGamePanel = new JPanel(new BorderLayout());
		_currentGamePanel.add(_scrollPaneCurrentGame, BorderLayout.NORTH);
		_currentGamePanel.add(_scrollPanePlayedRounds, BorderLayout.SOUTH);
		
		this.add(_scrollPaneRankings, BorderLayout.WEST);
		this.add(_scrollPanePlayedGames, BorderLayout.CENTER);
		this.add(_currentGamePanel, BorderLayout.EAST);
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
		
		Object[] columnNamesPlayedGames = new Object[5];
		columnNamesPlayedGames[0] = "#";
		columnNamesPlayedGames[1] = "C1";
		columnNamesPlayedGames[2] = "U1";
		columnNamesPlayedGames[3] = "C2";
		columnNamesPlayedGames[4] = "U2";
		
		DefaultTableModel modelPlayedGames = (DefaultTableModel)_playedGames.getModel();
		modelPlayedGames.setDataVector(data, columnNamesPlayedGames);
		
		Object[] columnNamesCurrentGame = new Object[2];
		columnNamesCurrentGame[0] = "Contestant";
		columnNamesCurrentGame[1] = "Utility";
		
		DefaultTableModel modelCurrentGame = (DefaultTableModel)_currentGame.getModel();
		modelCurrentGame.setDataVector(data, columnNamesCurrentGame);
		
		Object[] columnNamesPlayedRounds = new Object[5];
		columnNamesPlayedRounds[0] = "#";
		columnNamesPlayedRounds[1] = "C1";
		columnNamesPlayedRounds[2] = "A1";
		columnNamesPlayedRounds[3] = "C2";
		columnNamesPlayedRounds[4] = "A2";
		
		DefaultTableModel modelPlayedRounds = (DefaultTableModel)_playedRounds.getModel();
		modelPlayedRounds.setDataVector(data, columnNamesPlayedRounds);
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
		
		Object[] rowData = new Object[5];
		
		rowData[0] = game.getGameNumber() + 1;
		rowData[1] = game.getContestant1();
		rowData[2] = game.getUtilityContestant1();
		rowData[3] = game.getContestant2();
		rowData[4] = game.getUtilityContestant2();
		
		model.addRow(rowData);
	}
	
	
	public void updateCurrentGame(Game game)
	{
		DefaultTableModel model = (DefaultTableModel)_currentGame.getModel();
		
		Object[] rowContestant1 = new Object[2];
		Object[] rowContestant2 = new Object[2];
		
		rowContestant1[0] = game.getContestant1();
		rowContestant1[1] = game.getUtilityContestant1();
		rowContestant2[0] = game.getContestant2();
		rowContestant2[1] = game.getUtilityContestant2();
		
		model.setRowCount(0);
		model.addRow(rowContestant1);
		model.addRow(rowContestant2);
	}
	
	public void addPlayedRound(Round round)
	{
		DefaultTableModel model = (DefaultTableModel)_playedRounds.getModel();
		
		Object[] rowData = new Object[5];
		
		rowData[0] = round.getRoundNr() + 1;
		rowData[1] = round.getContestant1();
		rowData[2] = ActionToString(round.getActionContestant1());
		rowData[3] = round.getContestant2();
		rowData[4] = ActionToString(round.getActionContestant2());
		
		if (round.getRoundNr() == 0)
		{
			model.setRowCount(0);
		}
		model.addRow(rowData);
	}
	
	private String ActionToString(int action)
	{
		if (action == 0)
		{
			return "C";
		}
		else
		{
			return "D";
		}
	}
}
