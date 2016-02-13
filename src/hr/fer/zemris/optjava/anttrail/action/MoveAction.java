package hr.fer.zemris.optjava.anttrail.action;

import hr.fer.zemris.optjava.anttrail.ant.Ant;

public class MoveAction extends Action {
    
    MoveAction() {};

    @Override
    public void execute(Ant ant) {
        ant.updatePosition(1);
    }

}
