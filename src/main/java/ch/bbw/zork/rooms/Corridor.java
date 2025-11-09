package ch.bbw.zork.rooms;

import ch.bbw.zork.Direction;
import ch.bbw.zork.RoomShape;
import ch.bbw.zork.Tuple;
import ch.bbw.zork.furniture.Door;

import java.util.HashSet;

import static ch.bbw.zork.Constants.*;

public class Corridor extends Room {
    public Corridor () {
        this(0,0,Direction.NORTH);
    }

    public Corridor(int x, int y, Direction entranceDirection) {
        super("Corridor", CORRIDOR_SHAPE, new Class[]{
                Attic.class,
                Basement.class,
                Bathroom.class,
                Bedroom.class,
                Cellar.class,
                Corridor.class,
                DiningRoom.class,
                FrontYard.class,
                Kitchen.class,
                Livingroom.class,
                Office.class
        });
    }

    public static RoomShape getShape() {
        return CORRIDOR_SHAPE;
    }
}
