package axelrod;

import java.util.HashSet;
import java.util.Set;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class AvailableAgentsList extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5256505106730436864L;

	
	private Set<String> _availableAgents;
	
	private MutableList _selectedList;
	private MutableList _availableList;
	
	private JButton _addButton;
	private JButton _removeButton;
	
	public AvailableAgentsList() 
	{
		super();
		//divide in two regions.
		setLayout(new GridLayout(0, 2));
		
		_availableAgents = new HashSet<String>();
		_selectedList = new MutableList();
		_availableList = new MutableList();
		_addButton = new JButton("<<");
		_removeButton = new JButton(">>");
		
		_addButton.addActionListener(new AddListener()); 
		_removeButton.addActionListener(new RemoveListener());
		
		 JPanel leftPanel = new JPanel(new BorderLayout());
	    leftPanel.add(new JLabel("Selected Agents:"), BorderLayout.NORTH);
	    leftPanel.add(new JScrollPane(_selectedList), BorderLayout.CENTER);
	    leftPanel.add(_removeButton, BorderLayout.SOUTH);

	    JPanel rightPanel = new JPanel(new BorderLayout());
	    rightPanel.add(new JLabel("Available Agents:"), BorderLayout.NORTH);
	    rightPanel.add(new JScrollPane(_availableList), BorderLayout.CENTER);
	    rightPanel.add(_addButton, BorderLayout.SOUTH);
		
		
		
		this.add(leftPanel);
		this.add(rightPanel);
		
	}
	
	public void setAvailableAgents(String[] agents)
	{
		Set<String> selectedAgents = getSelectedAgents();
		
		for(String s : selectedAgents)
		{
			if()  
		}
	}
	
	public Set<String> getAvailableAgents()
	{
		Set<String> returnSet = new HashSet<String>();
		Object[] array = _availableList.getContents().toArray();
		for(Object o : array)
		{
			returnSet.add((String)o);
		}
		return returnSet;
	}
	
	public Set<String> getSelectedAgents()
	{
		Set<String> returnSet = new HashSet<String>();
		Object[] array = _selectedList.getContents().toArray();
		for(Object o : array)
		{
			returnSet.add((String)o);
		}
		return returnSet;
	}

	
	private void addElements(MutableList list, Object[] elements)
	{
		for(int i=0; i<elements.length; i++)
		{
			list.getContents().addElement(elements[i]);
		}
	}
	
	private void clearSelected(MutableList list) 
	{
	    Object selected[] = list.getSelectedValues();
	    for (int i = selected.length - 1; i >= 0; --i) {
	    	list.getContents().removeElement(selected[i]);
	    }
	    list.getSelectionModel().clearSelection();
	}

	
	class AddListener implements ActionListener 
	{
	    public void actionPerformed(ActionEvent e) 
	    {
	      Object[] selected = _availableList.getSelectedValues();
	      addElements(_selectedList, selected);
	      clearSelected(_availableList);
	    }
	  }

	 class RemoveListener implements ActionListener 
	 {
	    public void actionPerformed(ActionEvent e) 
	    {
	      Object[] selected = _selectedList.getSelectedValues();
	      addElements(_availableList, selected);
	      clearSelected(_selectedList);
	    }
	  }
	
	
}




class MutableList extends JList {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1689653672302817819L;
	
	MutableList() 
	{
		super(new DefaultListModel());
    }
	
    DefaultListModel getContents() 
    {
    	return (DefaultListModel)getModel();
    }
}   