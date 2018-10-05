
package com.nd.util;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.XYDifferenceRenderer;
import org.jfree.data.function.Function2D;
import org.jfree.data.function.NormalDistributionFunction2D;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.XYDataset;

import javax.swing.*;
import java.awt.*;
/**
 * @author: Neha Dadhich
 */
/**
 * This class has the methods for creating graphs. The graphs are created using JFreeChart library. 
 */
public class NDChartUtil {
       /**
        * This method will create the default chart panel. This will displayed on the click of submit button. 
        * The created chart is returned at the end.
        * @return ChartPanel
        */
    public static ChartPanel getDefaultChart() {
        // Creating the dataset for the default chart.
        XYDataset dataset = getBellcurveDataset(0, 1);
        // passing the dataset to the chartpanel.
        final JFreeChart chart = getJFreeChart(dataset);
        // adding the chart to the chart panel
        final ChartPanel chartPanel = new ChartPanel(chart);
        return chartPanel; // returning the chart panel.
    }

    /**
     * This method creates and returns the data set with the given values.
     * @param double (mean)
     * @param double (sd)
     * @return XYDataset
     */
    public static XYDataset getBellcurveDataset(double mean, double sd){
        // creating normal distrbution function
        Function2D normalDistribution = new NormalDistributionFunction2D(mean, sd);
        // taking 6 sigma as standard chart
        double curveStart = mean - (6*sd); // defining start value of curve
        double curveEnd = mean + (6*sd); // defining end value of curve.
        // creating the dataset of the graph with the given inputs. (Note: 1000 is the number of strips.)
        XYDataset bellcurveDataset = DatasetUtilities.sampleFunction2D(normalDistribution, curveStart, curveEnd, 10000, "Normal Distribution Chart");
        return bellcurveDataset; // returning the dataset
    }
   /**
    * This methods creates and returns a XYLineChart according to the given dataset.
    * @param XYDataset (dataset)
    * @return JFreeChart
    */
    public static JFreeChart getJFreeChart(XYDataset dataset){
        // Creating the XYLineChart with the given dataset and default settings.
        final JFreeChart jfreeChart = ChartFactory.createXYLineChart(
                "Normal Distribution Function",
                "x Axis",
                "y Axis",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        return jfreeChart; // returning the chart.
    }
    /**
     * This method creates and returns the XYDifferenceRenderer. 
     * @return 
     */
    public static XYDifferenceRenderer getDiffRenderer(){
        // Creating a new XYDifferenceRenderer.
        XYDifferenceRenderer diffRenderer = new XYDifferenceRenderer();
        Color noColor = new Color(0,0,0,0);
        // If second series is higher then it will be hidden
        diffRenderer.setNegativePaint(noColor);
        // Diff area border will be hidden
        diffRenderer.setSeriesPaint(1, noColor);
        return diffRenderer; // returning the XYDifferenceRenderer.
    }
    /**
     * This method adds the chart to the JPanel and displays it using CardLayout.
     * @param JFreeChart (chart)
     * @param JPanel (bellchartPanel)
     * @param CardLayout (chartPanelLayout) 
     */
    public static void showUpdatedChart(JFreeChart chart, JPanel bellchartPanel, CardLayout chartPanelLayout){
        ChartPanel chartPanel = new ChartPanel(chart);
        bellchartPanel.add(chartPanel, "updated");
        chartPanelLayout.show(bellchartPanel, "updated");
    }
}
