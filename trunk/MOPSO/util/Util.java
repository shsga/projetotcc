/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import grids.Grid;
import grids.Hipercube;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import mymopso.Particle;
import mymopso.Position;

/**
 *
 * @author Elliackin
 */
public class Util {

    public static int verifyDominance(Position p1,Position p2){
        double[] fitness1 = p1.getFitness();
        double[] fitness2 = p2.getFitness();
        boolean winInAllDimension = true; // nao é pior em nenhuma dimensao
        boolean lostInAllDimension = true; //nao é melhor em nenhuma dimensao
        for(int i=0;i<fitness1.length;i++){
            if(fitness1[i] > fitness2[i]){
                winInAllDimension = false; //é pior em pelo menos uma dimensao
                if(!lostInAllDimension){
                    return Constants.INCOMPARABLE;
                }
            }
            if(fitness1[i] < fitness2[i]){
                lostInAllDimension = false; //é melhor em pelo menos uma dimensao
                if(!winInAllDimension){
                    return Constants.INCOMPARABLE;
                }
            }
        }

        if(winInAllDimension && lostInAllDimension){
            return Constants.INCOMPARABLE;
        }
        if(winInAllDimension){
            return Constants.DOMINATES;
        }
        if(lostInAllDimension){
            return Constants.DOMINATED;
        }

        return Constants.INCOMPARABLE;
    }

    public static ArrayList<Position> retrivePositionsIncomparable(Particle[] swarm){
        ArrayList<Position> result = new ArrayList<Position>();
        boolean isDominated[] = new boolean[swarm.length];
        int dominance = 0;
        for(int i=0;i < swarm.length ;i++){
            
            for(int j=0;j<swarm.length  && !isDominated[i];j++){
               if(i != j && !isDominated[j]){
                    dominance =
                          verifyDominance(swarm[i].getPosition(), swarm[j].getPosition());
                   if(dominance == Constants.DOMINATES){
                        isDominated[j] = true;
                   }
                   if(dominance == Constants.DOMINATED){
                       isDominated[i] = true;
                   }
               
               }

            }
            
        }

        for(int i=0;i<swarm.length;i++){
            if(!isDominated[i]){
                result.add(swarm[i].getPosition().clone());
            }
        }
        return result;
    }
    
    public static void calculateCrowdingDistance(ArrayList<Position> externalArchive){
        if(externalArchive.size() == 0) return;

        double[] crowdingDistance = null;
        ArrayList[] values = new ArrayList[Parameters.NUMBER_OF_OBJECTIVES];
        crowdingDistance = new double[externalArchive.size()];

        for(int i=0;i< Parameters.NUMBER_OF_OBJECTIVES ;i++){
            values[i] = new ArrayList();
            for(int j=0;j<externalArchive.size();j++){
                Position p = externalArchive.get(j);
                values[i].add(p.getFitness()[i]);
            }
            Collections.sort(values[i]);
            Double min = (Double) values[i].get(0);
            Double max = (Double) values[i].get(values[i].size()-1);
            double deltaMax = Math.abs(max.doubleValue() - min.doubleValue());

            for(int j=1;j<externalArchive.size()-1 && deltaMax != 0.0 ;j++){
                double x0 = ((Double)values[i].get(j-1)).doubleValue();
                double x1 = ((Double)values[i].get(j)).doubleValue();
                double x2 = ((Double)values[i].get(j+1)).doubleValue();
                double delta1 = Math.abs(x1-x0);
                double delta2 = Math.abs(x2-x1);
                crowdingDistance[j] += (delta1 + delta2)/deltaMax;
            }

            if(externalArchive.size() >= 2 && deltaMax != 0.0){
                int size = externalArchive.size();
                double x0 = ((Double)values[i].get(1)).doubleValue();
                double x1 = ((Double)values[i].get(0)).doubleValue();
                double x2 = ((Double)values[i].get(size-1)).doubleValue();
                double delta1 = Math.abs(x1-x0);
                double delta2 = Math.abs(x2-x1);
                crowdingDistance[0] += (delta1 + delta2)/deltaMax;

                 x0 = ((Double)values[i].get(0)).doubleValue();
                 x1 = ((Double)values[i].get(size-1)).doubleValue();
                 x2 = ((Double)values[i].get(size-2)).doubleValue();
                 delta1 = Math.abs(x1-x0);
                 delta2 = Math.abs(x2-x1);
                 crowdingDistance[size-1] += (delta1 + delta2)/deltaMax;
            }

        }

        for(int i=0; i<externalArchive.size();i++){
            externalArchive.get(i).setCrowdingDistance(crowdingDistance[i]);
        }

    }
    
    public static void normalizeVector(double[] vector,double base){
        for(int i=0;i<vector.length;i++){
            vector[i] = vector[i]/base;
        }
    }

