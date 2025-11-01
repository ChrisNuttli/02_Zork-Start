package ch.bbw.zork.rooms;

import ch.bbw.zork.Direction;
import ch.bbw.zork.Tuple;
import ch.bbw.zork.furniture.Door;

public class Office extends Room {
	public Office(Tuple<Direction, Door> entrance) {
		super("office", 2, entrance, new Class[]{
                Bedroom.class,
                Corridor.class,
                DiningRoom.class,
                Livingroom.class,
        });
	}
}
