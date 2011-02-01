package contractnet.src;

import jade.lang.acl.ACLMessage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import contractnet.src.agents.ManagerAgent;

public class ManagerWindow extends JFrame {

	private final String[] manufacturers = { "", "HP", "Intel", "Compaq"};
	private final String[] qualities = { "", "Average", "Good", "Best"};
	private final String[] prices = { "", "100", "1000"};
	private ManagerAgent agent;
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JLabel specifyOrderLabel = null;
	private JPanel cpuPanel = null;
	private JLabel cpuLabel = null;
	private JComboBox cpuManComboBox = null;
	private JComboBox cpuQualityComboBox = null;
	private JComboBox cpuPriceComboBox = null;
	private JPanel gpuPanel = null;
	private JLabel gpuLabel = null;
	private JComboBox gpuManComboBox = null;
	private JComboBox gpuQualityComboBox = null;
	private JComboBox gpuPriceComboBox = null;
	private JPanel motherboardPanel = null;
	private JLabel mbLabel = null;
	private JComboBox mbManComboBox = null;
	private JComboBox mbQualityComboBox = null;
	private JComboBox mbPriceComboBox = null;
	private JButton addCompButton = null;
	private JButton completeOrderButton = null;
	private JPanel textAreaPanel = null;
	private JTextArea infoTextArea = null;
	private JPanel specifyOrderPanel = null;
	/**
	 * This is the default constructor
	 */
	public ManagerWindow( ManagerAgent agent) {
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
		this.setSize(400, 300);
		this.setContentPane(getJContentPane());
		this.setTitle("Manager Window");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			specifyOrderLabel = new JLabel();
			specifyOrderLabel.setText("Specify the order :");
			GridLayout gridLayout = new GridLayout();
			gridLayout.setRows(2);
			gridLayout.setVgap(8);
			gridLayout.setColumns(1);
			jContentPane = new JPanel();
			jContentPane.setLayout(gridLayout);
			jContentPane.add(getSpecifyOrderPanel(), null);
			jContentPane.add(getTextAreaPanel(), null);
		}
		return jContentPane;	}

	/**
	 * This method initializes cpuPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getCpuPanel() {
		if (cpuPanel == null) {
			cpuLabel = new JLabel();
			cpuLabel.setText("CPU:");
			GridLayout gridLayout1 = new GridLayout();
			gridLayout1.setRows(1);
			gridLayout1.setColumns(4);
			cpuPanel = new JPanel();
			cpuPanel.setLayout(gridLayout1);
			cpuPanel.add(cpuLabel, null);
			cpuPanel.add(getCpuManComboBox(), null);
			cpuPanel.add(getCpuQualityComboBox(), null);
			cpuPanel.add(getCpuPriceComboBox(), null);
		}
		return cpuPanel;
	}

	/**
	 * This method initializes cpuManComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCpuManComboBox() {
		if (cpuManComboBox == null) {
			cpuManComboBox = new JComboBox( manufacturers);	
			cpuManComboBox.setSelectedIndex(0);
		}
		return cpuManComboBox;
	}

	/**
	 * This method initializes cpuQualityComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCpuQualityComboBox() {
		if (cpuQualityComboBox == null) {
			cpuQualityComboBox = new JComboBox( qualities);
			cpuQualityComboBox.setSelectedIndex(0);
		}
		return cpuQualityComboBox;
	}

	/**
	 * This method initializes cpuPriceComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCpuPriceComboBox() {
		if (cpuPriceComboBox == null) {
			cpuPriceComboBox = new JComboBox(prices);
			cpuPriceComboBox.setSelectedIndex(0);
		}
		return cpuPriceComboBox;
	}

	/**
	 * This method initializes gpuPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getGpuPanel() {
		if (gpuPanel == null) {
			gpuLabel = new JLabel();
			gpuLabel.setText("GPU:");
			GridLayout gridLayout2 = new GridLayout();
			gridLayout2.setRows(1);
			gridLayout2.setColumns(4);
			gpuPanel = new JPanel();
			gpuPanel.setLayout(gridLayout2);
			gpuPanel.add(gpuLabel, null);
			gpuPanel.add(getGpuManComboBox(), null);
			gpuPanel.add(getGpuQualityComboBox(), null);
			gpuPanel.add(getGpuPriceComboBox(), null);
		}
		return gpuPanel;
	}

	/**
	 * This method initializes gpuManComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getGpuManComboBox() {
		if (gpuManComboBox == null) {
			gpuManComboBox = new JComboBox( manufacturers);
			gpuManComboBox.setSelectedIndex(0);
		}
		return gpuManComboBox;
	}

	/**
	 * This method initializes gpuQualityComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getGpuQualityComboBox() {
		if (gpuQualityComboBox == null) {
			gpuQualityComboBox = new JComboBox( qualities);
			gpuQualityComboBox.setSelectedIndex(0);
		}
		return gpuQualityComboBox;
	}

	/**
	 * This method initializes gpuPriceComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getGpuPriceComboBox() {
		if (gpuPriceComboBox == null) {
			gpuPriceComboBox = new JComboBox( prices);
			gpuPriceComboBox.setSelectedIndex(0);
		}
		return gpuPriceComboBox;
	}

	/**
	 * This method initializes motherboardPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getMotherboardPanel() {
		if (motherboardPanel == null) {
			mbLabel = new JLabel();
			mbLabel.setText("M.Board:");
			GridLayout gridLayout3 = new GridLayout();
			gridLayout3.setRows(1);
			gridLayout3.setColumns(4);
			motherboardPanel = new JPanel();
			motherboardPanel.setLayout(gridLayout3);
			motherboardPanel.add(mbLabel, null);
			motherboardPanel.add(getMbManComboBox(), null);
			motherboardPanel.add(getMbQualityComboBox(), null);
			motherboardPanel.add(getMbPriceComboBox(), null);
		}
		return motherboardPanel;
	}

	/**
	 * This method initializes mbManComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getMbManComboBox() {
		if (mbManComboBox == null) {
			mbManComboBox = new JComboBox( manufacturers);
			mbManComboBox.setSelectedIndex(0);
		}
		return mbManComboBox;
	}

	/**
	 * This method initializes mbQualityComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getMbQualityComboBox() {
		if (mbQualityComboBox == null) {
			mbQualityComboBox = new JComboBox( qualities);
			mbQualityComboBox.setSelectedIndex(0);
		}
		return mbQualityComboBox;
	}

	/**
	 * This method initializes mbPriceComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getMbPriceComboBox() {
		if (mbPriceComboBox == null) {
			mbPriceComboBox = new JComboBox( prices);
			mbPriceComboBox.setSelectedIndex(0);
		}
		return mbPriceComboBox;
	}

	/**
	 * This method initializes addCompButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getAddCompButton() {
		if (addCompButton == null) {
			addCompButton = new JButton("Add Configuration");
			addCompButton.setHorizontalAlignment(SwingConstants.CENTER);
			addCompButton.addActionListener( new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					//cpu
					double price = getPrice( (String)cpuPriceComboBox.getSelectedItem());
					double quality = getQuality( (String)cpuQualityComboBox.getSelectedItem());
					String manufacturer = (String)cpuManComboBox.getSelectedItem();
					Component cpu = new Component("CPU", manufacturer, quality, price);
					//gpu
					price = getPrice( (String)gpuPriceComboBox.getSelectedItem());
					quality = getQuality( (String)gpuQualityComboBox.getSelectedItem());
					manufacturer = (String)gpuManComboBox.getSelectedItem();
					Component gpu = new Component("GPU", manufacturer, quality, price);
					//motherboard
					price = getPrice( (String)mbPriceComboBox.getSelectedItem());
					quality = getQuality( (String)mbQualityComboBox.getSelectedItem());
					manufacturer = (String)mbManComboBox.getSelectedItem();
					Component mb = new Component("M.Board", manufacturer, quality, price);
					//computer
					Computer c = new Computer(cpu, gpu, mb);
					agent.getTask().computerList.add(c);
					infoTextArea.append("Computer added.\n");
					
				}
			});
		}
		return addCompButton;
	}

	/**
	 * This method initializes completeOrderButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getCompleteOrderButton() {
		if (completeOrderButton == null) {
			completeOrderButton = new JButton("Complete the order!");
			completeOrderButton.addActionListener( new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					/*ACLMessage startMsg = new ACLMessage( ACLMessage.INFORM);
					startMsg.setOntology("Startup");
					startMsg.setContent("Start execution");
					startMsg.addReceiver(agent.getAID());
					agent.send(startMsg);*/
					agent.setAnnounceReady(true);
					infoTextArea.append("Completed the order.\n");
					
				}
			});
		}
		return completeOrderButton;
	}

	/**
	 * This method initializes textAreaPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getTextAreaPanel() {
		if (textAreaPanel == null) {
			textAreaPanel = new JPanel();
			textAreaPanel.setLayout(new BorderLayout());
			textAreaPanel.setPreferredSize(new Dimension(400, 150));
			textAreaPanel.add(getInfoTextArea(), BorderLayout.NORTH);
		}
		return textAreaPanel;
	}

	/**
	 * This method initializes infoTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getInfoTextArea() {
		if (infoTextArea == null) {
			infoTextArea = new JTextArea();
			infoTextArea.setPreferredSize(new Dimension(400, 145));
			JScrollPane scrollPane = new JScrollPane( infoTextArea);
			infoTextArea.setEditable(false);
		}
		return infoTextArea;
	}

	/**
	 * This method initializes specifyOrderPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getSpecifyOrderPanel() {
		if (specifyOrderPanel == null) {
			GridLayout gridLayout4 = new GridLayout();
			gridLayout4.setRows(6);
			specifyOrderPanel = new JPanel();
			specifyOrderPanel.setLayout(gridLayout4);
			specifyOrderPanel.add(specifyOrderLabel, null);
			specifyOrderPanel.add(getCpuPanel(), null);
			specifyOrderPanel.add(getGpuPanel(), null);
			specifyOrderPanel.add(getMotherboardPanel(), null);
			specifyOrderPanel.add(getAddCompButton(), null);
			specifyOrderPanel.add(getCompleteOrderButton(), null);
		}
		return specifyOrderPanel;
	}

	public ManagerAgent getAgent() {
		return agent;
	}
	
	private double getQuality( String string)
	{
		if ( string.equals( "Average"))
			return 100;
		else if ( string.equals( "Good"))
			return 500;
		else if ( string.equals( "Best"))
			return 1000;
		else
			return 1;
	}
	
	private double getPrice( String string)
	{
		if ( string.equals("100"))
			return 100;
		else if ( string.equals("1000"))
			return 1000;
		else
			return 1;
	}



}  //  @jve:decl-index=0:visual-constraint="11,-10"
