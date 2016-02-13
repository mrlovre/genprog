package hr.fer.zemris.optjava.anttrail.tree;

import java.util.List;

public class Prog2Node extends NonTerminalNode {
    
    public Prog2Node(List<INode> children) {
        super(children);
    }
    
    public static int childrenSize() {
        return 2;
    }
    
    @Override
    public String getName() {
        return "Prog2";
    }
    
}
