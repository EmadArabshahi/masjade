package contractnet.src;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;

import contractnet.src.agents.ContractorAgent;

import java.awt.GridBagConstraints;
import javax.swing.Action;
import java.beans.PropertyChangeListener;
import java.lang.Object;
import java.lang.String;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ContractorWindow extends JFrame{

	private final String[] manufacturers = { "HP", "Intel", "Compaq"};
	private final String[] qualities = { "Average", "Good", "Best"};
	private final String[] prices = { "100", "1000"};
	private final String[] types = { "CPU", "GPU", "M.Board"};
	private ContractorAgent agent;
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel componentPanel = null;
	private JPanel textAreaPanel = null;
	private JLabel specifyCompLabel = null;
	private JPanel selectionPanel = null;
	private JComboBox typeComboBox = null;
	private JComboBox manComboBox = null;
	private JComboBox qualityComboBox = null;
	private JComboBox priceComboBox = null;
	private JButton addCompButton = null;
	private JTextArea infoTextArea = null;

	/**
	 * This is the default constructor
	 */
	public ContractorWindow( ContractorAgent agent) {
		super();
		initialize();
		this.agent = agent;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 200);
		this.setContentPane(getJContentPane());
		this.setTitle("JFrame");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			GridLayout gridLayout = new GridLayout();
			gridLayout.setRows(2);
			gridLayout.setHgap(5);
			jContentPane = new JPanel();
			jContentPane.setLayout(gridLayout);
			jContentPane.add(getComponentPanel(), null);
			jContentPane.add(getTextAreaPanel(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes componentPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getComponentPanel() {
		if (componentPanel == null) {
			specifyCompLabel = new JLabel();
			specifyCompLabel.setText("Specify Component:");
			specifyCompLabel.setHorizontalAlignment(SwingConstants.CENTER);
			GridLayout gridLayout1 = new GridLayout();
			gridLayout1.setRows(3);
			componentPanel = new JPanel();
			componentPanel.setLayout(gridLayout1);
			componentPanel.add(specifyCompLabel, null);
			componentPanel.add(getSelectionPanel(), null);
			componentPanel.add(getAddCompButton(), null);
		}
		return componentPanel;
	}

	/**
	 * This method initializes textAreaPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getTextAreaPanel() {
		if (textAreaPanel == null) {
			GridLayout gridLayout3 = new GridLayout();
			gridLayout3.setRows(1);
			textAreaPanel = new JPanel();
			textAreaPanel.setLayout(gridLayout3);
			textAreaPanel.add(getInfoTextArea(), null);
		}
		return textAreaPanel;
	}

	/**
	 * This method initializes selectionPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getSelectionPanel() {
		if (selectionPanel == null) {
			GridLayout gridLayout2 = new GridLayout();
			gridLayout2.setRows(1);
			gridLayout2.setColumns(4);
			selectionPanel = new JPanel();
			selectionPanel.setLayout(gridLayout2);
			selectionPanel.add(getTypeComboBox(), null);
			selectionPanel.add(getManComboBox(), null);
			selectionPanel.add(getQualityComboBox(), null);
			selectionPanel.add(getPriceComboBox(), null);
		}
		return selectionPanel;
	}

	/**
	 * This method initializes typeComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getTypeComboBox() {
		if (typeComboBox == null) {
			typeComboBox = new JComboBox( types);
			typeComboBox.setSelectedIndex(0);
		}
		return typeComboBox;
	}

	/**
	 * This method initializes manComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getManComboBox() {
		if (manComboBox == null) {
			manComboBox = new JComboBox( manufacturers);
			manComboBox.setSelectedIndex(0);
		}
		return manComboBox;
	}

	/**
	 * This method initializes qualityComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getQualityComboBox() {
		if (qualityComboBox == null) {
			qualityComboBox = new JComboBox( qualities);
			qualityComboBox.setSelectedIndex(0);
		}
		return qualityComboBox;
	}

	/**
	 * This method initializes priceComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getPriceComboBox() {
		if (priceComboBox == null) {
			priceComboBox = new JComboBox( prices);
			priceComboBox.setSelectedIndex(0);
		}
		return priceComboBox;
	}

	/**
	 * This method initializes addCompButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getAddCompButton() {
		if (addCompButton == null) {
			addCompButton = new JButton("Add Component");
			addCompButton.addActionListener( new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					double dQuality;
					String sQuality = (String)qualityComboBox.getSelectedItem();
					if ( sQuality.equals("Average"))
						dQuality = 100;
					else if ( sQuality.equals("Good"))
						dQuality = 500;
					else
						dQuality = 1000;
					
					double price =  new Double ( Double.parseDouble( (String)priceComboBox.getSelectedItem())).doubleValue();
					Component c = new Component( (String)typeComboBox.getSelectedItem(), (String)manComboBox.getSelectedItem(), dQuality, price);
					agent.getComponents().add(c);
					
					infoTextArea.append("Added(" + c.getType() + ":" + c.getManufacturer() + "," + c.getQuality() + "," + c.getManufacturer() +")\n");
				}
			});
		}
		return addCompButton;
	}

	/**
	 * This method initializes infoTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getInfoTextArea() {
		if (infoTextArea == null) {
			infoTextArea = new JTextArea();
			JScrollPane scrollPane = new JScrollPane( infoTextArea);
			infoTextArea.setEditable(false);
		}
		return infoTextArea;
	}

	public ContractorAgent getAgent() {
		return agent;
	}

	

}  //  @jve:decl-index=0:visual-constraint="11,10"
