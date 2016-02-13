package hr.fer.zemris.optjava.anttrail.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import hr.fer.zemris.optjava.anttrail.ant.Ant;

public abstract class NonTerminalNode implements INode {

    protected List<INode> children;

    protected NonTerminalNode(List<INode> children) {
        this.children = children;
    }

    @Override
    public void evaluate(Ant ant) {
        for (INode child : children) {
            child.evaluate(ant);
        }
    }

    public static List<Class<? extends NonTerminalNode>> nonTerminalNodes = new ArrayList<>();

    @Override
    public String toString() {
        return getName() + "("
            + children.stream().map(INode::toString).collect(Collectors.joining(", ")) + ")";
    }

    @Override
    public int queryDepth() {
        return 1 + children.stream().mapToInt(INode::queryDepth).max().getAsInt();
    }

    @Override
    public NonTerminalNode clone() {
        return new NonTerminalNode(children.stream().map(INode::clone).collect(Collectors
            .toList())) {

            @Override
            public String getName() {
                return this.getName();
            }

        };
    }

    public List<INode> getChildren() {
        return children;
    }

}
