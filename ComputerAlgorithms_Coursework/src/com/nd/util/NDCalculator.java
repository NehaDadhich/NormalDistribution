
package com.nd.util;
import static com.nd.data.ZTable.table;
import static com.nd.util.NDUtil.*;
/**
 * @author Neha Dadhich
 */
/**
 * This class consists of method to calculate the normal distribution using one of the method and z table.
 */
public class NDCalculator {
      private double mean = 0.0;
    private double sd = 1.0;
    // 1000 strips are choosen to make the application run faster. It can be increased to increase the precision, however this will decrease the speed.
    private int noOfStrips = 1000;
    private int sdLevels = 6;
/**
 * The constructor sets the mean and standard deviation for an instance of this class.
 * @param double (mean)
 * @param double (sd) 
 */
    public NDCalculator(double mean, double sd) {
        this.mean = mean;
        this.sd = sd;
    }
  /**
   * This method returns the mean of the current instance.
   * @return double.
   */
    public double getMean(){
        return toDoubleDecimal(mean);
    }
   /**
    * This method returns the standard deviation of the current instance.
    * @return double
    */
    public double getSD(){
        return toDoubleDecimal(sd);
    }
    /**
     * This method will set the starting point of the curve.
     * It is not practical to start from negative infinity. 
     * Thus, the graph will start from mean - (6 * standard deviation). 
     * Any value outside of this range is nearly impossible. (It would be 1 among 506797346 cases).
     * @return double
     */
    public double curveStart() {
        return (mean - (sd * sdLevels));
    }
    /**
     * This method will set the end point of the curve.
     * It is not practical to end at infinity. 
     * Thus, the graph will end at mean + (6 * standard deviation).
     * Any value outside of this range is nearly impossible. (It would be 1 among 506797346 cases).
     * @return double
     */
    public double curveEnd() {
        return (mean + (sd * sdLevels));
    }
    /**
     * This method will calculate the z value.
     * @param double (value)
     * @return Double
     */
    private Double findZScore(double value) {
        double diff = value - mean;
        double zValueD = diff / sd;
        Double result = getKeyForTable(zValueD, 2);
        return result;
    }
    /**
     * This method calculates the below probability with the specified integration method.
     * @param double (maxValue)
     * @param String (integrationMethod)
     * @return String
     */
    public String lessThanProbability(double maxValue, String integrationMethod) {
        double result = 0.0;
        // if x is less than 6 sd from mean, the probability will be rounded to 0.
        if(maxValue <= curveStart()) return resultFormat(0.0000);
        // If user has chosen trapezoidal method, then the probability will be calculated using trapezodial method.
        if(integrationMethod.equals("trapezoidal")) result = trapezoidalIntegrate(curveStart(), maxValue);
        else result = simpsonIntegration(curveStart(), maxValue); // else it would be done using simpson method.
        return resultFormat(result); // the result is returned in result format.
    }
    /**
     * This method calculates the above probability with the specified integration method.
     * @param double (minValue)
     * @param String (integrationMethod)
     * @return 
     */
    public String moreThanProbabilty(double minValue, String integrationMethod) {
        double result = 1.0;
        // if x is more than 6 sd from mean, the probability will be rounded to zero.
        if(minValue >= curveEnd()) return resultFormat(1.0000);
        // If user has chosen trapezoidal method, then the probability will be calculated using trapezodial method.
        if(integrationMethod.equals("trapezoidal")) result = trapezoidalIntegrate(minValue, curveEnd());
        else result = simpsonIntegration(minValue, curveEnd()); // else it would be done using simpson method.
        return resultFormat(result); // the result is returned in result format.
    }
    /**
     * This method will calculate probability between two points. (start and end value) using the chosen integration method.
     * @param double (startValue)
     * @param double (endValue)
     * @param String (integrationMethod)
     * @return String
     */
    public String inrangeProbability(double startValue, double endValue, String integrationMethod) {
        double result = 0.0;
        // If user has chosen trapezoidal method, then the probability will be calculated using trapezodial method.
        if(integrationMethod.equals("trapezoidal")) result = trapezoidalIntegrate(startValue, endValue);
        else result = simpsonIntegration(startValue, endValue);  // else it would be done using simpson method.
        return resultFormat(result); // the result is returned in result format.
    }
    /**
     * This method calculates the above minValue and below maxValue using the chosen integration method.
     * @param double (minValue)
     * @param  double (maxValue)
     * @param String integrationMethod
     * @return 
     */
    public String outsideRangeProbability(double minValue, double maxValue, String integrationMethod) {
        // First the inrange probabilty is calculated.
        String rangeP = inrangeProbability(minValue, maxValue, integrationMethod);
        double outsideRangeP = 1 - toDouble(rangeP); // 1 - inrange probaility.
        return resultFormat(outsideRangeP); // the result is returned in result format.
    }
    /**
     * This method checks if the given value is less than mean - (6 * standard deviation). If yes return true else return false.
     * @param double (input)
     * @return boolean
     */
   public boolean belowSixSigma(double input) {
        boolean result = false;
        if (input <= curveStart()) result = true;
        return result;
    }
    /**
     * This method will check if the given value is more than mean + (6 * standard deviation). If yes then return true else false.
     * @param double (input)
     * @return boolean
     */
    public boolean aboveSixSigma(double input) {
        boolean result = false;
        if (input >= curveEnd()) result = true;
        return result;
    }
    /**
     * This method will calculate the below probability from ZTable.
     * @param double (maxValue)
     * @return String
     */
    public String lessThanProbFromTable(double maxValue) {
        Double zValue = findZScore(maxValue); // zValue for the given value.
        // If zValue is less than -6 then 0 is formatted and returned.
        if(zValue <= -6.00) return zValueFormat(0.0000);
        // If it is above +6 then 1 is formatted and returned.
        if(aboveSixSigma(zValue)) return zValueFormat(1.0000);
        // Getting the key for thr table.
        Double key = getKeyForTable(zValue, 1);
        // Getting the index value.
        int index = getIndex(zValue) + 1;
        String[] value = table.get(key); // getting the row.
        String result = value[index]; // getting the value at the particular index in a row.
        return result; // returning the result as String for display.
    }
   /**
    * This method will calculate the above probability from table.
    * @param double (input)
    * @return String
    */
    public String moreThanProbFromTable(double input) {
        String maxP = lessThanProbFromTable(input); // calculating the below probability.
        double dResult = 1 - toDouble(maxP); // subtracting below probaability from one.
        return zValueFormat(dResult); // returning the result in zValue format.
    }
    /**
     * This method will calculate the between probability from zTable.
     * @param double (startValue)
     * @param double (endValue)
     * @return String
     */
    public String inrangePFromTable(double startValue, double endValue) {
        // first find -infinity to endValue probability
        String strUptoEndValueP = lessThanProbFromTable(endValue);
        double uptoEndValueP = toDouble(strUptoEndValueP);
        // then find -infinity to startValue probability
        String strUptoStartValueP = lessThanProbFromTable(startValue);
        double uptoStartValueP = toDouble(strUptoStartValueP);
        double result = Math.abs(uptoEndValueP - uptoStartValueP); 
        return zValueFormat(result);
    }
    /**
     * This method will calculate probability below startValue or above endValue. 
     * @param double (startValue)
     * @param double (endValue)
     * @return String
     */
    public String outsideRangePFromTable(double startValue, double endValue) {
        String rangePFromTable = inrangePFromTable(startValue, endValue);
        double dOutrangeP = 1 - toDouble(rangePFromTable);
        String outsideRangeP = zValueFormat(dOutrangeP);
        return outsideRangeP;
    }

