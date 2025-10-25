package ch.bbw.zork.rooms;

import ch.bbw.zork.Direction;
import ch.bbw.zork.Tuple;

public class Office extends Room {
	public Office(Tuple<Integer, Integer> coordinates) {
		super("office", new Direction[]{ Direction.SOUTH, Direction.WEST}, coordinates);
        this.addPotentialNeighbor(Direction.SOUTH, Livingroom.class, Corridor.class);
        this.addPotentialNeighbor(Direction.WEST, Bedroom.class, DiningRoom.class);
	}
}
