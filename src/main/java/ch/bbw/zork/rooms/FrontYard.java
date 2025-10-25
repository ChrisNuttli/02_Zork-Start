package ch.bbw.zork.rooms;

import ch.bbw.zork.Direction;
import ch.bbw.zork.Tuple;

public class FrontYard extends Room {
	public FrontYard() {
		super("front yard", new Direction[] { Direction.NORTH }, new Tuple<>(2, 0));
        this.addPotentialNeighbor(Direction.NORTH, Kitchen.class, Corridor.class);
	}
}