    public static void orderByCrowdingDistance(ArrayList<Position> externalArchive){
        Collections.sort(externalArchive,Collections.reverseOrder());
    }
    	
//falta testar
    public static void turbulence(Particle[] swarm, int currentIteration){
        Random r = new Random();
        Random random = new Random();
        for(int i =0; i < swarm.length; i++){
            double value =
                Math.pow((1.0 - ( currentIteration / Constants.TOT_ITERATIONS)), (5.0 / Constants.MUTATION_RATE));
            if(flip(value)){
                int dim = r.nextInt(Parameters.NUMBER_OF_DIMENSIONS);
                double mutRange = Parameters.DELTA*value;
                double lb = swarm[i].getPosition().getPosition()[dim] - mutRange;
                double ub = swarm[i].getPosition().getPosition()[dim] + mutRange;
                if(lb < Parameters.LOWER_LIMIT){
                    lb = Parameters.LOWER_LIMIT;
                }
                if(ub > Parameters.UPPER_LIMIT){
                    ub = Parameters.UPPER_LIMIT;
                }

                swarm[i].getPosition().getPosition()[dim] = lb + (ub-lb) * random.nextDouble();
            }
        }

    }

    private static boolean flip(double value) {
        if (Math.random() <= value) {
            return true;
        }

        return false;
    }

    public static double[] maxValuesFitnessExternArchive(ArrayList<Position> externalArchive ){
        double[] maxValues = new double[Parameters.NUMBER_OF_OBJECTIVES];
        for(int i=0;i<maxValues.length;i++){
            double max = 0.0; // so ha valores positivos
            for(int j=0;j<externalArchive.size();j++){
                max = Math.max(max, externalArchive.get(j).getFitness()[i]);
            }
            maxValues[i] = max;
        }
        return maxValues;
    }

    public static double[] minValuesFitnessExternArchive(ArrayList<Position> externalArchive ){
        double[] minValues = new double[Parameters.NUMBER_OF_OBJECTIVES];
        for(int i=0;i<minValues.length;i++){
            double min = Double.MAX_VALUE; // so ha valores positivos
            for(int j=0;j<externalArchive.size();j++){
                min = Math.max(min, externalArchive.get(j).getFitness()[i]);
            }
            minValues[i] = min;
        }
        return minValues;
    }

    public static Hipercube[] createCubes(){
        Hipercube[] cubes;
        int nCubes = (int)Math.pow(Constants.NUM_OF_GRIDS, Parameters.NUMBER_OF_OBJECTIVES);
        cubes = new Hipercube[nCubes];
        double[] line;
        for(int i=0;i<cubes.length;i++){
            line = MyMath.convertBase(i, Constants.NUM_OF_GRIDS, Parameters.NUMBER_OF_OBJECTIVES);
            cubes[i] = new Hipercube(line);
        }
        return cubes;
    }

    public static Position selectPbest(Particle particle){
        int dominance;
        dominance = Util.verifyDominance(particle.getPosition(), particle.getPbest());
        if (dominance == Constants.DOMINATES) {
            return particle.getPosition().clone();
        } else if (dominance == Constants.INCOMPARABLE) {
            if (Math.random() > 0.5) {
                  return particle.getPosition().clone();
            }
        }
        return particle.getPbest();   
    }

    public static void updateExternalArchive(ArrayList<Position> externalArchive,Particle[] swarm) {
        ArrayList<Position> result = Util.retrivePositionsIncomparable(swarm);
        result.addAll(externalArchive);
        externalArchive = Util.retrivePositionsIncomparable(result);
   }
   
    public static ArrayList<Position> retrivePositionsIncomparable(ArrayList<Position> archive){
        ArrayList<Position> result = new ArrayList<Position>();
        boolean isDominated[] = new boolean[archive.size()];
        int dominance = 0;
        for(int i=0;i < archive.size() ;i++){
            
            for(int j=0;j<archive.size() && !isDominated[i];j++){
               if(i != j && !isDominated[j]){
                    dominance =
                          verifyDominance(archive.get(i), archive.get(j));
                   if(dominance == Constants.DOMINATES){
                        isDominated[j] = true;
                   }
                   if(dominance == Constants.DOMINATED){
                       isDominated[i] = true;
                   }
               
               }

            }
            
        }

        for(int i=0;i<archive.size() ;i++){
            if(!isDominated[i]){
                result.add(archive.get(i).clone());
            }
        }
        return result;
    }

    /*
     * Limita o arquivo ao tamanho maximo
     */
    public static void trunkExternalArchive(Grid grid, ArrayList<Position> externalArchive) {
           while(externalArchive.size() > Constants.EXTERNAL_ARCHIVE_SIZE){
               Position positon =  grid.selectLider();
               externalArchive.remove(positon);
           }
    }

}
