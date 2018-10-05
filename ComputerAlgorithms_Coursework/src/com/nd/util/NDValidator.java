
package com.nd.util;
import com.nd.exceptions.InvalidSDException;

import static com.nd.util.NDUtil.notifyError;
import static com.nd.util.NDUtil.toDouble;
/**
 * @author Neha Dadhich
 */
/**
 * This class will has methods to validate the values entered by the user.
 */
public class NDValidator {
    /**
     * This method will check if mean and standard deviations are entered as valid numerical numbers or not. 
     * It also checks if standard deviation is negative or equal to zero. 
     * If all values are valid it returns true else false.
     * @param String (mean)
     * @param String (sd)
     * @return boolean
     */
        public static boolean validateMeanAndSd(String mean, String sd){
        try{
            // no non numeric values for mean and sd
            double dMean = toDouble(mean);
            double dSd = toDouble(sd);
            // sd can't be -ve
            if(dSd <= 0) throw new InvalidSDException("Standard deviation should be greater than zero.");
        }
        catch(InvalidSDException isde){
            notifyError(isde.getMessage());
            return false;
        }
        catch (NumberFormatException ex){
            notifyError("Enter valid numeric values");
            return false;
        }catch(Exception ex){
          notifyError("Sorry, something went wrong here");
          return false;
        }
      return true;
    }
    /**
     * This method will try to parse the given string to double. If it cannot parse, it will return false else true.
     * @param String (input)
     * @return boolean
     */
    public static boolean validateforDouble(String input){
        try{
            double value = toDouble(input);
        }catch(NumberFormatException ex){
            notifyError("Enter valid numeric value");
            return false;
        }catch(Exception ex){
           notifyError("Sorry, something went wrong here.");
            return false;
        }
        return true;
    }
    /**
     * This method will try to parse the given strings to double. If it cannot parse, it will return false else true.
     * @param String (value1)
     * @param String (value2)
     * @return boolean
     */
     private static boolean validateforDouble(String value1, String value2) {
        try{
            double firstValue = toDouble(value1);
            double secondValue = toDouble(value2);
        }catch(NumberFormatException ex){
            notifyError("Enter valid numeric value");
            return false;
        }catch(Exception ex){
           notifyError("Sorry, something went wrong here.");
            return false;
        }
        return true;
        
    }
    /**
     * This method will validate the input of the below text field. If it is not valid it returns false else true.
     * @param String (input)
     * @return boolean
     */
    public static boolean validateBelowTF(String input){
        return validateforDouble(input);
    }
    /**
     * This method will validate the input of the above text field. If it is not valid it returns false else true.
     * @param String (input)
     * @return boolean
     */
    public static boolean validateAboveTF(String input){
        return validateforDouble(input);
    }
    /**
     * This method will validate the between text fields. If any one or both are not valid it returns false else true.
     * @param String (startValue)
     * @param String (endValue)
     * @return boolean
     */
    public static boolean validateInRangeTF(String startValue, String endValue){
        return validateforDouble(startValue,endValue);
    }
    /**
     * This method will validate the outrange text fields. If any one or both are not valid it returns false else true.
     * @param String (startValueStr)
     * @param String (endValueStr)
     * @return boolean
     */
      public static boolean validateOutRangeTF(String startValueStr, String endValueStr){
         try {
          
       double startValue = toDouble(startValueStr);
       double endValue = toDouble(endValueStr);
        // notify error if the values entered are out of scope.
         if(startValue >=  endValue) {
             notifyError("Start value should be less than end value.");
             return false;
         }
   
       }catch(NumberFormatException ex){
          notifyError("Enter valid numeric values");
          return false;
       }catch(Exception ex){
        notifyError("Sorry, something went wrong here");
        return false;
       }
       return true;
    }
      /**
       * This method will validate the inputs for the z start and z end fields.
       * If any one or both are invalid it will return false else true.
       * @param String (startValueString)
       * @param String  (endValueString)
       * @return boolean
       */
    public static boolean validateZtableValues(String startValueString, String endValueString){
       try {
          
       double startValue = toDouble(startValueString);
       double endValue = toDouble(endValueString);
        // notify error if the values entered are out of scope.
         if(startValue < -4.1 || endValue > 4.1) {
             notifyError("Z values can be only between -4.1 to +4.1");
             return false;
         }
         // if startValue is greater than endValue report error.
        if(startValue > endValue){
             notifyError("Z start value should be less than or equal to end value");
             return false;
         } 
       }catch(NumberFormatException ex){
          notifyError("Enter valid numeric values");
          return false;
       }catch(Exception ex){
        notifyError("Sorry, something went wrong here");
        return false;
       }
       return true;
    }
    
   

   
}
