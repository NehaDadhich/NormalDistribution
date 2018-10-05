package com.nd.util;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;

import static com.nd.data.ZTable.table;

/**
 * @author Neha Dadhich
 */
/**
 * This class will used to perform general methods for the application.
 * 
 */
public class NDUtil {
    /**
     * This method will convert a string to double.
     * @param string
     * @return double
     */
    public static double toDouble(String dStr) {
        return Double.parseDouble(dStr);
    }
    /**
     * This method will round a decimal to a single decimal place.
     * @param double
     * @return double
     */
    public static double toSingleDecimal(double value) {
        return Math.round(value * 10.0) / 10.0;
    }
    /**
     * This method will round a decimal to two decimal places.
     * @param double
     * @return double
     */
    public static double toDoubleDecimal(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
    /**
     * This method returns a double value in z value format i.e., four decimal places.
     * @param double
     * @return String
     */
    public static String zValueFormat(double value) {
        DecimalFormat df = new DecimalFormat("0.0000");
        return df.format(value);
    }
    /**
     * This method returns a double value in result format i.e., 10 decimal places.
     * @param double
     * @return String
     */
    public static String resultFormat(double value) {
        DecimalFormat df = new DecimalFormat("0.0000000000");
        return df.format(value);
    }
    /**
     * This method will get the key for the table.
     * @param double (value)
     * @param int (noOfDecimals)
     * @return double
     */
    public static double getKeyForTable(double value, int noOfDecimals) {
        // converting to string
        String strInput = value + "000";
        int index = strInput.indexOf('.');
        // Handle non decimal values
        if (index == -1) {
            if (noOfDecimals == 1) return 0.0;
            else return 0.00;
        }
        String resultStr = strInput.substring(0, index + noOfDecimals + 1);
        double result = Double.parseDouble(resultStr);
        return result;
    }
    /**
    * This method returns the index of the table.
    * @param value
    * @return integer
    */
   public static int getIndex(double value) {
        String dblDecimal = toDoubleDecimal(value) + "000";

        String result = dblDecimal.substring(dblDecimal.length() - 1);
        return Integer.parseInt(result);
    }
  /**
   * This method will display the error message in the JOptionPane Message Dialog box.
   * @param String
   */
    public static void notifyError(String message) {
        JOptionPane.showMessageDialog(null, message);
       // System.out.println(ex);
    }
   /**
   * This method creates the default zTable which starts from -3.0 to 3.0
   * @param tableModel 
   */
    public static void populateDefaultTable(DefaultTableModel tableModel) {
        populateTable(tableModel, -3.0, 3.0);
    }
    /**
    * This method will populate the zTable with the passed startValues and endValues.
    * @param DefaultTableModel
    * @param double (startValue)
    * @param double (endValue)
    */
    public static void populateTable(DefaultTableModel tableModel, double startV, double endV) {
        tableModel.setRowCount(0);

        double sv = toSingleDecimal(startV);
        double ev = endV + 0.00000000001;

        for (double i = sv; i <= ev; i += 0.1) {
            double doubleKey = toSingleDecimal(i);
            Double key = Double.valueOf(doubleKey);
            tableModel.addRow(table.get(key));
        }
    }
    /**
    * This methods adds the component to the panel with specified positions (x and y), size (width and height) and alignment passed.
    * The layout used is GridBagLayout.
    * @param JPanel (panel)
    * @param JComponent (component)
    * @param int (x)
    * @param int (y)
    * @param int (width)
    * @param  int (height)
    * @param int (align)
    */
    public static void addComponent(JPanel panel, JComponent component, int x, int y, int width, int height, int align) {
        GridBagConstraints gbConstraints = new GridBagConstraints();
        gbConstraints.gridx = x;
        gbConstraints.gridy = y;
        gbConstraints.gridwidth = width;
        gbConstraints.gridheight = height;
        gbConstraints.weightx = 100.0;
        gbConstraints.weighty = 100.0;
        gbConstraints.insets = new Insets(5, 5, 5, 5);
        gbConstraints.anchor = align;
        gbConstraints.fill = GridBagConstraints.NONE;
        panel.add(component, gbConstraints);
    }
}
