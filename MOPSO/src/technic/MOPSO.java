/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package technic;

import Entities.Particle;
import Entities.Position;
import gui.PlotManager;
import grids.Grid;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfree.chart.ChartPanel;
import util.Constants;
import util.Util;
/**
 *
 * @author Elliackin
 */
public class MOPSO {

    private Particle[] swarm;
    private ArrayList<Position> externalArchive;
    private Grid grid;
    private PlotManager plotManager;

    public MOPSO() {
        this.swarm = MOPSOOperators.createSwarm();
        this.externalArchive = new ArrayList<Position>();
        this.grid = MOPSOOperators.createGrid();
        this.plotManager = new PlotManager();
        this.plotManager.setupPlotExternalArchive();
        this.plotManager.setupPlotSwarm();
        this.plotManager.setDebug(true);
    }

     public ChartPanel getChartPanelExternalArchive() {
        return plotManager.getChartPanelExternalArchive();
    }

     public ChartPanel getChartPanelSwarm() {
        return plotManager.getChartPanelSwarm();
    }
     
    public void run(){

        this.externalArchive = Util.updateExternalArchive(this.externalArchive, swarm);
        for(int currentIteration=0; currentIteration < Constants.TOT_ITERATIONS; currentIteration++){
            plotManager.plotSwarm(swarm, currentIteration);
            plotManager.plotExternalArchive(externalArchive,currentIteration);
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(MOPSO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Util.turbulence(swarm, currentIteration);
            this.grid.updateGrid(this.externalArchive);
            for(int i=0;i<this.swarm.length;i++){
                Position leader = this.grid.selectLider();
                this.swarm[i].setGbest(leader);
                Position pbest = Util.selectPbest(this.swarm[i]);
                this.swarm[i].setPbest(pbest);
                MOPSOOperators.updateParticlePosition(this.swarm[i]);
                MOPSOOperators.evaluateParticle(this.swarm[i]);
            }
            this.externalArchive = Util.updateExternalArchive(this.externalArchive, swarm);
            this.grid.updateGrid(this.externalArchive);
            Util.trunkExternalArchive(grid, externalArchive);
        }
    }


}
