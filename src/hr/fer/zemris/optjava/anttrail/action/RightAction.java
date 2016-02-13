package hr.fer.zemris.optjava.anttrail.action;

import hr.fer.zemris.optjava.anttrail.ant.Ant;

public class RightAction extends Action {
    
    RightAction() {};
    
    @Override
    public void execute(Ant ant) {
        ant.updateDirection(1);
    }
    
}
