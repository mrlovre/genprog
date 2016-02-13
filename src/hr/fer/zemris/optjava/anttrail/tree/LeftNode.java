package hr.fer.zemris.optjava.anttrail.tree;

import hr.fer.zemris.optjava.anttrail.action.Action;

public class LeftNode extends TerminalNode {

    public LeftNode() {
        super(Action.getLeftActionInstance());
    }
    
    @Override
    public String getName() {
        return "Left";
    }
    
}
