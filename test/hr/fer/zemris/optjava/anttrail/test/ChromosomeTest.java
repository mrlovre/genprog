package hr.fer.zemris.optjava.anttrail.test;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import hr.fer.zemris.optjava.anttrail.genetic.Chromosome;
import hr.fer.zemris.optjava.anttrail.tree.INode;
import hr.fer.zemris.optjava.anttrail.tree.IfFoodAheadNode;
import hr.fer.zemris.optjava.anttrail.tree.LeftNode;
import hr.fer.zemris.optjava.anttrail.tree.MoveNode;
import hr.fer.zemris.optjava.anttrail.tree.NonTerminalNode;
import hr.fer.zemris.optjava.anttrail.tree.Prog2Node;
import hr.fer.zemris.optjava.anttrail.tree.Prog3Node;
import hr.fer.zemris.optjava.anttrail.tree.RightNode;
import hr.fer.zemris.optjava.anttrail.tree.TerminalNode;

public class ChromosomeTest {

    @BeforeClass
    public static void before() {
        TerminalNode.terminalNodes.add(LeftNode.class);
        TerminalNode.terminalNodes.add(RightNode.class);
        TerminalNode.terminalNodes.add(MoveNode.class);

        NonTerminalNode.nonTerminalNodes.add(IfFoodAheadNode.class);
        NonTerminalNode.nonTerminalNodes.add(Prog2Node.class);
        NonTerminalNode.nonTerminalNodes.add(Prog3Node.class);
    }

    @Test
    public void mutationTest() {
        INode tree = INode.growTree(10, true);
        Chromosome chromosome = new Chromosome(tree);
        for (int i = 0; i < 2000; i++) {
            Chromosome mutant = chromosome.mutate();
            Assert.assertTrue(mutant.getTree().queryDepth() <= 10);
        }
    }

    @Test
    public void mutationTest2() {
        INode tree = INode.growTree(10, true);
        Chromosome chromosome = new Chromosome(tree);
        Chromosome mutant;
        for (int i = 0; i < 2000; i++) {
            mutant = chromosome.mutate();
            Assert.assertTrue(mutant.getTree().queryDepth() <= 10);
        }
    }

    @Test
    public void crossWithTest() {
        INode tree1 = INode.growTree(10, true);
        INode tree2 = INode.growTree(10, true);
        Chromosome chromosome1 = new Chromosome(tree1);
        Chromosome chromosome2 = new Chromosome(tree2);
        for (int i = 0; i < 2000; i++) {
            Chromosome offspring = chromosome1.crossWith(chromosome2);
            Assert.assertTrue(offspring.getTree().queryDepth() <= 10);
        }
    }

}
