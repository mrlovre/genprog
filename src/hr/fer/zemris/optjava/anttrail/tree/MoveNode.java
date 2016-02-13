package hr.fer.zemris.optjava.anttrail.tree;

import hr.fer.zemris.optjava.anttrail.action.Action;

public class MoveNode extends TerminalNode {

    public MoveNode() {
        super(Action.getMoveActionInstance());
    }

    public String getName() {
        return "Move";
    }

}
