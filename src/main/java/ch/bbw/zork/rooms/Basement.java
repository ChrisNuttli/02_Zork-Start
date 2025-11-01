package ch.bbw.zork.rooms;

import ch.bbw.zork.Direction;
import ch.bbw.zork.Tuple;
import ch.bbw.zork.furniture.Door;
import ch.bbw.zork.interfaces.ExitEast;

public class Basement extends Room {
	public Basement(Tuple<Direction, Door> entrance) {
		super("basement", 1, entrance, new Class[]{
                Corridor.class,
                Kitchen.class,
        });
	}
}
