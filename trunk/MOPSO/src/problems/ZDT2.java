/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package problems;

import java.util.ArrayList;
import util.Parameters;

/**
 *
 * @author robson
 */
public class ZDT2 implements Problem {
    private ArrayList<ObjectiveFunction> functions;
    private int nFunctions;
    private int nDimensions;

    public ZDT2( ) {
        functions = new ArrayList<ObjectiveFunction>();

        functions.add( new ZDT2_F1() );
        functions.add( new ZDT2_F2() );

        nFunctions = functions.size();
        nDimensions = 30;

        Parameters.UPPER_LIMIT = this.getMaxBound(nDimensions);
        Parameters.LOWER_LIMIT = this.getMinBound(nDimensions);
        Parameters.DELTA = Parameters.UPPER_LIMIT - Parameters.LOWER_LIMIT;
        Parameters.NUMBER_OF_OBJECTIVES = 2;
        Parameters.NUMBER_OF_DIMENSIONS = 30;
    }

    public ArrayList<ObjectiveFunction> getFunctions() {
        return functions;
    }

    public int getNDimensions() {
        return nDimensions;
    }

    public int getNObjectives() {
        return nFunctions;
    }

    public double getMaxBound( int dimension ) {
        return 1.0;
    }

    public double getMinBound( int dimension ) {
        return 0.0;
    }

    public double getMaxInitValue( int dimension ) {
        return 1.0;
    }

    public double getMinInitValue( int dimension ) {
        return 0.0;
    }

    public double getGValue() {
        return (( ZDT2_F2 ) functions.get(1)).getGValue();
    }

    public String getName() {
        return "ZDT 2";
    }
}
