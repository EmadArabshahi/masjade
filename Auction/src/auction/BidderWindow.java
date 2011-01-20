package auction;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneLayout;
import javax.swing.table.DefaultTableModel;
import javax.swing.JPanel;

public class BidderWindow extends JFrame {
	private JTable _tblWonGoods;
	private JPanel panel;
	private JLabel _lblBidderName;
	private JLabel label_1;
	private JLabel label_2;
	private JLabel _lblTotalBudget;
	private JLabel _lblRemainingBudget;
	
	public BidderWindow() {
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		
		_lblBidderName = new JLabel("bidderName");
		panel.add(_lblBidderName);
		
		label_1 = new JLabel("Total Budget:");
		panel.add(label_1);
		
		_lblTotalBudget = new JLabel("totalBudget");
		panel.add(_lblTotalBudget);
		
		label_2 = new JLabel("Remaining Budget:");
		panel.add(label_2);
		
		_lblRemainingBudget = new JLabel("remainingBudget");
		panel.add(_lblRemainingBudget);
		
		_tblWonGoods = new JTable();
		JScrollPane scrollPane = new JScrollPane(_tblWonGoods);
		_tblWonGoods.setFillsViewportHeight(true);
		
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		Object[][] data = { };
		
		Object[] columnNames = new Object[4];
		columnNames[0] = "Item";
		columnNames[1] = "Type";
		columnNames[2] = "Price";
		columnNames[3] = "Amount";
		
		DefaultTableModel model = (DefaultTableModel)_tblWonGoods.getModel();
		model.setDataVector(data, columnNames);
	}
	
	public void addItem(String itemName, String type, int unitPrice, int amount)
	{
		Object[] row = new Object[4];
		row[0] = itemName;
		row[1] = type;
		row[2] = unitPrice;
		row[3] = amount;
		
		DefaultTableModel model = (DefaultTableModel)_tblWonGoods.getModel();
		model.addRow(row);
	}
	
	public void setBidderName(String bidderName)
	{
		_lblBidderName.setText(bidderName);
	}
	
	public void setTotalBudget(int totalBudget)
	{
		_lblTotalBudget.setText(totalBudget + "");
	}
	
	public void setRemainingBudget(int remainingBudget)
	{
		_lblRemainingBudget.setText(remainingBudget + "");
	}
}
