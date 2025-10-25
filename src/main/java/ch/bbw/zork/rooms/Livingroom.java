package ch.bbw.zork.rooms;

import ch.bbw.zork.Direction;
import ch.bbw.zork.Tuple;

public class Livingroom extends Room {
	public Livingroom(Tuple<Integer, Integer> coordinates) {
		super("living room", new Direction[]{ Direction.EAST, Direction.WEST}, coordinates);
        this.addPotentialNeighbor(Direction.EAST, Office.class, Livingroom.class);
        this.addPotentialNeighbor(Direction.WEST, Kitchen.class);
	}
}
