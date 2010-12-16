package axelrod;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

public class SelectionList<T> extends JPanel
{

	private static final long serialVersionUID = -5256505106730436864L;
	
	private List<T> _totalList;
		
	private MutableList _selectedList;
	private MutableList _availableList;

	private String _selectionLabel;
	private String _availableLabel;
	
	/**
	 * Constructs a new empty SelectionList with default labels.
	 */
	public SelectionList()
	{
		this(new ArrayList<T>());
	}
	
	/**
	 * Constructs a new SelectionList with given list as available elements and default labels.
	 * @param list The list to have as starting available elements.
	 */
	public SelectionList(List<T> list)
	{
		this(list, "selected items:", "available items:");
	}
	
	/**
	 * Constructs a new empty selectionlist with giben labels.
	 * @param selectionLabel The label to display above the selection list.
	 * @param availableLabel The label to display above the available list.
	 */
	public SelectionList(String selectionLabel, String availableLabel)
	{
		this(new ArrayList<T>(),selectionLabel, availableLabel);
	}
	
	/**
	 * Constructs a new SelectionList with given list as available elements and given labels.
	 * @param list The list to have as starting available elements.
	 * @param selectionLabel The label to display above the selection list.
	 * @param availableLabel The label to dosplay above the available list.
	 */
	public SelectionList(List<T> list, String selectionLabel, String availableLabel) 
	{
		super();
		
		_totalList = list;
		_selectionLabel = selectionLabel;
		_availableLabel = availableLabel;
		
		init();
		update();
	}
	
	/**
	 * Initializes the selectionlist, does the layout.
	 */
	private void init()
	{
		//divide in two regions.
		setLayout(new GridLayout(0, 2));
		
		_selectedList = new MutableList();
		_availableList = new MutableList();
		
		JButton addButton = new JButton("<<");
		JButton removeButton = new JButton(">>");
		
		addButton.addActionListener(new AddListener()); 
		removeButton.addActionListener(new RemoveListener());
		
		 JPanel leftPanel = new JPanel(new BorderLayout());
	    leftPanel.add(new JLabel(_selectionLabel), BorderLayout.NORTH);
	    leftPanel.add(new JScrollPane(_selectedList), BorderLayout.CENTER);
	    leftPanel.add(removeButton, BorderLayout.SOUTH);

	    JPanel rightPanel = new JPanel(new BorderLayout());
	    rightPanel.add(new JLabel(_availableLabel), BorderLayout.NORTH);
	    rightPanel.add(new JScrollPane(_availableList), BorderLayout.CENTER);
	    rightPanel.add(addButton, BorderLayout.SOUTH);
		
		
		
		this.add(leftPanel);
		this.add(rightPanel);
	}
	
	/**
	 * Sets the contents of this list to the new given list.
	 * Items in either list that do not occur in this list are removed, items in this list that not occur in the available list are added.
	 * @param newAgents
	 */
	public void setItems(List<T> newList)
	{
		_totalList = newList;
		update();
	}
	
	/**
	 * Updates the items in the two lists to match the _totalList.
	 */
	private void update()
	{	
		
		//remove items from the selected list that are not in the total list.
		for(int i=0; i<_selectedList.getContents().size(); i++)
		{
			Object o = _selectedList.getContents().get(i);
			if(!_totalList.contains(o))
				_selectedList.getContents().remove(i);
		}
		
		//remove items from the available list that are not in the total list.
		for(int i=0; i<_availableList.getContents().size(); i++)
		{
			Object o = _availableList.getContents().get(i);
			if(!_totalList.contains(o))
				_availableList.getContents().remove(i);
		}
		
		
		
		//add items to the availablelist, if they are contained in the totallist.
		for(int i=0; i<_totalList.size(); i++)
		{
			Object item = _totalList.get(i);
			if(_selectedList.getContents().contains(item))
			{
				//do nothing since the item is in the selected list.
			}
			else if(_availableList.getContents().contains(item))
			{
				//do nothing since the item in the available list.
			}
			else
			{
				//insert the item to the selected list.
				_availableList.getContents().addElement(item);
			}
			
		}

	}
	
	
	
	/**
	 * Gets the list of items in the available list.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> getAvailableList()
	{
		List<T> list = new ArrayList<T>();
		
		Object[] array = _availableList.getContents().toArray();
		for(Object o : array)
		{
			list.add((T)o);
		}
		return list;
	}
	
	/**
	 * Gets the list of items in the selected list.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> getSelectedList()
	{
		List<T> list = new ArrayList<T>();
		
		Object[] array = _selectedList.getContents().toArray();
		for(Object o : array)
		{
			list.add((T)o);
		}
		return list;
	}

	/**
	 * Adds the object array to the given Mutablelist object.
	 * @param list The MutableList, either the available or selected list.
	 * @param elements The object array to add to the list.
	 */
	private void addElements(MutableList list, Object[] elements)
	{
		for(int i=0; i<elements.length; i++)
		{
			list.getContents().addElement(elements[i]);
		}
	}
	
	/**
	 * Clears the selected elements of the given list.
	 * @param list
	 */
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




class MutableList extends JList 
{
    
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