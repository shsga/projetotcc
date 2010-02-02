/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mymopso;

import gui.PlotManager;
import grids.Grid;
import java.util.ArrayList;
import util.Constants;
import util.Util;
/**
 *
 * @author Elliackin
 */
public class MOPSO {

    private Particle[] swarm;
    private ArrayList<Position> externArchive;
    private Grid grid;
    private PlotManager plotManager;

    public MOPSO() {
        this.swarm = MOPSOOperadores.createSwarm();
        this.externArchive = new ArrayList<Position>();
        this.grid = MOPSOOperadores.createGrid();
    }

    public void run(){

        this.externArchive = Util.updateExternalArchive(this.externArchive, swarm);
        Util.calculateCrowdingDistance(this.externArchive);

        for(int currentIreation=0; currentIreation < Constants.TOT_ITERATIONS; currentIreation++){
            Util.turbulence(swarm, currentIreation);
            this.grid.updateGrid(this.externArchive);
            for(int i=0;i<this.swarm.length;i++){
                Position leader = this.grid.selectLider();
                this.swarm[i].setGbest(leader);
                Position pbest = Util.selectPbest(this.swarm[i]);
                this.swarm[i].setPbest(pbest);
                MOPSOOperadores.updateParticlePosition(this.swarm[i]);
                MOPSOOperadores.evaluateParticle(this.swarm[i]);
            }
            this.externArchive = Util.updateExternalArchive(this.externArchive, swarm);
            Util.calculateCrowdingDistance(this.externArchive);
            this.grid.updateGrid(this.externArchive);
            Util.trunkExternalArchive(grid, externArchive);
        }
    }


}
