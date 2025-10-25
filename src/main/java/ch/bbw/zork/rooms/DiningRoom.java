package ch.bbw.zork.rooms;

import ch.bbw.zork.Direction;
import ch.bbw.zork.Tuple;

public class DiningRoom extends Room {
	public DiningRoom(Tuple<Integer, Integer> coordinates) {
		super("dining room", new Direction[]{Direction.NORTH, Direction.EAST, Direction.WEST}, coordinates);
        super.addPotentialNeighbor(Direction.NORTH, Attic.class, Office.class, Bedroom.class);
        super.addPotentialNeighbor(Direction.EAST, Bathroom.class, Corridor.class);
        super.addPotentialNeighbor(Direction.WEST, Corridor.class, Kitchen.class, DiningRoom.class);
	}
}
