package ch.bbw.zork.interfaces;

import ch.bbw.zork.Door;
import ch.bbw.zork.enums.Direction;

public interface Passage {
    public Door getTransition(Direction dir);
    public void setTransition(Direction dir, Door door);
}
