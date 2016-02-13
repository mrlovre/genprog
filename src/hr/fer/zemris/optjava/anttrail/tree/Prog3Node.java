package hr.fer.zemris.optjava.anttrail.tree;

import java.util.List;

public class Prog3Node extends NonTerminalNode {
    
    public Prog3Node(List<INode> children) {
        super(children);
    }
    
    public static int childrenSize() {
        return 3;
    }
    
    @Override
    public String getName() {
        return "Prog3";
    }
    
}
