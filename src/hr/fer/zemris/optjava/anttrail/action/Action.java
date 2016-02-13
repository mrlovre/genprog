package hr.fer.zemris.optjava.anttrail.action;

import hr.fer.zemris.optjava.anttrail.ant.Ant;

public abstract class Action {

    public abstract void execute(Ant ant);

    private static Action leftActionInstance  = new LeftAction();
    private static Action rightActionInstance = new RightAction();
    private static Action moveActionInstance  = new MoveAction();

    public static Action getLeftActionInstance() {
        return leftActionInstance;
    }

    public static Action getRightActionInstance() {
        return rightActionInstance;
    }

    public static Action getMoveActionInstance() {
        return moveActionInstance;
    }

}