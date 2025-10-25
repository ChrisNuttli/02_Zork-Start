package ch.bbw.zork.rooms;

import ch.bbw.zork.Direction;
import ch.bbw.zork.Tuple;

public class Cellar extends Room {
	public Cellar(Tuple<Integer, Integer> coordinates) {
		super("cellar", new Direction[]{Direction.NORTH}, coordinates);
        super.addPotentialNeighbor(Direction.NORTH, Corridor.class, Kitchen.class);
	}
}
