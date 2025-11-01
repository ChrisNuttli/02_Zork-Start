package ch.bbw.zork.rooms;

import ch.bbw.zork.Direction;
import ch.bbw.zork.Tuple;
import ch.bbw.zork.furniture.Door;
import ch.bbw.zork.interfaces.ExitNorth;

public class Cellar extends Room {
	public Cellar(Tuple<Direction, Door> entrance) {
		super("cellar", 1, entrance, new Class[]{
                Corridor.class,
                Kitchen.class,
        });
	}
}
