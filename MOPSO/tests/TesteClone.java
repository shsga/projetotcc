/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tests;

import mymopso.Position;

/**
 *
 * @author Elliackin
 */
public class TesteClone {

    public static void main(String[] args) {


        double[] f1 = {0,0,4,0};
        double[] f2 = {1,3,2,2};
        double[] f3 = {7,6,2,1};

        double[] f4 = {1,1,1,1};


        Position p1 = new Position();
        p1.setFitness(f1);
        Position p2 = p1.clone();
        p2.getFitness()[0] = 100;


    }
}
