package auction;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class AuctioneerWindow extends JFrame {
	private JTable _tblRemainingGoods;
	private JTable _tblLog;
	private JTable _tblInitialGoods;

	public AuctioneerWindow()
	{
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		_tblLog = new JTable();
		JScrollPane scrollPaneLog = new JScrollPane(_tblLog);
		_tblLog.setFillsViewportHeight(true);
		
		getContentPane().add(scrollPaneLog, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		Object[][] data = { };
		
		Object[] columnNames = new Object[4];
		columnNames[0] = "Item";
		columnNames[1] = "Type";
		columnNames[2] = "Price";
		columnNames[3] = "Amount";
		
		_tblInitialGoods = new JTable();
		_tblInitialGoods.setFillsViewportHeight(true);
		JPanel panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.CENTER);
		
		JLabel lblInitialStock = new JLabel("Initial items");
		panel_1.add(lblInitialStock, BorderLayout.NORTH);
		
		JScrollPane scrollPane = new JScrollPane(_tblInitialGoods);
		panel_1.add(scrollPane);
		scrollPane.setPreferredSize(new Dimension(600, 200));
		
		DefaultTableModel modelInitialGoods = (DefaultTableModel)_tblInitialGoods.getModel();
		modelInitialGoods.setDataVector(data, columnNames);		
		
		_tblRemainingGoods = new JTable();
		_tblRemainingGoods.setFillsViewportHeight(true);
		
		DefaultTableModel model = (DefaultTableModel)_tblRemainingGoods.getModel();
		model.setDataVector(data, columnNames);
		
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JLabel lblRemainingStock = new JLabel("Remaining items");
		panel_2.add(lblRemainingStock, BorderLayout.NORTH);
		JScrollPane scrollPaneRemaingGoods = new JScrollPane(_tblRemainingGoods);
		panel_2.add(scrollPaneRemaingGoods);
		
		scrollPaneRemaingGoods.setPreferredSize(new Dimension(600, 200));
		_tblInitialGoods.setFillsViewportHeight(true);	
		
		Object[] columnNamesLog = new Object[1];
		columnNamesLog[0] = "Auction Log";
		
		DefaultTableModel modelLog = (DefaultTableModel)_tblLog.getModel();
		modelLog.setDataVector(data, columnNamesLog);
		
	}
	
	/*
	public void removeItem(String itemName, int amount)
	{
		DefaultTableModel model = (DefaultTableModel)_tblRemainingGoods.getModel();
		
		model.
	}*/
	
	public void addItem(String itemName, String type, int unitPrice, int amount)
	{
		Object[] row = new Object[4];
		row[0] = itemName;
		row[1] = type;
		row[2] = unitPrice;
		row[3] = amount;
		
		DefaultTableModel model = (DefaultTableModel)_tblRemainingGoods.getModel();
		model.addRow(row);
		
		DefaultTableModel modelRemaining = (DefaultTableModel)_tblInitialGoods.getModel();
		modelRemaining.addRow(row);
	}
	
	public void addLogEntry(String entry)
	{
		Object[] row = new Object[1];
		row[0] = entry;
		
		DefaultTableModel model = (DefaultTableModel)_tblLog.getModel();
		model.addRow(row);
	}
}
