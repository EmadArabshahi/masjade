package auction;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class BidderWindow extends JFrame {
	JLabel _lblTotalBudget;
	JLabel _lblRemainingBudget;
	JLabel _lblBidderName;
	private JTable _tblWonGoods;
	
	public BidderWindow() {
		getContentPane().setLayout(null);
		
		_lblBidderName = new JLabel("bidderName");
		_lblBidderName.setBounds(10, 11, 123, 14);
		getContentPane().add(_lblBidderName);
		
		JLabel lbl1 = new JLabel("Total Budget:");
		lbl1.setBounds(10, 29, 110, 14);
		getContentPane().add(lbl1);
		
		JLabel lbl2 = new JLabel("Remaining Budget:");
		lbl2.setBounds(10, 47, 110, 14);
		getContentPane().add(lbl2);
		
		_lblRemainingBudget = new JLabel("remainingBudget");
		_lblRemainingBudget.setBounds(127, 47, 102, 14);
		getContentPane().add(_lblRemainingBudget);
		
		_lblTotalBudget = new JLabel("totalBudget");
		_lblTotalBudget.setBounds(127, 29, 102, 14);
		getContentPane().add(_lblTotalBudget);
		
		Object[][] data = { { "bla", "bla", "bla" }	};
		
		Object[] columnNames = new Object[3];
		columnNames[0] = "Item";
		columnNames[1] = "Price";
		columnNames[2] = "Amount";
		
		_tblWonGoods = new JTable(data, columnNames);
		_tblWonGoods.setBounds(10, 100, 200, 200);
		
		//DefaultTableModel model = (DefaultTableModel)_tblWonGoods.getModel();
		//model.setDataVector(data, columnNames);
		
		//JScrollPane scrollPane = new JScrollPane();
		//scrollPane.add(_tblWonGoods.getTableHeader());
		
		//scrollPane.add(_tblWonGoods);
		//scrollPane.setBounds(10, 72, 241, 134);
		
		getContentPane().add(_tblWonGoods);
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
