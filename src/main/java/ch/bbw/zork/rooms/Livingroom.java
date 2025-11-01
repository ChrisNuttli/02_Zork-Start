package ch.bbw.zork.rooms;

import ch.bbw.zork.Direction;
import ch.bbw.zork.Tuple;
import ch.bbw.zork.furniture.Door;
import ch.bbw.zork.interfaces.ExitEast;
import ch.bbw.zork.interfaces.ExitNorth;
import ch.bbw.zork.interfaces.ExitWest;

public class Livingroom extends Room{
	public Livingroom(Tuple<Direction, Door> entrance) {
		super("living room", 3, entrance, new Class[]{
                Attic.class,
                Bathroom.class,
                Bedroom.class,
                Corridor.class,
                DiningRoom.class,
                Kitchen.class,
                Office.class
        });
	}
}
