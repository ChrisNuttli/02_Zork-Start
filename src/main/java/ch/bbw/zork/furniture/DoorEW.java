package ch.bbw.zork.furniture;

import ch.bbw.zork.Direction;
import ch.bbw.zork.interfaces.ExitEast;
import ch.bbw.zork.interfaces.ExitWest;
import ch.bbw.zork.rooms.Room;

public class DoorEW extends Door implements ExitEast, ExitWest {
    public DoorEW(Direction dir, Room roomA) {
        super(dir, roomA);
    }

    @Override
    public void exitEast() {

    }

    @Override
    public void exitWest() {

    }
}
