package ch.bbw.zork.rooms;

import ch.bbw.zork.Direction;
import ch.bbw.zork.RoomShape;
import ch.bbw.zork.Tuple;
import ch.bbw.zork.furniture.Door;
import ch.bbw.zork.interfaces.ExitEast;

import static ch.bbw.zork.Constants.*;

public class Basement extends Room {
    public Basement () {
        this(0,0,Direction.NORTH);
    }
	public Basement(int x, int y, Direction entranceDirection) {
		super("basement", BASEMENT_SHAPE, new Class[]{
                Corridor.class,
                Kitchen.class,
        });
	}

    public static RoomShape getShape() {
        return BASEMENT_SHAPE;
    }
}
