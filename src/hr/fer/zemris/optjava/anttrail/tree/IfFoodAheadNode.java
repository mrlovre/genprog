package hr.fer.zemris.optjava.anttrail.tree;

import java.util.List;
import java.util.stream.Collectors;

import hr.fer.zemris.optjava.anttrail.ant.Ant;

public class IfFoodAheadNode extends NonTerminalNode {

    public IfFoodAheadNode(List<INode> children) {
        super(children);
    }

    @Override
    public void evaluate(Ant ant) {
        if (ant.foodAhead) {
            children.get(0).evaluate(ant);
        } else {
            children.get(1).evaluate(ant);
        }
    }

    public static int childrenSize() {
        return 2;
    }

    @Override
    public String getName() {
        return "IfFoodAhead";
    }

    public IfFoodAheadNode clone() {
        return new IfFoodAheadNode(
            children.stream().map(INode::clone).collect(Collectors.toList()));
    }

}
