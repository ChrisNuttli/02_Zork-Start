package ch.bbw.zork.rooms;

import ch.bbw.zork.Direction;
import ch.bbw.zork.RoomShape;
import ch.bbw.zork.Tuple;
import ch.bbw.zork.furniture.Door;
import ch.bbw.zork.interfaces.ExitSouth;

import java.util.HashSet;

import static ch.bbw.zork.Constants.*;

public class Attic extends Room {
    public Attic () {
        this(0,0,Direction.NORTH);
    }

	public Attic(int x, int y, Direction entranceDirection) {
		super("attic", ATTIC_SHAPE, new Class[]{
                Corridor.class,
                Livingroom.class,
        });
	}

    public static RoomShape getShape() {
        return ATTIC_SHAPE;
    }
}
