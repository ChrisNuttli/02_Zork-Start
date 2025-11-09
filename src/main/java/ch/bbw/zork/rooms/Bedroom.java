package ch.bbw.zork.rooms;

import ch.bbw.zork.Direction;
import ch.bbw.zork.RoomShape;
import ch.bbw.zork.Tuple;
import ch.bbw.zork.furniture.Door;
import ch.bbw.zork.interfaces.ExitEast;
import ch.bbw.zork.interfaces.ExitSouth;
import static ch.bbw.zork.Constants.*;
public class Bedroom extends Room {
    public Bedroom () {
        this(0,0,Direction.NORTH);
    }

	public Bedroom(int x, int y, Direction entranceDirection) {
		super("bedroom", BEDROOM_SHAPE, new Class[]{
                Bathroom.class,
                Corridor.class,
                Livingroom.class,
                Office.class
        });
	}

    public static RoomShape getShape() {
        return BEDROOM_SHAPE;
    }
}
