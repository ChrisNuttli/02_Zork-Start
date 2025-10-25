package ch.bbw.zork.rooms;

import ch.bbw.zork.Direction;
import ch.bbw.zork.Tuple;

public class Bathroom extends Room {
	public Bathroom(Tuple<Integer, Integer> coordinates) {
		super("bathroom", new Direction[]{ Direction.WEST}, coordinates);
        super.addPotentialNeighbor(Direction.EAST, Livingroom.class, Bedroom.class, Corridor.class);
	}
}
