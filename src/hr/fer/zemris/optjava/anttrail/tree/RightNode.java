package hr.fer.zemris.optjava.anttrail.tree;

import hr.fer.zemris.optjava.anttrail.action.Action;

public class RightNode extends TerminalNode {
    
    public RightNode() {
        super(Action.getRightActionInstance());
    }
    
    @Override
    public String getName() {
        return "Right";
    }
    
}
