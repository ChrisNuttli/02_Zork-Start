package ch.bbw.zork.furniture;

import ch.bbw.zork.Direction;
import ch.bbw.zork.House;
import ch.bbw.zork.Tuple;
import ch.bbw.zork.interfaces.Lockable;
import ch.bbw.zork.rooms.*;

import java.util.*;

public abstract class Door extends Furniture implements Lockable {
    private Room roomA;
    private Room roomB;
    private Direction dirA;
    private Direction dirB;

    private boolean isLocked;

    /**
     * Instantiates a door with one room already present.
     * @param dirA The direction of where the provided room is located in relation to the door
     * @param roomA The room from where the door is being instantiated from
     */
    public Door(Direction dirA, Room roomA) {
        super("door", "");
        this.isLocked = false;

        this.dirA = dirA;
        this.roomA = roomA;
        this.dirB = dirA.getOpposite();
        generateRoom();
    }

    private void generateRoom() {
        House house = House.getInstance();
        Tuple<Direction, Door> roomEntrance = new Tuple<>(this.dirA.getOpposite(), this);
        Class<? extends Room>[] validRooms = this.roomA.getValidNeighbors();
        HashSet<Class<? extends Room>> roomTypes = new HashSet<>();


        for (Class<? extends Room> roomType : house.getUnusedRoomTypes()) {
            if (Arrays.asList(validRooms).contains(roomType)) {
                roomTypes.add(roomType);
            }
        }

        if (roomTypes.isEmpty()) {
            this.roomB = new Corridor(roomEntrance);
            return;
        }

        Random rand = new Random();
        int index = rand.nextInt(roomTypes.size());
        Class roomClass = roomTypes.toArray(new Class[0])[index];

        switch(roomClass.getSimpleName()) {
            case "Attic":
                this.roomB = new Attic(new Tuple<>(dirB, this));
                break;
            case "Basement":
                this.roomB = new Basement(new Tuple<>(dirB, this));
                break;
            case "Bathroom":
                this.roomB = new Bathroom(new Tuple<>(dirB, this));
                break;
            case "Bedroom":
                this.roomB = new Bedroom(new Tuple<>(dirB, this));
                break;
            case "Cellar":
                this.roomB = new Cellar(new Tuple<>(dirB, this));
                break;
            case "DiningRoom":
                this.roomB = new DiningRoom(new Tuple<>(dirB, this));
                break;
            case "Kitchen":
                this.roomB = new Kitchen(new Tuple<>(dirB, this));
                break;
            case "Livingroom":
                this.roomB = new Livingroom(new Tuple<>(dirB, this));
                break;
            case "Office":
                this.roomB = new Office(new Tuple<>(dirB, this));
                break;
        }

//        this.roomB.generateDoors();
    }

    public Room getRoom(Direction direction) {
        if (this instanceof DoorNS) {
            switch (direction) {
                case NORTH:
                case EAST:
                    return roomA;
                case SOUTH:
                case WEST:
                    return roomB;
            }
        }
        else {
            switch (direction) {
                case NORTH:
                case EAST:
                    return roomB;
                case SOUTH:
                case WEST:
                    return roomA;
            }
        }

        return null;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }
}
