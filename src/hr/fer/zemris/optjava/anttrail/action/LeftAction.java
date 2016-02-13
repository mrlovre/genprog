package hr.fer.zemris.optjava.anttrail.action;

import hr.fer.zemris.optjava.anttrail.ant.Ant;

public class LeftAction extends Action {

    LeftAction() {};

    @Override
    public void execute(Ant ant) {
        ant.updateDirection(-1);
    }

}
