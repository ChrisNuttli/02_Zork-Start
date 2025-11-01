package ch.bbw.zork.rooms;

import ch.bbw.zork.Direction;
import ch.bbw.zork.Tuple;
import ch.bbw.zork.furniture.Door;
import ch.bbw.zork.interfaces.ExitWest;

public class Bathroom extends Room {
	public Bathroom(Tuple<Direction, Door> entrance) {
		super("bathroom", 1, entrance, new Class[]{
                Bedroom.class,
                Corridor.class,
                Livingroom.class,
        });
	}
}
