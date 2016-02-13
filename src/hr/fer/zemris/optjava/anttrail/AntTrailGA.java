package hr.fer.zemris.optjava.anttrail;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;

import javax.swing.SwingUtilities;

import hr.fer.zemris.optjava.anttrail.ant.Ant;
import hr.fer.zemris.optjava.anttrail.chart.Chart;
import hr.fer.zemris.optjava.anttrail.genetic.ChartStatistics;
import hr.fer.zemris.optjava.anttrail.genetic.Chromosome;
import hr.fer.zemris.optjava.anttrail.genetic.GeneticAlgorithm;
import hr.fer.zemris.optjava.anttrail.gui.MainWindow;
import hr.fer.zemris.optjava.anttrail.tree.INode;
import hr.fer.zemris.optjava.anttrail.tree.IfFoodAheadNode;
import hr.fer.zemris.optjava.anttrail.tree.LeftNode;
import hr.fer.zemris.optjava.anttrail.tree.MoveNode;
import hr.fer.zemris.optjava.anttrail.tree.NonTerminalNode;
import hr.fer.zemris.optjava.anttrail.tree.Prog2Node;
import hr.fer.zemris.optjava.anttrail.tree.Prog3Node;
import hr.fer.zemris.optjava.anttrail.tree.RightNode;
import hr.fer.zemris.optjava.anttrail.tree.TerminalNode;

public class AntTrailGA {

    static {
        TerminalNode.terminalNodes.add(LeftNode.class);
        TerminalNode.terminalNodes.add(RightNode.class);
        TerminalNode.terminalNodes.add(MoveNode.class);

        NonTerminalNode.nonTerminalNodes.add(IfFoodAheadNode.class);
        NonTerminalNode.nonTerminalNodes.add(Prog2Node.class);
        NonTerminalNode.nonTerminalNodes.add(Prog3Node.class);
    }

    public static void main(String[] args)
        throws IOException, InvocationTargetException, InterruptedException {
        Chart chart = Chart.createFromFile(Paths.get("trail.txt"));
        Ant ant = new Ant();
        ant.addAntChangeObserver(chart);
        Chromosome solution = GeneticAlgorithm.solve(chart, ant);
        INode tree = solution.getTree();
        ChartStatistics stats = new ChartStatistics(chart, ant);
        stats.reset();
        SwingUtilities.invokeAndWait(() -> {
            MainWindow mainWindow = new MainWindow(chart, ant);
            ant.addAntChangeObserver(mainWindow);
            chart.addChartObserver(mainWindow);
        });
        stats.evaluateTree(tree);
        // System.out.println(tree);
    }

}
