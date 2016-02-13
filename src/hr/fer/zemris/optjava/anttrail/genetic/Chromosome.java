package hr.fer.zemris.optjava.anttrail.genetic;

import static hr.fer.zemris.optjava.anttrail.config.Configuration.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import hr.fer.zemris.optjava.anttrail.random.RandomGenerator;
import hr.fer.zemris.optjava.anttrail.tree.INode;
import hr.fer.zemris.optjava.anttrail.tree.NonTerminalNode;

public class Chromosome {

    private INode            tree;
    private int              pieces;
    private int              parentPieces;
    private Optional<Double> fitness;

    public Chromosome(INode tree) {
        this.tree = tree;
        pieces = 0;
        parentPieces = 0;
        fitness = Optional.empty();
    }

    public double evaluateFitness(ChartStatistics stats) {
        if (fitness.isPresent()) {
            return fitness.get();
        }
        int pieces = stats.evaluateTree(tree);
        double penalty = pieces <= parentPieces ? 0.9 : 1;
        fitness = Optional.of(pieces * penalty);
        return fitness.get();
    }

    public Chromosome replicate() {
        Chromosome replicant = new Chromosome(tree.clone());
        replicant.parentPieces = pieces;
        return replicant;
    }

    public Chromosome mutate() {
        Chromosome mutant = replicate();
        List<Integer> traversal = randomTraversal(mutant.tree);
        int totalDepth = traversal.size();
        int p = RandomGenerator.nextInt(totalDepth);
        int remainingMaxDepth = Math.max(0, MAXIMUM_TREE_DEPTH - p);
        int r = RandomGenerator.nextInt(remainingMaxDepth);
        boolean q = RandomGenerator.nextBoolean();
        INode replacement = INode.growTree(r, q);
        INode parent = mutant.tree;
        if (p == 0) {
            mutant.tree = replacement;
        } else {
            for (int i = 0; i < p; i++) {
                parent = ((NonTerminalNode) parent).getChildren().get(traversal.get(i));
            }
            ((NonTerminalNode) parent).getChildren().set(traversal.get(p), replacement);
        }
        return mutant;
    }

    public Chromosome crossWith(Chromosome other) {
        Chromosome offspring = replicate();
        INode otherTree = other.tree.clone();
        List<Integer> traversal = randomTraversal(offspring.tree);
        List<Integer> traversalOther = randomTraversal(otherTree);
        int otherTotalDepth = traversalOther.size() + 1;
        int depth = traversal.size();
        int p = RandomGenerator.nextInt(depth);
        int maxAllowedDepth = Math.max(0, MAXIMUM_TREE_DEPTH - p - 1);
        int r0 = Math.max(0, otherTotalDepth - maxAllowedDepth);
        int rd = Math.min(maxAllowedDepth, otherTotalDepth);
        int r = r0 + RandomGenerator.nextInt(rd);
        INode replacement = otherTree;
        for (int i = 0; i < r - 1; i++) {
            replacement = ((NonTerminalNode) replacement).getChildren().get(traversalOther.get(i));
        }
        INode parent = offspring.tree;
        if (p == 0) {
            offspring.tree = replacement;
            offspring.parentPieces = other.pieces;
        } else {
            for (int i = 0; i < p; i++) {
                parent = ((NonTerminalNode) parent).getChildren().get(traversal.get(i));
            }
            ((NonTerminalNode) parent).getChildren().set(traversal.get(p), replacement);
        }
        return offspring;
    }

    public void truncate() {
        List<Integer> traversal = randomTraversal(tree);
        int totalDepth = traversal.size() / 2;
        int p = RandomGenerator.nextInt(totalDepth);
        int remainingMaxDepth = Math.max(0, totalDepth - p - 1);
        int r = RandomGenerator.nextInt(remainingMaxDepth);
        r = 1;
        INode replacement = INode.growTree(r, false);
        INode parent = tree;
        if (p == 0) {
            tree = replacement;
        } else {
            for (int i = 0; i < p; i++) {
                parent = ((NonTerminalNode) parent).getChildren().get(traversal.get(i));
            }
            ((NonTerminalNode) parent).getChildren().set(traversal.get(p), replacement);
        }
    }

    private List<Integer> randomTraversal(INode tree) {
        List<Integer> traversal = new ArrayList<>();
        INode node = tree;
        while (true) {
            if (node instanceof NonTerminalNode) {
                List<INode> children = ((NonTerminalNode) node).getChildren();
                int n = children.size();
                int p = RandomGenerator.nextInt(n);
                traversal.add(p);
                node = children.get(p);
            } else {
                break;
            }
        }
        return traversal;
    }

    public INode getTree() {
        return tree;
    }

}
