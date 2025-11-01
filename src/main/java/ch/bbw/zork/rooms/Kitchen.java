package ch.bbw.zork.rooms;

import ch.bbw.zork.Direction;
import ch.bbw.zork.Tuple;
import ch.bbw.zork.furniture.Door;
import ch.bbw.zork.interfaces.ExitEast;
import ch.bbw.zork.interfaces.ExitSouth;
import ch.bbw.zork.interfaces.ExitWest;

public class Kitchen extends Room{
	public Kitchen(Tuple<Direction, Door> entrance) {
		super("kitchen", 3, entrance, new Class[]{
                Basement.class,
                Cellar.class,
                Corridor.class,
                DiningRoom.class,
                FrontYard.class,
                Livingroom.class,
        });
	}
}
