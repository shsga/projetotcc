/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mymopso;

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
    private ArrayList<Position> externArchive;
    private Grid grid;
    private PlotManager plotManager;

    public MOPSO() {
        this.swarm = MOPSOOperadores.createSwarm();
        this.externArchive = new ArrayList<Position>();
        this.grid = MOPSOOperadores.createGrid();
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

        this.externArchive = Util.updateExternalArchive(this.externArchive, swarm);        
        for(int currentIreation=0; currentIreation < Constants.TOT_ITERATIONS; currentIreation++){
            plotManager.plotSwarm(swarm, currentIreation);
            plotManager.plotExternalArchive(externArchive,currentIreation);
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(MOPSO.class.getName()).log(Level.SEVERE, null, ex);
            }
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
            this.grid.updateGrid(this.externArchive);
            Util.trunkExternalArchive(grid, externArchive);
        }
    }


}
