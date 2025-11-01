package ch.bbw.zork.rooms;

import ch.bbw.zork.Direction;
import ch.bbw.zork.Tuple;
import ch.bbw.zork.furniture.Door;
import ch.bbw.zork.interfaces.ExitSouth;

import java.util.HashSet;

public class Attic extends Room {
	public Attic(Tuple<Direction, Door> entrance) {
		super("attic", 1, entrance, new Class[]{
                Corridor.class,
                Livingroom.class,
        });
	}
}
