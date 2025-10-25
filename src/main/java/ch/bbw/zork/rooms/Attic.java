package ch.bbw.zork.rooms;

import ch.bbw.zork.Direction;
import ch.bbw.zork.Tuple;

public class Attic extends Room {
	public Attic(Tuple<Integer, Integer> coordinates) {
		super("attic", new Direction[] { Direction.SOUTH }, coordinates);
        super.addPotentialNeighbor(Direction.SOUTH, Corridor.class, Livingroom.class);
	}
}
