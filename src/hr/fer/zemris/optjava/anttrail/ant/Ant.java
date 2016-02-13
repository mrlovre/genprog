package hr.fer.zemris.optjava.anttrail.ant;

import java.util.ArrayList;
import java.util.List;

public class Ant {

    private int                    direction;
    private Position               pos                = new Position(0, 0);
    public boolean                 foodAhead          = false;

    public List<AntChangeObserver> antChangeObservers = new ArrayList<>();

    public void updateDirection(int d) {
        direction = (direction + d + 4) % 4;
        fireAntChanged();
    }

    public Position forwardPosition() {
        switch (direction) {
        case 0:
            return new Position(pos.x + 1, pos.y);
        case 1:
            return new Position(pos.x, pos.y + 1);
        case 2:
            return new Position(pos.x - 1, pos.y);
        case 3:
            return new Position(pos.x, pos.y - 1);
        default:
            return null;
        }
    }

    public void updatePosition(int d) {
        switch (direction) {
        case 0:
            pos.x += d;
            break;
        case 1:
            pos.y += d;
            break;
        case 2:
            pos.x -= d;
            break;
        case 3:
            pos.y -= d;
            break;
        default:
        }
        fireAntChanged();
    }
    
    public void reset() {
        pos.x = 0;
        pos.y = 0;
        direction = 0;
        fireAntChanged();
    }

    public Position getPos() {
        return pos;
    }
    
    public int getDirection() {
        return direction;
    }

    public void addAntChangeObserver(AntChangeObserver observer) {
        antChangeObservers.add(observer);
        observer.onAntChanged(this);
    }

    public void fireAntChanged() {
        antChangeObservers.stream().forEach(t -> t.onAntChanged(this));
    }

}
