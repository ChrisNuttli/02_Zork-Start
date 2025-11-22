package ch.bbw.zork.interfaces;

import ch.bbw.zork.Transition;
import ch.bbw.zork.enums.Direction;

public interface Passage {
    public Transition getTransition(Direction dir);
    public void setTransition(Direction dir, Transition transition);
}
