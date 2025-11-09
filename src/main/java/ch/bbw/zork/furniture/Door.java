package ch.bbw.zork.furniture;

import ch.bbw.zork.Direction;
import ch.bbw.zork.House;
import ch.bbw.zork.Tuple;
import ch.bbw.zork.interfaces.Lockable;
import ch.bbw.zork.rooms.*;

import java.util.*;

public abstract class Door extends Furniture implements Lockable {
    private Room roomA;
    private Room roomB;
    private Direction dirA;
    private Direction dirB;

    private boolean isLocked;

    /**
     * Instantiates a door with one room already present.
     * @param dirA The direction of where the provided room is located in relation to the door
     * @param roomA The room from where the door is being instantiated from
     */
    public Door(Direction dirA, Room roomA) {
        super("door", "");
        this.isLocked = false;

        this.dirA = dirA;
        this.roomA = roomA;
        this.dirB = dirA.getOpposite();
    }

    public Room getRoom(Direction direction) {
        if (this instanceof DoorNS) {
            switch (direction) {
                case NORTH:
                case EAST:
                    return roomA;
                case SOUTH:
                case WEST:
                    return roomB;
            }
        }
        else {
            switch (direction) {
                case NORTH:
                case EAST:
                    return roomB;
                case SOUTH:
                case WEST:
                    return roomA;
            }
        }

        return null;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }
}
