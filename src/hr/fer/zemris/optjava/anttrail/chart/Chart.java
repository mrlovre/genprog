package hr.fer.zemris.optjava.anttrail.chart;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import hr.fer.zemris.optjava.anttrail.ant.Ant;
import hr.fer.zemris.optjava.anttrail.ant.AntChangeObserver;
import hr.fer.zemris.optjava.anttrail.ant.Position;

public class Chart implements AntChangeObserver {

    private ArrayList<ArrayList<Tile>> field;
    private ArrayList<ArrayList<Tile>> initialField;
    private int                        n;
    private int                        m;
    private List<ChartObserver>        chartObservers = new ArrayList<>();

    private Chart() {};

    public static Chart createFromFile(Path file) throws IOException {
        List<String> lines = Files.readAllLines(file).stream()
            .map(String::trim).filter(t -> !t.isEmpty()).collect(Collectors.toList());
        Chart chart = new Chart();
        int n = lines.size();
        chart.n = n;
        chart.initialField = new ArrayList<>(n);
        boolean first = true;
        for (String line : lines) {
            String[] chunks = line.split("");
            if (first) {
                chart.m = chunks.length;
                first = false;
            }
            ArrayList<Tile> row = new ArrayList<>();
            Arrays.stream(chunks).map(Tile::fromString).forEach(row::add);
            if (row.size() != chart.m) {
                throw new InvalidChartDefinitionException(
                    "Inconsistent number of elements in each row ("
                        + chart.m + ", " + row.size() + ").");
            }
            chart.initialField.add(row);
        }
        chart.field = new ArrayList<>(n);
        chart.initialField.stream().forEach(t -> chart.field.add(new ArrayList<>(t)));
        return chart;
    }

    public void reset(Ant ant) {
        field.clear();
        initialField.stream().forEach(t -> field.add(new ArrayList<>(t)));
        ant.reset();
    }

    public int getRowsCount() {
        return n;
    }

    public int getColsCount() {
        return m;
    }

    public Tile get(int i, int j) {
        return field.get(j).get(i);
    }

    public Tile get(Position pos) {
        return get(pos.x, pos.y);
    }

    public void set(int i, int j, Tile tile) {
        Tile oldTile = field.get(j).get(i);
        field.get(j).set(i, tile);
        fireChartChanged(i, j, oldTile, tile);
    }

    public void set(Position pos, Tile tile) {
        set(pos.x, pos.y, tile);
    }

    public void refreshAnt(Ant ant) {
        clampPosition(ant.getPos());
        Position forwardPos = clampPosition(ant.forwardPosition());
        if (get(ant.getPos()) == Tile.FOOD) {
            set(ant.getPos(), Tile.EMPTY);
        }
        ant.foodAhead = get(forwardPos) == Tile.FOOD;
    }

    private Position clampPosition(Position pos) {
        if (pos.x < 0) {
            pos.x = 0;
        } else if (pos.x >= m) {
            pos.x = m - 1;
        }
        if (pos.y < 0) {
            pos.y = 0;
        } else if (pos.y >= n) {
            pos.y = n - 1;
        }
        return pos;
    }

    public void addChartObserver(ChartObserver observer) {
        chartObservers.add(observer);
    }

    @Override
    public void onAntChanged(Ant ant) {
        refreshAnt(ant);
    }

    private void fireChartChanged(int i, int j, Tile oldTile, Tile newTile) {
        chartObservers.stream().forEach(t -> t.onChartChanged(this, i, j, oldTile, newTile));
    }

}
