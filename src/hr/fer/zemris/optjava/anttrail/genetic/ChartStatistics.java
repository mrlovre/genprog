package hr.fer.zemris.optjava.anttrail.genetic;

import hr.fer.zemris.optjava.anttrail.ant.Ant;
import hr.fer.zemris.optjava.anttrail.ant.AntChangeObserver;
import hr.fer.zemris.optjava.anttrail.chart.Chart;
import hr.fer.zemris.optjava.anttrail.chart.ChartObserver;
import hr.fer.zemris.optjava.anttrail.chart.Tile;
import hr.fer.zemris.optjava.anttrail.tree.INode;

import static hr.fer.zemris.optjava.anttrail.config.Configuration.*;

public class ChartStatistics implements ChartObserver, AntChangeObserver {

    private Ant ant;
    private Chart chart;
    private int piecesCount;
    private int remainingAntChanges;

    public ChartStatistics(Chart chart, Ant ant) {
        this.ant = ant;
        this.chart = chart;
        ant.addAntChangeObserver(this);
        chart.addChartObserver(this);
    }

    public int evaluateTree(INode tree) {
        reset();        
        while (remainingAntChanges > 0) {
            tree.evaluate(ant);
        }
        return piecesCount;
    }

    public void reset() {
        remainingAntChanges = MAXIMUM_ACTIONS;
        piecesCount = 0;
        chart.reset(ant);
    }

    @Override
    public void onChartChanged(Chart chart, int i, int j, Tile oldTile, Tile newTile) {
        if (remainingAntChanges > 0) {
            piecesCount++;
        }
    }

    @Override
    public void onAntChanged(Ant ant) {
        remainingAntChanges--;
    }

}
