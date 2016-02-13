package hr.fer.zemris.optjava.anttrail.genetic;

import static hr.fer.zemris.optjava.anttrail.config.Configuration.CROSSING_RATE;
import static hr.fer.zemris.optjava.anttrail.config.Configuration.ELITIST_MODE;
import static hr.fer.zemris.optjava.anttrail.config.Configuration.MAXIMUM_GENERATIONS;
import static hr.fer.zemris.optjava.anttrail.config.Configuration.MAXIMUM_INITIAL_TREE_DEPTH;
import static hr.fer.zemris.optjava.anttrail.config.Configuration.MUTATION_RATE;
import static hr.fer.zemris.optjava.anttrail.config.Configuration.POPULATION_SIZE;
import static hr.fer.zemris.optjava.anttrail.config.Configuration.TOURNAMENT_SELECTION_SIZE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import hr.fer.zemris.optjava.anttrail.ant.Ant;
import hr.fer.zemris.optjava.anttrail.chart.Chart;
import hr.fer.zemris.optjava.anttrail.random.RandomGenerator;
import hr.fer.zemris.optjava.anttrail.tree.INode;

public class GeneticAlgorithm {

    public static Chromosome solve(Chart chart, Ant ant) {
        List<Chromosome> population = new ArrayList<>();
        ChartStatistics stats = new ChartStatistics(chart, ant);
        int n = POPULATION_SIZE / (MAXIMUM_INITIAL_TREE_DEPTH - 1) / 2;
        for (int i = 2; i <= MAXIMUM_INITIAL_TREE_DEPTH; i++) {
            for (int j = 0; j < n; j++) {
                population.add(new Chromosome(INode.growTree(i, false)));
                population.add(new Chromosome(INode.growTree(i, true)));
            }
        }
        for (int i = 0; i < MAXIMUM_GENERATIONS; i++) {
            System.out.println("Generation #" + i);
            population.stream().forEach(c -> c.evaluateFitness(stats));
            List<Chromosome> newPopulation = new ArrayList<>();
            for (int j = 0; j < POPULATION_SIZE; j++) {
                int max = population.size();
                List<Integer> picks = pickRandom(max, TOURNAMENT_SELECTION_SIZE);
                Chromosome winner = picks.stream()
                    .map(population::get).max((l, r) -> Double.compare(
                        l.evaluateFitness(stats), r.evaluateFitness(stats))).get();
                double p = RandomGenerator.nextDouble();
                if (p < CROSSING_RATE) {
                    int winnerIndex = population.indexOf(winner);
                    List<Integer> picksOther = pickRandom(max, TOURNAMENT_SELECTION_SIZE,
                        winnerIndex);
                    Chromosome winnerOther = picksOther.stream()
                        .map(population::get).max((l, r) -> Double.compare(
                            l.evaluateFitness(stats), r.evaluateFitness(stats))).get();
                    Chromosome offspring = winner.crossWith(winnerOther);
                    newPopulation.add(offspring);
                } else if (p - CROSSING_RATE < MUTATION_RATE) {
                    Chromosome mutant = winner.mutate();
                    newPopulation.add(mutant);
                } else {
                    Chromosome replicant = winner.replicate();
                    newPopulation.add(replicant);
                }
            }
            Chromosome best = population.stream().max((l, r) -> Double.compare(
                l.evaluateFitness(stats), r.evaluateFitness(stats))).get();
            System.out.println("Best fitness: " + best.evaluateFitness(stats));
            if (ELITIST_MODE) {
                newPopulation.add(best);
            }
            population = newPopulation;
        }
        return population.stream().max((l, r) -> Double.compare(
            l.evaluateFitness(stats), r.evaluateFitness(stats))).get();
    }

    private static List<Integer> pickRandom(int max, int size, int... excluded) {
        List<Integer> ints = IntStream.range(0, max).boxed().collect(Collectors.toList());
        ints.removeAll(Arrays.asList(excluded));
        Collections.shuffle(ints);
        return ints.stream().limit(size).collect(Collectors.toList());
    }

}
