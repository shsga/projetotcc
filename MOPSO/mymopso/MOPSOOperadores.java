/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mymopso;

import grids.Grid;
import grids.Hipercube;
import util.Constants;
import util.Parameters;
import util.Util;

/**
 *
 * @author Elliackin
 */
public class MOPSOOperadores {

    public static Particle[] createSwarm(){
        Particle[] swarm = new Particle[Constants.SIZE_OF_SWARM];
        for(int i=0;i<swarm.length;i++){
            swarm[i] = new Particle(i);
        }
        return swarm;
    }

    public static Grid createGrid(){
        Hipercube[] cubes = Util.createCubes();
        return new Grid(cubes);
    }

    public static void updateParticlePosition(Particle particle){
        double[] position = particle.getPosition().getPosition();
        double[] velocity = particle.getVelocity();
        double[] pbest = particle.getPbest().getPosition();
        double[] gbest = particle.getGbest().getPosition();
        double delta = Parameters.DELTA;
        double lowerLimit = Parameters.LOWER_LIMIT;
        double upperLimit = Parameters.UPPER_LIMIT;
        for(int i=0;i<Parameters.NUMBER_OF_DIMENSIONS;i++){
            
            velocity[i] =
                   Constants.W * velocity[i] + Constants.C1*(pbest[i]-position[i])
                     + Constants.C2 * (gbest[i]-position[i]);
            if (Math.abs(velocity[i]) > delta) {
                velocity[i] = Math.signum(velocity[i]) * delta;
            }
             position[i] += velocity[i];

            if (position[i] < lowerLimit) {
                position[i] = lowerLimit;
                velocity[i] *= -1;
            } else if (position[i] > upperLimit) {
                position[i] = upperLimit;
                velocity[i] *= -1;
            }

        }
    }
    
    public static void evaluateParticle(Particle particle){
        double[] position = particle.getPosition().getPosition();
        double[] fitness = particle.getPosition().getFitness(); 
        
        for(int i=0; i < Parameters.NUMBER_OF_OBJECTIVES; i++){
            fitness[i] = Parameters.problem.getFunctions().get(i).evaluate(position);
        }
    }
}
