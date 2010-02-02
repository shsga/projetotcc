/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mymopso;

import util.Constants;
import util.Parameters;

/**
 *
 * @author Elliackin
 */
public class Position implements Comparable<Position>{


    private double[] position;
    private double[] fitness;
    private double crowdingDistance;
    

    public void setFitness(double[] fitness) {
        this.fitness = fitness;
    }

    public void setPosition(double[] position) {
        this.position = position;
    }

    public Position() {
        this.position =  new double[Parameters.NUMBER_OF_DIMENSIONS];
        this.fitness = new double[Parameters.NUMBER_OF_OBJECTIVES];
        for(int i=0;i<position.length;i++){
            this.position[i] = Parameters.LOWER_LIMIT + Parameters.DELTA * Math.random();         
        }
        for(int i=0;i<Parameters.NUMBER_OF_OBJECTIVES;i++){
            fitness[i] = Parameters.problem.getFunctions().get(i).evaluate(position);
        }
    }

    public double[] getFitness() {
        return this.fitness;
    }

    public Position clone(){
        Position p = new Position();
        p.fitness =  this.fitness.clone();
        p.position = this.position.clone();
        return p;
    }

    public double getCrowdingDistance() {
        return crowdingDistance;
    }

    public double[] getPosition() {
        return position;
    }

    public void setCrowdingDistance(double crowdingDistance) {
        this.crowdingDistance = crowdingDistance;
    }
    

    public int compareTo(Position o) {
        if(this.crowdingDistance < o.getCrowdingDistance()){
            return -1;
        }else if(this.crowdingDistance > o.getCrowdingDistance()){
            return 1;
        }
        return 0;
    }

    

}
