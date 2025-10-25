package ch.bbw.zork.rooms;

import ch.bbw.zork.Direction;
import ch.bbw.zork.Tuple;

public class Kitchen extends Room {
	public Kitchen(Tuple<Integer, Integer> coordinates) {
		super("kitchen", new Direction[]{ Direction.EAST, Direction.SOUTH, Direction.WEST }, coordinates);
        this.addPotentialNeighbor(Direction.EAST, DiningRoom.class, Livingroom.class);
        this.addPotentialNeighbor(Direction.SOUTH, FrontYard.class, Cellar.class, Corridor.class);
        this.addPotentialNeighbor(Direction.WEST, Corridor.class, Basement.class);
	}
}
