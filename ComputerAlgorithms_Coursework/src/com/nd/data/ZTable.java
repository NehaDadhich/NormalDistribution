package com.nd.data;

import java.util.HashMap;
import java.util.Map;

import com.nd.util.NDCalculator;
// static import allows to use the public static methods without specifying the class name. 
import static com.nd.util.NDUtil.toDouble;
import static com.nd.util.NDUtil.zValueFormat;

/**
 * @author Neha Dadhich
 */
/**
 * This class will populate the ZTable using a Map.
 * 
 */
public class ZTable {
    
    // Creating a ztable at the start. 
    // The table will be created only once. 
    // The selected rows will be searched from the zTable. This will make the application fast.
    // The key of map is of type double and the value is string array (row of the table).
    public static Map<Double, String[]> table  = populateZtable(-4.1, 4.1);
    
    /**
     * This method will populate the ZTable with the startValue and endValue.
     * @param startValue
     * @param endValue
     * @return HashMap.
     */
    public static Map<Double, String[]> populateZtable(double startValue, double endValue) {
        // Map is an interface and HashMap is the implementation to it. 
        // Creating a new hashmap.
        Map<Double, String[]> result = new HashMap();
        
        // Creating an instance of NDCalculator with mean 0.0 and standard deviation 1.0. 
        // Mean and standard deviation of ztable is 0.0 and 1.0 rewspectively.
        NDCalculator ndCalculator = new NDCalculator(0.0, 1.0);

        // Rounding start value to one decimal.
        double sv = Math.round(startValue * 10.0) / 10.0;
        
        // adding negligible value to ev. This would fix for-loop counter's double precision errors
        double ev = endValue + 0.00000000001;
        
        // This loop will add data to the ztable. 
        for (double i = sv; i <= ev; i += 0.1) {

            // Again round of to 1 decimal place
            double key = Math.round(i * 10.0) / 10.0;
           
            // Creating columns for the ztable.
            String[] column = new String[11];
            // The first column will be the key.
            column[0] = Double.toString(key);
            
            int rowCount = 0;
            
            // This loop will calculate the probability for the z values.
            for (double j = 0.00; j <= 0.09; j += 0.01) {
                // i+j will be passed to lessThanProbability. 
                // The probabiltiy will be calculated using simpson rule.
                
                String zTableValue = ndCalculator.lessThanProbability(i + j, "simpson");
                // Rounding the value to four decimal.
                column[rowCount+1] = zValueFormat(toDouble(zTableValue));
                rowCount++; // incrementing the rowCount with one.
            }
            
            // Getting a double instance of the double value (key).
            Double dKey = Double.valueOf(key);
           // adding the key and column to the map.
            result.put(dKey, column);
        }
        return result; // returning the map.
    }
}
