package ch.bbw.zork.rooms;

import ch.bbw.zork.Direction;
import ch.bbw.zork.RoomShape;
import ch.bbw.zork.Tuple;
import ch.bbw.zork.furniture.Door;
import ch.bbw.zork.interfaces.ExitWest;

import static ch.bbw.zork.Constants.*;

public class Bathroom extends Room {
    public Bathroom () {
        this(0,0,Direction.NORTH);
    }

	public Bathroom(int x, int y, Direction entranceDirection) {
		super("bathroom", BATHROOM_SHAPE, new Class[]{
                Bedroom.class,
                Corridor.class,
                Livingroom.class,
        });
	}

    public static RoomShape getShape() {
        return BATHROOM_SHAPE;
    }
}
