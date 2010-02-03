/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tests;

import gui.MOPSO_CDRJFrame;
import technic.MOPSO;
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
        MOPSO_CDRJFrame frameMopso_CDR;
        frameMopso_CDR = new MOPSO_CDRJFrame(mopso);
        frameMopso_CDR.setTitle("MOPSO_CDR (" + "AZT1" + ")");
        frameMopso_CDR.setVisible(true);

        
        mopso.run();
    }
}
