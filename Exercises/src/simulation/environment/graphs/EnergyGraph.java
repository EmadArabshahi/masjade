package simulation.environment.graphs;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JPanel;

import org.jfree.*;
import org.jfree.chart.*;
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

import simulation.environment.LogicalEnv;

public class EnergyGraph extends ChartPanel
{

	private static XYSeries _meanEnergy = new XYSeries("Mean Energy Level");
	private static XYSeries _energyVariance = new XYSeries("Energy Variance");
		
	private static XYSeries _agentType1AliveRatios = new XYSeries("Agent Type 1");
	private static XYSeries _agentType2AliveRatios = new XYSeries("Agent Type 2");
	private static XYSeries _agentType3AliveRatios = new XYSeries("Agent Type 3");
	private static XYSeries _agentType4AliveRatios = new XYSeries("Agent Type 4");
	
	public EnergyGraph()
	{
		super(createChart(createDataset()));
		setFillZoomRectangle(true);
        setMouseWheelEnabled(true);
		setPreferredSize(new java.awt.Dimension(500, 270));
	}
	
	/**
     * Creates a chart.
     *
     * @param dataset  a dataset.
     *
     * @return A chart.
     */
    private static JFreeChart createChart(XYDataset dataset) 
    {
    	
    	JFreeChart chart = ChartFactory.createXYLineChart(
    			"Mean Energy Level",   		// title 
    			"Round", 			   		// x-axis label
    			"Energy",              		// y-axis label
    			dataset,			   		// data 
    			PlotOrientation.VERTICAL,   // orientation
    			true, 						// create legend?
    			true, 						// generate tooltips?
    			false						// generate urls?
    			);
    	
        chart.setBackgroundPaint(Color.white);

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);

        XYItemRenderer r = plot.getRenderer();
        if (r instanceof XYLineAndShapeRenderer) {
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
            renderer.setBaseShapesVisible(true);
            renderer.setBaseShapesFilled(true);
            renderer.setDrawSeriesLineAsPath(true);
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
    private static XYDataset createDataset() 
    {
        
        //TimeSeriesCollection dataset = new TimeSeriesCollection();
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(_meanEnergy);
        dataset.addSeries(_energyVariance);
        return dataset;

    }
    
    public static void update(int roundNr, int[][] agentEnergyLevels)
    {   		
    	double meanEnergyLevel = calulateMeanEnergyLevel(agentEnergyLevels);
    	double varianceEnergyLevel = calculateVarianceEnergyLevel(agentEnergyLevels, meanEnergyLevel);
    	double[] aliveRatios = calculateAliveRatios(agentEnergyLevels);
    	
    	_meanEnergy.add(roundNr, meanEnergyLevel);
    	_energyVariance.add(roundNr, varianceEnergyLevel);
    	_agentType1AliveRatios.add(roundNr, aliveRatios[0]);
    	_agentType2AliveRatios.add(roundNr, aliveRatios[1]);
    	_agentType3AliveRatios.add(roundNr, aliveRatios[2]);
    	_agentType4AliveRatios.add(roundNr, aliveRatios[3]);
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
    		aliveRatios[i] = ((double)aliveAgents) / ((double)totalAgents);
    	}
    	
    	return aliveRatios;
    }
    
    private static double calculateVarianceEnergyLevel(int[][] agentEnergyLevels, double mean)
    {
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
    
    private static double calulateMeanEnergyLevel(int[][] agentEnergyLevels)
    {
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
