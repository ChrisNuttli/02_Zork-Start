package ch.bbw.zork.rooms;

import ch.bbw.zork.Direction;
import ch.bbw.zork.Tuple;
import ch.bbw.zork.furniture.Door;
import ch.bbw.zork.furniture.DoorEW;
import ch.bbw.zork.furniture.DoorNS;
import ch.bbw.zork.interfaces.ExitNorth;

import java.util.HashSet;
import java.util.Random;

public class FrontYard extends Room {
	public FrontYard() {
		super("front yard", 1, new Class[]{
                Corridor.class,
                Kitchen.class,
        });

        Direction dir = null;
        Door door = null;

        if (this.getCoordinates().first == 0 && this.getCoordinates().second == 2) {
            dir = Direction.NORTH;
            door = new DoorNS(Direction.SOUTH, this.get());
        }
        else if (this.getCoordinates().first == 2 && this.getCoordinates().second == 4) {
            dir = Direction.EAST;
            door = new DoorEW(Direction.WEST, this.get());
        }
        else if (this.getCoordinates().first == 4 && this.getCoordinates().second == 2) {
            dir = Direction.SOUTH;
            door = new DoorNS(Direction.NORTH, this.get());
        }
        else if (this.getCoordinates().first == 2 && this.getCoordinates().second == 0) {
            dir = Direction.WEST;
            door = new DoorEW(Direction.EAST, this.get());
        }

        if (dir == null) {
            throw new RuntimeException("Unable to generate initial door!");
        }

        this.addDoor(dir, door);
	}

    @Override
    public void generateCoordinates() {
        Random rand = new Random();
        Direction dir = Direction.values()[rand.nextInt(Direction.values().length)];
        Tuple<Integer, Integer> coordinates;
        switch (dir) {
            case EAST:
                coordinates = new Tuple<>(2, 4);
                break;
            case SOUTH:
                coordinates = new Tuple<>(4, 2);
                break;
            case WEST:
                coordinates = new Tuple<>(2, 0);
                break;
            case NORTH:
            default:
                coordinates = new Tuple<>(0, 2);
                break;
        }

        this.setCoordinates(coordinates);
    }
}
