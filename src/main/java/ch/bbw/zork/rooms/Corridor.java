package ch.bbw.zork.rooms;

import ch.bbw.zork.Direction;
import ch.bbw.zork.Tuple;

public class Corridor extends Room {
    public Corridor(Tuple<Integer, Integer> coordinates) {
        super("Corridor", new Direction[]{Direction.NORTH,  Direction.EAST, Direction.SOUTH, Direction.NORTH}, coordinates);
        this.addPotentialNeighbor(Direction.NORTH, Kitchen.class, Bedroom.class, Attic.class, Office.class);
        this.addPotentialNeighbor(Direction.EAST, Kitchen.class, Bathroom.class, Livingroom.class);
        this.addPotentialNeighbor(Direction.SOUTH, FrontYard.class, Cellar.class);
        this.addPotentialNeighbor(Direction.WEST, Livingroom.class, Basement.class);
    }
}
