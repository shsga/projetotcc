/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package grids;


import java.util.ArrayList;
import mymopso.Position;
import util.Constants;
import util.Parameters;

/**
 *
 * @author Elliackin
 */
public class Hipercube {

    private double[] line;
    private ArrayList<Position> positions;
    private double[] upperLimit;
    private double[] lowerLimit;
    private double fitness;

    public Hipercube(double[] line) {
        this.line = line;
        this.positions = new ArrayList<Position>();
        this.lowerLimit = new double[Parameters.NUMBER_OF_OBJECTIVES];
        this.upperLimit = new double[Parameters.NUMBER_OF_OBJECTIVES];
        this.fitness = 0.0;
    }
    public void update(double[] ranges){
        for(int i=0;i<ranges.length;i++){
            this.lowerLimit[i] = line[i] * ranges[i];
            this.upperLimit[i] = lowerLimit[i] + ranges[i];
        }
    }

    public void localizePositions(ArrayList<Position> externArchive){
        this.positions.clear();
        for(int i=0;i<externArchive.size();i++){
            Position p = externArchive.get(i);
            if(this.isWithin(p.getPosition())){
                this.positions.add(p);
            }
        }
    }
    public boolean isWithin(double[] p){
        boolean result = true;
        for(int i=0; i < p.length; i++){
            if(p[i] < lowerLimit[i] || p[i] >= upperLimit[i]){
                result = false;
            }
        }
        return true;
    }

    public void calculateFitnessDiverty(){
        double numPositions = (double)this.positions.size();
        if(numPositions > 0){
        this.fitness = Constants.X/numPositions;
        }
    }

    public double getFitnessDiverty(){
        return this.fitness;
    }

   

    public ArrayList<Position> getPositions() {
        return this.positions;
    }
}