    /**
     * This method applies the probability density function for a given value. 
     * @param x
     * @return double
     */
    private double pdf(double x) {
        double sdSqrtTwoPi = sd * (Math.sqrt(Math.PI * 2));
        double iSdSqrtTwoPi = 1 / sdSqrtTwoPi;
        double diffSquare = (Math.pow(x - mean, 2.0));
        double twoSdSquare = 2 * (Math.pow(sd, 2));
        double expValue = -(diffSquare) / twoSdSquare;
        double epower = Math.pow(Math.E, expValue);
        return iSdSqrtTwoPi * (epower);
    }
    /**
     * This method will perform integration using trapezoidal method.
     * @param startValue
     * @param endValue
     * @return 
     */
    public double trapezoidalIntegrate(double startValue, double endValue) {
        
        // step size = (endValue - startValue)/no of strips
        double stepSize = (endValue - startValue) / noOfStrips;
        // area = (f(a) + f(b))/2
        double area = 0.5 * (pdf(startValue) + (pdf(endValue)));
        // integrate all steps
        for (int i = 1; i < noOfStrips; i++) {
            // startValue + (step size * i)
            double inputPdf = startValue + (stepSize * i);
            // accumulating partial areas
            area = area + (pdf(inputPdf));
        }
       double result = area * stepSize;
        return Math.abs(result);
    }
   /**
    * This method will perform integration using simpson method.
    * @param double (startValue)
    * @param double (endValue)
    * @return double
    */
    public double simpsonIntegration(double startValue, double endValue) {
        double stepSize = (endValue - startValue) / (noOfStrips);
        double thirdFirstValue = pdf(startValue) / 3; // integrating startValue.
        double sumOfOdds = 0.0;
        // integrating odd steps.
        for (int i = 1; i <= noOfStrips; i += 2) {
            double temp = startValue + stepSize * i;
            sumOfOdds = sumOfOdds + ((4.0 / 3.0) * pdf(temp));
        }
        double sumOfEvens = 0.0;
        // integrating even steps.
        for (int i = 2; i <= noOfStrips; i += 2) {
            double temp = startValue + stepSize * i;
            sumOfEvens = sumOfEvens + ((2.0 / 3.0) * pdf(temp));
        }

        double thirdLastValue = pdf(endValue) / 3; // integrating end value
        double result = stepSize * (thirdFirstValue + sumOfOdds + sumOfEvens + thirdLastValue); // adding all the values.
        return Math.abs(result);
    }
}
