/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tests;

import java.util.ArrayList;
import Entities.Particle;
import Entities.Position;

/**
 *
 * @author Elliackin
 */
public class TesteInitializationArchive {

     public static void main(String[] args) {
        // TODO code application logic here
        Particle p1 = new Particle(0);
        Particle p2 = new Particle(1);
        Particle p3 = new Particle(2);
        Particle p4 = new Particle(3);

        double[] f1 = {0,0,4,0};
        double[] f2 = {1,3,2,2};
        double[] f3 = {7,6,2,1};

        double[] f4 = {1,1,1,1};

        p1.getPosition().setFitness(f1);
        p2.getPosition().setFitness(f2);
        p3.getPosition().setFitness(f3);
        p4.getPosition().setFitness(f4);
        ArrayList<Position> archive = new ArrayList<Position>();
        Particle swarm[] = new Particle[4];
        swarm[0] = p1;
        swarm[1] = p2;
        swarm[2] = p3;
        swarm[3] = p4;
        //util.Util.retrivePositionsIncomparable(archive, swarm);

    }
}
