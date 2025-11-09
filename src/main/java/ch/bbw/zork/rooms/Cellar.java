package ch.bbw.zork.rooms;

import ch.bbw.zork.Direction;
import ch.bbw.zork.RoomShape;
import ch.bbw.zork.Tuple;
import ch.bbw.zork.furniture.Door;
import ch.bbw.zork.interfaces.ExitNorth;

import static ch.bbw.zork.Constants.*;

public class Cellar extends Room {
    public Cellar () {
        this(0,0,Direction.NORTH);
    }
	public Cellar(int x, int y, Direction entranceDirection) {
		super("cellar", CELLAR_SHAPE, new Class[]{
                Corridor.class,
                Kitchen.class,
        });
	}

    public static RoomShape getShape() {
        return CELLAR_SHAPE;
    }
}
