package ch.bbw.zork.rooms;

import ch.bbw.zork.Direction;
import ch.bbw.zork.Tuple;

public class Bedroom extends Room {
	public Bedroom(Tuple<Integer, Integer> coordinates) {
		super("bedroom", new Direction[]{Direction.EAST, Direction.SOUTH}, coordinates);
        super.addPotentialNeighbor(Direction.EAST, Office.class, Bathroom.class);
        super.addPotentialNeighbor(Direction.SOUTH, Livingroom.class, Corridor.class);
	}
}
