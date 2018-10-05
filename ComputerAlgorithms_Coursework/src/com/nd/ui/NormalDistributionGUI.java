package com.nd.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYDifferenceRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.nd.util.NDCalculator;
import static com.nd.util.NDChartUtil.*;
import static com.nd.util.NDFormUtil.*;
import static com.nd.util.NDUtil.*;
import static com.nd.util.NDValidator.*;
/**
 * @author Neha Dadhich
 */
/**
 * This class will create the GUI components of the class.
 */
public class NormalDistributionGUI {
    
    // Bell chart related components
    private static JPanel bellchartPanel;
    private static CardLayout chartPanelLayout = new CardLayout();

    // Z Table related components
    private static CardLayout ztableLayout = new CardLayout();
    private static JPanel ztablePanel = new JPanel(ztableLayout);
    private static DefaultTableModel tableModel = new DefaultTableModel();
    
    /**
     * This methods creates and returns the input panel.
     * @return JPanel
     */
    public static JPanel getInputPanel() {

        // Creating the JPanel with GridBagLayout. 
        /* GridBagLayout allows to place components according to the cell location. 
           It also allows to choose the width and height of each component.
        */
        JPanel formPanel = new JPanel(new GridBagLayout());
        populateForm(formPanel); // adding components to the form.
       
        JPanel inputPanel = new JPanel(new GridBagLayout());
        // adding the inputPanel to the form panel.
        addComponent(inputPanel, formPanel, 0, 0, 1, 1, GridBagConstraints.NORTH);

        return inputPanel; // returing the input panel.
    }

