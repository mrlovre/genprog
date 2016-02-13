package hr.fer.zemris.optjava.anttrail.tree;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.optjava.anttrail.action.Action;
import hr.fer.zemris.optjava.anttrail.ant.Ant;

public abstract class TerminalNode implements INode {

    private Action action;

    protected TerminalNode(Action action) {
        this.action = action;
    }

    @Override
    public void evaluate(Ant ant) {
        action.execute(ant);
    }

    public static List<Class<? extends TerminalNode>> terminalNodes = new ArrayList<>();
    
    @Override
    public String toString() {
        return getName();
    }
    
    @Override
    public int queryDepth() {
        return 1;
    }
    
    @Override
    public TerminalNode clone() {
        return this;
    }
}
