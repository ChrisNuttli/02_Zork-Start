package ch.bbw.zork.rooms;

import ch.bbw.zork.Direction;
import ch.bbw.zork.RoomShape;
import ch.bbw.zork.Tuple;
import ch.bbw.zork.furniture.Door;
import ch.bbw.zork.interfaces.ExitEast;
import ch.bbw.zork.interfaces.ExitNorth;
import ch.bbw.zork.interfaces.ExitWest;

import static ch.bbw.zork.Constants.*;

public class Livingroom extends Room{
    public Livingroom () {
        this(0,0,Direction.NORTH);
    }

	public Livingroom(int x, int y, Direction entranceDirection) {
		super("living room", LIVING_ROOM_SHAPE, new Class[]{
                Attic.class,
                Bathroom.class,
                Bedroom.class,
                Corridor.class,
                DiningRoom.class,
                Kitchen.class,
                Office.class
        });
	}

    public static RoomShape getShape() {
        return LIVING_ROOM_SHAPE;
    }
}
