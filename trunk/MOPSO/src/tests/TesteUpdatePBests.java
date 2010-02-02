package tests;


import java.util.ArrayList;

import mymopso.Particle;
import mymopso.Position;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Elliackin
 */
public class TesteUpdatePBests {

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
        ArrayList<Position> pbests = new ArrayList<Position>();

        pbests.add(p1.getPosition());
        pbests.add(p2.getPosition());
        pbests.add(p3.getPosition());
       // p4.setPBestes(pbests);
        //util.Util.updatePBests(p4);

    }
}
