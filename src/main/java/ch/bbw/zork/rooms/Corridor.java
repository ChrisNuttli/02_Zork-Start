package ch.bbw.zork.rooms;

import ch.bbw.zork.Direction;
import ch.bbw.zork.Tuple;
import ch.bbw.zork.furniture.Door;

import java.util.HashSet;

public class Corridor extends Room {
    public Corridor(Tuple<Direction, Door> entrance) {
        super("Corridor", entrance, new Class[]{
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
}