    /**
     * This methods adds the components to the input Panel
     * @param formPanel (JPanel)
     */
    private static void populateForm(JPanel formPanel) {

        // setting the border and title to the form panel.
        setFormPanelTitle(formPanel);

        // Creating the userInputPanel.
        JPanel userInputPanel = new JPanel(new GridBagLayout());
        JTextField meanTF = new JTextField(10); 
        addMeanField(meanTF, userInputPanel);  // adding the mean textfield to the userInputPanel.
        JTextField sdTF = new JTextField(10); 
        addSdField(sdTF, userInputPanel);    // adding the standard deviation textfield to the userInputPanel.
        JTextField zValueStartTF = new JTextField(10);
        addStartZvalueField(zValueStartTF, userInputPanel); // adding the zValueStartTF to the userInputPanel.
        JTextField zValueEndTF = new JTextField(10);
        addEndZvalueField(zValueEndTF, userInputPanel); // adding the zValueEndTF to the userInputPanel.

        JComboBox<String> integrations = new JComboBox<String>(new String[]{"simpson", "trapezoidal"});
        addIntegrations(integrations, userInputPanel); // adding the integrations combobox to userInputPanel.
       
        // adding the userInputPanel to the formPanel.
        addComponent(formPanel, userInputPanel, 0, 0, 3, 1, GridBagConstraints.NORTH);

        // Creating the range panel.
        JPanel rangePanel = getRangePanel();
         
        
        JRadioButton below = new JRadioButton("Below");
        JTextField belowTF = new JTextField(4);  
        setBelowRangeButton(below, belowTF); // setting the below field and radiobutton 

        JRadioButton above = new JRadioButton("Above");
        JTextField aboveTF = new JTextField(4);
        setAboveRangeButton(above); // setting the above button.

        JRadioButton between = new JRadioButton("Between");
        JTextField betweenTF = new JTextField(4);
        between.setActionCommand("INRANGE"); // setting the command for inrange button.
        JLabel and = new JLabel(" and ");
        JTextField betweenSTF = new JTextField(4);

        JRadioButton outside = new JRadioButton("Below");
        JTextField outsideTF = new JTextField(4);
        outside.setActionCommand("OUTRANGE"); // setting the  command for outrange button.
        JLabel or = new JLabel(" or  Above");
        JTextField outsideSTF = new JTextField(4);
        // adding the radio buttons to the button group.
        ButtonGroup rangeGroup = createRangeButtonGroup(below, above, between, outside);

        // adding below and above fields to the range panel.
        addBelowAndAboveFields(rangePanel, below, belowTF, above, aboveTF);
        // adding betweeen fields to the range panel. 
        addBetweenFields(rangePanel, between, betweenTF, and, betweenSTF);
        // adding outside range fields to the range panel.
        addOutsideRangeFields(rangePanel, outside, outsideTF, or, outsideSTF);
        // adding range panel to the form panel.
        addComponent(formPanel, rangePanel, 0, 1, 3, 1, GridBagConstraints.CENTER);

        JButton showResults = new JButton("Show results");
        // adding showresults button to the panel.
        addComponent(formPanel, showResults, 0, 2, 3, 1, GridBagConstraints.CENTER);


        // creating the result panel.
        JPanel resultPanel = getResultPanel();
        JLabel tableL = new JLabel("Probability using z table:");
        JLabel integrationL = new JLabel("Probability using integration:");
        // adding table and integration label to the result panel.
        addResultFields(resultPanel, tableL, integrationL);
        // adding the result panel to the form panel.
        addComponent(formPanel, resultPanel, 0, 3, 3, 1, GridBagConstraints.CENTER);
     
      // addding action listener to the showResults button.
        showResults.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String mean = meanTF.getText();
                String sd = sdTF.getText();
                // if the values entered for mean and sd are not valid, the control will be returned from here.
                if(!validateMeanAndSd(mean, sd)) 
                { 
                    return; 
                }
                // If the ztable start value and end values are not entered, the control will be retuned from here.
                if(!validateZtableValues(zValueStartTF.getText(), zValueEndTF.getText())) {
                 return;
                }
                else {
                double dMean = toDouble(mean);
                double dSd = toDouble(sd);
                String rangeType = rangeGroup.getSelection().getActionCommand();
                String integrationMethod = (String)integrations.getSelectedItem();
                NDCalculator cal = new NDCalculator(dMean, dSd);
                boolean isMax = false;
                double xValue = 0.0; // user entered single value
                double sValue = 0.0; //start value
                double eValue = 0.0; //end value
                
              
             
               
                String resultFromPDF = "0.0";
                String resultFromZTable = "";
               // If the user has selected max option then less than probability will be calculated.
                if (rangeType.equals("MAX")) {
                    // If the value entered in the text field is not valid, the control will be returned from here.
                    if(!validateBelowTF(belowTF.getText())) return;
                    xValue = toDouble(belowTF.getText());
                    // calculating the result using the integration method specified. 
                    resultFromPDF = cal.lessThanProbability(xValue, integrationMethod);
                    // calculating the result using z table.
                    resultFromZTable = cal.lessThanProbFromTable(xValue);
                    // Since the max option was selected, setting isMax as true.
                    isMax = true;
                } 
                // If the user has selected min option then more than probability will be calculated.
                else if (rangeType.equals("MIN")) {
                    // If the value entered in the text field is not valid, the control will be returned from here.
                    if(!validateAboveTF(aboveTF.getText())) return;
                    xValue = toDouble(aboveTF.getText());
                    // calculating the result using the integration method specified. 
                    resultFromPDF = cal.moreThanProbabilty(xValue, integrationMethod);
                    // calculating the result using z table.
                    resultFromZTable = cal.moreThanProbFromTable(xValue);
                    // Since the min option was selected, setting isMax as false.
                    isMax = false;
                } 
                // If the user has selected inrange option then inrange probability is calculated.
                else if (rangeType.equals("INRANGE")) {
                    // If the values entered are not valid, the control will be returned from here.
                    if(! validateInRangeTF(betweenTF.getText(), betweenSTF.getText())) return;
                    sValue = toDouble(betweenTF.getText());
                    eValue = toDouble(betweenSTF.getText());
                    // calculating the result using the integration method specified.
                    resultFromPDF = cal.inrangeProbability(sValue, eValue, integrationMethod);
                   // calculating the result using ztable. 
                    resultFromZTable = cal.inrangePFromTable(sValue, eValue);
                }
                // If nothing of the above is true, then the user has selected outrange option hence, outrange probability is calculated.
                else {
                    // If the values entered are not valid, the control will be returned from here.
                   if(!validateOutRangeTF(outsideTF.getText(), outsideSTF.getText())) return;
                    sValue = toDouble(outsideTF.getText());
                    eValue = toDouble(outsideSTF.getText());
                    // calculating the result using the integration method specified.
                    resultFromPDF = cal.outsideRangeProbability(sValue, eValue, integrationMethod);
                    // calculating the result using ztable. 
                    resultFromZTable = cal.outsideRangePFromTable(sValue, eValue);
                }

                String tableResult = "Probability using z table: " + resultFromZTable;
                String integrationResult = "Probability using "+ integrationMethod +" rule: " + resultFromPDF;
                // displaying the answer to the user.
                integrationL.setText(integrationResult);
                tableL.setText(tableResult);
                
                populateTable(tableModel,toDouble(zValueStartTF.getText()),toDouble(zValueEndTF.getText()));

                // draw chart for given inputs
                // if the user has selected below or above option, the graph will be updated accordingly.
                if (rangeType.equals("MAX") || rangeType.equals("MIN")) {
                    updateChartByValue(cal, xValue, isMax);
                } 
                // if the user has selected inrage option, the graph will be updated accordingly.
                else if (rangeType.equals("INRANGE")) {
                    updateRangeChart(cal, sValue, eValue);
                } 
                // if nothing of the above is true, this means user has selected outrange hence, the graph will be updated accordingly.
                else {
                    drawOutrangeChart(cal, sValue, eValue);
                }
                }
            }
        });

    }

    /**
     * This method creates and returns the default chart panel.
     * @param JFrame (mainFrame)
     * @return JPanel
     */
    public static JPanel createBellchartPanel(JFrame mainFrame) {
        // Creating the  JPanel using CardLayout.
        bellchartPanel = new JPanel(chartPanelLayout);
        // Creating the default ChartPanel.
        ChartPanel defaultPanel = getDefaultChart();
        // Adding the ChartPanel to JPanel.
        bellchartPanel.add(defaultPanel, "default");
        // Flipping bellChartPanel with defaultPanel.
        chartPanelLayout.show(bellchartPanel, "default");
        return bellchartPanel; // returning the ChartPanel.
    }
    /**
     * This method will update the chart for below or above case.
     * @param NDCalculator (cal)
     * @param double (x)
     * @param boolean (isMax) 
     */
    public static void updateChartByValue(NDCalculator cal, double x, boolean isMax) {
       // Creating the dataset with mean and sd.
        XYDataset dataset = getBellcurveDataset(cal.getMean(), cal.getSD());
        // Creating the chart with the dataset.
        JFreeChart chart = getJFreeChart(dataset);
        // if x is below (mean - 6 * sd) or above (mean + 6*sd) then just show bell curve
        if((isMax & (cal.belowSixSigma(x))) || (!isMax & (cal.aboveSixSigma(x)))){
            showUpdatedChart(chart, bellchartPanel, chartPanelLayout);
            return;
        }
        // Creating the XYPlot.
        XYPlot plot = chart.getXYPlot();
        // Creating the NumberAxis
        NumberAxis domain = (NumberAxis) plot.getDomainAxis();
        // This takes care even, if zero falls outside range
        domain.setAutoRangeStickyZero(false);
        // Creating the XYSeriesCollection
        XYSeriesCollection newDataset = (XYSeriesCollection) dataset;
        // Creating XYSeries.
        XYSeries area = new XYSeries("");

        // if isMax true, shade left hand side.
        // probability of value less than or equal to x (eg: probability of a person with iq less than 110)
        // if isMax false, shade right hand side.
        // probability of value greate than or equal to x (eg: probability of a person with iq more than 110)

        if (isMax) area.add(cal.curveStart(), 0); // This will paints from left end.
        else area.add(cal.curveEnd(), 0); // This will paints from right end
        // one more layer. Now difference will be highlighted
        area.add(x, 0);
        // adding the area to the dataset (sends a DataChangeEvent)
        newDataset.addSeries(area);
       // Creating the XYDifferenceRenderer.
        XYDifferenceRenderer diffRenderer = getDiffRenderer();
        // Setting the renderer for diffRenderer with index 0.
        plot.setRenderer(0, diffRenderer);
        // showing the updated chart.
        showUpdatedChart(chart, bellchartPanel, chartPanelLayout);
    }
    /**
     * This method updates the chart for between startValue and endValue.
     * @param NDCalculator (cal)
     * @param double (startValue)
     * @param double (endValue) 
     */
    public static void updateRangeChart(NDCalculator cal, double startValue, double endValue) {
       // Creating the dataset with mean and sd.
        XYDataset dataset = getBellcurveDataset(cal.getMean(), cal.getSD());
        // Creating the chart with the dataset.
        JFreeChart chart = getJFreeChart(dataset);
        // Creating the XYPlot.
        XYPlot plot = chart.getXYPlot();
        // Creating the NumberAxis
        NumberAxis domain = (NumberAxis) plot.getDomainAxis();
        // This takes care even, if zero falls outside range
        domain.setAutoRangeStickyZero(false);
        // Creating the XYSeriesCollection
        XYSeriesCollection newDataset = (XYSeriesCollection) dataset;
        // Creating XYSeries.
        XYSeries area = new XYSeries("");
        // layering diff datasets. Diff of sets will be highlighted
        area.add(startValue, 0);
        area.add(endValue, 0);
        // adding the area to the dataset (sends a DataChangeEvent)
        newDataset.addSeries(area);
        // Creating the XYDifferenceRenderer.
        XYDifferenceRenderer diffRenderer = getDiffRenderer();
         // Setting the renderer for diffRenderer with index 0.
        plot.setRenderer(0, diffRenderer);
         // showing the updated chart.
        showUpdatedChart(chart, bellchartPanel, chartPanelLayout);
    }
    /**
     * This method updates the chart for between startValue and endValue.
     * @param NDCalculator (cal)
     * @param double (startValue)
     * @param double (endValue) 
     */
    private static void drawOutrangeChart(NDCalculator cal, double startValue, double endValue) {
        // Creating the dataset with mean and sd.
        XYDataset dataset = getBellcurveDataset(cal.getMean(), cal.getSD());
        // Creating the chart with the dataset.
        JFreeChart chart = getJFreeChart(dataset);
        // Creating the XYPlot.
        XYPlot plot = chart.getXYPlot();
        // Creating the NumberAxis
        NumberAxis domain = (NumberAxis) plot.getDomainAxis();
        // This takes care even, if zero falls outside range
        domain.setAutoRangeStickyZero(false);
        // Creating the XYSeriesCollection
        XYSeriesCollection newDataset = (XYSeriesCollection) dataset;
        // Creating XYSeries.
        XYSeries area = new XYSeries("area");
        // defing the curveHeight
        double curveHeight = toDoubleDecimal(0.4 / cal.getSD()); // Roughly 0.4/sd
        // layering diff datasets. Diff of sets will be highlighted
        area.add(cal.curveStart(), 0); // This will paints from left end.
        area.add(startValue, 0); // highlighting difference in left end.
        area.add(startValue + 0.01, curveHeight); // Adding negligible value to negate double precision error
        area.add(endValue - 0.01, curveHeight); // Removing negligible value to negate double precision error
        area.add(endValue, 0); // This will paints from right end.
        area.add(cal.curveEnd(), 0); // highlighting difference in right end.
        // adding the area to the dataset (sends a DataChangeEvent)
        newDataset.addSeries(area);
        // Creating the XYDifferenceRenderer.
        XYDifferenceRenderer renderer = getDiffRenderer();
        // Setting the renderer for diffRenderer with index 0.
        plot.setRenderer(0, renderer);
        // showing the updated chart.
        showUpdatedChart(chart, bellchartPanel, chartPanelLayout);
    }

/**
 * This method will create and return the zTable panel.
 * @return JPanel (zTable)
 */
    public static JPanel createZtablePanel() {
        // creating the table
        JTable zTable = new JTable(tableModel);
        // creating the columns for ztable
        String[] columns = {"Z", "0.00", "0.01", "0.02", "0.03", "0.04", "0.05", "0.06", "0.07", "0.08", "0.09"};
        zTable.getTableHeader().setReorderingAllowed(false); // setting the reordering of zTable as false.
       
        zTable.getTableHeader().setBackground(Color.blue);
        zTable.getTableHeader().setForeground(Color.white);
        // adding columns to the table.
        for (String column : columns) {
            tableModel.addColumn(column);
        }
        // Creating the scrollPane
        JScrollPane scrollPane = new JScrollPane(zTable);
        scrollPane.setMinimumSize(scrollPane.getMaximumSize());
        // populating the default ztable.
        populateDefaultTable(tableModel);
       // adding the scrollpane to the ztablepanel
        ztablePanel.add(scrollPane, "default");
        ztableLayout.show(ztablePanel, "default");

        return ztablePanel; // returning the ztablepanel.
    }
}



