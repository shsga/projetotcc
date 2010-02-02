/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tests;

import mymopso.MOPSO;
import problems.ZDT1;
import util.Parameters;

/**
 *
 * @author Elliackin
 */
public class TestMOPSO {

    public static void main(String[] args) {

        Parameters.problem = new ZDT1();
        MOPSO mopso = new MOPSO();
        mopso.run();
    }
}
