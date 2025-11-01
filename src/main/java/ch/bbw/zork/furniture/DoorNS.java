package ch.bbw.zork.furniture;

import ch.bbw.zork.Direction;
import ch.bbw.zork.interfaces.ExitNorth;
import ch.bbw.zork.interfaces.ExitSouth;
import ch.bbw.zork.rooms.Room;

public class DoorNS extends Door implements ExitNorth, ExitSouth {
    public DoorNS(Direction dir, Room roomA) {
        super(dir, roomA);
    }

    @Override
    public void exitNorth() {

    }

    @Override
    public void exitSouth() {

    }
}
