

package com.nd;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import com.nd.ui.NormalDistributionGUI;
import com.nd.ui.TopFrame;

/**
 * @author Neha Dadhich
 */

 /**
 * This is the main class of the applications.  
 */
public class NormalDistributionMain {
  /**
   * The constructor will create the application frame. 
   * The application frame consists of an input panel, chart panel and z table panel.
   */
    public NormalDistributionMain(){
        
    // Creating the TopFrame. 
    final JFrame topFrame = new TopFrame();
    
    // Creating the inputPanel. 
    final JPanel inputPanel = NormalDistributionGUI.getInputPanel();
    
    // Creating the chartPanel.
    JPanel bellChartPanel = NormalDistributionGUI.createBellchartPanel(topFrame);
    
    // Creating the ztablePanel.
    JPanel ztablePanel = NormalDistributionGUI.createZtablePanel();
    
    // Adding the chart and ztable panels to the chartAndTabelPanel (JSplitPane) and splitting it vertically.
    JSplitPane chartAndTablePanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT,bellChartPanel,ztablePanel);
    
    // Adding the inputPanel and chartAndTabePanel to the topPanel (JSplitPane) and splitting it horizontally.
    JSplitPane topPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,inputPanel,chartAndTablePanel);
    
    // Setting the size of topFrame as full screen.
    topFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    
    // Setting the panel to the frame   
    topFrame.setContentPane(topPanel);
    
    // Sizing the window to fit the sizing and layout of subcomponents. 
    topFrame.pack(); 
    
    // Setting the frame in the center of the screen.
    topFrame.setLocationRelativeTo(null);
    
    // Making the frame visible.
    topFrame.setVisible(true);
    }
/**
 * The main method will call the constructor of the NormalDistributionMain. 
 * @param args 
 */
    public static void main(String[] args) {
        new NormalDistributionMain();
    }
    
}
