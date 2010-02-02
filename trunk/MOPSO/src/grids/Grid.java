/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package grids;

import java.util.ArrayList;
import java.util.Random;
import mymopso.Position;
import util.Parameters;
import util.Util;
/**
 *
 * @author Elliackin
 */
public class Grid {

    private Hipercube[] cubes;
    private double[] maxValues;
    private double[] minValues;
    private double[] ranges;
    private int totPositions;

    public Grid(Hipercube[] cubes) {
        this.cubes = cubes;
        this.ranges = new double[Parameters.NUMBER_OF_OBJECTIVES];
        this.maxValues = new double[Parameters.NUMBER_OF_OBJECTIVES];
        this.minValues = new double[Parameters.NUMBER_OF_OBJECTIVES];
        this.totPositions = 0;
    }

    public Hipercube[] getHupercubes() {
        return this.cubes;
    }

    public void updateGrid(ArrayList<Position> externArchive) {
        this.totPositions = externArchive.size();
        this.maxValues = Util.maxValuesFitnessExternArchive(externArchive);
        this.minValues = Util.minValuesFitnessExternArchive(externArchive);
        for(int i=0;i<this.ranges.length;i++){
            this.ranges[i] =this.maxValues[i] = this.maxValues[i];
        }
        for(int i=0;i<cubes.length;i++){
            cubes[i].update(ranges);
        }
        
        for(int i=0;i<cubes.length;i++){
            this.cubes[i].localizePositions(externArchive);
        }
        
        for(int i=0;i<cubes.length;i++){
            this.cubes[i].calculateFitnessDiverty();
        }

    }

    public Position selectLider(){
        if(totPositions == 0) return null;      
        double sum = 0.0;
        for(int i=0;i<this.cubes.length ;i++){
             if(cubes[i].getFitnessDiverty() != 0){
               sum += cubes[i].getFitnessDiverty();
             }
        }
        double rouletteValue;
        double lowerValue = 0.0;
        double upperValue = 0.0;;
        rouletteValue = Math.random() * sum;
        Hipercube cubeSelected = null;

        for(int i=0;i<cubes.length;i++){
            if(cubes[i].getFitnessDiverty() != 0.0){
                upperValue += cubes[i].getFitnessDiverty();
                if( rouletteValue >= lowerValue && rouletteValue < upperValue){
                    cubeSelected = cubes[i];
                    break;
                }
                lowerValue = upperValue;
            }
        }

        Position leader;
        Random r = new Random();
        int intRadom = r.nextInt(cubeSelected.getPositions().size());
        leader = cubeSelected.getPositions().get(intRadom);
        return leader;
    }
}
