package ch.bbw.zork.rooms;

import ch.bbw.zork.Direction;
import ch.bbw.zork.RoomShape;
import ch.bbw.zork.Tuple;
import ch.bbw.zork.furniture.Door;
import ch.bbw.zork.interfaces.ExitEast;
import ch.bbw.zork.interfaces.ExitWest;
import static ch.bbw.zork.Constants.*;

public class DiningRoom extends Room {
    public DiningRoom () {
        this(0,0,Direction.NORTH);
    }

	public DiningRoom(int x, int y, Direction entranceDirection) {
		super("dining room", DINING_ROOM_SHAPE, new Class[]{
                Kitchen.class,
                Livingroom.class,
                Office.class
        });
	}

    public static RoomShape getShape() {
        return DINING_ROOM_SHAPE;
    }
}
