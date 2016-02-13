package hr.fer.zemris.optjava.anttrail.chart;

public interface ChartObserver {
    
    void onChartChanged(Chart chart, int i, int j, Tile oldTile, Tile newTile);

}
