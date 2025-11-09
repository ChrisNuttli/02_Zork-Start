package ch.bbw.zork.rooms;

import ch.bbw.zork.Direction;
import ch.bbw.zork.RoomShape;
import ch.bbw.zork.Tuple;
import ch.bbw.zork.furniture.Door;

import static ch.bbw.zork.Constants.*;

public class Office extends Room {
    public Office () {
        this(0,0,Direction.NORTH);
    }

	public Office(int x, int y, Direction entranceDirection) {
		super("office", OFFICE_SHAPE, new Class[]{
                Bedroom.class,
                Corridor.class,
                DiningRoom.class,
                Livingroom.class,
        });
	}

    public static RoomShape getShape() {
        return OFFICE_SHAPE;
    }
}
