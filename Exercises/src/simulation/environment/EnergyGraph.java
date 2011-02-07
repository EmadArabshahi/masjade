package simulation.environment;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.ImageProducer;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.*;
import org.jfree.chart.*;
import org.jfree.chart.axis.AxisSpace;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.*;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.general.DatasetGroup;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.*;


public class EnergyGraph extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3922240536901999978L;
	private static XYSeries _meanEnergy = new XYSeries("Mean Energy Level");
	private static XYSeries _energyVariance = new XYSeries("Energy Variance");
		
	private static XYSeries _agentType1AliveRatios = new XYSeries("Agent Type 1");
	private static XYSeries _agentType2AliveRatios = new XYSeries("Agent Type 2");
	private static XYSeries _agentType3AliveRatios = new XYSeries("Agent Type 3");
	private static XYSeries _agentType4AliveRatios = new XYSeries("Agent Type 4");
	
	private static XYSeries _minimalApplePrice = new XYSeries("Minimal Apple price in market");
	private static XYSeries _meanApplePrice = new XYSeries("Average Apple price paid for");
	private static XYSeries _varianceApplePrice = new XYSeries("Variance in apple price paid");
	
	private static ImageIcon[] imageAgents = getImagesAgents();
	
	private static JLabel _aliveRatio1 = new JLabel(imageAgents[7]);
	private static JLabel _aliveRatio2 = new JLabel(imageAgents[1]);
	private static JLabel _aliveRatio3 = new JLabel(imageAgents[3]);
	private static JLabel _aliveRatio4 = new JLabel(imageAgents[9]);
	
	private static String getAliveRatioString(int type, double aliveRatio)
	{
		
		NumberFormat percentFormatter = NumberFormat.getPercentInstance();
		String percentage = percentFormatter.format(aliveRatio);
		
		while(percentage.length() < 6)
			percentage+= " ";
		
		if(type == Agent.GREEDY)
		{
			return "Greedy Agent:   " + percentage + "  ";
		}
		else if(type == Agent.COMMUNIST)
		{
			return "Communist Agent:   " + percentage+ "  ";
		}
		else if(type== Agent.LIBERAL)
		{
			return "Liberal Agent:   " + percentage + "  ";
		}
		else if(type == Agent.FREE_MIND)
		{
			return "Free-minded Agent:   " + percentage + "  ";
		}
		else
		{
			return "wrong type";
		}
	}
	
	

	
	private static ImageIcon[] getImagesAgents()
	{
		ImageIcon[] imgAgents = new ImageIcon[10];
		try 
		{
			
			//Image image = new ImageIcon( EnergyGraph.class.getResource( "image/plus.png" ) ).getImage();
		// Changed SA:
		imgAgents[0] = new ImageIcon( EnergyGraph.class.getResource("images/agents/agent_army.gif"));
		imgAgents[1] = new ImageIcon( EnergyGraph.class.getResource("images/agents/agent_blue.gif"));
		imgAgents[2] = new ImageIcon( EnergyGraph.class.getResource("images/agents/agent_gray.gif"));
		imgAgents[3] = new ImageIcon( EnergyGraph.class.getResource("images/agents/agent_green.gif"));
		imgAgents[4] = new ImageIcon( EnergyGraph.class.getResource("images/agents/agent_orange.gif"));
		imgAgents[5] = new ImageIcon( EnergyGraph.class.getResource("images/agents/agent_pink.gif"));
		imgAgents[6] = new ImageIcon( EnergyGraph.class.getResource("images/agents/agent_purple.gif"));
		imgAgents[7] = new ImageIcon( EnergyGraph.class.getResource("images/agents/agent_red.gif"));
		imgAgents[8] = new ImageIcon( EnergyGraph.class.getResource("images/agents/agent_teal.gif"));
		imgAgents[9] = new ImageIcon( EnergyGraph.class.getResource("images/agents/agent_yellow.gif"));
		}
		catch(Exception e)
		{
			
		}
		return imgAgents;
	}
	
	public EnergyGraph()
	{
		ChartPanel energyLevel = new ChartPanel(createEnergyChart(createEnergyDataset()));
		ChartPanel aliveRatio = new ChartPanel(createAliveChart(createAliveDataset()));
		ChartPanel apples = new ChartPanel(createAppleChart(createAppleDataset()));
		
		energyLevel.setFillZoomRectangle(true);
        energyLevel.setMouseWheelEnabled(true);
		energyLevel.setPreferredSize(new java.awt.Dimension(450, 285));
		
	//	aliveRatio.setFillZoomRectangle(true);
	//	aliveRatio.setMouseWheelEnabled(true);
	//	aliveRatio.setPreferredSize(new java.awt.Dimension(450,230));
		
		apples.setFillZoomRectangle(true);
		apples.setMouseWheelEnabled(true);
		apples.setPreferredSize(new java.awt.Dimension(450,285));
	
		
		
		_aliveRatio1.setText(getAliveRatioString(0,1.0));
		_aliveRatio2.setText(getAliveRatioString(1,1.0));
		_aliveRatio3.setText(getAliveRatioString(2,1.0));
		_aliveRatio4.setText(getAliveRatioString(3,1.0));
		
		add(energyLevel);
		add(_aliveRatio1);
		add(_aliveRatio2);
		add(_aliveRatio3);
		add(_aliveRatio4);
		add(apples);
	}
	
	
	public static void clear()
	{
		_meanEnergy.clear();
		_energyVariance.clear();
		_minimalApplePrice.clear();
		_meanApplePrice.clear();
		_varianceApplePrice.clear();
		_aliveRatio1.setText(getAliveRatioString(0,1.0));
		_aliveRatio2.setText(getAliveRatioString(1,1.0));
		_aliveRatio3.setText(getAliveRatioString(2,1.0));
		_aliveRatio4.setText(getAliveRatioString(3,1.0));
	}
	
	/**
     * Creates a chart.
     *
     * @param dataset  a dataset.
     *
     * @return A chart.
     */
    private static JFreeChart createEnergyChart(XYDataset dataset) 
    {

    	JFreeChart chart = ChartFactory.createXYLineChart(
    			"Energy Level",   		// title 
    			"Round", 			   		// x-axis label
    			"Energy",              		// y-axis label
    			dataset,			   		// data 
    			PlotOrientation.VERTICAL,   // orientation
    			true, 						// create legend?
    			true, 						// generate tooltips?
    			false						// generate urls?
    			);
    	
        chart.setBackgroundPaint(Color.white);

        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainCrosshairVisible(false);
        plot.setRangeCrosshairVisible(false);
        XYItemRenderer r = plot.getRenderer();
        
        
        
        if (r instanceof XYLineAndShapeRenderer) 
        {
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
            renderer.setBaseShapesVisible(true);
            renderer.setBaseShapesFilled(true);
            renderer.setDrawSeriesLineAsPath(false);
        }
        
        
        
   //     DateAxis axis = (DateAxis) plot.getDomainAxis();
   //     axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));

        return chart;

    }
    
    /**
     * Creates a chart.
     *
     * @param dataset  a dataset.
     *
     * @return A chart.
     */
    private static JFreeChart createAliveChart(XYDataset dataset) 
    {

    	JFreeChart chart = ChartFactory.createXYLineChart(
    			"Alive Ratios",   		// title 
    			"Round", 			   		// x-axis label
    			"Alive Ratio",              		// y-axis label
    			dataset,			   		// data 
    			PlotOrientation.VERTICAL,   // orientation
    			true, 						// create legend?
    			true, 						// generate tooltips?
    			false						// generate urls?
    			);
    	
        chart.setBackgroundPaint(Color.white);

        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainCrosshairVisible(false);
        plot.setRangeCrosshairVisible(false);
        XYItemRenderer r = plot.getRenderer();
        
        
        
        if (r instanceof XYLineAndShapeRenderer) 
        {
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
            renderer.setBaseShapesVisible(true);
            renderer.setBaseShapesFilled(true);
            renderer.setDrawSeriesLineAsPath(false);
        }
        
        
        
   //     DateAxis axis = (DateAxis) plot.getDomainAxis();
   //     axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));

        return chart;

    }
    
    /**
     * Creates a chart.
     *
     * @param dataset  a dataset.
     *
     * @return A chart.
     */
    private static JFreeChart createAppleChart(XYDataset dataset) 
    {

    	JFreeChart chart = ChartFactory.createXYLineChart(
    			"Apple prices",   		// title 
    			"Round", 			   		// x-axis label
    			"Price",              		// y-axis label
    			dataset,			   		// data 
    			PlotOrientation.VERTICAL,   // orientation
    			true, 						// create legend?
    			true, 						// generate tooltips?
    			false						// generate urls?
    			);
    	
        chart.setBackgroundPaint(Color.white);

        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainCrosshairVisible(false);
        plot.setRangeCrosshairVisible(false);
        XYItemRenderer r = plot.getRenderer();
        
        
        
        if (r instanceof XYLineAndShapeRenderer) 
        {
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
            renderer.setBaseShapesVisible(true);
            renderer.setBaseShapesFilled(true);
            renderer.setDrawSeriesLineAsPath(false);
        }
        
        
        
   //     DateAxis axis = (DateAxis) plot.getDomainAxis();
   //     axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));

        return chart;

    }
    
    
    
    /**
     * Creates a dataset, consisting of two series of monthly data.
     *
     * @return The dataset.
     */
    private static XYDataset createEnergyDataset() 
    {
        
        //TimeSeriesCollection dataset = new TimeSeriesCollection();
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(_meanEnergy);
        dataset.addSeries(_energyVariance);
        return dataset;

    }
    
    /**
     * Creates a dataset, consisting of two series of monthly data.
     *
     * @return The dataset.
     */
    private static XYDataset createAliveDataset() 
    {
        
        //TimeSeriesCollection dataset = new TimeSeriesCollection();
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(_agentType1AliveRatios);
        dataset.addSeries(_agentType2AliveRatios);
        dataset.addSeries(_agentType3AliveRatios);
        dataset.addSeries(_agentType4AliveRatios);
        return dataset;

    }
    
    
    /**
     * Creates a dataset, consisting of two series of monthly data.
     *
     * @return The dataset.
     */
    private static XYDataset createAppleDataset() 
    {
        
        //TimeSeriesCollection dataset = new TimeSeriesCollection();
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(_meanApplePrice);
        dataset.addSeries(_varianceApplePrice);
        dataset.addSeries(_minimalApplePrice);
        return dataset;

    }
    
    
    
    public static void update(int roundNr, int[][] agentEnergyLevels, int[] pricesPaidForApples, int[] pricesInMarket)
    {   		
    	double meanEnergyLevel = calulateMeanEnergyLevel(agentEnergyLevels);
    	double varianceEnergyLevel = calculateVarianceEnergyLevel(agentEnergyLevels, meanEnergyLevel);
    	double[] aliveRatios = calculateAliveRatios(agentEnergyLevels);
    	double meanApplePrice = calculateMeanApplePrice(pricesPaidForApples);
    	double varianceApplePrice = calculateVarianceApplePrice(pricesPaidForApples, meanApplePrice);
    	double minimalApplePrice = calculateMinimalApplePrice(pricesInMarket);
    	
    	
    	_meanEnergy.add(roundNr, meanEnergyLevel);
    	_energyVariance.add(roundNr, varianceEnergyLevel);
    	if(agentEnergyLevels.length > 3)
    	{
    		_agentType1AliveRatios.add(roundNr, aliveRatios[0]);
    		_agentType2AliveRatios.add(roundNr, aliveRatios[1]);
    		_agentType3AliveRatios.add(roundNr, aliveRatios[2]);
    		_agentType4AliveRatios.add(roundNr, aliveRatios[3]);
    	}
    	_aliveRatio1.setText(getAliveRatioString(0,aliveRatios[0]));
    	_aliveRatio2.setText(getAliveRatioString(1,aliveRatios[1]));
    	_aliveRatio3.setText(getAliveRatioString(2,aliveRatios[2]));
    	_aliveRatio4.setText(getAliveRatioString(3,aliveRatios[3]));
    	
    	
    	_meanApplePrice.add(roundNr, meanApplePrice);
    	_varianceApplePrice.add(roundNr, varianceApplePrice);
    	_minimalApplePrice.add(roundNr, minimalApplePrice);
    	
    }

    
    private static double[] calculateAliveRatios(int[][] agentEnergyLevels)
    {
    	
    	double[] aliveRatios = new double[agentEnergyLevels.length];
    	
    	int aliveAgents = 0;
		int totalAgents = 0;
    	
    	for(int i=0; i<agentEnergyLevels.length; i++)
    	{
    		aliveAgents = 0;
    		totalAgents = 0;
    		for(int j=0; j<agentEnergyLevels[i].length; j++)
    		{
    			if(agentEnergyLevels[i][j] > 0)
    				aliveAgents++;
    			totalAgents += 1;
    		}
    		
    		if(totalAgents == 0)
    			aliveRatios[i] = Double.NaN;
    		else
    			aliveRatios[i] = ((double)aliveAgents) / ((double)totalAgents);
    	}
    	
    	return aliveRatios;
    }
    
    private static double calculateMinimalApplePrice(int[] pricesInMarket)
    {
    	if(pricesInMarket.length == 0)
    		return Double.NaN;
    	
    	double minimumApplePrice = pricesInMarket[0];
    	for(int i=1; i<pricesInMarket.length; i++)
    	{
    		if(pricesInMarket[i] < minimumApplePrice)
    			minimumApplePrice = pricesInMarket[i];
    	}
    	
    	return minimumApplePrice;
    }
    
    private static double calculateVarianceEnergyLevel(int[][] agentEnergyLevels, double mean)
    {
    	if(Double.isNaN(mean))
    		return Double.NaN;
    	
    	double totalDeviation = 0;
    	int totalAgents = 0;
    	
    	for(int i=0; i<agentEnergyLevels.length; i++)
    	{
    		for(int j=0; j<agentEnergyLevels[i].length; j++)
    		{
    			totalDeviation += Math.abs(agentEnergyLevels[i][j] - mean);
    			totalAgents += 1;
    		}
    	}
    	
    	double varianceEnergyLevel = (totalDeviation / ((double)totalAgents));
    	return varianceEnergyLevel;
    }
    
    private static double calculateVarianceApplePrice(int[] pricesPaidForApples, double mean)
    {
    	if(Double.isNaN(mean))
    		return Double.NaN;
    	
    	double totalDeviation = 0;
    	
    	for(int i=0; i<pricesPaidForApples.length; i++)
    	{
    		totalDeviation += Math.abs(((double) pricesPaidForApples[i]) - ((double) mean));
    	}
    	
    	double varianceApplePrice = (totalDeviation / ((double)pricesPaidForApples.length));
    	return varianceApplePrice;
    }
    
    private static double calulateMeanEnergyLevel(int[][] agentEnergyLevels)
    {
    	if(agentEnergyLevels.length == 0)
    		return Double.NaN;
    	
    	
    	int totalEnergy = 0;
    	int totalAgents = 0;
    	
    	for(int i=0; i<agentEnergyLevels.length; i++)
    	{
    		for(int j=0; j<agentEnergyLevels[i].length; j++)
    		{
    			totalEnergy += agentEnergyLevels[i][j];
    			totalAgents += 1;
    		}
    	}
    	
    	
    	double meanEnergyLevel = (((double)totalEnergy) / ((double)totalAgents)) ;
    	return meanEnergyLevel;
    }
    
    private static double calculateMeanApplePrice(int[] pricesPaidForApples)
    {
    	if(pricesPaidForApples.length == 0)
    		return Double.NaN;
    	
    	
    	int totalPrice = 0;
    	
    	for(int i=0; i<pricesPaidForApples.length; i++)
    	{
    		totalPrice += pricesPaidForApples[i];
    	}
    	
    	double meanApplePrice = ((double) totalPrice) / ((double) pricesPaidForApples.length);
    	
    	return meanApplePrice;
    }
    
    /*
    
    /
      Creates a panel for the demo (used by SuperDemo.java).
     
      @return A panel.
     /
    public static JPanel createDemoPanel() {
        JFreeChart chart = createChart(createDataset());
        ChartPanel panel = new ChartPanel(chart);
        panel.setFillZoomRectangle(true);
        panel.setMouseWheelEnabled(true);
        return panel;
    }

    */
    
    /*
    /
      Starting point for the demonstration application.
     
      @param args  ignored.
     /
    public static void main(String[] args) {

        TimeSeriesChartDemo1 demo = new TimeSeriesChartDemo1(
                "Time Series Chart Demo 1");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

    }
    
    */
}
