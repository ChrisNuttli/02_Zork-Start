package ch.bbw.zork.furniture;

import ch.bbw.zork.Direction;
import ch.bbw.zork.Tuple;
import ch.bbw.zork.interfaces.Lockable;
import ch.bbw.zork.rooms.Room;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;

public class Door extends Furniture implements Lockable {
    private final Tuple<Direction, Room> roomA;
    private final Tuple<Direction, Room> roomB;

	public Door(Tuple<Direction, Room> roomA, Tuple<Direction, Room> roomB) {
		super("door", "");
        this.roomA = roomA;
        this.roomB = roomB;
	}

    public Room getRoom(Direction direction) {
        if (direction != roomA.first && direction != roomB.first) {
            throw new IllegalArgumentException("Invalid direction");
        }
        else if (direction == roomA.first) {
            return roomA.second;
        }
        else {
            return roomB.second;
        }
    }
}
