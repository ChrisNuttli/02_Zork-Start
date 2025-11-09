package ch.bbw.zork.rooms;

import ch.bbw.zork.Direction;
import ch.bbw.zork.RoomShape;
import ch.bbw.zork.Tuple;
import ch.bbw.zork.furniture.Door;
import ch.bbw.zork.interfaces.ExitEast;
import ch.bbw.zork.interfaces.ExitSouth;
import ch.bbw.zork.interfaces.ExitWest;

import static ch.bbw.zork.Constants.*;

public class Kitchen extends Room{
    public Kitchen () {
        this(0,0,Direction.NORTH);
    }

	public Kitchen(int x, int y, Direction entranceDirection) {
		super("kitchen", KITCHEN_SHAPE, new Class[]{
                Basement.class,
                Cellar.class,
                Corridor.class,
                DiningRoom.class,
                FrontYard.class,
                Livingroom.class,
        });
	}

    public static RoomShape getShape() {
        return KITCHEN_SHAPE;
    }
}
