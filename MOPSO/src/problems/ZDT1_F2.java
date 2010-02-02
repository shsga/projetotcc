/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package problems;

/**
 *
 * @author Elliackin
 */
public class ZDT1_F2 implements ObjectiveFunction{

   private double gValue;

    public double evaluate( double[] dimension ) {
        return g( dimension ) * ( 1.0 - Math.sqrt( dimension[0]/g( dimension ) ) );
    }

    private double g ( double[] dimension ) {
        double sum = 0.0;
//        double result;

        for( int i = 1; i < dimension.length; i++ ) {
            sum += dimension[i];
        }

        gValue = 1.0 + ((9.0 * sum) / (dimension.length - 1));

        return gValue;

//        return 1 + ( ( 9 * sum ) / ( dimension.length - 1 ) );
    }

    public double getGValue() {
        return gValue;
    }



}
