package contractnet.src;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;

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
	private JScrollPane textAreaPanel = null;
	private JTextArea infoTextArea = null;
	private JPanel specifyOrderPanel = null;
	public String taskStatus = "Not yet sent";
	
	public HashMap<String, String> responses = new HashMap<String, String>();
	/**
	 * This is the default constructor
	 */
	public ManagerWindow( ManagerAgent agent) {
		super();
		initialize();
		this.agent = agent;
		this.setTitle("Manager: " + agent.getLocalName());
		updateInfoTextArea();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(400, 300);
		this.setContentPane(getJContentPane());
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
					updateInfoTextArea();
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
					agent.setAnnounceReady(true);
					
					taskStatus = "Task sent, awaiting responses";
					updateInfoTextArea();					
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
	private JScrollPane getTextAreaPanel() {
		if (textAreaPanel == null) {
			textAreaPanel = new JScrollPane(getInfoTextArea());	
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
	
	private String getQualityDisplay (double value)
	{
		if (value == 100)
			return "Average";
		else if (value == 500)
			return "Good";
		else if (value == 1000)
			return "Best";
		else
			return "Not Set";
		
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
	
	public void updateInfoTextArea()
	{
		infoTextArea.setText("");
		
		infoTextArea.append(String.format("Task status: %s\n", taskStatus ));
		
		Iterator<String> keys = responses.keySet().iterator();
		
		while (keys.hasNext())
		{
			String key = keys.next();
			infoTextArea.append(String.format("\t%s: %s\n", key, responses.get(key)));
		}
		
		Iterator<Bid> bids = agent.getBids().iterator();
		
		if (bids.hasNext())
			infoTextArea.append("Bids:\n");
		
		while (bids.hasNext())
		{
			Bid bid = bids.next();
			
			Iterator<Computer> computers = bid.computerList.iterator();
			
			if (computers.hasNext())
				infoTextArea.append(String.format("- %s:\n", bid.bidderID.getLocalName()));
			
			int configCounter = 1;
			while (computers.hasNext())
			{
				Computer computer = computers.next();
			
				Component cpu = computer.getCpu();
				Component gpu = computer.getGpu();
				Component mb = computer.getMotherboard();
				
				infoTextArea.append(String.format("\tConfiguration %s: \n\tComponent: Manufacturer / Quality / Price \n\tCPU: %s / %s / %s \n\tGPU: %s / %s / %s \n\tM.Board:%s / %s / %s\n", 
						configCounter,
						getManufacturerDisplay(cpu.getManufacturer()), getQualityDisplay(cpu.getQuality()), getPriceDisplay(cpu.getPrice()),
						getManufacturerDisplay(gpu.getManufacturer()), getQualityDisplay(gpu.getQuality()), getPriceDisplay(gpu.getPrice()),
						getManufacturerDisplay(mb.getManufacturer()), getQualityDisplay(mb.getQuality()), getPriceDisplay(mb.getPrice())));
				
				configCounter++;
				infoTextArea.append("\n");
			}
		}
			
		
		Iterator<Computer> i = agent.getTask().computerList.iterator();
		
		if (i.hasNext())
			infoTextArea.append("Request:\n");
		
		int configCounter = 1;
		while (i.hasNext())
		{
			Computer computer = i.next();
			
			Component cpu = computer.getCpu();
			Component gpu = computer.getGpu();
			Component mb = computer.getMotherboard();
			
			infoTextArea.append(String.format("\tConfiguration %s: \n\tComponent: Manufacturer / Quality / Price \n\tCPU: %s / %s / %s \n\tGPU: %s / %s / %s \n\tM.Board:%s / %s / %s\n", 
					configCounter,
					getManufacturerDisplay(cpu.getManufacturer()), getQualityDisplay(cpu.getQuality()), getPriceDisplay(cpu.getPrice()),
					getManufacturerDisplay(gpu.getManufacturer()), getQualityDisplay(gpu.getQuality()), getPriceDisplay(gpu.getPrice()),
					getManufacturerDisplay(mb.getManufacturer()), getQualityDisplay(mb.getQuality()), getPriceDisplay(mb.getPrice())));
			
			configCounter++;
			infoTextArea.append("\n");
		}
	}
	
	private String getManufacturerDisplay(String string)
	{
		if (string.equals(""))
			string = "Not set";
		return string;
	}
	
	private String getPriceDisplay(double price)
	{
		String string = "Not Set";
		if (price > 1)
		{
			string = String.valueOf(price);
		}
		return string;			
	}
}  //  @jve:decl-index=0:visual-constraint="11,-10"
