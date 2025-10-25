package ch.bbw.zork.rooms;

import ch.bbw.zork.Direction;
import ch.bbw.zork.Tuple;

public class Basement extends Room {
	public Basement(Tuple<Integer, Integer> coordinates) {
		super("basement", new Direction[] { Direction.EAST }, coordinates);
        super.addPotentialNeighbor(Direction.EAST, Corridor.class, Kitchen.class);
	}
}
