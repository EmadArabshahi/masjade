package axelrod;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Window extends JFrame
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2165175645115692616L;
	
	private SelectionList<String> _availableAgentsList;
	
	public Window(List<String> agentList)
	{
		super("Axelrod Tournament");
		
		_availableAgentsList = new SelectionList<String>(agentList, "selected agents:", "available agents:");
		init();
	}
	
	private void init()
	{
		this.setSize(800, 600);	
		//divide into toprow and bottomrow.
		this.setLayout(new GridLayout(2,0));
		
		JPanel agentList = _availableAgentsList;
		
		
		JPanel topRow = new JPanel();
		
		JPanel bottomRow = new JPanel();
		bottomRow.setLayout(new GridLayout(0,2));
		
		
		bottomRow.add(new JPanel());
		bottomRow.add(agentList);
		
		getContentPane().add(topRow);
		getContentPane().add(bottomRow);
	}
	
	public void updateAgentList()
	{
		this._availableAgentsList.update();
	}
	
}
