
package com.nd.util;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

import static com.nd.ui.FormConstants.PANEL_BORDER;
import static com.nd.util.NDUtil.addComponent;

/**
 * @author Neha Dadhich
 */
/**
 * This class consists of methods to add components of the application frame. 
 * It consists of methods to return components.
 */
public class NDFormUtil {
    /**
     * This methods sets the title and border for formPanels.
     * @param JPanel (formPanel) 
     */
    public static void setFormPanelTitle(JPanel formPanel) {
        String titleStr = "Enter sample details";
        TitledBorder formTitle = new TitledBorder(PANEL_BORDER, titleStr);
        formTitle.setTitleJustification(TitledBorder.CENTER);
        formPanel.setBorder(formTitle);
    }
    /**
     * This method sets the title and border for the result panel. It is returned at the end.
     * @return JPanel
     */
    public static JPanel getResultPanel(){
        JPanel resultPanel = new JPanel(new GridBagLayout());
        String resultTitle = "Result";
        Border resultBorder = new LineBorder(Color.RED, 2);
        TitledBorder resultTitleBorder = BorderFactory.createTitledBorder(resultBorder, resultTitle, TitledBorder.CENTER, TitledBorder.TOP);
        resultPanel.setBorder(resultTitleBorder);
        return resultPanel;
    }
    /**
    * This method will add mean label and text field to the formTopPanel.
    * It also sets default value for the text field as 0.0
    * @param JTextField (meanField)
    * @param JPanel (formTopPanel)
    */
    public static void addMeanField(JTextField meanField, JPanel formTopPanel) {
        JLabel meanL = new JLabel("Mean:");
        meanField.setText("0.0"); // default value
        // adding label and textfield to the first row of the panel.
        addComponent(formTopPanel, meanL, 0, 0, 1, 1, GridBagConstraints.EAST);
        addComponent(formTopPanel, meanField, 1, 0, 2, 1, GridBagConstraints.WEST);
    }
    /**
     * This method will add the standard deviation label and text field to the formTopPanel.
     * It also sets the default value of the text field as 1.0.
     * @param JTextField (sdField)
     * @param JPanel (formTopPanel) 
     */
    public static void addSdField(JTextField sdField, JPanel formTopPanel) {
        JLabel sdL = new JLabel("Sd:");
        sdField.setText("1.0"); // default value
        // adding sd label and text fields to the second row of the panel. 
        addComponent(formTopPanel, sdL, 0, 1, 1, 1, GridBagConstraints.EAST);
        addComponent(formTopPanel, sdField, 1, 1, 2, 1, GridBagConstraints.WEST);
    }
    /**
    * This method will the Z start value label and text field to the formTopPanel.
    * It also sets the default value of the text field as -1.0.
    * @param JTextField (zValueStartTF)
    * @param JPanel (formTopPanel)
    */
    public static void addStartZvalueField(JTextField zValueStartTF, JPanel formTopPanel) {
        JLabel zValueStartL = new JLabel("Z value start:");
        zValueStartTF.setText("-1.0"); // default value
        // adding z start value label and text to the third row of the panel.
        addComponent(formTopPanel, zValueStartL, 0, 2, 1, 1, GridBagConstraints.EAST);
        addComponent(formTopPanel, zValueStartTF, 1, 2, 2, 1, GridBagConstraints.WEST);
    }
    /**
    * This method will Z end value label and text field to the formTopPanel.
    * It also sets the default value of text field as 1.0.
    * @param JtextField (zValueEndTF)
    * @param JPanel (formTopPanel)
    */
    public static void addEndZvalueField(JTextField zValueEndTF, JPanel formTopPanel) {
        JLabel zValueEndL = new JLabel("Z value end:");
        zValueEndTF.setText("1.0"); // default value
        // adding z end value label and text field to the fourth row of the panel.
        addComponent(formTopPanel, zValueEndL, 0, 3, 1, 1, GridBagConstraints.EAST);
        addComponent(formTopPanel, zValueEndTF, 1, 3, 2, 1, GridBagConstraints.WEST);
    }
    /**
     * This method will add integration ComboBox to the formPanel. 
     * It also selects simpson method as the default option.
     * @param JComboBox (integrationsJCB)
     * @param JPanel (formTopPanel)
     */
    public static void addIntegrations(JComboBox integrationsJCB, JPanel formTopPanel){
        JLabel integrationsL = new JLabel("Integration type:");
        integrationsJCB.setSelectedItem("simpson"); // setting the default value.
        // adding the comboxbox label and combox to the fourth row of the panel.
        addComponent(formTopPanel, integrationsL, 0, 4, 1, 1, GridBagConstraints.EAST);
        addComponent(formTopPanel, integrationsJCB, 1, 4, 2, 1, GridBagConstraints.WEST);

    }
    /**
     * This method sets the title and border for the range panel. It is returned at tbe end.
     * @return JPanel 
     */
    public static JPanel getRangePanel() {
        JPanel rangePanel = new JPanel(new GridBagLayout());
        String rangeTypesTitle = "required values of outcome";
        Border rangeBorder = new LineBorder(Color.GRAY, 1);
        TitledBorder typesTitleBorder = BorderFactory.createTitledBorder(rangeBorder, rangeTypesTitle, TitledBorder.CENTER, TitledBorder.TOP);
        rangePanel.setBorder(typesTitleBorder);
        return rangePanel;
    }
    /**
     * This method sets the default text for belowTextField and action command as "Max" for below radio button. 
     * By default the radio button is set as selected.
     * @param below
     * @param belowTF 
     */
    public static void setBelowRangeButton(JRadioButton below, JTextField belowTF) {
        belowTF.setText("1"); 
        below.setActionCommand("MAX");
        below.setSelected(true); 
    }
    /**
     * This method sets the action command as min for the above radio button.
     * @param JRadioButton (above) 
     */
    public static void setAboveRangeButton(JRadioButton above) {
        above.setActionCommand("MIN");
    }
    /**
     * This method creates and returns a button group of four radio buttons.
     * @param JRadioButton (below)
     * @param JRadioButton (above)
     * @param JRadioButton (between)
     * @param JRadioButton (outside)
     * @return ButtonGroup
     */
    public static ButtonGroup createRangeButtonGroup(JRadioButton below, JRadioButton above, JRadioButton between, JRadioButton outside) {
        ButtonGroup rangeButtonGroup = new ButtonGroup();
        rangeButtonGroup.add(below);
        rangeButtonGroup.add(above);
        rangeButtonGroup.add(between);
        rangeButtonGroup.add(outside);
        return rangeButtonGroup;
    }
    /**
     * This method will add the label and text field of below and above.
     * @param JPanel (rangePanel)
     * @param JRadioButton (below)
     * @param JTextField (belowTF)
     * @param JRadioButton (above)
     * @param JRadioButton (aboveTF) 
     */
    public static void addBelowAndAboveFields(JPanel rangePanel, JRadioButton below, JTextField belowTF, JRadioButton above, JTextField aboveTF) {

        // adding below range as first row
        addComponent(rangePanel, below, 0, 0, 1, 1, GridBagConstraints.EAST);
        addComponent(rangePanel, belowTF, 1, 0, 1, 1, GridBagConstraints.WEST);

        // adding above range as second row
        addComponent(rangePanel, above, 0, 1, 1, 1, GridBagConstraints.EAST);
        addComponent(rangePanel, aboveTF, 1, 1, 1, 1, GridBagConstraints.WEST);
    }
    /**
     * This method will add the between range labels and text fields to the rangePanel.
     * @param JPanel (rangePanel)
     * @param JRadioButton (between)
     * @param JTextField (betweenTF)
     * @param JLabel (and)
     * @param JTextField (betweenSTF) 
     */
    public static void addBetweenFields(JPanel rangePanel, JRadioButton between, JTextField betweenTF, JLabel and, JTextField betweenSTF) {
        // adding between range as third row
        addComponent(rangePanel, between, 0, 2, 1, 1, GridBagConstraints.EAST);
        addComponent(rangePanel, betweenTF, 1, 2, 1, 1, GridBagConstraints.WEST);
        addComponent(rangePanel, and, 2, 2, 1, 1, GridBagConstraints.CENTER);
        addComponent(rangePanel, betweenSTF, 3, 2, 1, 1, GridBagConstraints.EAST);
    }
    /**
     * This method will add the outside range labels and text field to the range panel. 
     * @param JPanel (rangePanel)
     * @param JRadioButton (outside)
     * @param JTextField (outsideTF)
     * @param JLabel (or)
     * @param JTextField (outsideSTF)
     */
    public static void addOutsideRangeFields(JPanel rangePanel, JRadioButton outside, JTextField outsideTF, JLabel or, JTextField outsideSTF) {
       // adding the outside range components as the fourth row to the panel. 
        addComponent(rangePanel, outside, 0, 3, 1, 1, GridBagConstraints.EAST);
        addComponent(rangePanel, outsideTF, 1, 3, 1, 1, GridBagConstraints.WEST);
        addComponent(rangePanel, or, 2, 3, 1, 1, GridBagConstraints.WEST);
        addComponent(rangePanel, outsideSTF, 3, 3, 1, 1, GridBagConstraints.EAST);
    }
    /**
     * This method will add the result labels to the result panel.
     * @param JLabel (resultPanel)
     * @param JLabel (tableL)
     * @param JLabel (integrationL) 
     */
    public static void addResultFields(JPanel resultPanel, JLabel tableL, JLabel integrationL){
        JLabel resultL = new JLabel("Please enter Mean, SD and expected outcome value/values.");
        addComponent(resultPanel, resultL, 0, 0, 3, 1, GridBagConstraints.NORTH);
        addComponent(resultPanel, tableL, 0, 1, 3, 1, GridBagConstraints.WEST);
        addComponent(resultPanel, integrationL, 0, 2, 3, 1, GridBagConstraints.WEST);
    }
}
