package ch.bbw.zork.furniture;

import ch.bbw.zork.Direction;
import ch.bbw.zork.Tuple;
import ch.bbw.zork.interfaces.Lockable;
import ch.bbw.zork.rooms.Room;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;

public abstract class Door extends Furniture implements Lockable {
	private Room roomA; // The room to the north or east
    private Room roomB; // The room to the south or west

    public Door() {
		super("door", "");
	}

    public void setRoomA(Room roomA) {
        if (roomA != this.roomB) {
            this.roomA = roomA;
        }
    }

    public void setRoomB(Room roomB) {
        if (roomB != this.roomA) {
            this.roomB = roomB;
        }
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
}
