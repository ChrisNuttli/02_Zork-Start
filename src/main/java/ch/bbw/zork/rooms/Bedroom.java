package ch.bbw.zork.rooms;

import ch.bbw.zork.Direction;
import ch.bbw.zork.Tuple;
import ch.bbw.zork.furniture.Door;
import ch.bbw.zork.interfaces.ExitEast;
import ch.bbw.zork.interfaces.ExitSouth;

public class Bedroom extends Room {
	public Bedroom(Tuple<Direction, Door> entrance) {
		super("bedroom", 2, entrance, new Class[]{
                Bathroom.class,
                Corridor.class,
                Livingroom.class,
                Office.class
        });
	}
}
