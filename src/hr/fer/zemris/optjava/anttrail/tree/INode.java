package hr.fer.zemris.optjava.anttrail.tree;

import static hr.fer.zemris.optjava.anttrail.tree.NonTerminalNode.nonTerminalNodes;
import static hr.fer.zemris.optjava.anttrail.tree.TerminalNode.terminalNodes;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.optjava.anttrail.ant.Ant;
import hr.fer.zemris.optjava.anttrail.random.RandomGenerator;

public interface INode {

    public void evaluate(Ant ant);

    public String getName();
    
    public static INode growTree(int depth, boolean full) {
        int n1 = terminalNodes.size();
        int n2 = nonTerminalNodes.size();
        int p;
        if (full && depth > 1) {
            p = n1 + RandomGenerator.nextInt(n2);
        } else {
            int n = n1 + (depth > 1 ? n2 : 0);
            p = RandomGenerator.nextInt(n);
        }
        INode node = null;
        if (p < n1) {
            try {
                node = terminalNodes.get(p).newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            p -= n1;
            try {
                Class<? extends NonTerminalNode> nonTerminalNodeClass = nonTerminalNodes.get(p);
                int size = (int) nonTerminalNodeClass.getMethod("childrenSize").invoke(null);
                List<INode> children = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    children.add(growTree(depth - 1, full));
                }
                node = nonTerminalNodeClass.getConstructor(List.class).newInstance(children);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException | InstantiationException e) {
                e.printStackTrace();
            }
        }
        return node;
    }
    
    public int queryDepth();
    
    public INode clone();

}
