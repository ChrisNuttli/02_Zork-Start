package ch.bbw.zork.rooms;

import ch.bbw.zork.Direction;
import ch.bbw.zork.Tuple;
import ch.bbw.zork.furniture.Door;
import ch.bbw.zork.interfaces.ExitEast;
import ch.bbw.zork.interfaces.ExitWest;

public class DiningRoom extends Room {
	public DiningRoom(Tuple<Direction, Door> entrance) {
		super("dining room", 2, entrance, new Class[]{
                Kitchen.class,
                Livingroom.class,
                Office.class
        });
	}
}
