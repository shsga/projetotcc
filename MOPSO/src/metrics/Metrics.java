/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package metrics;

import java.util.ArrayList;
import mymopso.Particle;
import mymopso.Position;

/**
 *
 * @author Elliackin
 */
public class Metrics {

    public static double  calculateSpacing(ArrayList<Position> solutions){
        int nSolutions = solutions.size();
        double[] distances = new double[nSolutions];
        double meanDistance;
        double sum = 0.0;
        double[] differenceDistances = new double[nSolutions];
        double sumDifferences = 0.0;

        for (int i = 0; i < nSolutions; i++) {
            distances[i] = Metrics.calculateMinDistance(solutions.get(i), i, solutions);
            sum += distances[i];
        }

        meanDistance = sum / (double) nSolutions;

        for (int i = 0; i < nSolutions; i++) {
            differenceDistances[i] = Math.pow(meanDistance - distances[i], 2);
            sumDifferences += differenceDistances[i];
        }

        return Math.sqrt((1 / ((double) nSolutions - 1)) * sumDifferences);
    }

    private static double calculateMinDistance(Position solution, int solutionIndex, ArrayList<Position> solutions) {
        double minDistance = Double.MAX_VALUE;
        double distance;

        for (int i = 0; i < solutions.size(); i++) {
            if (i != solutionIndex) {
                distance = Metrics.calculateDistance(solution, solutions.get(i));

                if (distance < minDistance) {
                    minDistance = distance;
                }
            }
        }

        return minDistance;
    }

       private static double calculateDistance(Position solution, Position anotherSolution) {
        double distance = 0.0;

        for (int i = 0; i < solution.getFitness().length; i++) {
            distance += Math.abs(solution.getFitness()[i] - anotherSolution.getFitness()[i]);
        }

        return distance;
    }

}
